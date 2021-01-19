package media.kitchen.parkour.itemtype.parkour;

import media.kitchen.parkour.Parkour;
import media.kitchen.parkour.itemtype.nbthandles.ItemData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AquaParkour extends ParkourBase {

    private final String dashTag = "kdas";

    private final int maxDash = 9;

    // constructors

    public AquaParkour(final double xz, final double y) {
        super(xz, y);
        setup();
    }

    public AquaParkour(final double universalMultiplier) {
        super(universalMultiplier);
        setup();
    }

    public AquaParkour() {
        super();
        setup();
    }

    public AquaParkour(Item.Properties props) {
        super(props);
        setup();
    }

    // constructor helpers

    public AquaParkour setElytraFlyer() {
        isElytraFlyer = true;
        return this;
    }

    public AquaParkour setCooldownTime(int counts) {
        maxCooldown = counts;
        return this;
    }

    public AquaParkour setWallLeapTime(int counts) {
        maxWallLeap = counts;
        return this;
    }

    public AquaParkour setChargeTime(int counts) {
        maxCharge = counts;
        return this;
    }

    protected void setup() {
        leapSound = new AmbigSoundType(Parkour.AQUA_PARKOUR_DASH);
    }

    // !constructor helpers

    // !constructors

    // aqua boost

    /**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);

        // lingering speed boost
        if ( entityIn instanceof PlayerEntity ) {
            if ( isSelected && entityIn.isInWater() ) {
                PlayerEntity player = (PlayerEntity) entityIn;
                int dash = ItemData.getNBTInt(stack, dashTag);
                if ( dash == -1 ) dash = 0;
                if ( dash > 0 ) {
                    --dash;
                    Vec3d path = Vec3d.fromPitchYaw(player.rotationPitch, player.rotationYaw);
                    float dfn = 180 - player.rotationYaw;
                    int sign = getSign(dfn);
                    player.addVelocity(multiplierXZ * .05 * path.x, multiplierY * 0.05 * path.y,
                            multiplierXZ * .05 * path.z);
                    ItemData.setNBTInt(stack, dashTag, dash);
                }
            }

        }
        // !lingering speed boost

    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link #onItemUse}.
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World e, PlayerEntity player, Hand handIn) {

        ItemStack stack = player.getHeldItem(handIn);

        player.getAdjustedHorizontalFacing();
        // get nbt data
        int     cooldown = ItemData.getNBTInt(stack, cooldownTag),
                wallLeap = ItemData.getNBTInt(stack, wallLeapTag),
                charge   = ItemData.getNBTInt(stack, chargeTag),
                ready    = ItemData.getNBTInt(stack, readyTag);

        if (cooldown == -1) cooldown = maxCooldown;
        if (wallLeap == -1) wallLeap = maxWallLeap;
        if (charge   == -1) charge   = maxCharge;
        if (ready    == -1) ready    = maxReady;
        // !get nbt data
        if (ready == 0) {
            // code to jump, wall leap, etc.
            short glide = countWalls(player, e);
            if (cooldown == 0 && player.isInWater() && !player.abilities.isFlying) {
                cooldown = maxCooldown;
                if (player.isAirBorne && charge == 0 && glide > 1) {
                    // walljump hook
                    this.wallLeapTriggered(e, player, handIn);
                } else if (isElytraFlyer || !player.isElytraFlying()) {
                    this.leapTriggered(e, player, handIn);
                    // damage itemstack serverside
                    if(!e.isRemote() && player instanceof ServerPlayerEntity) {
                        if (stack.attemptDamageItem(5, rand,  (ServerPlayerEntity) player)) {
                            stack.shrink(1);
                        }
                    }
                }
            }
            // !code above
            // efficiently sync NBT
            ItemData.setNBTInt(stack, cooldownTag, cooldown);
            //setNBTInt(stack, wallLeapTag, wallLeap); // read-only
            //setNBTInt(stack, chargeTag, charge);     // read-only

            return ActionResult.resultSuccess(stack);
        } else {
            return ActionResult.resultFail(stack);
        }
    }

    /**
     * event hook for leaping
     *
     * @param world
     * @param player
     * @param hand
     */
    protected void leapTriggered(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        Vec3d path = Vec3d.fromPitchYaw(player.rotationPitch, player.rotationYaw);
        float dfn = 180 - player.rotationYaw;
        int sign = getSign(dfn);
        player.addVelocity(multiplierXZ * .3 * path.x, multiplierY * 0.3 * path.y,
                multiplierXZ * .3 * path.z);
        world.playSound(null, new BlockPos(player), leapSound.getSound(), SoundCategory.AMBIENT, 1F, 1F + ( Item.random.nextFloat() * 0.4F ) - 0.2F );
        ItemData.setNBTInt(stack, dashTag, maxDash);
        // set player position
        player.setSwimming(true);
        // return air
        this.returnAir(world, player, hand);
    }

    // return air

    private void returnAir(World world, PlayerEntity player, Hand hand) {
        for ( int a = 0; a < 4 && player.getAir() + 15 < player.getMaxAir(); ++a ) {
            player.setAir( player.getAir() + 15 );
        }
    }

    // !return air

    // !aqua boost

    // remove wall effects

    /**
     * event hook for wall leaping
     *
     * @param world
     * @param player
     * @param hand
     */
    @Override
    protected void wallLeapTriggered(World world, PlayerEntity player, Hand hand) {

    }

    /**
     * event hook for wall running
     */
    @Override
    protected void triggerWallRun(ItemStack stack, World world, PlayerEntity player, Direction wallDirec) {

    }

    // !remove wall effects

}
