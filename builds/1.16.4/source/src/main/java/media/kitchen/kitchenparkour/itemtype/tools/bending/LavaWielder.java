package media.kitchen.kitchenparkour.itemtype.tools.bending;

import media.kitchen.kitchenparkour.entity.LavaAOEEntity;
import media.kitchen.kitchenparkour.entity.LavaAOESchorchedEarthEntity;
import media.kitchen.kitchenparkour.itemtype.tools.SwordBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class LavaWielder extends SwordBase {
    public LavaWielder(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    @Override
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
            playerIn.getCooldownTracker().setCooldown(this, 70); // 240
            ItemStack stack = playerIn.getHeldItem(handIn);
            if(!worldIn.isRemote() && playerIn instanceof ServerPlayerEntity) {
                if (stack.attemptDamageItem(7, worldIn.rand, (ServerPlayerEntity) playerIn)) {
                    stack.shrink(1);
                }
            }
            ListNBT list = stack.getEnchantmentTagList();
            boolean scorched = false;
            for(int i = 0; i < list.size(); ++i) {
                CompoundNBT element = list.getCompound(i);
                if (element.getString("id").contains("scorched_earth")) {
                    scorched = true;
                    break;
                }
            }
            double fac = 4;
            double mx = playerIn.getLookVec().normalize().scale(fac).getX() + playerIn.getPosX();
            double my = playerIn.getLookVec().normalize().scale(fac).getY() + playerIn.getPosY();
            double mz = playerIn.getLookVec().normalize().scale(fac).getZ() + playerIn.getPosZ();
            if ( scorched ) {
                LavaAOESchorchedEarthEntity lava = new LavaAOESchorchedEarthEntity(worldIn, mx, my, mz);
                lava.setRadius(4);
                lava.addEffect(new EffectInstance(Effects.SLOWNESS, 45, 2));
                lava.addEffect(new EffectInstance(Effects.WITHER, 45, 2));
                lava.setDuration(160);
                worldIn.addEntity(lava);
            } else {
                LavaAOEEntity lava = new LavaAOEEntity(worldIn, mx, my, mz);
                lava.setRadius(3);
                lava.addEffect(new EffectInstance(Effects.SLOWNESS, 45, 1));
                lava.addEffect(new EffectInstance(Effects.WITHER, 45, 1));
                lava.setDuration(120);
                worldIn.addEntity(lava);
            }
            return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
