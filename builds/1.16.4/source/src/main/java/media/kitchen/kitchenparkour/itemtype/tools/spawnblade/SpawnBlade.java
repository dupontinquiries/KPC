package media.kitchen.kitchenparkour.itemtype.tools.spawnblade;

import media.kitchen.kitchenparkour.itemtype.nbthandles.ItemData;
import media.kitchen.kitchenparkour.Parkour;
import media.kitchen.kitchenparkour.sound.AmbigSoundType;
import media.kitchen.kitchenparkour.itemtype.tools.SwordBase;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class SpawnBlade<E extends CreatureEntity> extends SwordBase {

    protected EntityType eType = null;
    //protected ArrayList<E> activeUnits = new ArrayList<>();

    protected AmbigSoundType callSound = new AmbigSoundType(Parkour.SWORD_ATTACK);
    protected final String
            COOLDOWN_TAG = "spawncooltag",
            LIFE_TAG     = "spawnlifetimetag",
            ID_TAG       = "summonTarget",
            UNITS_TAG    = "kACtUni";

    protected int
            maxLife = 700,
            maxCool = 300;

    private int maxUnits = 1;

    /*
    public SpawnBlade(IItemTier tier) {
        super(tier);
    }

    public SpawnBlade(IItemTier tier, Item.Properties props) {
        super(tier, props);
    }
     */

    public SpawnBlade(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties props) {
        super(tier, attackDamageIn, attackSpeedIn, props);
    }

    public SpawnBlade(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties props, EntityType eType) {
        super(tier, attackDamageIn, attackSpeedIn, props);
        this.eType = eType;
    }

    public SpawnBlade(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties props, EntityType eType, int maxLife, int maxCool) {
        this(tier, attackDamageIn, attackSpeedIn, props, eType);
        this.maxCool = maxCool;
        this.maxLife = maxLife;
    }

    public SpawnBlade(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties props, EntityType eType, int maxLife, int maxCool, int maxUnits) {
        this(tier, attackDamageIn, attackSpeedIn, props, eType);
        this.maxCool = maxCool;
        this.maxLife = maxLife;
        this.maxUnits = maxUnits;
    }

    public SpawnBlade setCount(int n) {
        this.maxUnits = n;
        return this;
    }

    /*
       public void add(int p_add_1_, INBT p_add_2_) {
      if (!this.func_218660_b(p_add_1_, p_add_2_)) {
         throw new UnsupportedOperationException(String.format("Trying to add tag of type %d to list of %d", p_add_2_.getId(), this.tagType));
      }
   }
    * */

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        //
        int cool = ItemData.getNBTInt(stack, this.COOLDOWN_TAG),
            life = ItemData.getNBTInt(stack,     this.LIFE_TAG);

        ListNBT activeUnits = ItemData.getListNBT(stack, this.UNITS_TAG);

        boolean inList = false;

        for ( int g = 0; g < activeUnits.size(); ++g ) {
            CompoundNBT el = activeUnits.getCompound(g);
            if ( el.getInt("id") == target.getEntityId() ) {
                inList = true;
                break;
            }
        }

        if ( inList ) {
            target.heal(6F);
            return false;
        } else {
            if ( cool == 0 && eType != null ) {
                attacker.world.playSound(null, attacker.getPosition(), callSound.getSound(), SoundCategory.AMBIENT, 0.8F, 1F );
                cool = this.maxCool;
                life = maxLife;
                for ( int g = 0; g < maxUnits - activeUnits.size(); ++g ) {
                    Entity entity = eType.create(attacker.world);
                    if ( entity instanceof CreatureEntity) {
                        E l = (E) entity;
                        l.setHealth(l.getMaxHealth());
                        l.clearActivePotions();
                        l.setPosition(attacker.getPosX() + random.nextInt(2)  - 1, attacker.getPosY(), attacker.getPosZ() + random.nextInt(2)  - 1);

                        //l.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(new AttributeModifier());

                        if ( !attacker.world.isRemote() ) {
                            attacker.world.addEntity(l);
                        }
                        //l.setLastAttackedEntity(target);
                        l.setRevengeTarget(target);

                        l.addPotionEffect( new EffectInstance(Effects.REGENERATION, maxLife, 2) );
                        l.addPotionEffect( new EffectInstance(Effects.ABSORPTION, maxLife, 7) );
                        l.addPotionEffect( new EffectInstance(Effects.STRENGTH, maxLife, 3) );


                        int amp = 1;

                        for ( int a = 0; a < stack.getEnchantmentTagList().size(); ++a ) {
                            CompoundNBT compoundNBT = stack.getEnchantmentTagList().getCompound(a);
                            if ( compoundNBT.getString("id").contains("sharpness") ) {
                                amp += compoundNBT.getInt("lvl");
                            }
                        }

                        l.addPotionEffect( new EffectInstance(Effects.STRENGTH, maxLife, amp) );
                        l.addPotionEffect( new EffectInstance(Effects.SPEED, maxLife, amp / 2) );


                        CompoundNBT el = new CompoundNBT();
                        el.putInt("id", l.getEntityId() );
                        activeUnits.add(el);

                    }
                }
                if ( target != null && target instanceof LivingEntity ) {
                    ItemData.setNBTInt(stack, ID_TAG, target.getEntityId());
                }
            }

                // create list of allies and pass it to the attack AI
                ArrayList<Integer> allies = new ArrayList<Integer>();
                allies.add( attacker.getEntityId() );
                for ( int b = 0; b < activeUnits.size(); ++b ) {
                    CompoundNBT el = activeUnits.getCompound(b);
                    allies.add( el.getInt("id") );
                    //LivingEntity ally = (LivingEntity) attacker.world.getEntityByID( el.getInt("id") );
                    //if ( ally != null ) {
                    //    allies.add( ally );
                    //}
                }

                for ( int g = 0; g < activeUnits.size(); ++g ) {
                    CompoundNBT el = activeUnits.getCompound(g);
                    CreatureEntity l = (CreatureEntity) target.world.getEntityByID( el.getInt("id") );

                    NearestExcludingCaster<LivingEntity, LivingEntity> goal = new NearestExcludingCaster<>( l, LivingEntity.class,
                            10, true, false, this::shouldSummonedAttack, allies );
                    goal.setCaster(attacker);

                    for ( Goal go : l.goalSelector.getRunningGoals().collect(Collectors.toList()) ) {
                        if ( go instanceof NearestAttackableTargetGoal ) {
                            l.goalSelector.removeGoal( go );
                        }
                    }

                    for ( Goal go : l.targetSelector.getRunningGoals().collect(Collectors.toList()) ) {
                        if ( go instanceof NearestAttackableTargetGoal ) {
                            l.targetSelector.removeGoal( go );
                        }
                    }

                    l.goalSelector.addGoal(0, goal);
                    l.targetSelector.addGoal(0, goal);

                }



            ItemData.setNBTInt(stack, this.COOLDOWN_TAG, cool);
            ItemData.setNBTInt(stack, this.LIFE_TAG, life);
            //
            ItemData.setListNBT(stack, this.UNITS_TAG, activeUnits);
            //
        }


        return super.hitEntity(stack, target, attacker);
    }

    public boolean shouldSummonedAttack(@Nullable LivingEntity living) {
        if (living != null) {
            //for ( E li : activeUnits ) {
                //if ( living.getUniqueID() == li.getUniqueID() ) return false;
            //}
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        int cool = ItemData.getNBTInt(stack, this.COOLDOWN_TAG),
            life = ItemData.getNBTInt(stack,     this.LIFE_TAG);

        //UUID targetId = ItemData.getUUID(stack, this.idTag);
        int entityId = ItemData.getNBTInt(stack, ID_TAG);
        //
        ListNBT activeUnits = ItemData.getListNBT(stack, this.UNITS_TAG);
        //

        // get target
        LivingEntity li = null;
        Entity en = null;
        if ( entityId != -1 ) {
            en = worldIn.getEntityByID(entityId);
        }
        if ( en != null && en instanceof LivingEntity ) {
            li = (LivingEntity) en;
            if ( !li.isAlive() ) {
                li = null;
            }
        }
        if ( li != null && li.getUniqueID() == entityIn.getUniqueID() ) {
            li = null;
        }

        if ( !activeUnits.isEmpty() ) {
            --life;
            if ( life <= 0 ) {
                for ( int g = 0; g < activeUnits.size(); ++g ) {
                    CompoundNBT el = activeUnits.getCompound(g);
                    LivingEntity l = (LivingEntity) worldIn.getEntityByID( el.getInt("id") );
                    if ( l != null ) {
                        l.remove();
                    }
                }
                activeUnits.clear();
            } else {
                for ( int g = 0; g < activeUnits.size(); ++g ) {
                    CompoundNBT el = activeUnits.getCompound(g);
                    LivingEntity l = (LivingEntity) worldIn.getEntityByID( el.getInt("id") );
                    if ( l != null ) {
                        if ( l.getRevengeTarget() != li ) {
                            l.setRevengeTarget(li);
                        }
                    }
                }
            }
            ItemData.setNBTInt(stack, this.LIFE_TAG, life);
        } else {
            if ( cool == -1 ) {
                cool = maxCool;
            } else if ( cool > 0 ) {
                --cool;
            }
            ItemData.setNBTInt(stack, this.COOLDOWN_TAG, cool);
        }
        //
        ItemData.setListNBT(stack, this.UNITS_TAG, activeUnits);
        //
    }

}
