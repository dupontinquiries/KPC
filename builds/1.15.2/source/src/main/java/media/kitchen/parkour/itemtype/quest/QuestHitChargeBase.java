package media.kitchen.parkour.itemtype.quest;

import media.kitchen.parkour.Parkour;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;

public class QuestHitChargeBase<T extends Item> extends QuestItemBase {

    protected ArrayList<Entity> filter;

    public QuestHitChargeBase( T item, int d ) {
        super(item, new Item.Properties().maxDamage(d));
        filter = new ArrayList<Entity>();
    }

    public QuestHitChargeBase( T item ) {
        super(item, 1);
        filter = new ArrayList<Entity>();
    }

    public QuestHitChargeBase( T item, float f ) {
        super(item, 1);
        filter = new ArrayList<Entity>();
    }

    @Override
    protected void activateQuest(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if (getNBTBoolean(stack, actTag) == false && stack == player.getHeldItemMainhand()) {
                //stack.setDamage((int) (stack.getMaxDamage() * f) - 1);
                if (!world.isRemote) {
                    stack.setDamage(stack.getMaxDamage() - 1);
                    world.playSound(null, new BlockPos(entity), Parkour.QUEST_COMPLETE.get(), SoundCategory.AMBIENT, 1F, 1F + ( Item.random.nextFloat() * 0.4F ) - 0.2F );
                    setNBTBoolean(stack, actTag, true);
                }
            }
        }
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    //@Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) attacker;
            boolean flag = false;

            if ( filter.isEmpty() ) {
                flag = true;
            } else for (Entity e : filter) {
                if ( target.getClass() == e.getClass() ) {
                    flag = true;
                }
            }
            double val = 1 + Math.sqrt( target.getMaxHealth() ) / 2.5;
            for ( int g = 0; g < val; ++g ) {
                if ( flag && rechargeOnePoint(stack, player) ) {
                    if (getNBTBoolean(stack, actTag) == false) {

                    } else {
                        yieldItem(stack, player);
                        break;
                    }
                }
            }


        }
        return false;
    }

}
