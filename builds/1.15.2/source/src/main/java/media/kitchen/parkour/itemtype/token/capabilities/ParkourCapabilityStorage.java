package media.kitchen.parkour.itemtype.token.capabilities;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class ParkourCapabilityStorage implements Capability.IStorage<IParkourCapability> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IParkourCapability> capability, IParkourCapability instance, Direction side) {
        return IntNBT.valueOf(instance.getValue());
    }

    @Override
    public void readNBT(Capability<IParkourCapability> capability, IParkourCapability instance, Direction side, INBT nbt) {
        if (!(instance instanceof IParkourCapability))
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");

        instance.setValue(((IntNBT)nbt).getInt());
    }
}
