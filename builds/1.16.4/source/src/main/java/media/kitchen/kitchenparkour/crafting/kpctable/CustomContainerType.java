package media.kitchen.kitchenparkour.crafting.kpctable;

import com.sun.corba.se.spi.orbutil.threadpool.Work;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.*;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CustomContainerType<T extends Container> extends net.minecraftforge.registries.ForgeRegistryEntry<net.minecraft.inventory.container.ContainerType<?>> implements net.minecraftforge.common.extensions.IForgeContainerType<T> {

    private final CustomContainerType.IFactory<T> factory;

    public CustomContainerType(CustomContainerType.IFactory<T> factory) {
        this.factory = factory;
    }

    @OnlyIn(Dist.CLIENT)
    public T create(int windowId, PlayerInventory inv) {
        return this.factory.create(windowId, inv);
    }

    @Override
    public T create(int windowId, PlayerInventory playerInv, net.minecraft.network.PacketBuffer extraData) {
        if (this.factory instanceof net.minecraftforge.fml.network.IContainerFactory) {
            return ((net.minecraftforge.fml.network.IContainerFactory<T>) this.factory).create(windowId, playerInv, extraData);
        }
        return create(windowId, playerInv);
    }

    public interface IFactory<T extends Container> {
        @OnlyIn(Dist.CLIENT)
        T create(int p_create_1_, PlayerInventory p_create_2_);
    }

}