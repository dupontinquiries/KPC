package media.kitchen.kitchenparkour.itemtype.token.capabilities;

import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ParkourAlmightyCapability implements ICapabilitySerializable<IntNBT> {

    @CapabilityInject(IParkourCapability.class)
    public static final Capability<IParkourCapability> PARKOUR_ALMIGHTY_CAPABILITY = null;
    private LazyOptional<IParkourCapability> instance = LazyOptional.of(PARKOUR_ALMIGHTY_CAPABILITY::getDefaultInstance);

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IParkourCapability.class, new ParkourCapabilityStorage(), TankCapability::new);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return PARKOUR_ALMIGHTY_CAPABILITY.orEmpty(cap, instance);
    }

    @Override
    public IntNBT serializeNBT() {
        return (IntNBT) PARKOUR_ALMIGHTY_CAPABILITY.getStorage().writeNBT(PARKOUR_ALMIGHTY_CAPABILITY,
                instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")),
                null);
    }

    @Override
    public void deserializeNBT(IntNBT nbt) {
        PARKOUR_ALMIGHTY_CAPABILITY.getStorage().readNBT(PARKOUR_ALMIGHTY_CAPABILITY,
                instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")),
                null, nbt);
    }
}