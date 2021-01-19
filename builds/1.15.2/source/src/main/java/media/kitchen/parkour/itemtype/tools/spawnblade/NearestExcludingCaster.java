package media.kitchen.parkour.itemtype.tools.spawnblade;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;

public class NearestExcludingCaster<T extends LivingEntity, B extends LivingEntity> extends TargetGoal {
    protected final Class<T> targetClass;
    protected final int targetChance;
    protected LivingEntity nearestTarget;
    /** This filter is applied to the Entity search. Only matching entities will be targeted. */
    protected EntityPredicate targetEntitySelector;
    private B caster;

    private ArrayList<Integer> allies = new ArrayList<Integer>();

    public NearestExcludingCaster(MobEntity goalOwnerIn, Class<T> targetClassIn, boolean checkSight) {
        this(goalOwnerIn, targetClassIn, checkSight, false);
    }

    public NearestExcludingCaster(MobEntity goalOwnerIn, Class<T> targetClassIn, boolean checkSight, boolean nearbyOnlyIn) {
        this(goalOwnerIn, targetClassIn, 10, checkSight, nearbyOnlyIn, (Predicate<LivingEntity>)null, new ArrayList<>());
    }

    public NearestExcludingCaster(MobEntity goalOwnerIn, Class<T> targetClassIn, int targetChanceIn, boolean checkSight, boolean nearbyOnlyIn,
                                  @Nullable Predicate<LivingEntity> targetPredicate, ArrayList<Integer> allies) {
        super(goalOwnerIn, checkSight, nearbyOnlyIn);
        this.allies = allies;
        this.targetClass = targetClassIn;
        this.targetChance = targetChanceIn;
        this.setMutexFlags(EnumSet.of(Goal.Flag.TARGET));
        this.targetEntitySelector = (new EntityPredicate()).setDistance(this.getTargetDistance()).setCustomPredicate(targetPredicate);
    }

    public void setCaster(B caster) {
        this.caster = caster;
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean shouldExecute() {
        if (this.targetChance > 0 && this.goalOwner.getRNG().nextInt(this.targetChance) != 0) {
            return false;
        } else {
            this.findNearestTarget();
            // exclude caster
            if ( ( this.nearestTarget != null && this.nearestTarget.getUniqueID() == caster.getUniqueID() ) || ( allies.size() > 1 && nearestTarget != null && allies.contains( nearestTarget.getEntityId() ) ) ) {
                return false;
            }
            return this.nearestTarget != null;
        }
    }

    protected AxisAlignedBB getTargetableArea(double targetDistance) {
        return this.goalOwner.getBoundingBox().grow(targetDistance, 4.0D, targetDistance);
    }

    protected void findNearestTarget() {
        if (this.targetClass != PlayerEntity.class && this.targetClass != ServerPlayerEntity.class) {
            this.nearestTarget = this.goalOwner.world.<T>func_225318_b(this.targetClass,
                    this.targetEntitySelector.setCustomPredicate( ( obj ) -> !allies.contains( obj.getEntityId() ) ),
                    this.goalOwner, this.goalOwner.getPosX(), this.goalOwner.getPosYEye(), this.goalOwner.getPosZ(), this.getTargetableArea(this.getTargetDistance()));
        } else {
            Predicate pred;
            this.nearestTarget = this.goalOwner.world.getClosestPlayer(
                    this.targetEntitySelector.setCustomPredicate( ( obj ) -> !allies.contains( obj.getEntityId() ) ),
                    this.goalOwner, this.goalOwner.getPosX(), this.goalOwner.getPosYEye(), this.goalOwner.getPosZ());
        }

    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.goalOwner.setAttackTarget(this.nearestTarget);
        super.startExecuting();
    }
}