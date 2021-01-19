package media.kitchen.parkour.itemtype.quest;

import media.kitchen.parkour.itemtype.ItemBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class QuestItemBase<T extends Item> extends ItemBase {

    private final T result;
    protected final String actTag = "kqct";

    public QuestItemBase(T item, Item.Properties props) {
        super(props);
        result = item;
    }

    public QuestItemBase(T item) {
        result = item;
    }

    public QuestItemBase(T item, int n) {
        super(n);
        result = item;
    }

    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        boolean isActivated = getNBTBoolean(stack, actTag);

        if (!isActivated) {
            activateQuest(stack, worldIn, entityIn, itemSlot, isSelected);
        }
    }

    protected void activateQuest(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        setNBTBoolean(stack, actTag, true);
    }

    public boolean rechargeOnePoint(ItemStack stack, PlayerEntity holder) {

        stack.setDamage(MathHelper.clamp(stack.getDamage() - 1, 0, stack.getMaxDamage()));

        if (stack.getDamage() == 0) {
            return true;
        } else return false;

    }

    public void yieldItem(ItemStack stack, PlayerEntity holder) {
        holder.inventory.deleteStack(stack);
        World world = holder.world;
        if (!world.isRemote()) {
            ItemStack newStack = new ItemStack(result);
            holder.dropItem(newStack, true);
        }
    }

    protected void setNBTBoolean(ItemStack stack, final String query, final boolean value) {
        CompoundNBT tags = stack.getOrCreateTag();

        tags.putBoolean(query, value);

        stack.setTag(tags);
    }

    protected boolean getNBTBoolean(ItemStack stack, final String query) {
        CompoundNBT tags = stack.getOrCreateTag();

        if (tags.contains(query)) {
            return tags.getBoolean(query);
        } else {
            tags.putBoolean(query, false);
            stack.setTag(tags);
            return false;
        }
    }

}
