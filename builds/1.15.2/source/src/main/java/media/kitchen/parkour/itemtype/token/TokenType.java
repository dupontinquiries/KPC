package media.kitchen.parkour.itemtype.token;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;

import java.util.HashMap;


public enum TokenType {
    WARRIOR("kwarr"),
    TANK("ktank"),
    SCOUT("kscou");

    private HashMap<IAttribute, Double> modifiers;
    private String id = "kwarr";

    private TokenType(String s) {
        this.modifiers = new HashMap<>();
        this.id = s;
        setup();
    }

    private void setup() {
        if ( id == "kwarr" ) {
            modifiers.put(SharedMonsterAttributes.ATTACK_DAMAGE, 2.8D); //1.75
            modifiers.put(SharedMonsterAttributes.MAX_HEALTH, -1D);
        } else if ( id == "ktank" ) {
            modifiers.put(SharedMonsterAttributes.MAX_HEALTH, 2.0D);
            modifiers.put(SharedMonsterAttributes.MOVEMENT_SPEED, -0.0005D); //-0.001
        } else if ( id == "kscou" ) {
            modifiers.put(SharedMonsterAttributes.MOVEMENT_SPEED, 0.0015D);
            //modifiers.put(SharedMonsterAttributes.ATTACK_DAMAGE, -0.25D);
        }

    }
    public String getId() {
        return this.id;
    }

    public HashMap<IAttribute, Double> getModifiers() {

        return modifiers;
    }
}
