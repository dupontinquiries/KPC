package media.kitchen.kitchenparkour.itemtype.tools.bending;

import media.kitchen.kitchenparkour.entity.SwirlFireBall;
import media.kitchen.kitchenparkour.Parkour;
import media.kitchen.kitchenparkour.itemtype.tools.SwordBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class FireStaff extends SwordBase {
    public FireStaff(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.hurtTime = 0;
        return super.hitEntity(stack, target, attacker);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if ( playerIn.getCooldownTracker().hasCooldown(this) || playerIn.isInWater() || playerIn.isWet() ) {
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }
        playerIn.getCooldownTracker().setCooldown(this, 7); // 240
        ItemStack stack = playerIn.getHeldItem(handIn);
        if(!worldIn.isRemote() && playerIn instanceof ServerPlayerEntity) {
            if (stack.attemptDamageItem(3, worldIn.rand, (ServerPlayerEntity) playerIn)) {
                stack.shrink(1);
            }
        }
        worldIn.playSound(null, playerIn.getPosition(), Parkour.FIREBALL_SOUND.get(), SoundCategory.AMBIENT, 1F, 1F);
        double fac = 3.5;
        double mx = playerIn.getLookVec().normalize().scale(fac).getX() + playerIn.getPosX();
        double my = playerIn.getLookVec().normalize().scale(fac).getY() + playerIn.getPosY();
        double mz = playerIn.getLookVec().normalize().scale(fac).getZ() + playerIn.getPosZ();
        Vector3d vector3d = playerIn.getLook(1.0F);
        double d2 = vector3d.x * 4.0D; //playerIn.getPosX() - (playerIn.getPosX() + vector3d.x * 4.0D);
        double d3 = vector3d.y * 4.0D; //playerIn.getPosYHeight(0.5D) - (0.5D + playerIn.getPosYHeight(0.5D));
        double d4 = vector3d.z * 4.0D; //playerIn.getPosZ() - (playerIn.getPosZ() + vector3d.z * 4.0D);
        SwirlFireBall fb = new SwirlFireBall(worldIn, playerIn, d2 * fac, d3 * fac, d4 * fac);
        //fb.explosionPower = 1; // for large fireball media.kitchen.kitchenparkour.entity only
        fb.setPosition(playerIn.getPosX() + vector3d.x * 1.0D, playerIn.getPosYHeight(0.5D) + 0.5D, fb.getPosZ() + vector3d.z * 1.0D);
        worldIn.addEntity(fb);
        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }
}
