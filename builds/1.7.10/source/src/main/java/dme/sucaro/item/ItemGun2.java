// 
// Decompiled by Procyon v0.5.36
// 

/*
package dme.sucaro.item;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.eventhandler.Event;
import dme.sucaro.main.BulletEvent;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ItemGun2 extends Item
{
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
    
    public EnumAction func_77661_b(final ItemStack p_77661_1_) {
        return EnumAction.bow;
    }
    
    public ItemStack func_77654_b(final ItemStack p_77654_1_, final World p_77654_2_, final EntityPlayer p_77654_3_) {
        return p_77654_1_;
    }
    
    public int func_77626_a(final ItemStack p_77626_1_) {
        return 72000;
    }
    
    public ItemGun2(final int S, final double D, final double R, final double A, final float P, final int K, final int N, final int F, final Item M, final String p, final Boolean E, final Boolean xg, final double xs) {
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
        //this.func_77664_n();
    }
    
    public void func_77663_a(final ItemStack stack, final World world, final Entity entity, final int p_77663_4_, final boolean p_77663_5_) {
        if (stack.getItemDamage() != 0) {
            stack.setItemDamage(stack.getItemDamage() - 1);
        }
    }
    
    public void func_77624_a(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final List par3List, final boolean par4) {
    }
    
    public void func_94581_a(final IIconRegister p_94581_1_) {
        this.field_77791_bV = p_94581_1_.func_94245_a(this.func_111208_A() + "");
    }
    
    public ItemStack func_77659_a(final ItemStack stack, final World p_77659_2_, final EntityPlayer p_77659_3_) {
        if (stack.getItemDamage() == 0) {
            p_77659_3_.func_71008_a(stack, this.func_77626_a(stack));
            p_77659_3_.field_70170_p.func_72908_a(p_77659_3_.posX, p_77659_3_.posY, p_77659_3_.posZ, "", 1.0f, 1.0f);
        }
        return stack;
    }
    
    public void func_77615_a(final ItemStack p_77615_1_, final World p_77615_2_, final EntityPlayer p_77615_3_, final int p_77615_4_) {
    }
    
    public void onUsingTick(final ItemStack stack, final EntityPlayer player, final int count) {
        final Random rand = new Random();
        final BulletEvent event = new BulletEvent(player, stack, count, this.Ammo);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            return;
        }
        if (stack.getItemDamage() == 0) {
            player.worldObj.func_72908_a(player.posX, player.posY, player.posZ, "fireworks.largeBlast", 1.0f, 1.0f);
        }
        final boolean flag = player.field_71075_bZ.field_75098_d || EnchantmentHelper.func_77506_a(Enchantment.field_77342_w.field_77352_x, stack) > 0 || this.Ammo == null;
        if (flag || player.field_71071_by.func_146028_b(this.Ammo)) {
            if (stack.getItemDamage() == 0) {
                if (!this.Explode) {
                    for (int c = 0; c <= this.Number; ++c) {
                        final EntityBullet entityarrow = new EntityBullet(player.func_130014_f_(), (EntityLivingBase)player, this.Power, this.Ammo);
                        if (this.FireRate != 0) {
                            entityarrow.setDamage(this.Damage);
                        }
                        entityarrow.setKnockbackStrength(this.Knockback);
                        entityarrow.func_82142_c(true);
                        entityarrow.func_70015_d(this.FireTime);
                        if (flag) {
                            entityarrow.canBePickedUp = 2;
                        }
                        else {
                            player.field_71071_by.func_146026_a(this.Ammo);
                        }
                        final double f1 = (rand.nextDouble() - 0.5) * this.Accuracy;
                        final double f2 = (rand.nextDouble() - 0.5) * this.Accuracy;
                        final double f3 = (rand.nextDouble() - 0.5) * this.Accuracy;
                        entityarrow.func_70024_g(f1, f2, f3);
                        player.worldObj.func_72869_a(this.particle, player.posX + MathHelper.func_76134_b(player.field_70177_z / 180.0f * 3.1415927f) * 0.16f, player.posY + 0.10000000149011612, player.posZ + MathHelper.func_76126_a(player.field_70177_z / 180.0f * 3.1415927f) * 0.16f, entityarrow.field_70159_w, entityarrow.field_70181_x, entityarrow.field_70179_y);
                        player.worldObj.func_72869_a(this.particle, player.posX + MathHelper.func_76134_b(player.field_70177_z / 180.0f * 3.1415927f) * 0.16f, player.posY + 0.10000000149011612, player.posZ + MathHelper.func_76126_a(player.field_70177_z / 180.0f * 3.1415927f) * 0.16f, entityarrow.field_70159_w, entityarrow.field_70181_x, entityarrow.field_70179_y);
                        player.worldObj.func_72869_a(this.particle, player.posX + MathHelper.func_76134_b(player.field_70177_z / 180.0f * 3.1415927f) * 0.16f, player.posY + 0.10000000149011612, player.posZ + MathHelper.func_76126_a(player.field_70177_z / 180.0f * 3.1415927f) * 0.16f, entityarrow.field_70159_w, entityarrow.field_70181_x, entityarrow.field_70179_y);
                        stack.setItemDamage(this.FireRate);
                        player.worldObj.func_72838_d((Entity)entityarrow);
                        player.func_70024_g(-(-MathHelper.func_76126_a(player.field_70177_z / 180.0f * 3.1415927f) * MathHelper.func_76134_b(player.field_70125_A / 180.0f * 3.1415927f)) * this.Recoil, -(-MathHelper.func_76126_a(player.field_70125_A / 180.0f * 3.1415927f)) * this.Recoil, -(MathHelper.func_76134_b(player.field_70177_z / 180.0f * 3.1415927f) * MathHelper.func_76134_b(player.field_70125_A / 180.0f * 3.1415927f)) * this.Recoil);
                    }
                }
                else {
                    final EntityEnergyBeam entityarrow2 = new EntityEnergyBeam(player.func_130014_f_(), (EntityLivingBase)player, this.Power, this.ExSize, this.Explode, this.ExGravity);
                    if (this.FireRate != 0) {
                        entityarrow2.setDamage(this.Damage);
                    }
                    entityarrow2.setKnockbackStrength(this.Knockback);
                    entityarrow2.func_82142_c(true);
                    entityarrow2.func_70015_d(this.FireTime);
                    if (flag) {
                        entityarrow2.canBePickedUp = 2;
                    }
                    else {
                        player.field_71071_by.func_146026_a(this.Ammo);
                    }
                    final double f4 = (rand.nextDouble() - 0.5) * this.Accuracy;
                    final double f5 = (rand.nextDouble() - 0.5) * this.Accuracy;
                    final double f6 = (rand.nextDouble() - 0.5) * this.Accuracy;
                    entityarrow2.func_70024_g(f4, f5, f6);
                    for (int c2 = 0; c2 < 1; ++c2) {
                        player.worldObj.func_72869_a(this.particle, player.posX + MathHelper.func_76134_b(player.field_70177_z / 180.0f * 3.1415927f) * 0.16f, player.posY + 0.10000000149011612, player.posZ + MathHelper.func_76126_a(player.field_70177_z / 180.0f * 3.1415927f) * 0.16f, entityarrow2.field_70159_w, entityarrow2.field_70181_x, entityarrow2.field_70179_y);
                    }
                    stack.setItemDamage(this.FireRate);
                    player.worldObj.func_72838_d((Entity)entityarrow2);
                    player.func_70024_g(-(-MathHelper.func_76126_a(player.field_70177_z / 180.0f * 3.1415927f) * MathHelper.func_76134_b(player.field_70125_A / 180.0f * 3.1415927f)) * this.Recoil, -(-MathHelper.func_76126_a(player.field_70125_A / 180.0f * 3.1415927f)) * this.Recoil, -(MathHelper.func_76134_b(player.field_70177_z / 180.0f * 3.1415927f) * MathHelper.func_76134_b(player.field_70125_A / 180.0f * 3.1415927f)) * this.Recoil);
                }
            }
            else {
                player.func_71034_by();
            }
        }
    }
}
*/