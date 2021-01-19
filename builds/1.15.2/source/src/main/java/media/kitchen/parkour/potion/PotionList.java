package media.kitchen.parkour.potion;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;

public class PotionList {

    public static Potion camo_potion = null;
    public static Effect camo_effect = null;

    public static class CamoEffect extends Effect {

        public CamoEffect(EffectType type, int color) {
            super(type, color);
        }
    }

}
