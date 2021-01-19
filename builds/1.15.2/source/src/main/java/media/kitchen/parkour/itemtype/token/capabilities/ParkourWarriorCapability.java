package media.kitchen.parkour.itemtype.token.capabilities;

import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ParkourWarriorCapability implements ICapabilitySerializable<IntNBT> {

    @CapabilityInject(IParkourCapability.class)
    public static final Capability<IParkourCapability> PARKOUR_WARRIOR_CAPABILITY = null;
    private LazyOptional<IParkourCapability> instance = LazyOptional.of(PARKOUR_WARRIOR_CAPABILITY::getDefaultInstance);

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IParkourCapability.class, new ParkourCapabilityStorage(), WarriorCapability::new);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return PARKOUR_WARRIOR_CAPABILITY.orEmpty(cap, instance);
    }

    @Override
    public IntNBT serializeNBT() {
        return (IntNBT) PARKOUR_WARRIOR_CAPABILITY.getStorage().writeNBT(PARKOUR_WARRIOR_CAPABILITY,
                instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")),
                null);
    }

    @Override
    public void deserializeNBT(IntNBT nbt) {
        PARKOUR_WARRIOR_CAPABILITY.getStorage().readNBT(PARKOUR_WARRIOR_CAPABILITY,
                instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")),
                null, nbt);
    }
}