package media.kitchen.parkour.itemtype.parkour;

import media.kitchen.parkour.itemtype.nbthandles.ItemData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TeleParkour extends ParkourBase {

    private final String dashTag = "kdas";

    private final int maxDash = 5;

    // constructors

    public TeleParkour(final double xz, final double y) {
        super(xz, y);
        setup();
    }

    public TeleParkour(final double universalMultiplier) {
        super(universalMultiplier);
        setup();
    }

    public TeleParkour() {
        super();
        setup();
    }

    public TeleParkour(Properties props) {
        super(props);
        setup();
    }

    // constructor helpers

    public TeleParkour setElytraFlyer() {
        isElytraFlyer = true;
        return this;
    }

    public TeleParkour setCooldownTime(int counts) {
        maxCooldown = counts;
        return this;
    }

    public TeleParkour setWallLeapTime(int counts) {
        maxWallLeap = counts;
        return this;
    }

    public TeleParkour setChargeTime(int counts) {
        maxCharge = counts;
        return this;
    }

    protected void setup() {
        leapSound = new AmbigSoundType(SoundEvents.ENTITY_SHULKER_TELEPORT);
    }

    // !constructor helpers

    // !constructors

    // teleport

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
            if (cooldown == 0) {
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
        path.mul(multiplierXZ, multiplierY, multiplierXZ);
        BlockPos newPos = new BlockPos(player.getPosX() + path.x,
                player.getPosY() + path.y,
                player.getPosZ() + path.z);
        if ( !world.getBlockState(newPos).isSolid() ) {

        }
        float dfn = 180 - player.rotationYaw;
        int sign = getSign(dfn);
        player.setLocationAndAngles(newPos.getX(), newPos.getY(), newPos.getZ(), player.rotationYaw, player.rotationPitch);
        world.playSound(null, new BlockPos(player), leapSound.getSound(), SoundCategory.AMBIENT, 1F, 1F + ( random.nextFloat() * 0.4F ) - 0.2F );
        ItemData.setNBTInt(stack, dashTag, maxDash);
        // cloak for one second
        boolean potionFlag = !player.isPotionActive(Effects.INVISIBILITY) ||
                ( player.isPotionActive(Effects.INVISIBILITY) && player.getActivePotionEffect(Effects.INVISIBILITY).getDuration() < 20 );
        if ( potionFlag ) {
            player.addPotionEffect(new EffectInstance(Effects.INVISIBILITY, 20, 1));
        }
    }

    // !teleport

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
