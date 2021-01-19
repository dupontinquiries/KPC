package media.kitchen.parkour.itemtype.tools;

import com.google.common.collect.Multimap;
import media.kitchen.parkour.Parkour;
import media.kitchen.parkour.itemtype.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SwordBase extends ItemBase {

    protected float attackDamage;
    protected float attackSpeed;

    private final IItemTier tier;

    public SwordBase(IItemTier tier) {
        super();
        this.tier = tier;
        this.attackDamage = 1F;
        this.attackSpeed = 0.1F;
    }

    public SwordBase(IItemTier tier, Item.Properties props) {
        super(props);
        this.tier = tier;
        this.attackDamage = 1F;
        this.attackSpeed = 0.1F;
    }

    public SwordBase(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties props) {
        super(props);
        this.tier = tier;
        this.attackSpeed = attackSpeedIn;
        this.attackDamage = (float)attackDamageIn; // + tier.getAttackDamage();
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return !(player.isCreative() || player.isOnLadder() || player.isInvisible()); //player.getPose() != Pose.CROUCHING;
    }

    public float getDestroySpeed(ItemStack stack, BlockState state) {
        Block block = state.getBlock();
        if (block == Blocks.COBWEB || state.getMaterial() == Material.GLASS || state.getMaterial() == Material.CACTUS || state.getMaterial() == Material.WOOD) {
            return 12.0F;
        } else {
            Material material = state.getMaterial();
            return material != Material.PLANTS && material != Material.TALL_PLANTS && material != Material.CORAL && !state.isIn(BlockTags.LEAVES) && material != Material.GOURD ? 1.0F : 1.5F;
        }
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        //attacker.world.playSound(null, new BlockPos(attacker), Parkour.SWORD_ATTACK.get(), SoundCategory.AMBIENT, 0.7F, 1F + ( random.nextFloat() * 0.4F ) - 0.2F );
        stack.damageItem(1, attacker, (p_220045_0_) -> {
            p_220045_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        target.hurtResistantTime *= .8;
        return true;
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        //entityLiving.world.playSound(null, new BlockPos(entityLiving), Parkour.SWORD_ATTACK.get(), SoundCategory.AMBIENT, 1F, 1F);
        if (state.getBlockHardness(worldIn, pos) != 0.0F) {
            stack.damageItem(2, entityLiving, (p_220044_0_) -> {
                p_220044_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }

        return true;
    }

    /**
     * Check whether this Item can harvest the given Block
     */
    public boolean canHarvestBlock(BlockState blockIn) {
        return blockIn.getBlock() == Blocks.COBWEB;
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot);
        if (equipmentSlot == EquipmentSlotType.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double) this.attackDamage, AttributeModifier.Operation.ADDITION));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double) this.attackSpeed, AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return toRepair.isDamaged() && repair.getItem() == Parkour.RUBY_GEM.get();
    }


    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability() {
        return 15; //this.tier.getEnchantability();
    }

    public static <T extends SwordBase> T setAttackAndSpeed(T object, int i, float v) {
        object.attackDamage = i;
        object.attackSpeed = v;
        return object;
    }
}
