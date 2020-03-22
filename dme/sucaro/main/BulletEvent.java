// 
// Decompiled by Procyon v0.5.36
// 

package dme.sucaro.main;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.eventhandler.Cancelable;
import net.minecraftforge.event.entity.player.PlayerEvent;

@Cancelable
public class BulletEvent extends PlayerEvent
{
    public final ItemStack bow;
    public int charge;
    public Item ammo;
    
    public BulletEvent(final EntityPlayer player, final ItemStack bow, final int charge, final Item ammo) {
        super(player);
        this.bow = bow;
        this.charge = charge;
        this.ammo = ammo;
    }
}