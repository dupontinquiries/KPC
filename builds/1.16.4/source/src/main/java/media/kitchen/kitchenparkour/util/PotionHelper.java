package media.kitchen.kitchenparkour.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;

public class PotionHelper {

    public static void applyPotion(LivingEntity le, Effect effect, int d, int a) {
        boolean flag = !le.isPotionActive(effect) ||
                ( le.isPotionActive(effect) && le.getActivePotionEffect(effect).getDuration() < 50 );
        if ( flag ) {
            le.addPotionEffect(new EffectInstance(effect, d, a));
        }
    }

    public static void applyPotion(PlayerEntity p, Effect effect, int d, int a) {
        boolean flag = !p.isPotionActive(effect) ||
                ( p.isPotionActive(effect) && p.getActivePotionEffect(effect).getDuration() < 50 );
        if ( flag ) {
            p.addPotionEffect(new EffectInstance(effect, d, a));
        }
    }

}
