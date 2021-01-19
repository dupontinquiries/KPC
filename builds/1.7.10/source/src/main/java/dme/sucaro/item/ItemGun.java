// 
// Decompiled by Procyon v0.5.36
// 


package dme.sucaro.item;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dme.sucaro.entity.EntityEnergyBeam;
import dme.sucaro.entity.EntityBullet;
import dme.sucaro.main.BulletEvent;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ItemGun extends Item
{
    private static final double pi = 3.1415927f;
    private static final double kVal = 0.10000000149011612;
	
	private double Damage;
    private double Recoil;
    private double Accuracy;
    private float Power;
    private int Knockback;
    private int Number;
    private int FireTime;
    private int FireRate;
    private Item Ammo;
    private String particle;
    private boolean Explode;
    private boolean ExGravity;
    private double ExSize;
    
    public EnumAction getItemUseAction(final ItemStack p_77661_1_) {
        return EnumAction.none;
    }
    
    public ItemStack onEaten(final ItemStack p_77654_1_, final World p_77654_2_, final EntityPlayer p_77654_3_) {
        return p_77654_1_;
    }
    
    public int getMaxItemUseDuration(final ItemStack p_77626_1_) {
        return 72000;
    }
    
    /**
     * firerate, damage, recoil, accuracy, power, knockback, number, firetime, ammo, particle, explode, exsize, exgravity 
     */
    public ItemGun(final int S, final double D, final double R, final double A, final float P, final int K, final int N, final int F, final Item M, final String p, final Boolean E, final Boolean xg, final double xs) {
        this.FireRate = S;
        this.Damage = D;
        this.Recoil = R;
        this.Accuracy = A;
        this.Power = P;
        this.Knockback = K;
        this.Number = N;
        this.FireTime = F;
        this.Ammo = M;
        this.particle = p;
        this.Explode = E;
        this.ExSize = xs;
        this.ExGravity = xg;
        this.setMaxDamage(this.FireRate + 1);
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.setMaxStackSize(1);
        this.setNoRepair();
        //this.func_77664_n();
    }
    
    public void onUpdate(final ItemStack stack, final World world, final Entity entity, final int p_77663_4_, final boolean p_77663_5_) {
        if (stack.getItemDamage() != 0) {
            stack.setItemDamage(stack.getItemDamage() - 1);
        }
    }
    
    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final List par3List, final boolean par4) {
    }
    
    public void registerIcons(final IIconRegister p_94581_1_) {
        this.itemIcon = p_94581_1_.registerIcon(this.getIconString() + "");
    }
    
    public ItemStack onItemRightClick(final ItemStack stack, final World w, final EntityPlayer p) {
        if (stack.getItemDamage() == 0) {
            p.setItemInUse(stack, this.getMaxItemUseDuration(stack));
            //p.worldObj.playSoundAtEntity(p, "random.bow", 1.0F, 1.0F);
            //p.worldObj.getLightBrightness(p_72801_1_, p_72801_2_, p_72801_3_)
            //p.field_70170_p.func_72908_a(p.posX, p.posY, p.posZ, "", 1.0f, 1.0f);
        }
        return stack;
    }
    
    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(ItemStack s, World w, EntityPlayer p, int o) {
    	
    }

    float rad(float angle) { 
    	return angle * (float) Math.PI / 180; 
    }
    
    public void onUsingTick(final ItemStack stack, final EntityPlayer player, final int count) {
        final Random rand = new Random();
        final BulletEvent event = new BulletEvent(player, stack, count, this.Ammo);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            return;
        }
        if (stack.getItemDamage() == 0) {
            
        }
        final boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0 || this.Ammo == null;
        if (flag || player.inventory.hasItem(this.Ammo)) {
            if (stack.getItemDamage() == 0) {
                if (!this.Explode) {
                    for (int c = 0; c <= this.Number; ++c) {
                        final EntityBullet entityarrow = new EntityBullet(player.worldObj, (EntityLivingBase)player, this.Power, this.Ammo);
                        if (this.FireRate != 0) {
                            entityarrow.setDamage(this.Damage);
                        }
                        entityarrow.setKnockbackStrength(this.Knockback);
                        entityarrow.setIsCritical(true);
                        entityarrow.setFire(this.FireTime);
                        if (flag) {
                            entityarrow.canBePickedUp = 2;
                        }
                        else {
                            player.inventory.consumeInventoryItem(this.Ammo);
                        }
                        final double f1 = (rand.nextDouble() - 0.5) * this.Accuracy;
                        final double f2 = (rand.nextDouble() - 0.5) * this.Accuracy;
                        final double f3 = (rand.nextDouble() - 0.5) * this.Accuracy;
                        //entityarrow.setVelocity(entityarrow.motionX*this.Power, entityarrow.motionY*this.Power, entityarrow.motionZ*this.Power);
                        entityarrow.addVelocity(f1, f2, f3);
                        stack.setItemDamage(this.FireRate);
                        player.worldObj.spawnEntityInWorld((Entity)entityarrow);
                        player.worldObj.spawnParticle(this.particle, entityarrow.posX, entityarrow.posY, entityarrow.posZ, entityarrow.motionX, entityarrow.motionY, entityarrow.motionZ);
                        player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "fireworks.largeBlast", 1.0f, 1.0f);
                        float rotationYaw = player.rotationYaw, rotationPitch = player.rotationPitch;
                        float vx = -MathHelper.sin(rad(rotationYaw)) * MathHelper.cos(rad(rotationPitch));
                        float vz = MathHelper.cos(rad(rotationYaw)) * MathHelper.cos(rad(rotationPitch));
                        float vy = -MathHelper.sin(rad(rotationPitch));
                        //player.addVelocity(-1*Math.sin(player.rotationYaw)*Math.cos(player.rotationPitch)*this.Recoil, Math.sin(player.rotationPitch), -1*Math.cos(player.rotationYaw)*Math.cos(player.rotationPitch)*this.Recoil);
                        //player.addVelocity(-(-MathHelper.sqrt_double(player.rotationYaw / 180.0f * pi) * MathHelper.sqrt_double(player.rotationPitch / 180.0f * pi)) * this.Recoil, -(-MathHelper.sqrt_double(player.rotationPitch / 180.0f * pi)) * this.Recoil, -(MathHelper.sqrt_double(player.rotationYaw / 180.0f * pi) * MathHelper.sqrt_double(player.rotationPitch / 180.0f * pi)) * this.Recoil);
                        //-(-MathHelper.sqrt_double(player.rotationYaw / 180.0f * pi) * MathHelper.sqrt_double(player.rotationPitch / 180.0f * pi)) * this.Recoil, -(-MathHelper.sqrt_double(player.rotationPitch / 180.0f * pi)) * this.Recoil, -(MathHelper.sqrt_double(player.rotationYaw / 180.0f * pi) * MathHelper.sqrt_double(player.rotationPitch / 180.0f * pi)) * this.Recoil
                    }
                }
                else {
                    final EntityEnergyBeam entitybeam = new EntityEnergyBeam(player.worldObj, (EntityLivingBase)player, this.Power, this.ExSize, this.Explode, this.ExGravity);
                    if (this.FireRate != 0) {
                        entitybeam.setDamage(this.Damage);
                    }
                    entitybeam.setKnockbackStrength(this.Knockback);
                    entitybeam.setIsCritical(true);
                    entitybeam.setFire(this.FireTime); //entityarrow2.func_70015_d(this.FireTime);
                    if (flag) {
                        entitybeam.canBePickedUp = 2;
                    }
                    else {
                        player.inventory.consumeInventoryItem(this.Ammo);
                    }
                    final double f4 = (rand.nextDouble() - 0.5) * this.Accuracy;
                    final double f5 = (rand.nextDouble() - 0.5) * this.Accuracy;
                    final double f6 = (rand.nextDouble() - 0.5) * this.Accuracy;
                    entitybeam.setVelocity(entitybeam.motionX*this.Power, entitybeam.motionY*this.Power, entitybeam.motionZ*this.Power);
                    entitybeam.addVelocity(f4, f5, f6);
                    stack.setItemDamage(this.FireRate);
                    player.worldObj.spawnEntityInWorld((Entity) entitybeam);
                    for (int c2 = 0; c2 < 3; ++c2) {
                    	player.worldObj.spawnParticle(this.particle, entitybeam.posX, entitybeam.posY, entitybeam.posZ, entitybeam.motionX, entitybeam.motionY, entitybeam.motionZ);
                    }
                    player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "fireworks.largeBlast", 1.0f, 1.0f);
                    player.addVelocity(-(-MathHelper.sqrt_double(player.rotationYaw / 180.0f * pi) * MathHelper.sqrt_double(player.rotationPitch / 180.0f * pi)) * this.Recoil, -(-MathHelper.sqrt_double(player.rotationPitch / 180.0f * pi)) * this.Recoil, -(MathHelper.sqrt_double(player.rotationYaw / 180.0f * pi) * MathHelper.sqrt_double(player.rotationPitch / 180.0f * pi)) * this.Recoil);
                }
            }
            else {
                player.stopUsingItem();
            }
        }
    }
}

