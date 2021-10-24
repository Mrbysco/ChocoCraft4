package net.chococraft.common.entities;

import net.chococraft.Chococraft;
import net.chococraft.common.ChocoConfig;
import net.chococraft.common.entities.breeding.ChocoboMateGoal;
import net.chococraft.common.entities.properties.ChocoboColor;
import net.chococraft.common.entities.properties.EntityDataSerializers;
import net.chococraft.common.entities.properties.MovementType;
import net.chococraft.common.init.ModAttributes;
import net.chococraft.common.init.ModRegistry;
import net.chococraft.common.init.ModSounds;
import net.chococraft.common.inventory.SaddleBagContainer;
import net.chococraft.common.inventory.SaddleItemStackHandler;
import net.chococraft.common.items.ChocoDisguiseItem;
import net.chococraft.common.items.ChocoboSaddleItem;
import net.chococraft.common.network.PacketManager;
import net.chococraft.common.network.packets.OpenChocoboGuiMessage;
import net.chococraft.utils.RandomHelper;
import net.chococraft.utils.WorldUtils;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class ChocoboEntity extends TameableEntity {
    private static final String NBTKEY_CHOCOBO_COLOR = "Color";
    private static final String NBTKEY_CHOCOBO_IS_MALE = "Male";
    private static final String NBTKEY_MOVEMENTTYPE = "MovementType";
    private static final String NBTKEY_SADDLE_ITEM = "Saddle";
    private static final String NBTKEY_INVENTORY = "Inventory";
    private static final String NBTKEY_NEST_POSITION = "NestPos";
    private static final String NBTKEY_CHOCOBO_GENERATION = "Generation";
    private static final String NBTKEY_CHOCOBO_STAMINA = "Stamina";
    private static final String NBTKEY_CHOCOBO_CAN_FLY = "CanFly";
    private static final String NBTKEY_CHOCOBO_CAN_GLIDE = "CanGlide";
    private static final String NBTKEY_CHOCOBO_CAN_SPRINT = "CanSprint";
    private static final String NBTKEY_CHOCOBO_CAN_DIVE = "CanDive";

    private static final byte CAN_SPRINT_BIT = 0b0001;
    private static final byte CAN_DIVE_BIT = 0b0010;
    private static final byte CAN_GLIDE_BIT = 0b0100;
    private static final byte CAN_FLY_BIT = 0b1000;

    private static final DataParameter<ChocoboColor> PARAM_COLOR = EntityDataManager.defineId(ChocoboEntity.class, EntityDataSerializers.CHOCOBO_COLOR);
    private static final DataParameter<Boolean> PARAM_IS_MALE = EntityDataManager.defineId(ChocoboEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<MovementType> PARAM_MOVEMENT_TYPE = EntityDataManager.defineId(ChocoboEntity.class, EntityDataSerializers.MOVEMENT_TYPE);
    private static final DataParameter<ItemStack> PARAM_SADDLE_ITEM = EntityDataManager.defineId(ChocoboEntity.class, DataSerializers.ITEM_STACK);

    private final static DataParameter<Integer> PARAM_GENERATION = EntityDataManager.defineId(ChocoboEntity.class, DataSerializers.INT);
    private final static DataParameter<Float> PARAM_STAMINA = EntityDataManager.defineId(ChocoboEntity.class, DataSerializers.FLOAT);
    private final static DataParameter<Byte> PARAM_ABILITY_MASK = EntityDataManager.defineId(ChocoboEntity.class, DataSerializers.BYTE);

    private static final UUID CHOCOBO_SPRINTING_BOOST_ID = UUID.fromString("03ba3167-393e-4362-92b8-909841047640");
    private static final AttributeModifier CHOCOBO_SPRINTING_SPEED_BOOST = (new AttributeModifier(CHOCOBO_SPRINTING_BOOST_ID, "Chocobo sprinting speed boost", 1, Operation.MULTIPLY_BASE));

    public final Predicate<LivingEntity> playerSuitSelector = livingEntity -> {
        if(livingEntity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) livingEntity;
            int chance = 0;
            for (ItemStack stack : player.inventory.armor) {
                if (stack != null) {
                    if (stack.getItem() instanceof ChocoDisguiseItem)
                        chance += 25;
                }
            }

            return !RandomHelper.getChanceResult(chance);
        }
        return false;
    };
    private final AvoidEntityGoal chocoboAvoidPlayerGoal = new AvoidEntityGoal<>(this, PlayerEntity.class, playerSuitSelector, 10.0F, 1.0D, 1.2D, EntityPredicates.NO_CREATIVE_OR_SPECTATOR::test);


    public final ItemStackHandler chocoboInventory = new ItemStackHandler() {
        //Todo make it handle resizes
    };
    public final SaddleItemStackHandler saddleItemStackHandler = new SaddleItemStackHandler() {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return stack.getItem() instanceof ChocoboSaddleItem;
        }

        @Override
        protected void onStackChanged() {
            ChocoboEntity.this.setSaddleType(this.itemStack);
        }
    };

    private float wingRotation;
    private float destPos;
    private boolean isChocoboJumping;
    private float wingRotDelta;
    private BlockPos nestPos;

    public ChocoboEntity(EntityType<? extends ChocoboEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new ChocoboMateGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.6D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, false, Ingredient.of(ModRegistry.GYSAHL_GREEN.get())));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(1, new SwimGoal(this));
    }

    private final FollowOwnerGoal follow = new FollowOwnerGoal(this, 2.0D, 3.0F, 10.0F, false);
    public float followingmrhuman = 2;

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(ModAttributes.MAX_STAMINA.get(), ChocoConfig.COMMON.defaultStamina.get())
                .add(Attributes.MOVEMENT_SPEED, ChocoConfig.COMMON.defaultSpeed.get() / 100f)
                .add(Attributes.MAX_HEALTH, ChocoConfig.COMMON.defaultHealth.get());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PARAM_COLOR, ChocoboColor.YELLOW);
        this.entityData.define(PARAM_IS_MALE, false);
        this.entityData.define(PARAM_MOVEMENT_TYPE, MovementType.WANDER);
        this.entityData.define(PARAM_SADDLE_ITEM, ItemStack.EMPTY);

        this.entityData.define(PARAM_STAMINA, (float) ChocoConfig.COMMON.defaultStamina.get());
        this.entityData.define(PARAM_GENERATION, 0);
        this.entityData.define(PARAM_ABILITY_MASK, (byte) 0);
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setMale(this.level.random.nextBoolean());

        final Biome currentBiome = this.level.getBiome(blockPosition().below());
        if (currentBiome.getBiomeCategory() == Biome.Category.NETHER) {
            this.setChocoboColor(ChocoboColor.FLAME);
        }

        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public boolean canBeControlledByRider() {
        return this.isTame();
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setChocoboColor(ChocoboColor.values()[compound.getByte(NBTKEY_CHOCOBO_COLOR)]);
        this.setMale(compound.getBoolean(NBTKEY_CHOCOBO_IS_MALE));
        this.setMovementType(MovementType.values()[compound.getByte(NBTKEY_MOVEMENTTYPE)]);
        this.saddleItemStackHandler.deserializeNBT(compound.getCompound(NBTKEY_SADDLE_ITEM));

        if (!getSaddle().isEmpty())
            this.chocoboInventory.deserializeNBT(compound.getCompound(NBTKEY_INVENTORY));

        if (compound.contains(NBTKEY_NEST_POSITION))
            this.nestPos = NBTUtil.readBlockPos(compound.getCompound(NBTKEY_NEST_POSITION));

        this.setGeneration(compound.getInt(NBTKEY_CHOCOBO_GENERATION));
        this.setStamina(compound.getFloat(NBTKEY_CHOCOBO_STAMINA));

        this.setCanFly(compound.getBoolean(NBTKEY_CHOCOBO_CAN_FLY));
        this.setCanGlide(compound.getBoolean(NBTKEY_CHOCOBO_CAN_GLIDE));
        this.setCanSprint(compound.getBoolean(NBTKEY_CHOCOBO_CAN_SPRINT));
        this.setCanDive(compound.getBoolean(NBTKEY_CHOCOBO_CAN_DIVE));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putByte(NBTKEY_CHOCOBO_COLOR, (byte) this.getChocoboColor().ordinal());
        compound.putBoolean(NBTKEY_CHOCOBO_IS_MALE, this.isMale());
        compound.putByte(NBTKEY_MOVEMENTTYPE, (byte) this.getMovementType().ordinal());
        compound.put(NBTKEY_SADDLE_ITEM, this.saddleItemStackHandler.serializeNBT());

        if (!getSaddle().isEmpty())
            compound.put(NBTKEY_INVENTORY, this.chocoboInventory.serializeNBT());

        if (this.nestPos != null)
            compound.put(NBTKEY_NEST_POSITION, NBTUtil.writeBlockPos(this.nestPos));

        compound.putInt(NBTKEY_CHOCOBO_GENERATION, this.getGeneration());
        compound.putFloat(NBTKEY_CHOCOBO_STAMINA, this.getStamina());

        compound.putBoolean(NBTKEY_CHOCOBO_CAN_FLY, this.canFly());
        compound.putBoolean(NBTKEY_CHOCOBO_CAN_GLIDE, this.canGlide());
        compound.putBoolean(NBTKEY_CHOCOBO_CAN_SPRINT, this.canSprint());
        compound.putBoolean(NBTKEY_CHOCOBO_CAN_DIVE, this.canDive());
    }

    public ChocoboColor getChocoboColor() {
        return this.entityData.get(PARAM_COLOR);
    }

    public void setChocoboColor(ChocoboColor color) {
        this.entityData.set(PARAM_COLOR, color);
    }

    @Override
    public boolean fireImmune() {
        return getChocoboColor() == ChocoboColor.FLAME;
    }

    public boolean isMale() {
        return this.entityData.get(PARAM_IS_MALE);
    }

    public void setMale(boolean isMale) {
        this.entityData.set(PARAM_IS_MALE, isMale);
    }

    public MovementType getMovementType() {
        return this.entityData.get(PARAM_MOVEMENT_TYPE);
    }

    public void setMovementType(MovementType type) {
        this.entityData.set(PARAM_MOVEMENT_TYPE, type);
    }

    public boolean isSaddled() {
        return !this.getSaddle().isEmpty();
    }

    public ItemStack getSaddle() {
        return this.entityData.get(PARAM_SADDLE_ITEM);
    }

    private void setSaddleType(ItemStack saddleStack) {
        ItemStack oldStack = getSaddle();
        ItemStack newStack = saddleStack.copy();
        if (getSaddle().isEmpty() || getSaddle().getItem() != newStack.getItem()) {
            this.entityData.set(PARAM_SADDLE_ITEM, newStack);
            this.reconfigureInventory(oldStack, newStack);
        }
    }

    @Nullable
    public BlockPos getNestPosition() {
        return this.nestPos;
    }

    public void setNestPosition(@Nullable BlockPos nestPos) {
        this.nestPos = nestPos;
    }

    //region Chocobo statistics getter/setter
    public float getStamina() {
        return this.entityData.get(PARAM_STAMINA);
    }

    public void setStamina(float value) {
        this.entityData.set(PARAM_STAMINA, value);
    }

    public float getStaminaPercentage() {
        return (float) (this.getStamina() / this.getAttribute(ModAttributes.MAX_STAMINA.get()).getValue());
    }

    public int getGeneration() {
        return this.entityData.get(PARAM_GENERATION);
    }

    public void setGeneration(int value) {
        this.entityData.set(PARAM_GENERATION, value);
    }

    private boolean useStamina(float value) {
        if (value == 0) return true;
        float curStamina = this.entityData.get(PARAM_STAMINA);
        if (curStamina < value) return false;

        float maxStamina = (float) this.getAttribute(ModAttributes.MAX_STAMINA.get()).getValue();
        float newStamina = MathHelper.clamp(curStamina - value, 0, maxStamina);
        this.entityData.set(PARAM_STAMINA, newStamina);
        return true;
    }

    public boolean canFly() {
        return (this.entityData.get(PARAM_ABILITY_MASK) & CAN_FLY_BIT) > 0;
    }

    public void setCanFly(boolean state) {
        this.setAbilityMaskBit(CAN_FLY_BIT, state);
    }

    public boolean canGlide() {
        return (this.entityData.get(PARAM_ABILITY_MASK) & CAN_GLIDE_BIT) > 0;
    }

    public void setCanGlide(boolean state) {
        this.setAbilityMaskBit(CAN_GLIDE_BIT, state);
    }

    public boolean canSprint() {
        return (this.entityData.get(PARAM_ABILITY_MASK) & CAN_SPRINT_BIT) > 0;
    }

    public void setCanSprint(boolean state) {
        this.setAbilityMaskBit(CAN_SPRINT_BIT, state);
    }

    public boolean canDive() {
        return (this.entityData.get(PARAM_ABILITY_MASK) & CAN_DIVE_BIT) > 0;
    }

    public void setCanDive(boolean state) {
        this.setAbilityMaskBit(CAN_DIVE_BIT, state);
    }

    private void setAbilityMaskBit(int bit, boolean state) {
        int value = this.entityData.get(PARAM_ABILITY_MASK);
        this.entityData.set(PARAM_ABILITY_MASK, (byte) (state ? value | bit : value & ~bit));
    }
    //endregion

    @Override
    public double getPassengersRidingOffset() {
        return 1.65D;
    }

    @Override
    public boolean canBeRiddenInWater(Entity rider) {
        return true;
    }

    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

