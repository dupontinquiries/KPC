package media.kitchen.parkour.itemtype.armor;

import media.kitchen.parkour.itemtype.nbthandles.ItemData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Iterator;

public class SolarBase extends ArmorBase {

    private final String dashTag = "kdas", hasDashedTag = "khdas", dashCoolTag = "kdcdwn";

    protected double multiplierXZ = 1, multiplierY = 1, multiplierYMax = 1;
    protected int dashTime = 65;

    public SolarBase(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
        super(materialIn, slot, builder);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {

        // light detection
        BlockPos bp = new BlockPos(entityIn);

        boolean seeSky = worldIn.canBlockSeeSky(bp);
        if (worldIn.dimension.hasSkyLight()) {
            int lightValue = worldIn.getLightFor(LightType.SKY, bp) - worldIn.getSkylightSubtracted();
            float f = worldIn.getCelestialAngleRadians(1.0F);
            float f1 = f < (float)Math.PI ? 0.0F : ((float)Math.PI * 2F);
            f = f + (f1 - f) * 0.2F;
            lightValue = Math.round((float)lightValue * MathHelper.cos(f));

            lightValue = MathHelper.clamp(lightValue, 0, 15);

            lightValue += worldIn.getLightFor(LightType.BLOCK, bp) / 2;

            boolean isDay = worldIn.isDaytime();
            boolean living = entityIn instanceof LivingEntity;
            LivingEntity livingEntity = null;
            if ( living ) {
                livingEntity = (LivingEntity) entityIn;
            }
            boolean solarEffect = ( lightValue > 6 );

            if ( livingEntity instanceof PlayerEntity ) {
                PlayerEntity player = (PlayerEntity) livingEntity;
                EquipmentSlotType slot = null;
                Iterator<ItemStack> iter = player.getArmorInventoryList().iterator();
                while ( iter.hasNext() ) {
                    ItemStack s = iter.next();
                    if ( s == stack ) {
                        ArmorItem ai = (ArmorItem) s.getItem();
                        slot = ai.getEquipmentSlot();
                    }
                }
                solarEffect(stack, worldIn, player, itemSlot, isSelected, seeSky, lightValue, solarEffect, slot);
            }
        }
        // !light detection

        // lingering speed boost
        if ( entityIn instanceof PlayerEntity && worldIn.isRemote() ) {
            PlayerEntity player = (PlayerEntity) entityIn;

            int dash = ItemData.getNBTInt(stack, dashTag);
            int dashCooldown = ItemData.getNBTInt(stack, dashCoolTag);

            if ( dashCooldown > 0 ) --dashCooldown;
            ItemData.setNBTInt(stack, dashCoolTag, dashCooldown);

            int hasDashed = ItemData.getNBTInt(stack, hasDashedTag);

            if ( player.fallDistance == 0 && player.onGround && worldIn.getBlockState(player.getPosition().add(0, -1, 0)).isSolid() && dash == 0 ) {
                hasDashed = 0;
            }

            if (dash > 0) {
                --dash;
                hasDashed = 1;
                multiplierY *= 0.75;
                Vec3d path = Vec3d.fromPitchYaw(player.rotationPitch, player.rotationYaw);
                float dfn = 180 - player.rotationYaw;
                int sign = getSign(dfn);
                player.addVelocity(multiplierXZ * .007 * path.x, multiplierY * 1.5,
                        multiplierXZ * .007 * path.z);
            }
            ItemData.setNBTInt(stack, dashTag, dash); // update nbt
            ItemData.setNBTInt(stack, hasDashedTag, hasDashed);
        }
        // !lingering speed boost


    }

    // helper function for double jump
    protected int getSign(double n) {
        if (n == 0) {
            return 0;
        } else if (n > 0) {
            return 1;
        } else {
            return -1;
        }
    }

    protected String cooldownTag = "kcooldown";

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

    protected void slowSolarEffect(ItemStack stack, World worldIn, PlayerEntity player, int itemSlot, boolean isSelected,
                               boolean seeSky, int lightValue, boolean solarEffect, EquipmentSlotType slot) {
        if ( solarEffect ) {
            if ( slot != null ) {
                player.heal(1);
            }
            repairItem(stack, player, lightValue);

            ListNBT list = stack.getEnchantmentTagList();
            int blessing = 0;
            boolean fa = false;
            for(int i = 0; i < list.size() && !fa; ++i) {
                CompoundNBT element = list.getCompound(i);
                if (element.getString("id").toLowerCase().contains("sun_blessing")) {
                    blessing = element.getInt("lvl");
                    fa = true;
                }
            }
            if ( fa ) {
                ItemStack handStack = player.getHeldItem(Hand.MAIN_HAND);
                if (handStack.isRepairable()) {
                    repairItem( handStack, player, -1 * (lightValue + blessing * 2) / 3 );
                }
                ItemStack chest = player.inventory.armorItemInSlot(EquipmentSlotType.CHEST.getIndex()); //////
                if (chest.isRepairable()) {
                    repairItem( chest, player, -1 * (lightValue + blessing * 2) / 3 );
                }
            }

        }

    }

    protected void solarEffect(ItemStack stack, World worldIn, PlayerEntity player, int itemSlot, boolean isSelected,
                               boolean seeSky, int lightValue, boolean solarEffect, EquipmentSlotType slot) {


        // timer
        int cooldown = getNBTInt(stack, cooldownTag);
        if (cooldown == -1) cooldown = maxCooldown;
        if (cooldown > 0) {
            --cooldown;
        } else {
            cooldown = maxCooldown;
            slowSolarEffect(stack, worldIn, player, itemSlot, isSelected, seeSky, lightValue, solarEffect, slot);
        }
        // !timer
        if (solarEffect) {
            if ( slot == EquipmentSlotType.FEET ) {

                // double jump
                if ( Minecraft.getInstance().gameSettings.keyBindJump.isPressed() && worldIn.isRemote() )
                {
                    int hasDashed = ItemData.getNBTInt(stack, hasDashedTag);
                    int dashCooldown = ItemData.getNBTInt(stack, dashCoolTag);

                    System.out.println("1) jump = " + hasDashed);
                    if ( hasDashed != 1 && dashCooldown < 1 && player.isAirBorne ) {
                        multiplierY = multiplierYMax;
                        ItemData.setNBTInt(stack, dashTag, dashTime);
                        hasDashed = 1;
                        dashCooldown = 5;
                        System.out.println("2) jump");
                    } else {
                        hasDashed = 0;
                        dashCooldown = 0;
                        ItemData.setNBTInt(stack, dashTag, 0);
                    }
                    System.out.println("3) jump = " + hasDashed);
                    ItemData.setNBTInt(stack, hasDashedTag, hasDashed);
                    ItemData.setNBTInt(stack, cooldownTag, dashCooldown);
                }
                // !double jump

                // float
                if ( worldIn.getFluidState(player.getPosition()).isEmpty() && !player.isSwimming()
                        && !player.abilities.isFlying && player.getTicksElytraFlying() < 30) {
                    //if ( !player.isCrouching() ) player.addVelocity(0, .03 + (lightValue * .001625), 0);
                    if (player.fallDistance > 1.4) player.fallDistance -= 0.4;
                }
                // !float

            }

            // potions
            applyPotions(stack, worldIn, player, itemSlot, isSelected, seeSky, lightValue, solarEffect, slot);
            // !potions


        }


        setNBTInt(stack, cooldownTag, cooldown);

    }

    protected void applyPotions(ItemStack stack, World worldIn, PlayerEntity player, int itemSlot, boolean isSelected,
                               boolean seeSky, int lightValue, boolean solarEffect, EquipmentSlotType slot) {


        if ( slot != null && ( worldIn.isDaytime() || solarEffect ) ) {
            boolean regenFlag = !player.isPotionActive(Effects.REGENERATION) ||
                    ( player.isPotionActive(Effects.REGENERATION) && player.getActivePotionEffect(Effects.REGENERATION).getDuration() < 50 );
            if ( regenFlag ) {
                player.addPotionEffect(new EffectInstance(Effects.REGENERATION, 140, 1));
            }

            boolean strengthFlag = !player.isPotionActive(Effects.STRENGTH) ||
                    ( player.isPotionActive(Effects.STRENGTH) && player.getActivePotionEffect(Effects.STRENGTH).getDuration() < 50 );
            if ( strengthFlag ) {
                player.addPotionEffect(new EffectInstance(Effects.STRENGTH, 140, 1));
            }

            boolean hasteFlag = !player.isPotionActive(Effects.HASTE) ||
                    ( player.isPotionActive(Effects.HASTE) && player.getActivePotionEffect(Effects.HASTE).getDuration() < 50 );
            if ( hasteFlag ) {
                player.addPotionEffect(new EffectInstance(Effects.HASTE, 140, 1));
            }
        }

    }

    protected void repairItem(ItemStack stack, LivingEntity entityLiving, int lightValue) {
        stack.damageItem((int) ( -1 - .2 * ( 15 - lightValue ) ), entityLiving, (p_220044_0_) -> {
            p_220044_0_.sendBreakAnimation(this.slot);
        });
    }

}
