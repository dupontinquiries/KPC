package media.kitchen.parkour.itemtype.tools.supertrident;

import media.kitchen.parkour.Parkour;
import media.kitchen.parkour.world.structure.goal.KRangedGoal;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.monster.DrownedEntity;
import net.minecraft.util.Hand;

public class SuperTridentGoal extends KRangedGoal {
    private final DrownedEntity entity;

    public SuperTridentGoal(IRangedAttackMob mobIn, double p_i48907_2_, int p_i48907_4_, float p_i48907_5_) {
        super(mobIn, p_i48907_2_, p_i48907_4_, p_i48907_5_);
        this.entity = (DrownedEntity)mobIn;
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean shouldExecute() {
        System.out.println("   shouldExecute");
        return super.shouldExecute() && this.entity.getHeldItemMainhand().getItem() == Parkour.SUPER_TRIDENT.get();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        System.out.println("   startExecuting");
        super.startExecuting();
        this.entity.setAggroed(true);
        this.entity.setActiveHand(Hand.MAIN_HAND);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        System.out.println("   resetTask");
        super.resetTask();
        this.entity.resetActiveHand();
        this.entity.setAggroed(false);
    }

    @Override
    public void tick() {
        System.out.println("   tick");
        super.tick();
    }
}