//    @Override
//    public boolean handleWaterMovement() { TODO: Is this still needed?
//        if (this.getRidingEntity() instanceof ChocoboEntity) {
//            this.inWater = false;
//        } else if (this.world.handleMaterialAcceleration(this.getBoundingBox().grow(0.0D, -0.4000000059604645D, 0.0D).shrink(0.001D), Material.WATER, this)) {
//            this.fallDistance = 0.0F;
//            this.inWater = true;
//            this.extinguish();
//        } else {
//            this.inWater = false;
//        }
//
//        return this.inWater;
//    }

    @Override
    public void travel(Vector3d travelVector) {
        Vector3d newVector = travelVector;
        if (this.getControllingPassenger() instanceof PlayerEntity) {
            PlayerEntity rider = (PlayerEntity) this.getControllingPassenger();

            this.yRotO = rider.yRot;
            this.xRotO = rider.xRot;
            this.yRot = rider.yRot;
            this.xRot = rider.xRot;
            this.setRot(this.yRot, this.xRot);
            this.yHeadRot = this.yRot;
            this.yBodyRot = this.yRot;

            newVector = new Vector3d(rider.xxa * 0.5F, newVector.y, rider.zza); //Strafe - Vertical - Forward

            // reduce movement speed by 75% if moving backwards
            if (newVector.z() <= 0.0D)
                newVector = new Vector3d(newVector.x, newVector.y, newVector.z * 0.25F);

            if (this.onGround)
                this.isChocoboJumping = false;

            if ((this.canGlide() || this.canFly()) && (!rider.isInWater())) {
                this.flyingSpeed = .07f;
            } else {
                if ((rider.isInWater()) || (!this.canGlide() && !this.canFly())) {
                    this.flyingSpeed = .05f;
                }
            }

            if (this.isControlledByLocalInstance()) {
                if (rider.jumping) {
                    if (rider.isSprinting() && this.canFly() && !rider.isInWater() && this.useStamina(ChocoConfig.COMMON.flyStaminaCost.get().floatValue())) {
                        // flight logic
                        Vector3d motion = getDeltaMovement();
                        double groundValue = this.onGround ? .5F : .1F;
                        setDeltaMovement(new Vector3d(motion.x, motion.y + groundValue, motion.z));
                        if (getDeltaMovement().y > 0.5f)
                            setDeltaMovement(new Vector3d(motion.x, 0.5f, motion.z));

                        this.setSprinting(false);
                    } else {
                        // jump logic
                        if (!this.isChocoboJumping && this.onGround && this.useStamina(ChocoConfig.COMMON.jumpStaminaCost.get().floatValue())) {
                            Vector3d motion = getDeltaMovement();
                            setDeltaMovement(new Vector3d(motion.x, .6f, motion.z));
                            this.isChocoboJumping = true;
                        }
                    }
                }

                if (rider.isInWater()) {
                    Vector3d motion = getDeltaMovement();
                    if (this.canDive()) {
                        if (rider.isShiftKeyDown()) {
                            setDeltaMovement(new Vector3d(motion.x, motion.y - 0.05f, motion.z));
                            if (this.getDeltaMovement().y < -0.7f)
                                setDeltaMovement(new Vector3d(motion.x, 0.7f, motion.z));
                        }

                        if (rider.jumping) {
                            setDeltaMovement(new Vector3d(motion.x, .5f, motion.z));
                        }

                        this.wasTouchingWater = false;
                        this.setSprinting(false);
                    } else {
                        if (rider.jumping) {
                            setDeltaMovement(new Vector3d(motion.x, .5f, motion.z));
                        } else if (this.getDeltaMovement().y < 0) {
                            int distance = WorldUtils.getDistanceToSurface(this.blockPosition(), this.getCommandSenderWorld());
                            if (distance > 0)
                                setDeltaMovement(new Vector3d(motion.x, .01f + Math.min(0.05f * distance, 0.7), motion.z));
                        }
                    }
                }

                if (!this.onGround && !this.isInWater() && !rider.isShiftKeyDown() && this.getDeltaMovement().y < 0 && this.canGlide() &&
                        this.useStamina(ChocoConfig.COMMON.glideStaminaCost.get().floatValue())) {
                    Vector3d motion = getDeltaMovement();
                    setDeltaMovement(new Vector3d(motion.x, motion.y * 0.65F, motion.z));
                }

                if ((this.isSprinting() && !this.useStamina(ChocoConfig.COMMON.sprintStaminaCost.get().floatValue())) || (this.isSprinting() &&
                        this.isInWater() && this.useStamina(ChocoConfig.COMMON.sprintStaminaCost.get().floatValue())) || (this.isSprinting() &&
                        !this.canSprint() && this.useStamina(ChocoConfig.COMMON.sprintStaminaCost.get().floatValue()))) {
                    this.setSprinting(false);
                }

                this.setSpeed((float) this.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
                super.travel(newVector);
            }
        } else {
            super.travel(newVector);
        }
    }

    @Override
    public void positionRider(Entity passenger) {
        super.positionRider(passenger);
        if (passenger instanceof MobEntity && this.getControllingPassenger() == passenger) {
            this.yBodyRot = ((LivingEntity) passenger).yBodyRot;
        }
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity mate) {
        return null;
    }

    @Override
    public boolean canMate(AnimalEntity otherAnimal) {
        if (otherAnimal == this || !(otherAnimal instanceof ChocoboEntity)) return false;
        if (!this.isInLove() || !otherAnimal.isInLove()) return false;
        ChocoboEntity otherChocobo = (ChocoboEntity) otherAnimal;
        return otherChocobo.isMale() != this.isMale();
    }

    @Override
    public void setSprinting(boolean sprinting) {
        this.setSharedFlag(3, sprinting);
        ModifiableAttributeInstance attributeInstance = this.getAttribute(Attributes.MOVEMENT_SPEED);

        if (attributeInstance.getModifier(CHOCOBO_SPRINTING_BOOST_ID) != null) {
            attributeInstance.removeModifier(CHOCOBO_SPRINTING_SPEED_BOOST);
        }

        if (sprinting && this.canSprint()) {
            attributeInstance.addTransientModifier(CHOCOBO_SPRINTING_SPEED_BOOST);
        }
    }

    public void dropFeather() {
        if (this.getCommandSenderWorld().isClientSide)
            return;

        if (this.isBaby())
            return;

        this.spawnAtLocation(new ItemStack(ModRegistry.CHOCOBO_FEATHER.get(), 1), 0.0F);
    }

    public int TimeSinceFeatherChance = 0;

    @Override
    protected boolean canRide(Entity entityIn) {
        return !this.getSaddle().isEmpty() && super.canRide(entityIn);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        this.setRot(this.yRot, this.xRot);

        this.regenerateStamina();

        this.maxUpStep = 1f;
        this.fallDistance = 0f;

        if (this.TimeSinceFeatherChance == 3000) {
            this.TimeSinceFeatherChance = 0;

            if ((float) Math.random() < .25) {
                this.dropFeather();
            }
        } else {
            this.TimeSinceFeatherChance++;
        }

        if (!this.getCommandSenderWorld().isClientSide) {
            if (this.tickCount % 60 == 0)
            {
                if (this.canDive()) {
                    this.addEffect(new EffectInstance(Effects.WATER_BREATHING, 100, 0, true, false));
                    if (this.isVehicle()) {
                        Entity controller = this.getControllingPassenger();
                        if (controller instanceof PlayerEntity) {
                            ((PlayerEntity) controller).addEffect(new EffectInstance(Effects.WATER_BREATHING, 100, 0, true, false));
                        }
                    }
                }
                if (this.getChocoboColor() == ChocoboColor.FLAME) {
                    this.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 100, 0, true, false));
                    if (this.isVehicle()) {
                        Entity controller = this.getControllingPassenger();
                        if (controller instanceof PlayerEntity) {
                            ((PlayerEntity) controller).addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 100, 0, true, false));
                        }
                    }
                }
            }
        } else {
            // Wing rotations, control packet, client side
            // Client side
            this.destPos += (double) (this.onGround ? -1 : 4) * 0.3D;
            this.destPos = MathHelper.clamp(destPos, 0f, 1f);

            if (!this.onGround)
                this.wingRotDelta = Math.min(wingRotation, 1f);
            this.wingRotDelta *= 0.9D;
            this.wingRotation += this.wingRotDelta * 2.0F;

            if (this.onGround) {
                this.animationSpeedOld = this.animationSpeed;
                double d1 = this.getX() - this.xo;
                double d0 = this.getZ() - this.zo;
                float f4 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;

                if (f4 > 1.0F) {
                    f4 = 1.0F;
                }

                this.animationSpeed += (f4 - this.animationSpeed) * 0.4F;
                this.animationPosition += this.animationSpeed;
            } else {
                this.animationPosition = 0;
                this.animationSpeed = 0;
                this.animationSpeedOld = 0;
            }
        }
    }

    private void regenerateStamina() {
        // ... yes, we also allow regeneration while in lava :P
        // this effectivly limits regeneration to only work while on the ground
        if (!this.onGround && !this.isInWater() && !this.isInLava() && !this.isSprinting())
            return;

        float regen = ChocoConfig.COMMON.staminaRegenRate.get().floatValue();

        // half the amount of regeneration while moving
        Vector3d motion = getDeltaMovement();
        if (motion.x != 0 || motion.z != 0)
            regen *= 0.85;

        // TODO: implement regen bonus (another IAttribute?)
        this.useStamina(-regen);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Override
    public ActionResultType interactAt(PlayerEntity player, Vector3d vec, Hand hand) {
        ItemStack heldItemStack = player.getItemInHand(hand);

        if (heldItemStack.getItem() == ModRegistry.CHOCOPEDIA.get()) {
            if(level.isClientSide) {
                net.chococraft.client.gui.ChocoboInfoScreen.openScreen(this, player);
            }
            return ActionResultType.SUCCESS;
        }

        if (this.isTame() && player.isShiftKeyDown() && !this.isBaby()) {
            if (player instanceof ServerPlayerEntity)
                this.displayChocoboInventory((ServerPlayerEntity) player);
            return ActionResultType.SUCCESS;
        }

        if (this.getCommandSenderWorld().isClientSide)
            return ActionResultType.SUCCESS;

        if (this.isSaddled() && heldItemStack.isEmpty() && !player.isShiftKeyDown() && !this.isBaby()) {
            player.startRiding(this);
            return ActionResultType.SUCCESS;
        }

        if (!this.isTame() && heldItemStack.getItem() == ModRegistry.GYSAHL_GREEN_ITEM.get()) {
            this.usePlayerItem(player, player.inventory.getSelected());
            if ((float) Math.random() < ChocoConfig.COMMON.tameChance.get().floatValue()) {
                this.setOwnerUUID(player.getUUID());
                this.setTame(true);
                player.displayClientMessage(new TranslationTextComponent(Chococraft.MODID + ".entity_chocobo.tame_success"), true);
            } else {
                player.displayClientMessage(new TranslationTextComponent(Chococraft.MODID + ".entity_chocobo.tame_fail"), true);
            }
            return ActionResultType.SUCCESS;
        }

        if (this.isTame() && heldItemStack.getItem() == ModRegistry.GYSAHL_GREEN_ITEM.get()) {
            if (getHealth() != getMaxHealth()) {
                this.usePlayerItem(player, player.inventory.getSelected());
                heal(5);
            } else {
                player.displayClientMessage(new TranslationTextComponent(Chococraft.MODID + ".entity_chocobo.heal_fail"), true);
            }
        }

        if (this.isTame() && heldItemStack.getItem() == ModRegistry.CHOCOBO_WHISTLE.get() && !this.isBaby()) {
            if (isOwnedBy(player)) {
                if (this.followingmrhuman == 3) {
                    this.playSound(ModSounds.WHISTLE_SOUND_FOLLOW.get(), 1.0F, 1.0F);
                    this.setNoAi(false);
                    this.goalSelector.addGoal(0, this.follow);
                    followingmrhuman = 1;
                    player.displayClientMessage(new TranslationTextComponent(Chococraft.MODID + ".entity_chocobo.chocobo_followcmd"), true);
                } else if (this.followingmrhuman == 1) {
                    this.playSound(ModSounds.WHISTLE_SOUND_WANDER.get(), 1.0F, 1.0F);
                    this.goalSelector.removeGoal(this.follow);
                    followingmrhuman = 2;
                    player.displayClientMessage(new TranslationTextComponent(Chococraft.MODID + ".entity_chocobo.chocobo_wandercmd"), true);
                } else if (this.followingmrhuman == 2) {
                    this.playSound(ModSounds.WHISTLE_SOUND_STAY.get(), 1.0F, 1.0F);
                    this.setNoAi(true);
                    followingmrhuman = 3;
                    player.displayClientMessage(new TranslationTextComponent(Chococraft.MODID + ".entity_chocobo.chocobo_staycmd"), true);
                }
            } else {
                player.displayClientMessage(new TranslationTextComponent(Chococraft.MODID + ".entity_chocobo.not_owner"), true);
            }
            return ActionResultType.SUCCESS;
        }

        if (this.isTame() && !this.isInLove() && heldItemStack.getItem() == ModRegistry.LOVELY_GYSAHL_GREEN.get() && !this.isBaby()) {
            this.usePlayerItem(player, player.inventory.getSelected());
            this.setInLove(player);
            return ActionResultType.SUCCESS;
        }

        if (heldItemStack.getItem() instanceof ChocoboSaddleItem && this.isTame() && !this.isSaddled() && !this.isBaby()) {
            this.saddleItemStackHandler.setStackInSlot(0, heldItemStack.copy().split(1));
            this.setSaddleType(heldItemStack);
            this.usePlayerItem(player, heldItemStack);
            return ActionResultType.SUCCESS;
        }

        if (this.isTame() && !heldItemStack.isEmpty()) {
            Optional<ChocoboColor> color = ChocoboColor.getColorForItemstack(heldItemStack);
            if (color.isPresent()) {
                if (isOwnedBy(player)) {
                    this.usePlayerItem(player, heldItemStack);
                    this.setChocoboColor(color.get());
                } else {
                    player.displayClientMessage(new TranslationTextComponent(Chococraft.MODID + ".entity_chocobo.not_owner"), true);
                }
                return ActionResultType.SUCCESS;
            }
        }

        if (this.isTame() && heldItemStack.getItem() == Items.NAME_TAG && !isOwnedBy(player)) {
            player.displayClientMessage(new TranslationTextComponent(Chococraft.MODID + ".entity_chocobo.not_owner"), true);
            return ActionResultType.SUCCESS;
        }

        return super.interactAt(player, vec, hand);
    }

    private void displayChocoboInventory(ServerPlayerEntity player) {
        if (player.containerMenu != player.inventoryMenu) {
            player.closeContainer();
        }

        player.nextContainerCounter();
        PacketManager.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new OpenChocoboGuiMessage(this, player.containerCounter));

        player.containerMenu = new SaddleBagContainer(player.containerCounter, player.inventory, this);
        player.containerMenu.addSlotListener(player);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.player.PlayerContainerEvent.Open(player, player.containerMenu));
    }

    private void reconfigureInventory(ItemStack oldSaddle, ItemStack newSaddle) {

        if (!this.getCommandSenderWorld().isClientSide) {
            // TODO: Handle resizing. ItemStackHandler#setSize() clears the internal inventory!
            for (int i = 0; i < this.chocoboInventory.getSlots(); i++) {
                if (this.isAlive()) {
                    ItemStack stack = this.chocoboInventory.extractItem(i, Integer.MAX_VALUE, false);
                    InventoryHelper.dropItemStack(this.getCommandSenderWorld(), this.getX(), this.getY() + .5, this.getZ(), stack);
                }
            }
        }

        if(!newSaddle.isEmpty() && newSaddle.getItem() instanceof ChocoboSaddleItem) {
            this.chocoboInventory.setSize(((ChocoboSaddleItem)newSaddle.getItem()).getInventorySize());
        }

        for (PlayerEntity player : level.players()) {
            if (player.containerMenu instanceof SaddleBagContainer)
                ((SaddleBagContainer) player.containerMenu).refreshSlots(this, player.inventory);
        }
    }

    @Override
    protected void dropFromLootTable(DamageSource damageSourceIn, boolean attackedRecently) {
        super.dropFromLootTable(damageSourceIn, attackedRecently);

        if (this.chocoboInventory != null && this.isSaddled()) {
            for (int i = 0; i < this.chocoboInventory.getSlots(); i++) {
                if (!this.chocoboInventory.getStackInSlot(i).isEmpty())
                    this.spawnAtLocation(this.chocoboInventory.getStackInSlot(i), 0.0f);
            }
        }
    }

    protected SoundEvent getAmbientSound() {
        return ModSounds.AMBIENT_SOUND.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSounds.AMBIENT_SOUND.get();
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.AMBIENT_SOUND.get();
    }

    @Override
    protected float getSoundVolume() {
        return .6f;
    }

    @Override
    public int getAmbientSoundInterval() {
        return (24 * (int) (Math.random() * 100));
    }

    @Override
    public boolean checkSpawnRules(IWorld worldIn, SpawnReason spawnReasonIn) {
        if (BiomeDictionary.getBiomes(BiomeDictionary.Type.NETHER).contains(this.level.getBiome((new BlockPos(blockPosition())).below())))
            return true;

        return super.checkSpawnRules(worldIn, spawnReasonIn);
    }

    @Override
    protected void reassessTameGoals() {
        super.reassessTameGoals();
        if(isTame()) {
            goalSelector.removeGoal(chocoboAvoidPlayerGoal);
        } else {
            goalSelector.addGoal(5, chocoboAvoidPlayerGoal);
        }
    }
}
