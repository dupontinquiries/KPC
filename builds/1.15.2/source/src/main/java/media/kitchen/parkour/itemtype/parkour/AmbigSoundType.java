package media.kitchen.parkour.itemtype.parkour;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;

public class AmbigSoundType {
    protected SoundEvent event;
    protected RegistryObject<SoundEvent> registeredEvent;
    protected boolean isCustom;

    public AmbigSoundType(RegistryObject<SoundEvent> soundEvent) {
        registeredEvent = soundEvent;
        isCustom = true;
    }

    public AmbigSoundType(SoundEvent soundEvent) {
        event = soundEvent;
        isCustom = false;
    }

    public SoundEvent getSound() {
        if ( isCustom ) return registeredEvent.get();
        else return event;
    }
}
