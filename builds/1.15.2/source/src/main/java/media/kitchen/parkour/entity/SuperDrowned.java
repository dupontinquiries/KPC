package media.kitchen.parkour.entity;

import media.kitchen.parkour.itemtype.tools.supertrident.SuperTridentGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.DrownedEntity;
import net.minecraft.world.World;

public class SuperDrowned extends DrownedEntity {
    public SuperDrowned(EntityType<? extends DrownedEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void applyEntityAI() {
        this.goalSelector.addGoal(2, new SuperTridentGoal(this, 1.0D, 40, 10.0F));
        super.applyEntityAI();
    }

}
