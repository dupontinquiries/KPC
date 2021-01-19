package media.kitchen.kitchenparkour.itemtype.tools.mining;

import media.kitchen.kitchenparkour.Parkour;
import media.kitchen.kitchenparkour.itemtype.nbthandles.ItemData;
import media.kitchen.kitchenparkour.util.Coord;
import media.kitchen.kitchenparkour.util.Shapes;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class AreaPickaxeBase extends PickaxeBase {

    private Random rand = new Random();

    private final String kMineTag = "kmine";

    private static final Set<Block> FORTUNE_BLOCKS = ImmutableSet.of(
            Blocks.DIAMOND_ORE, Blocks.COAL_ORE, Blocks.REDSTONE_ORE, Blocks.COAL_ORE,
            Parkour.RUBY_ORE.get(), Parkour.TAYDON_ORE.get()
    );

    protected ArrayList<Coord> area = new ArrayList<>();

    public AreaPickaxeBase(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties props) {
        super(tier, attackDamageIn, attackSpeedIn, props);
        setup();
    }

    public static <T extends AreaPickaxeBase> T setSize(T object, int a) {
        object.area = Shapes.makeCube(a);
        return object;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if ( playerIn.getCooldownTracker().hasCooldown(this) ) {
            return ActionResult.resultPass(stack);
        } else {
            playerIn.getCooldownTracker().setCooldown(this, 20);
            if ( ItemData.getNBTInt(stack, kMineTag) == 1 ) {
                ItemData.setNBTInt(stack, kMineTag, 0);
            } else {
                ItemData.setNBTInt(stack, kMineTag, 1);
            }
            worldIn.playSound(null, playerIn.getPosition(), Parkour.BLOCK_CHARGE_SOUND.get(), SoundCategory.AMBIENT, .6F, 1F + ( random.nextFloat() * 0.1F ) - 0.05F );
            return ActionResult.resultSuccess(stack);
        }
    }

    private void mine(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving, Coord coord, int fortune, boolean silk, ServerWorld sw) {
        BlockPos bp = new BlockPos(pos.getX() + coord.getX(), pos.getY() + coord.getY(),
                pos.getZ() + coord.getZ());

        if (worldIn.getBlockState(bp).getBlock() == state.getBlock()) {
            BlockState iblockstate = worldIn.getBlockState(bp);
            Block block = iblockstate.getBlock();

            if (worldIn.isAirBlock(bp)) {
                // do nothing
            } else {
                if ( silk ) {
                    ItemEntity ie = new ItemEntity(worldIn, bp.getX(), bp.getY(), bp.getZ(), new ItemStack( block.getBlock().asItem() ) );
                    worldIn.addEntity(ie);
                } else {
                    int b = 1;
                    if (FORTUNE_BLOCKS.contains(block.getBlock())) {
                        b += rand.nextInt(fortune + 1);
                    }
                    for ( ItemStack s : block.getDrops(iblockstate, sw, bp, worldIn.getTileEntity(bp)) ) {
                        for (int a = 0; a < b; ++a) {
                            ItemEntity ie = new ItemEntity(worldIn, bp.getX(), bp.getY(), bp.getZ(), s);
                            worldIn.addEntity(ie);
                            if ( worldIn.isRemote ) {
                                block.dropXpOnBlockBreak(sw, bp, block.getExpDrop(iblockstate, worldIn, bp, fortune, silk ? 1 : 0));
                            }
                        }
                    }
                }
                block.onPlayerDestroy(worldIn, bp, iblockstate);
                worldIn.playEvent(2001, bp, Block.getStateId(iblockstate));
                worldIn.setBlockState(bp, Blocks.AIR.getDefaultState());
            }
        }
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     **/
    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) {
            stack.damageItem(1, entityLiving, (p_220038_0_) -> {
                p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }

        // area mine
        if (!worldIn.isRemote && entityLiving instanceof PlayerEntity) {
            ServerWorld sw = worldIn.getServer().getWorld(worldIn.getDimensionKey()); //(ServerWorld) worldIn;
            int fortune = 0;
            boolean silk = false;

            //CompoundNBT tags = stack.getOrCreateTag();
            ListNBT list = stack.getEnchantmentTagList();
            boolean fa = false, fb = false;
            for(int i = 0; i < list.size() && (!fa || !fb); ++i) {
                CompoundNBT element = list.getCompound(i);
                if (element.getString("id").toLowerCase().contains("fortune")) {
                    fortune = element.getInt("lvl");
                    fa = true;
                }
                if (element.getString("id").toLowerCase().contains("silk_touch")) {
                    if ( element.getInt("lvl") == 1 ) {
                        silk = true;
                        fb = true;
                    }
                }
            }

            if (ItemData.getNBTInt(stack, kMineTag) == 1) {
                for (Coord coord : area) {
                    mine(stack, worldIn, state, pos, entityLiving, coord, fortune, silk, sw);
                }
            } else {
                mine(stack, worldIn, state, pos, entityLiving, new Coord(0, 0, 0), fortune, silk, sw);
            }

        }
        if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) {
            stack.damageItem( (int) (1 + area.size() / 1.3), entityLiving, (p_220038_0_) -> {
                p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }
        // !area mine

        return true;
    }

}
