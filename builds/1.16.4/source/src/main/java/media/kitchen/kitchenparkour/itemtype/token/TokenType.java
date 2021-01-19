package media.kitchen.kitchenparkour.itemtype.token;

//import net.minecraft.media.kitchen.kitchenparkour.entity.SharedMonsterAttributes;
//import net.minecraft.media.kitchen.kitchenparkour.entity.ai.attributes.IAttribute;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.Attributes;

import java.util.HashMap;


public enum TokenType {
    WARRIOR("kwarr"),
    TANK("ktank"),
    SCOUT("kscou"),
    ALMIGHTY("kalmi");

    private HashMap<Attribute, Double> modifiers;
    private String id = "kwarr";

    private TokenType(String s) {
        this.modifiers = new HashMap<Attribute, Double>();
        this.id = s;
        setup();
    }

    private void setup() {
        if ( id == "kwarr" ) {
            modifiers.put(Attributes.ATTACK_DAMAGE, 2.8D); //1.75
            modifiers.put(Attributes.MAX_HEALTH, -1D);
        } else if ( id == "ktank" ) {
            modifiers.put(Attributes.MAX_HEALTH, 2.0D);
            modifiers.put(Attributes.MOVEMENT_SPEED, -0.0005D); //-0.001
        } else if ( id == "kscou" ) {
            modifiers.put(Attributes.MOVEMENT_SPEED, 0.0015D);
        } else if ( id == "kalmi" ) {
            //modifiers.put(Attributes.ARMOR_TOUGHNESS, 2D);
            modifiers.put(Attributes.MAX_HEALTH, 5D);
            modifiers.put(Attributes.MOVEMENT_SPEED, 0.0045D);
            modifiers.put(Attributes.ATTACK_DAMAGE, 5D);
            //modifiers.put(Attributes.FLYING_SPEED, 0.0045D);
        }

    }
    public String getId() {
        return this.id;
    }

    public HashMap<Attribute, Double> getModifiers() {
        return this.modifiers;
    }
}
