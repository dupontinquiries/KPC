package media.kitchen.kitchenparkour.util;

import java.util.Random;

public class ParticleHelper {

    public static double randMotionMagHasSunWalker(Random r) {
        return (r.nextDouble() - .5 ) * 0.4;
    }

    public static double offsetHasSunWalker(Random r) {
        return (r.nextDouble() - .5 ) * 1.2;
    }

}
