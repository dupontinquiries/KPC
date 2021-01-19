package media.kitchen.kitchenparkour.itemtype.quest;

import media.kitchen.kitchenparkour.Parkour;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class QuestHitChanceToInsta<T extends Item> extends QuestHitChargeBase{

    private float chanceToInsta = 0F;

    public QuestHitChanceToInsta(Item item, int d) {
        super(item, d);
    }

    public QuestHitChanceToInsta(Item item) {
        super(item);
    }

    public QuestHitChanceToInsta(Item item, float f) {
        super(item, f);
    }

    public QuestHitChanceToInsta<T> setChance(float f) {
        chanceToInsta = f;
        return this;
    }

    @Override
    protected void activateQuest(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if (getNBTBoolean(stack, actTag) == false && stack == player.getHeldItemMainhand()) {
                //stack.setDamage((int) (stack.getMaxDamage() * f) - 1);
                if (!world.isRemote()) {
                    if (world.rand.nextFloat() < chanceToInsta) {
                        yieldItem(stack, player);
                        world.playSound(null, entity.getPosition(), Parkour.QUEST_COMPLETE.get(), SoundCategory.AMBIENT, 1F, 1F + ( random.nextFloat() * 0.4F ) - 0.2F );
                    } else {
                        stack.setDamage(stack.getMaxDamage() - 1);
                        world.playSound(null, entity.getPosition(), Parkour.QUEST_COMPLETE.get(), SoundCategory.AMBIENT, 1F, 1F + ( random.nextFloat() * 0.4F ) - 0.2F );
                        setNBTBoolean(stack, actTag, true);
                    }
                }
            }
        }
    }

}
