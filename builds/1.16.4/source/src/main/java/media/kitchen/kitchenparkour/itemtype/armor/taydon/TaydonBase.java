package media.kitchen.kitchenparkour.itemtype.armor.taydon;

import media.kitchen.kitchenparkour.itemtype.armor.LightBasedArmor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class TaydonBase extends LightBasedArmor {

    public TaydonBase(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
        super(materialIn, slot, builder, -1, 6);
    }

    public void specialArmorEffectSlow(ItemStack stack, World worldIn, PlayerEntity player, boolean isSelected, boolean seeSky, int lightValue, boolean lightBasedEffect) {
        if ( this.slot != null && lightBasedEffect ) {
            player.heal(4);
            int stealth = getEnchant("stealth", stack);
            if ( stealth != 0 && player.getAttackingEntity() != null ) {
                if ( player.getAttackingEntity() instanceof LivingEntity ) {
                    LivingEntity le = player.getAttackingEntity();
                    applyPotion(le, Effects.BLINDNESS, 40, 1);
                    applyPotion(player, Effects.STRENGTH, 40, 1);
                }
            }
        }
    }

    public void specialArmorEffect(ItemStack stack, World worldIn, PlayerEntity player, boolean isSelected, boolean seeSky, int lightValue, boolean lightBasedEffect) {

    }

    public void applyPotions(PlayerEntity player) {
        applyPotion(player, Effects.SPEED, 140, 1);
        applyPotion(player, Effects.JUMP_BOOST, 140, 1);
    }

}
