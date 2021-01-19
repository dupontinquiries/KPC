package media.kitchen.kitchenparkour.itemtype.armor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.Iterator;

public abstract class LightBasedArmor extends ArmorBase {

    protected int li, la;

    public LightBasedArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder, int li, int la) {
        this(materialIn, slot, builder);
        this.li = li;
        this.la = la;
    }

    public LightBasedArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
        super(materialIn, slot, builder);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        BlockPos bp = entityIn.getPosition();
        boolean seeSky = worldIn.canBlockSeeSky(bp);
        if (worldIn.getDimensionType().hasSkyLight()) {
            int lightValue = worldIn.getLightFor(LightType.SKY, bp) - worldIn.getSkylightSubtracted();
            float f = worldIn.getCelestialAngleRadians(1.0F);
            float f1 = f < (float)Math.PI ? 0.0F : ((float)Math.PI * 2F);
            f = f + (f1 - f) * 0.2F;
            lightValue = Math.round((float)lightValue * MathHelper.cos(f));

            lightValue = MathHelper.clamp(lightValue, 0, 15);

            lightValue += worldIn.getLightFor(LightType.BLOCK, bp) / 2;

            boolean living = entityIn instanceof LivingEntity;
            LivingEntity livingEntity = null;
            if ( living ) {
                livingEntity = (LivingEntity) entityIn;
            }
            boolean lightBasedEffect = ( inRange(lightValue, li, la) );

            if ( livingEntity instanceof PlayerEntity ) {
                PlayerEntity player = (PlayerEntity) livingEntity;
                armorEffect(stack, worldIn, player, isSelected, seeSky, lightValue, lightBasedEffect);
            }
        }
    }

    private boolean inRange(int lightValue, int li, int la) {
        return li <= lightValue && lightValue <= la;
    }

    protected String cooldownTag = "kcoo";

    protected int maxCooldown = 80;

    protected int getNBTInt(ItemStack stack, final String query) {
        CompoundNBT tags = stack.getOrCreateTag();

        if (tags.contains(query)) {
            return tags.getInt(query);
        } else {
            tags.putInt(query, -1);
            stack.setTag(tags);
            return -1;
        }
    }

    protected void setNBTInt(ItemStack stack, final String query, final int value) {
        CompoundNBT tags = stack.getOrCreateTag();

        tags.putInt(query, value);

        stack.setTag(tags);
    }

    protected int getEnchant(String q, ItemStack stack) {
        ListNBT list = stack.getEnchantmentTagList();
        if (list.size() == 0) return 0;
        for(int i = 0; i < list.size(); ++i) {
            CompoundNBT element = list.getCompound(i);
            if (element.getString("id").toLowerCase().contains(q)) {
                return element.getInt("lvl");
            }
        }
        return 0;
    }

    public abstract void specialArmorEffectSlow(ItemStack stack, World worldIn, PlayerEntity player, boolean isSelected,
                                          boolean seeSky, int lightValue, boolean lightBasedEffect);


    public abstract void specialArmorEffect(ItemStack stack, World worldIn, PlayerEntity player, boolean isSelected,
                                               boolean seeSky, int lightValue, boolean lightBasedEffect);

    protected void armorEffect(ItemStack stack, World worldIn, PlayerEntity player, boolean isSelected,
                               boolean seeSky, int lightValue, boolean lightBasedEffect) {


        // timer
        if ( !player.getCooldownTracker().hasCooldown(this) ) {
            player.getCooldownTracker().setCooldown(this, maxCooldown);
            // slow effects
            if ( lightBasedEffect ) {
                repairItem(stack, player, 1);
                applyPotions(stack, worldIn, player, isSelected, seeSky, lightValue);
            }
            // !slow effects
            specialArmorEffectSlow(stack, worldIn, player, isSelected, seeSky, lightValue, lightBasedEffect);
        }
        // !timer
        // potions
        if (lightBasedEffect) {
            if ( this.slot != null && worldIn.isRemote() ) applyPotions(player);
        }
        // !potions
        specialArmorEffect(stack, worldIn, player, isSelected, seeSky, lightValue, lightBasedEffect);
    }

    protected void applyPotions(ItemStack stack, World worldIn, PlayerEntity player, boolean isSelected,
                                boolean seeSky, int lightValue) {}

    public abstract void applyPotions(PlayerEntity player);

    protected void applyPotion(LivingEntity le, Effect effect, int d, int a) {
        boolean flag = !le.isPotionActive(effect) ||
                ( le.isPotionActive(effect) && le.getActivePotionEffect(effect).getDuration() < 50 );
        if ( flag ) {
            le.addPotionEffect(new EffectInstance(effect, d, a));
        }
    }

    protected void repairItem(ItemStack stack, LivingEntity entityLiving, int amount) {
        stack.damageItem(-amount, entityLiving, (p_220044_0_) -> {
            p_220044_0_.sendBreakAnimation(this.slot);
        });
    }

}
