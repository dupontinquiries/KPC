package media.kitchen.kitchenparkour.itemtype.token;

import media.kitchen.kitchenparkour.Parkour;
import media.kitchen.kitchenparkour.itemtype.ItemBase;
import media.kitchen.kitchenparkour.itemtype.token.capabilities.*;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.NonNullConsumer;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class TokenBase extends ItemBase {

    private TokenType tokenType;
    protected HashMap<Attribute, Double> modifiers;

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

    /*
    protected void onNewPotionEffect(EffectInstance id) {
      this.potionsNeedUpdate = true;
      if (!this.world.isRemote) {
         id.getPotion().applyAttributesModifiersToEntity(this, this.getAttributeManager(), id.getAmplifier());
      }

   }
     */

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand handIn) {
        ItemStack stack = player.getHeldItem(handIn);

        player.world.playSound(null, player.getPosition(), Parkour.PARKOUR_GRIPPER_READY.get(), SoundCategory.AMBIENT, 0.6F, 1F + ( random.nextFloat() * 0.4F ) - 0.2F );
        player.setActiveHand(handIn);

        TokenBase token = (TokenBase) (stack.getItem());

        if ( token.getTokenType() == TokenType.WARRIOR ) {
            player.getCapability(ParkourWarriorCapability.PARKOUR_WARRIOR_CAPABILITY).ifPresent(new NonNullConsumer<IParkourCapability>() {
                @Override
                public void accept(@Nonnull IParkourCapability token) {
                    player.sendMessage(new StringTextComponent("Warrior Value: " +  token.getValue() ), Util.DUMMY_UUID);
                    token.setValue(token.getValue() + 1);
                }
            });
        } else if ( token.getTokenType() == TokenType.TANK ) {
            player.getCapability(ParkourTankCapability.PARKOUR_TANK_CAPABILITY).ifPresent(new NonNullConsumer<IParkourCapability>() {
                @Override
                public void accept(@Nonnull IParkourCapability token) {
                    player.sendMessage(new StringTextComponent("Tank Value: " + token.getValue()), Util.DUMMY_UUID);
                    token.setValue(token.getValue() + 1);
                }
            });
        } else if ( token.getTokenType() == TokenType.SCOUT ) {
            player.getCapability(ParkourScoutCapability.PARKOUR_SCOUT_CAPABILITY).ifPresent(new NonNullConsumer<IParkourCapability>() {
                @Override
                public void accept(@Nonnull IParkourCapability token) {
                    player.sendMessage(new StringTextComponent("Scout Value: " + token.getValue()), Util.DUMMY_UUID);
                    token.setValue(token.getValue() + 1);
                }
            });
        } else if ( token.getTokenType() == TokenType.ALMIGHTY ) {
            player.getCapability(ParkourAlmightyCapability.PARKOUR_ALMIGHTY_CAPABILITY).ifPresent(new NonNullConsumer<IParkourCapability>() {
                @Override
                public void accept(@Nonnull IParkourCapability token) {
                    player.sendMessage(new StringTextComponent("Almighty Value: " + token.getValue()), Util.DUMMY_UUID);
                    token.setValue(token.getValue() + 1);
                }
            });
        }

        //AbstractAttributeMap map = player.getAttributes();

        for (Map.Entry<Attribute, Double> entry : modifiers.entrySet()) {
            Attribute a = entry.getKey();
            Double b = entry.getValue();
            player.getAttribute(a).applyPersistentModifier(new AttributeModifier("Token Modifier", b, AttributeModifier.Operation.ADDITION));
        }

        stack.shrink(1);

        return ActionResult.resultConsume(stack);
    }

    public TokenBase ofType(TokenType type) {
        this.modifiers = type.getModifiers();
        return this;
    }

}
