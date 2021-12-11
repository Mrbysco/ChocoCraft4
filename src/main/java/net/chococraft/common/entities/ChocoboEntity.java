package net.chococraft.common.entities;

import net.chococraft.Chococraft;
import net.chococraft.common.ChocoConfig;
import net.chococraft.common.entities.breeding.ChocoboMateGoal;
import net.chococraft.common.entities.properties.ChocoboColor;
import net.chococraft.common.entities.properties.ModDataSerializers;
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
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class ChocoboEntity extends TamableAnimal {
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

    private static final EntityDataAccessor<ChocoboColor> PARAM_COLOR = SynchedEntityData.defineId(ChocoboEntity.class, ModDataSerializers.CHOCOBO_COLOR);
    private static final EntityDataAccessor<Boolean> PARAM_IS_MALE = SynchedEntityData.defineId(ChocoboEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<MovementType> PARAM_MOVEMENT_TYPE = SynchedEntityData.defineId(ChocoboEntity.class, ModDataSerializers.MOVEMENT_TYPE);
    private static final EntityDataAccessor<ItemStack> PARAM_SADDLE_ITEM = SynchedEntityData.defineId(ChocoboEntity.class, EntityDataSerializers.ITEM_STACK);

    private final static EntityDataAccessor<Integer> PARAM_GENERATION = SynchedEntityData.defineId(ChocoboEntity.class, EntityDataSerializers.INT);
    private final static EntityDataAccessor<Float> PARAM_STAMINA = SynchedEntityData.defineId(ChocoboEntity.class, EntityDataSerializers.FLOAT);
    private final static EntityDataAccessor<Byte> PARAM_ABILITY_MASK = SynchedEntityData.defineId(ChocoboEntity.class, EntityDataSerializers.BYTE);

    private static final UUID CHOCOBO_SPRINTING_BOOST_ID = UUID.fromString("03ba3167-393e-4362-92b8-909841047640");
    private static final AttributeModifier CHOCOBO_SPRINTING_SPEED_BOOST = (new AttributeModifier(CHOCOBO_SPRINTING_BOOST_ID, "Chocobo sprinting speed boost", 1, Operation.MULTIPLY_BASE));

    private AvoidEntityGoal chocoboAvoidPlayerGoal;
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

    public ChocoboEntity(EntityType<? extends ChocoboEntity> type, Level world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new ChocoboMateGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(ModRegistry.GYSAHL_GREEN.get()), false));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(1, new FloatGoal(this));
    }

    private final FollowOwnerGoal follow = new FollowOwnerGoal(this, 2.0D, 3.0F, 10.0F, false);
    public float followingmrhuman = 2;

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
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
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        this.setMale(this.level.random.nextBoolean());
        final Biome currentBiome = this.level.getBiome(blockPosition().below());
        if (currentBiome.getBiomeCategory() == Biome.BiomeCategory.NETHER) {
            this.setChocoboColor(ChocoboColor.FLAME);
        }
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public boolean canBeControlledByRider() {
        return this.isTame();
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setChocoboColor(ChocoboColor.values()[compound.getByte(NBTKEY_CHOCOBO_COLOR)]);
        this.setMale(compound.getBoolean(NBTKEY_CHOCOBO_IS_MALE));
        this.setMovementType(MovementType.values()[compound.getByte(NBTKEY_MOVEMENTTYPE)]);
        this.saddleItemStackHandler.deserializeNBT(compound.getCompound(NBTKEY_SADDLE_ITEM));

        if (!getSaddle().isEmpty())
            this.chocoboInventory.deserializeNBT(compound.getCompound(NBTKEY_INVENTORY));

        if (compound.contains(NBTKEY_NEST_POSITION))
            this.nestPos = NbtUtils.readBlockPos(compound.getCompound(NBTKEY_NEST_POSITION));

        this.setGeneration(compound.getInt(NBTKEY_CHOCOBO_GENERATION));
        this.setStamina(compound.getFloat(NBTKEY_CHOCOBO_STAMINA));

        this.setCanFly(compound.getBoolean(NBTKEY_CHOCOBO_CAN_FLY));
        this.setCanGlide(compound.getBoolean(NBTKEY_CHOCOBO_CAN_GLIDE));
        this.setCanSprint(compound.getBoolean(NBTKEY_CHOCOBO_CAN_SPRINT));
        this.setCanDive(compound.getBoolean(NBTKEY_CHOCOBO_CAN_DIVE));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putByte(NBTKEY_CHOCOBO_COLOR, (byte) this.getChocoboColor().ordinal());
        compound.putBoolean(NBTKEY_CHOCOBO_IS_MALE, this.isMale());
        compound.putByte(NBTKEY_MOVEMENTTYPE, (byte) this.getMovementType().ordinal());
        compound.put(NBTKEY_SADDLE_ITEM, this.saddleItemStackHandler.serializeNBT());

        if (!getSaddle().isEmpty())
            compound.put(NBTKEY_INVENTORY, this.chocoboInventory.serializeNBT());

        if (this.nestPos != null)
            compound.put(NBTKEY_NEST_POSITION, NbtUtils.writeBlockPos(this.nestPos));

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
        float newStamina = Mth.clamp(curStamina - value, 0, maxStamina);
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

    @Override
    protected boolean updateInWaterStateAndDoFluidPushing() {
        this.fluidHeight.clear();
        this.updateInWaterStateAndDoWaterCurrentPushing();
        double d0 = this.level.dimensionType().ultraWarm() ? 0.007D : 0.0023333333333333335D;
        boolean flag = this.updateFluidHeightAndDoFluidPushing(FluidTags.LAVA, d0);
        return this.isInWater() || flag;
    }

    private void updateInWaterStateAndDoWaterCurrentPushing() {
        if (this.getVehicle() instanceof ChocoboEntity) {
            this.wasTouchingWater = false;
        } else if (this.updateFluidHeightAndDoFluidPushing(FluidTags.WATER, 0.014D)) {
            if (!this.wasTouchingWater && !this.firstTick) {
                this.doWaterSplashEffect();
            }

            this.fallDistance = 0.0F;
            this.wasTouchingWater = true;
            this.clearFire();
        } else {
            this.wasTouchingWater = false;
        }
    }

    @Override
    public void travel(Vec3 travelVector) {
        Vec3 newVector = travelVector;
        if (this.getControllingPassenger() instanceof Player rider) {
            this.yRotO = rider.getYRot();
            this.xRotO = rider.getXRot();
            this.setYRot(rider.getYRot());
            this.setXRot(rider.getXRot());
            this.setRot(this.getYRot(), this.getXRot());
            this.yHeadRot = this.getYRot();
            this.yBodyRot = this.getYRot();

            newVector = new Vec3(rider.xxa * 0.5F, newVector.y, rider.zza); //Strafe - Vertical - Forward

            // reduce movement speed by 75% if moving backwards
            if (newVector.z() <= 0.0D)
                newVector = new Vec3(newVector.x, newVector.y, newVector.z * 0.25F);

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
                        Vec3 motion = getDeltaMovement();
                        double groundValue = this.onGround ? .5F : .1F;
                        setDeltaMovement(new Vec3(motion.x, motion.y + groundValue, motion.z));
                        if (getDeltaMovement().y > 0.5f)
                            setDeltaMovement(new Vec3(motion.x, 0.5f, motion.z));

                        this.setSprinting(false);
                    } else {
                        // jump logic
                        if (!this.isChocoboJumping && this.onGround && this.useStamina(ChocoConfig.COMMON.jumpStaminaCost.get().floatValue())) {
                            Vec3 motion = getDeltaMovement();
                            setDeltaMovement(new Vec3(motion.x, .6f, motion.z));
                            this.isChocoboJumping = true;
                        }
                    }
                }

                if (rider.isInWater()) {
                    Vec3 motion = getDeltaMovement();
                    if (this.canDive()) {
                        if (rider.isShiftKeyDown()) {
                            setDeltaMovement(new Vec3(motion.x, motion.y - 0.05f, motion.z));
                            if (this.getDeltaMovement().y < -0.7f)
                                setDeltaMovement(new Vec3(motion.x, 0.7f, motion.z));
                        }

                        if (rider.jumping) {
                            setDeltaMovement(new Vec3(motion.x, .5f, motion.z));
                        }

                        this.wasTouchingWater = false;
                        this.setSprinting(false);
                    } else {
                        if (rider.jumping) {
                            setDeltaMovement(new Vec3(motion.x, .5f, motion.z));
                        } else if (this.getDeltaMovement().y < 0) {
                            int distance = WorldUtils.getDistanceToSurface(this.blockPosition(), this.getCommandSenderWorld());
                            if (distance > 0)
                                setDeltaMovement(new Vec3(motion.x, .01f + Math.min(0.05f * distance, 0.7), motion.z));
                        }
                    }
                }

                if (!this.onGround && !this.isInWater() && !rider.isShiftKeyDown() && this.getDeltaMovement().y < 0 && this.canGlide() &&
                        this.useStamina(ChocoConfig.COMMON.glideStaminaCost.get().floatValue())) {
                    Vec3 motion = getDeltaMovement();
                    setDeltaMovement(new Vec3(motion.x, motion.y * 0.65F, motion.z));
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
        if (passenger instanceof Mob && this.getControllingPassenger() == passenger) {
            this.yBodyRot = ((LivingEntity) passenger).yBodyRot;
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob mate) {
        return null;
    }

    @Override
    public boolean canMate(Animal otherAnimal) {
        if (otherAnimal == this || !(otherAnimal instanceof ChocoboEntity otherChocobo)) return false;
        if (!this.isInLove() || !otherAnimal.isInLove()) return false;
        return otherChocobo.isMale() != this.isMale();
    }

    @Override
    public void setSprinting(boolean sprinting) {
        this.setSharedFlag(3, sprinting);
        AttributeInstance attributeInstance = this.getAttribute(Attributes.MOVEMENT_SPEED);

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

        this.setRot(this.getYRot(), this.getXRot());

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
                    this.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 100, 0, true, false));
                    if (this.isVehicle()) {
                        Entity controller = this.getControllingPassenger();
                        if (controller instanceof Player) {
                            ((Player) controller).addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 100, 0, true, false));
                        }
                    }
                }
                if (this.getChocoboColor() == ChocoboColor.FLAME) {
                    this.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100, 0, true, false));
                    if (this.isVehicle()) {
                        Entity controller = this.getControllingPassenger();
                        if (controller instanceof Player) {
                            ((Player) controller).addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100, 0, true, false));
                        }
                    }
                }
            }
        } else {
            // Wing rotations, control packet, client side
            // Client side
            this.destPos += (double) (this.onGround ? -1 : 4) * 0.3D;
            this.destPos = Mth.clamp(destPos, 0f, 1f);

            if (!this.onGround)
                this.wingRotDelta = Math.min(wingRotation, 1f);
            this.wingRotDelta *= 0.9D;
            this.wingRotation += this.wingRotDelta * 2.0F;

            if (this.onGround) {
                this.animationSpeedOld = this.animationSpeed;
                double d1 = this.getX() - this.xo;
                double d0 = this.getZ() - this.zo;
                float f4 = ((float)Math.sqrt(d1 * d1 + d0 * d0)) * 4.0F;

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
        Vec3 motion = getDeltaMovement();
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
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
        ItemStack heldItemStack = player.getItemInHand(hand);

        if (heldItemStack.getItem() == ModRegistry.GYSAHL_CAKE.get()) {
            this.usePlayerItem(player, hand, heldItemStack);
            ageBoundaryReached();
            return InteractionResult.SUCCESS;
        }

        if (heldItemStack.getItem() == ModRegistry.CHOCOPEDIA.get()) {
            if(level.isClientSide) {
                net.chococraft.client.gui.ChocoboInfoScreen.openScreen(this, player);
            }
            return InteractionResult.SUCCESS;
        }

        if (this.isTame() && player.isShiftKeyDown() && !this.isBaby()) {
            if (player instanceof ServerPlayer)
                this.displayChocoboInventory((ServerPlayer) player);
            return InteractionResult.SUCCESS;
        }

        if (this.getCommandSenderWorld().isClientSide)
            return InteractionResult.SUCCESS;

        if (this.isSaddled() && heldItemStack.isEmpty() && !player.isShiftKeyDown() && !this.isBaby()) {
            player.startRiding(this);
            return InteractionResult.SUCCESS;
        }

        if (!this.isTame() && heldItemStack.getItem() == ModRegistry.GYSAHL_GREEN_ITEM.get()) {
            this.usePlayerItem(player, hand, player.getInventory().getSelected());
            if ((float) Math.random() < ChocoConfig.COMMON.tameChance.get().floatValue()) {
                this.setOwnerUUID(player.getUUID());
                this.setTame(true);
                player.displayClientMessage(new TranslatableComponent(Chococraft.MODID + ".entity_chocobo.tame_success"), true);
            } else {
                player.displayClientMessage(new TranslatableComponent(Chococraft.MODID + ".entity_chocobo.tame_fail"), true);
            }
            return InteractionResult.SUCCESS;
        }

        if (this.isTame() && heldItemStack.getItem() == ModRegistry.GYSAHL_GREEN_ITEM.get()) {
            if (getHealth() != getMaxHealth()) {
                this.usePlayerItem(player, hand, player.getInventory().getSelected());
                heal(5);
            } else {
                player.displayClientMessage(new TranslatableComponent(Chococraft.MODID + ".entity_chocobo.heal_fail"), true);
            }
        }

        if (this.isTame() && heldItemStack.getItem() == ModRegistry.CHOCOBO_WHISTLE.get() && !this.isBaby()) {
            if (isOwnedBy(player)) {
                if (this.followingmrhuman == 3) {
                    this.playSound(ModSounds.WHISTLE_SOUND_FOLLOW.get(), 1.0F, 1.0F);
                    this.setNoAi(false);
                    this.goalSelector.addGoal(0, this.follow);
                    followingmrhuman = 1;
                    player.displayClientMessage(new TranslatableComponent(Chococraft.MODID + ".entity_chocobo.chocobo_followcmd"), true);
                } else if (this.followingmrhuman == 1) {
                    this.playSound(ModSounds.WHISTLE_SOUND_WANDER.get(), 1.0F, 1.0F);
                    this.goalSelector.removeGoal(this.follow);
                    followingmrhuman = 2;
                    player.displayClientMessage(new TranslatableComponent(Chococraft.MODID + ".entity_chocobo.chocobo_wandercmd"), true);
                } else if (this.followingmrhuman == 2) {
                    this.playSound(ModSounds.WHISTLE_SOUND_STAY.get(), 1.0F, 1.0F);
                    this.setNoAi(true);
                    followingmrhuman = 3;
                    player.displayClientMessage(new TranslatableComponent(Chococraft.MODID + ".entity_chocobo.chocobo_staycmd"), true);
                }
            } else {
                player.displayClientMessage(new TranslatableComponent(Chococraft.MODID + ".entity_chocobo.not_owner"), true);
            }
            return InteractionResult.SUCCESS;
        }

        if (this.isTame() && !this.isInLove() && heldItemStack.getItem() == ModRegistry.LOVELY_GYSAHL_GREEN.get() && !this.isBaby()) {
            this.usePlayerItem(player, hand, player.getInventory().getSelected());
            this.setInLove(player);
            return InteractionResult.SUCCESS;
        }

        if (heldItemStack.getItem() instanceof ChocoboSaddleItem && this.isTame() && !this.isSaddled() && !this.isBaby()) {
            this.saddleItemStackHandler.setStackInSlot(0, heldItemStack.copy().split(1));
            this.setSaddleType(heldItemStack);
            this.usePlayerItem(player, hand, heldItemStack);
            return InteractionResult.SUCCESS;
        }

        if (this.isTame() && !heldItemStack.isEmpty()) {
            Optional<ChocoboColor> color = ChocoboColor.getColorForItemstack(heldItemStack);
            if (color.isPresent()) {
                if (isOwnedBy(player)) {
                    this.usePlayerItem(player, hand, heldItemStack);
                    this.setChocoboColor(color.get());
                } else {
                    player.displayClientMessage(new TranslatableComponent(Chococraft.MODID + ".entity_chocobo.not_owner"), true);
                }
                return InteractionResult.SUCCESS;
            }
        }

        if (this.isTame() && heldItemStack.getItem() == Items.NAME_TAG && !isOwnedBy(player)) {
            player.displayClientMessage(new TranslatableComponent(Chococraft.MODID + ".entity_chocobo.not_owner"), true);
            return InteractionResult.SUCCESS;
        }

        return super.interactAt(player, vec, hand);
    }

    private void displayChocoboInventory(ServerPlayer player) {
        if (player.containerMenu != player.inventoryMenu) {
            player.closeContainer();
        }

        player.nextContainerCounter();
        PacketManager.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new OpenChocoboGuiMessage(this, player.containerCounter));

        player.containerMenu = new SaddleBagContainer(player.containerCounter, player.getInventory(), this);
        player.initMenu(player.containerMenu);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.player.PlayerContainerEvent.Open(player, player.containerMenu));
    }

    private void reconfigureInventory(ItemStack oldSaddle, ItemStack newSaddle) {

        if (!this.getCommandSenderWorld().isClientSide) {
            // TODO: Handle resizing. ItemStackHandler#setSize() clears the internal inventory!
            for (int i = 0; i < this.chocoboInventory.getSlots(); i++) {
                if (this.isAlive()) {
                    ItemStack stack = this.chocoboInventory.extractItem(i, Integer.MAX_VALUE, false);
                    Containers.dropItemStack(this.getCommandSenderWorld(), this.getX(), this.getY() + .5, this.getZ(), stack);
                }
            }
        }

        if(!newSaddle.isEmpty() && newSaddle.getItem() instanceof ChocoboSaddleItem) {
            this.chocoboInventory.setSize(((ChocoboSaddleItem)newSaddle.getItem()).getInventorySize());
        }

        for (Player player : level.players()) {
            if (player.containerMenu instanceof SaddleBagContainer)
                ((SaddleBagContainer) player.containerMenu).refreshSlots(this, player.getInventory());
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
    public boolean checkSpawnRules(LevelAccessor worldIn, MobSpawnType spawnReasonIn) {
        if (BiomeDictionary.getBiomes(BiomeDictionary.Type.NETHER).contains(this.level.getBiome((new BlockPos(blockPosition())).below())))
            return true;

        return super.checkSpawnRules(worldIn, spawnReasonIn);
    }

    @Override
    protected void reassessTameGoals() {
        super.reassessTameGoals();
        if(chocoboAvoidPlayerGoal == null) {
            chocoboAvoidPlayerGoal = new AvoidEntityGoal(this, Player.class, livingEntity -> {
                if(livingEntity instanceof Player player) {
                    int chance = 0;
                    for (ItemStack stack : player.getInventory().armor) {
                        if (stack != null) {
                            if (stack.getItem() instanceof ChocoDisguiseItem)
                                chance += 25;
                        }
                    }

                    return !RandomHelper.getChanceResult(chance);
                }
                return false;
            }, 10.0F, 1.0D, 1.2D, EntitySelector.NO_CREATIVE_OR_SPECTATOR);
        }
        if(isTame()) {
            goalSelector.removeGoal(chocoboAvoidPlayerGoal);
        } else {
            goalSelector.addGoal(5, chocoboAvoidPlayerGoal);
        }
    }
}
