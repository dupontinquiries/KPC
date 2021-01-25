package media.kitchen.kitchenparkour.itemtype.tools.bending;

import media.kitchen.kitchenparkour.Parkour;
import media.kitchen.kitchenparkour.itemtype.tools.SwordBase;
import media.kitchen.kitchenparkour.rule.IBendingRule;
import media.kitchen.kitchenparkour.util.Coord;
import media.kitchen.kitchenparkour.util.Shapes;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class EarthWielder extends SwordBase {
    public EarthWielder(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.hurtTime = 0;
        return super.hitEntity(stack, target, attacker);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (worldIn.getBlockState(playerIn.getPosition().down()).isAir() || playerIn.getCooldownTracker().hasCooldown(this)) {
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }
        if ( playerIn.isSneaking() ) {
            ArrayList<Coord> coords = new ArrayList<>();
            playerIn.getCooldownTracker().setCooldown(this, 80); // 240
            ItemStack stack = playerIn.getHeldItem(handIn);
            if(!worldIn.isRemote() && playerIn instanceof ServerPlayerEntity) {
                if (stack.attemptDamageItem(6, worldIn.rand, (ServerPlayerEntity) playerIn)) {
                    stack.shrink(1);
                }
            }
            worldIn.playSound(null, playerIn.getPosition(), Parkour.EARTH_WIELD_LIFT.get(), SoundCategory.AMBIENT, 1.5F, 1F);
            double fac = 3;
            double mx = playerIn.getLookVec().normalize().scale(fac).getX() + playerIn.getPosX();
            double my = playerIn.getLookVec().normalize().scale(fac).getY() + playerIn.getPosY();
            double mz = playerIn.getLookVec().normalize().scale(fac).getZ() + playerIn.getPosZ();
            Coord m = new Coord((int)mx, (int)my, (int)mz);
            for ( Coord c : Shapes.makeSphere(worldIn, playerIn.getPosition(), 4, false) ) {
                if ( !worldIn.getBlockState(c.getPosition()).getBlockState().isSolid() && worldIn.getBlockState(c.getPosition().down()).isSolid()
                && c.dist(m) < 3.5) {
                    if (IBendingRule.EARTH_BENDING_RULE.criteriaMet(worldIn.getBlockState(c.getPosition().down()).getBlockState())) {
                        coords.add(c);
                    }
                }
            }
            for ( Coord c : coords ) {
                worldIn.setBlockState(c.getPosition().up(), worldIn.getBlockState(c.getPosition().down()));
                worldIn.setBlockState(c.getPosition(), worldIn.getBlockState(c.getPosition().down(2)));
            }
            for ( Coord c : coords ) {
                worldIn.setBlockState(c.getPosition().down(2), Blocks.AIR.getDefaultState());
                worldIn.setBlockState(c.getPosition().down(1), Blocks.AIR.getDefaultState());
            }
            return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
