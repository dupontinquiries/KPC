package media.kitchen.parkour.itemtype.parkour;

import media.kitchen.parkour.Parkour;
import media.kitchen.parkour.itemtype.nbthandles.ItemData;
import media.kitchen.parkour.itemtype.tools.SwordBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ParkourBase extends SwordBase {

    // item type variables
    protected String cooldownTag = "kcoo",
        wallLeapTag = "kwal",
        chargeTag = "kchr",
        readyTag = "krdy",
        usesTag = "kuse",
        wallTimeTag = "kwallt";

    protected AmbigSoundType leapSound;

    protected int maxCooldown, maxCharge, maxWallLeap, maxReady, maxUses, maxWallRun;

    protected Random rand;

    protected double multiplierXZ, multiplierY;

    protected boolean isElytraFlyer;

    // !item type variables

    // constructors

    public ParkourBase(Item.Properties props, final double xz, final double y) {
        this(new Item.Properties().group(ItemGroup.COMBAT).addToolType(ToolType.AXE, 1).maxStackSize(1).defaultMaxDamage(550));
        multiplierXZ = xz;
        multiplierY = y;
    }

    public ParkourBase(Item.Properties props, final double universalMultiplier) {
        this(new Item.Properties().group(ItemGroup.COMBAT).addToolType(ToolType.AXE, 1).maxStackSize(1).defaultMaxDamage(550));
        multiplierXZ = multiplierY = universalMultiplier;
    }

    public ParkourBase(final double xz, final double y) {
        this(new Item.Properties().group(ItemGroup.COMBAT).addToolType(ToolType.AXE, 1).maxStackSize(1).defaultMaxDamage(550));
        multiplierXZ = xz;
        multiplierY = y;
    }

    public ParkourBase(final double universalMultiplier) {
        this(new Item.Properties().group(ItemGroup.COMBAT).addToolType(ToolType.AXE, 1).maxStackSize(1).defaultMaxDamage(550));
        multiplierXZ = multiplierY = universalMultiplier;
    }

    public ParkourBase() {
        this(new Item.Properties().group(ItemGroup.COMBAT).addToolType(ToolType.AXE, 1).maxStackSize(1).defaultMaxDamage(550));
        multiplierXZ = 1;
        multiplierY = 1;
    }

    public ParkourBase(Item.Properties props) {
        super(null, 24, 0.7F, props);

        rand = new Random();

        maxCooldown = maxCharge = maxWallLeap = 13;
        maxReady = 9;
        maxWallRun = 10; //////

        maxUses = 1;

        isElytraFlyer = false;

        leapSound = new AmbigSoundType(Parkour.PARKOUR_GRIPPER_JUMP);

        addTab(ItemGroup.TRANSPORTATION);

    }

    public ParkourBase(Item.Properties props, int attack, float speed) {
        super(null, attack, speed, props);

        rand = new Random();

        maxCooldown = maxCharge = maxWallLeap = maxReady = 13;

        isElytraFlyer = false;

        leapSound = new AmbigSoundType(Parkour.PARKOUR_GRIPPER_JUMP);

        addTab(ItemGroup.TRANSPORTATION);

    }

    // constructor helpers

    public ParkourBase setElytraFlyer() {
        isElytraFlyer = true;
        maxUses += 2;
        return this;
    }

    public ParkourBase setCooldownTime(int counts) {
        maxCooldown = counts;
        return this;
    }

    public ParkourBase setWallLeapTime(int counts) {
        maxWallLeap = counts;
        return this;
    }

    public ParkourBase setChargeTime(int counts) {
        maxCharge = counts;
        return this;
    }

    public ParkourBase setWallTime(int counts) { //////
        maxWallRun = counts;
        return this;
    }



    // !constructor helpers

    // !constructors

    // nbt functions

    // charge var handler - for cooldown on leaping
    // wall leap var handler - for ensuring player is on wall for a time before being able to wall leap

    /**
     * Called when item is crafted/smelted. Used only by maps so far.
     */
    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        worldIn.playSound(null, new BlockPos(playerIn), Parkour.PARKOUR_GRIPPER_READY.get(), SoundCategory.AMBIENT, 1F, 1F + ( random.nextFloat() * 0.4F ) - 0.2F );
    }

    // !nbt functions

    // parkour capabilities

    // attributes

    // !attributes

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link #onItemUse}.
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World e, PlayerEntity player, Hand handIn) {

        player.setActiveHand(handIn);

        ItemStack stack = player.getHeldItem(handIn);

        player.getAdjustedHorizontalFacing();
        // get nbt data
        int     cooldown = ItemData.getNBTInt(stack, cooldownTag),
                wallLeap = ItemData.getNBTInt(stack, wallLeapTag),
                charge   = ItemData.getNBTInt(stack, chargeTag),
                ready    = ItemData.getNBTInt(stack, readyTag),
                uses     = ItemData.getNBTInt(stack, usesTag);

        if (cooldown == -1) cooldown = maxCooldown;
        if (wallLeap == -1) wallLeap = maxWallLeap;
        if (charge   == -1) charge   = maxCharge;
        if (ready    == -1) ready    = maxReady;
        if (uses     == -1) uses     = maxUses;
        // !get nbt data
        if (ready == 0) {
            // code to jump, wall leap, etc.
            short glide = countWalls(player, e);
            System.out.println(uses);
            if (cooldown == 0 && uses > 0
                    && !(player.isInWater() || player.abilities.isFlying || player.onGround)) {
                cooldown = maxCooldown;
                --uses;
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
            ItemData.setNBTInt(stack, usesTag,     uses);
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
        Vec3d path = Vec3d.fromPitchYaw(player.rotationPitch, player.rotationYaw);
        float dfn = 180 - player.rotationYaw;
        int sign = getSign(dfn);
        if (isElytraFlyer) {
            if (player.onGround) {
                player.addVelocity(multiplierXZ * .3 * path.x, multiplierY * 1.0 + (.2 * path.y),
                        multiplierXZ * .3 * path.z);
            } else {
                player.addVelocity(multiplierXZ * .3 * path.x, multiplierY * .5 + (.2 * path.y),
                        multiplierXZ * .3 * path.z);
            }

        } else {
            player.addVelocity(multiplierXZ * .3 * path.x, multiplierY * .5 + (.2 * path.y),
                    multiplierXZ * .3 * path.z);
        }
        world.playSound(null, new BlockPos(player), leapSound.getSound(), SoundCategory.AMBIENT, 1F, 1F + ( random.nextFloat() * 0.4F ) - 0.2F );

    }

    /**
     * event hook for wall leaping
     *
     * @param world
     * @param player
     * @param hand
     */
    // @SideOnly(Side.CLIENT)
    protected void wallLeapTriggered(World world, PlayerEntity player, Hand hand) {
        Vec3d path = Vec3d.fromPitchYaw(player.rotationPitch, player.rotationYaw);
        player.addVelocity(multiplierXZ * .4 * path.x, multiplierY * .6 + (.2 * path.y),
                multiplierXZ * .4 * path.z);
        world.playSound(null, new BlockPos(player), leapSound.getSound(), SoundCategory.AMBIENT, 1F, 1F);
    }

    /**
     * Called each tick as long the item is on a player inventory. Used by maps to check if is on a player hand and
     * update it's contents.
     */
    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        // code for tracking timers
        // get nbt data
        int     cooldown = ItemData.getNBTInt(stack, cooldownTag),
                wallLeap = ItemData.getNBTInt(stack, wallLeapTag),
                charge   = ItemData.getNBTInt(stack, chargeTag),
                ready    = ItemData.getNBTInt(stack, readyTag),
                uses     = ItemData.getNBTInt(stack, usesTag),
                wallTime     = ItemData.getNBTInt(stack, wallTimeTag);

        if (cooldown == -1) cooldown = maxCooldown;
        if (wallLeap == -1) wallLeap = maxWallLeap;
        if (charge   == -1) charge   = maxCharge;
        if (ready    == -1) ready    = maxReady;
        if (uses     == -1) uses     = maxUses;
        if (wallTime     == -1) wallTime     = maxWallRun;
        // !get nbt data

        if (!isSelected) {
            ready = maxReady;
        } else {
            if (ready > 0) {
                if (ready == 1) {
                    worldIn.playSound(null, new BlockPos(entityIn), Parkour.PARKOUR_GRIPPER_READY.get(), SoundCategory.AMBIENT, 1F, 1F + ( random.nextFloat() * 0.4F ) - 0.2F );
                }
                --ready;
            } else {
                // !code for tracking timers
                if (entityIn instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) entityIn;
                    // remove fall damage
                    if (isSelected) {
                        if (player.fallDistance > 1) player.fallDistance -= 0.65;
                    }
                    // !remove fall damage

                    // recharge uses

                    if ( cooldown > 0 & (!player.isAirBorne || player.isOnLadder()) ) {
                        --cooldown;
                    }
                    if (wallLeap > 0) {
                        --wallLeap;
                    }

                    if ( !player.isAirBorne || player.isOnLadder() ) {
                        uses = maxUses;
                    }
                    // !recharge uses

                    // wallrunning search
                    short glide = 0;

                    int i = MathHelper.floor(player.getPosX());
                    int j = MathHelper.floor(player.getPosY() - 0.20000000298023224D - (double) player.getYOffset());
                    int k = MathHelper.floor(player.getPosZ());

                    // new... not quite sure what this does
                    //IBlockReader reader = world.getBlockReader(i / 16, k / 16);
                    //world.getBlockState(pp.add(1, 2, 0)).isNormalCube(reader, pp)

                    BlockPos pp = new BlockPos(i, j, k);
                    BlockPos ret = null;
                    // i + 1
                    if (worldIn.getBlockState(pp.add(1, 2, 0)).isSolid()) {
                        ++glide;
                        ret = pp.add(1, 2, 0);
                    }
                    if (worldIn.getBlockState(pp.add(1, 1, 0)).isSolid() && !worldIn.getBlockState(pp).isSolid()) {
                        ++glide;
                        ret = pp.add(1, 2, 0);
                    }

                    // i - 1
                    if (worldIn.getBlockState(pp.add(-1, 2, 0)).isSolid()) {
                        ++glide;
                        ret = pp.add(-1, 2, 0);
                    }
                    if (worldIn.getBlockState(pp.add(-1, 1, 0)).isSolid() && !worldIn.getBlockState(pp).isSolid()) {
                        ++glide;
                        ret = pp.add(-1, 2, 0);
                    }

                    // k + 1
                    if (worldIn.getBlockState(pp.add(0, 2, 1)).isSolid()) {
                        ++glide;
                        ret = pp.add(0, 2, 1);
                    }
                    if (worldIn.getBlockState(pp.add(0, 1, 1)).isSolid() && !worldIn.getBlockState(pp).isSolid()) {
                        ++glide;
                        ret = pp.add(0, 2, 1);
                    }

                    // k - 1
                    if (worldIn.getBlockState(pp.add(0, 2, -1)).isSolid()) {
                        ++glide;
                        ret = pp.add(0, 2, -1);
                    }
                    if (worldIn.getBlockState(pp.add(0, 1, -1)).isSolid() && !worldIn.getBlockState(pp).isSolid()) {
                        ++glide;
                        ret = pp.add(0, 2, -1);
                    }
                    // !wallrunning search

                    // code for wall running mechanic and wall leap cooldown
                    if (glide > 0 && player.isAirBorne && !worldIn.getBlockState(new BlockPos(i, j, k)).isSolid()) {

                        // EnumFacing
                        Direction d = calculateDirect(pp, ret);

                        if (isSelected) {
                            // determine if wall run is off cooldown
                            if (wallTime > 1) {
                                wallTime -= 2;
                                triggerWallRun(stack, worldIn, player, d);
                            }
                        }
                        // determine when wall leap can happen
                        if (charge > 0) {
                            if (magPlayer(player) < 0.33) {
                                --charge;
                            } else {
                                charge = maxCharge;
                            }
                        }
                    } else {
                        // wall leap extra jump mechanic
                        if (wallLeap > 0) {
                            Vec3d path = Vec3d.fromPitchYaw(player.rotationPitch, player.rotationYaw); // player.getForward().normalize();
                            player.addVelocity(0, .024, 0);
                            --wallLeap;
                        }
                    }
                    if (wallTime < maxWallRun) {
                        ++wallTime;
                    } //////
                    // !code for wall running and wall leap cooldown
                }
            }
        }
        // efficiently sync NBT
        ItemData.setNBTInt(stack, cooldownTag, cooldown);
        ItemData.setNBTInt(stack, wallLeapTag, wallLeap);
        ItemData.setNBTInt(stack, chargeTag,   charge);
        ItemData.setNBTInt(stack, readyTag,    ready);
        ItemData.setNBTInt(stack, usesTag,     uses);
        ItemData.setNBTInt(stack, wallTimeTag, wallTime);
        // !sync NBT
    }

    /**
     * event hook for wall running
     */
    protected void triggerWallRun(ItemStack stack, World world, PlayerEntity player, Direction wallDirec) {
        Direction direc = invertDirec(wallDirec);
        Direction pf = player.getHorizontalFacing();
        if (!player.isAirBorne) {
            return;
        }
        if (wallDirec == pf) {
            // trigger the grab hold function
            if (magPlayer(player) < .6) {
                player.addVelocity(0, .055, 0); //.068
            } else {
                player.addVelocity(0, 0.015, 0);
            }
            if (player.isCrouching()) {
                player.addVelocity(0, 0.0 + -0.4 * player.getMotion().y, 0);
                if (player.getMotion().y < 0.4) {
                    player.setVelocity(player.getMotion().x, 0, player.getMotion().z);
                }
            }
            if (player.getMotion().y > .4) {
                player.addVelocity(0, -0.05, 0);
            }

            // TODO: implement a cooldown function
        } else {
            // trigger the wall run sideways function
            if (magPlayer(player) < .6) {
                player.addVelocity(0, .068, 0);
            } else {
                player.addVelocity(0, 0.055, 0);
            }
            if (player.isCrouching()) {
                player.addVelocity(0, .007, 0);
            }
        }
    }

    // parkour helper functions

    protected double magPlayer(PlayerEntity player) {
        return Math.pow(
                    (player.getMotion().x * player.getMotion().x)
                  + (player.getMotion().y * player.getMotion().y)
                  + (player.getMotion().z * player.getMotion().z),
                    .5);
    }

    protected int getSign(double n) {
        if (n == 0) {
            return 0;
        } else if (n > 0) {
            return 1;
        } else {
            return -1;
        }
    }

    protected short countWalls(PlayerEntity player, World world) {
        // wallrunning search
        short glide = 0;

        int i = MathHelper.floor(player.getPosX());
        int j = MathHelper.floor(player.getPosY() - 0.20000000298023224D - (double) player.getYOffset());
        int k = MathHelper.floor(player.getPosZ());

        // new... not quite sure what this does
        //IBlockReader reader = world.getBlockReader(i / 16, k / 16);
        //world.getBlockState(pp.add(1, 2, 0)).isNormalCube(reader, pp)

        BlockPos pp = new BlockPos(i, j, k);
        BlockPos ret = null;
        // i + 1
        if (world.getBlockState(pp.add(1, 2, 0)).isSolid()) {
            ++glide;
            ret = pp.add(1, 2, 0);
        }
        if (world.getBlockState(pp.add(1, 1, 0)).isSolid() && !world.getBlockState(pp).isSolid()) {
            ++glide;
            ret = pp.add(1, 2, 0);
        }

        // i - 1
        if (world.getBlockState(pp.add(-1, 2, 0)).isSolid()) {
            ++glide;
            ret = pp.add(-1, 2, 0);
        }
        if (world.getBlockState(pp.add(-1, 1, 0)).isSolid() && !world.getBlockState(pp).isSolid()) {
            ++glide;
            ret = pp.add(-1, 2, 0);
        }

        // k + 1
        if (world.getBlockState(pp.add(0, 2, 1)).isSolid()) {
            ++glide;
            ret = pp.add(0, 2, 1);
        }
        if (world.getBlockState(pp.add(0, 1, 1)).isSolid() && !world.getBlockState(pp).isSolid()) {
            ++glide;
            ret = pp.add(0, 2, 1);
        }

        // k - 1
        if (world.getBlockState(pp.add(0, 2, -1)).isSolid()) {
            ++glide;
            ret = pp.add(0, 2, -1);
        }
        if (world.getBlockState(pp.add(0, 1, -1)).isSolid() && !world.getBlockState(pp).isSolid()) {
            ++glide;
            ret = pp.add(0, 2, -1);
        }
        // !wallrunning search
        return glide;
    }

    /**
     * valid only for a very specific set of assumptions - the blockpositions are
     * only one apart in either the x or z direction
     *
     * @param start
     * @param end
     * @return
     */
    private Direction calculateDirect(BlockPos start, BlockPos end) {
        int x1, x2, y1, y2, z1, z2;
        x1 = start.getX();
        y1 = start.getY();
        z1 = start.getZ();
        x2 = end.getX();
        y2 = end.getY();
        z2 = end.getZ();
        Direction ret = null;
        if (x1 == x2) {
            if (z1 > z2) {
                ret = Direction.NORTH;
            } else {
                ret = Direction.SOUTH;
            }
        } else {
            if (x1 > x2) {
                ret = Direction.WEST;
            } else {
                ret = Direction.EAST;
            }
        }
        // System.out.println("x1, x2 = (" + x1 + "," + x2 + ")");
        return ret;
    }

    /**
     * warning: this only works for cardinal directions and not up or down!
     *
     * @param d
     * @return
     */
    @SuppressWarnings("incomplete-switch")
    private Direction invertDirec(Direction d) {
        switch (d) {
            case NORTH:
                return Direction.SOUTH;
            case EAST:
                return Direction.WEST;
            case SOUTH:
                return Direction.NORTH;
            case WEST:
                return Direction.EAST;
        }
        return d;
    }

    // !parkour helper functions

    // !parkour capabilities

    // creative tabs

    // !creative tabs

    // tooltip

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        //tooltip.add(new ITextComponent());
    }

    // !tooltip

}
