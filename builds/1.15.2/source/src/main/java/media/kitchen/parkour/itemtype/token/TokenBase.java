package media.kitchen.parkour.itemtype.token;

import media.kitchen.parkour.Parkour;
import media.kitchen.parkour.itemtype.ItemBase;
import media.kitchen.parkour.itemtype.token.capabilities.IParkourCapability;
import media.kitchen.parkour.itemtype.token.capabilities.ParkourScoutCapability;
import media.kitchen.parkour.itemtype.token.capabilities.ParkourTankCapability;
import media.kitchen.parkour.itemtype.token.capabilities.ParkourWarriorCapability;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.NonNullConsumer;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class TokenBase extends ItemBase {

    private TokenType tokenType;
    protected HashMap<IAttribute, Double> modifiers;

    public TokenBase(int n, TokenType type) {
        super(n);
        this.tokenType = type;
        this.modifiers = type.getModifiers();
        setup();
    }

    public TokenBase() {
        super();
        setup();
    }

    public TokenBase(Item.Properties props) {
        super(props);
        setup();
    }

    public void setup() {
        addTab(Parkour.KPC_TOKENS);
    }
    
    public TokenType getTokenType() {
        return this.tokenType;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand handIn) {
        ItemStack stack = player.getHeldItem(handIn);

        player.world.playSound(null, new BlockPos(player), Parkour.PARKOUR_GRIPPER_READY.get(), SoundCategory.AMBIENT, 0.6F, 1F + ( random.nextFloat() * 0.4F ) - 0.2F );
        player.setActiveHand(handIn);

        TokenBase token = (TokenBase) (stack.getItem());

        if ( token.getTokenType() == TokenType.WARRIOR ) {
            player.getCapability(ParkourWarriorCapability.PARKOUR_WARRIOR_CAPABILITY).ifPresent(new NonNullConsumer<IParkourCapability>() {
                @Override
                public void accept(@Nonnull IParkourCapability token) {
                    player.sendMessage(new StringTextComponent("Warrior Value: " + token.getValue()));
                    token.setValue(token.getValue() + 1);
                }
            });
        } else if ( token.getTokenType() == TokenType.TANK ) {
            player.getCapability(ParkourTankCapability.PARKOUR_TANK_CAPABILITY).ifPresent(new NonNullConsumer<IParkourCapability>() {
                @Override
                public void accept(@Nonnull IParkourCapability token) {
                    player.sendMessage(new StringTextComponent("Tank Value: " + token.getValue()));
                    token.setValue(token.getValue() + 1);
                }
            });
        } else if ( token.getTokenType() == TokenType.SCOUT ) {
            player.getCapability(ParkourScoutCapability.PARKOUR_SCOUT_CAPABILITY).ifPresent(new NonNullConsumer<IParkourCapability>() {
                @Override
                public void accept(@Nonnull IParkourCapability token) {
                    player.sendMessage(new StringTextComponent("Scout Value: " + token.getValue()));
                    token.setValue(token.getValue() + 1);
                }
            });
        }

        //AbstractAttributeMap map = player.getAttributes();

        for (Map.Entry<IAttribute, Double> entry : modifiers.entrySet()) {
            IAttribute a = entry.getKey();
            Double b = entry.getValue();
            player.getAttribute(a).applyModifier(new AttributeModifier("Token Modifier", b, AttributeModifier.Operation.ADDITION));
            //player.getAttributes().getAttributeInstance(a).setBaseValue(player.getAttributes().getAttributeInstance(a).getBaseValue() + b);
            //player.getAttribute(a).setBaseValue(player.getAttribute(a).getValue() + b);
        }

        stack.shrink(1);

        return ActionResult.resultConsume(stack);
    }

    public TokenBase ofType(TokenType type) {
        this.modifiers = type.getModifiers();
        return this;
    }

    /*

    public TokenBase withModifier(IAttribute a, double b) {
        TokenBase token = new TokenBase();
        token.modifiers.put(a, b);
        return token;
    }

    public TokenBase withTradeoff(IAttribute a, double b) {
        TokenBase token = new TokenBase();
        token.modifiers.put(a, -b);
        return token;
    }

     */
}
