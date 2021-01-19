package media.kitchen.kitchenparkour.itemtype.armor.solar;

import media.kitchen.kitchenparkour.itemtype.armor.LightBasedArmor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class SolarBase extends LightBasedArmor {

    public SolarBase(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
        super(materialIn, slot, builder, 6, 20);
    }

    public void specialArmorEffectSlow(ItemStack stack, World worldIn, PlayerEntity player, boolean isSelected, boolean seeSky, int lightValue, boolean lightBasedEffect) {
        if ( lightBasedEffect ) {
            if ( this.slot != null ) {
                player.addExhaustion(-0.5f);
                int blessing = getEnchant("sun_blessing", stack);
                if ( blessing != 0 ) {
                    ItemStack handStack = player.getHeldItem(Hand.MAIN_HAND);
                    if ( handStack.isRepairable() ) {
                        repairItem(handStack, player, blessing * 2);
                    }
                    ItemStack chestplate = null;
                    for ( ItemStack s : player.getArmorInventoryList() ) {
                        if ( s.getEquipmentSlot() == EquipmentSlotType.CHEST ) {
                            chestplate = s;
                            break;
                        }
                    }
                    if ( chestplate != null && chestplate.isRepairable() ) {
                        repairItem(chestplate, player, blessing * 2);
                    }
                }
                if ( this.slot == EquipmentSlotType.FEET ) {
                    player.fallDistance -= 0.01;
                }
            }
        }
    }

    public void specialArmorEffect(ItemStack stack, World worldIn, PlayerEntity player, boolean isSelected, boolean seeSky, int lightValue, boolean lightBasedEffect) {

    }

    public void applyPotions(PlayerEntity player) {
        applyPotion(player, Effects.REGENERATION, 140, 1);
        applyPotion(player, Effects.STRENGTH, 140, 1);
        applyPotion(player, Effects.HASTE, 140, 1);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("item.solar_base.desc"));
    }

}
