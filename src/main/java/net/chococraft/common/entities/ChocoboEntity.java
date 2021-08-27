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
import net.chococraft.common.items.ChocoboSaddleItem;
import net.chococraft.common.network.PacketManager;
import net.chococraft.common.network.packets.OpenChocoboGuiMessage;
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
import net.minecraft.inventory.container.HorseInventoryContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SOpenHorseWindowPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
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
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class ChocoboEntity extends TameableEntity {
    public static final String NBTKEY_CHOCOBO_COLOR = "Color";
    public static final String NBTKEY_CHOCOBO_IS_MALE = "Male";
    public static final String NBTKEY_MOVEMENTTYPE = "MovementType";
    public static final String NBTKEY_SADDLE_ITEM = "Saddle";
    public static final String NBTKEY_INVENTORY = "Inventory";
    public static final String NBTKEY_NEST_POSITION = "NestPos";
    public static final String NBTKEY_CHOCOBO_GENERATION = "Generation";
    public static final String NBTKEY_CHOCOBO_STAMINA = "Stamina";
    public static final String NBTKEY_CHOCOBO_CAN_FLY = "CanFly";
    public static final String NBTKEY_CHOCOBO_CAN_GLIDE = "CanGlide";
    public static final String NBTKEY_CHOCOBO_CAN_SPRINT = "CanSprint";
    public static final String NBTKEY_CHOCOBO_CAN_DIVE = "CanDive";

    private static final byte CAN_SPRINT_BIT = 0b0001;
    private static final byte CAN_DIVE_BIT = 0b0010;
    private static final byte CAN_GLIDE_BIT = 0b0100;
    private static final byte CAN_FLY_BIT = 0b1000;

    private static final DataParameter<ChocoboColor> PARAM_COLOR = EntityDataManager.createKey(ChocoboEntity.class, EntityDataSerializers.CHOCOBO_COLOR);
    private static final DataParameter<Boolean> PARAM_IS_MALE = EntityDataManager.createKey(ChocoboEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<MovementType> PARAM_MOVEMENT_TYPE = EntityDataManager.createKey(ChocoboEntity.class, EntityDataSerializers.MOVEMENT_TYPE);
    private static final DataParameter<ItemStack> PARAM_SADDLE_ITEM = EntityDataManager.createKey(ChocoboEntity.class, DataSerializers.ITEMSTACK);

    private final static DataParameter<Integer> PARAM_GENERATION = EntityDataManager.createKey(ChocoboEntity.class, DataSerializers.VARINT);
    private final static DataParameter<Float> PARAM_STAMINA = EntityDataManager.createKey(ChocoboEntity.class, DataSerializers.FLOAT);
    private final static DataParameter<Byte> PARAM_ABILITY_MASK = EntityDataManager.createKey(ChocoboEntity.class, DataSerializers.BYTE);

    private static final UUID CHOCOBO_SPRINTING_BOOST_ID = UUID.fromString("03ba3167-393e-4362-92b8-909841047640");
    private static final AttributeModifier CHOCOBO_SPRINTING_SPEED_BOOST = (new AttributeModifier(CHOCOBO_SPRINTING_BOOST_ID, "Chocobo sprinting speed boost", 1, Operation.MULTIPLY_BASE));

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

    public float wingRotation;
    public float destPos;
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
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, false, Ingredient.fromItems(ModRegistry.GYSAHL_GREEN.get())));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(1, new SwimGoal(this));
    }

    private final FollowOwnerGoal follow = new FollowOwnerGoal(this, 2.0D, 3.0F, 10.0F, false);
    public float followingmrhuman = 2;

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(ModAttributes.MAX_STAMINA.get(), ChocoConfig.COMMON.defaultStamina.get())
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, ChocoConfig.COMMON.defaultSpeed.get() / 100f)
                .createMutableAttribute(Attributes.MAX_HEALTH, ChocoConfig.COMMON.defaultHealth.get());
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(PARAM_COLOR, ChocoboColor.YELLOW);
        this.dataManager.register(PARAM_IS_MALE, false);
        this.dataManager.register(PARAM_MOVEMENT_TYPE, MovementType.WANDER);
        this.dataManager.register(PARAM_SADDLE_ITEM, ItemStack.EMPTY);

        this.dataManager.register(PARAM_STAMINA, (float) ChocoConfig.COMMON.defaultStamina.get());
        this.dataManager.register(PARAM_GENERATION, 0);
        this.dataManager.register(PARAM_ABILITY_MASK, (byte) 0);
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setMale(this.world.rand.nextBoolean());
        if (BiomeDictionary.getBiomes(BiomeDictionary.Type.NETHER).contains(this.world.getBiome((new BlockPos(getPosition())).down())))
            this.setChocoboColor(ChocoboColor.FLAME);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public boolean canBeSteered() {
        return this.isTamed();
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
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
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
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
        return this.dataManager.get(PARAM_COLOR);
    }

    public void setChocoboColor(ChocoboColor color) {
        this.dataManager.set(PARAM_COLOR, color);
    }

    @Override
    public boolean isImmuneToFire() {
        return getChocoboColor() == ChocoboColor.FLAME;
    }

    public boolean isMale() {
        return this.dataManager.get(PARAM_IS_MALE);
    }

    public void setMale(boolean isMale) {
        this.dataManager.set(PARAM_IS_MALE, isMale);
    }

    public MovementType getMovementType() {
        return this.dataManager.get(PARAM_MOVEMENT_TYPE);
    }

    public void setMovementType(MovementType type) {
        this.dataManager.set(PARAM_MOVEMENT_TYPE, type);
    }

    public boolean isSaddled() {
        return !this.getSaddle().isEmpty();
    }

    public ItemStack getSaddle() {
        return this.dataManager.get(PARAM_SADDLE_ITEM);
    }

    private void setSaddleType(ItemStack saddleStack) {
        ItemStack oldStack = getSaddle();
        ItemStack newStack = saddleStack.copy();
        if (getSaddle().isEmpty() || getSaddle().getItem() != newStack.getItem()) {
            this.dataManager.set(PARAM_SADDLE_ITEM, newStack);
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
        return this.dataManager.get(PARAM_STAMINA);
    }

    public void setStamina(float value) {
        this.dataManager.set(PARAM_STAMINA, value);
    }

    public float getStaminaPercentage() {
        return (float) (this.getStamina() / this.getAttribute(ModAttributes.MAX_STAMINA.get()).getValue());
    }

    public int getGeneration() {
        return this.dataManager.get(PARAM_GENERATION);
    }

    public void setGeneration(int value) {
        this.dataManager.set(PARAM_GENERATION, value);
    }

    private boolean useStamina(float value) {
        if (value == 0) return true;
        float curStamina = this.dataManager.get(PARAM_STAMINA);
        if (curStamina < value) return false;

        float maxStamina = (float) this.getAttribute(ModAttributes.MAX_STAMINA.get()).getValue();
        float newStamina = MathHelper.clamp(curStamina - value, 0, maxStamina);
        this.dataManager.set(PARAM_STAMINA, newStamina);
        return true;
    }

    public boolean canFly() {
        return (this.dataManager.get(PARAM_ABILITY_MASK) & CAN_FLY_BIT) > 0;
    }

    public void setCanFly(boolean state) {
        this.setAbilityMaskBit(CAN_FLY_BIT, state);
    }

    public boolean canGlide() {
        return (this.dataManager.get(PARAM_ABILITY_MASK) & CAN_GLIDE_BIT) > 0;
    }

    public void setCanGlide(boolean state) {
        this.setAbilityMaskBit(CAN_GLIDE_BIT, state);
    }

    public boolean canSprint() {
        return (this.dataManager.get(PARAM_ABILITY_MASK) & CAN_SPRINT_BIT) > 0;
    }

    public void setCanSprint(boolean state) {
        this.setAbilityMaskBit(CAN_SPRINT_BIT, state);
    }

    public boolean canDive() {
        return (this.dataManager.get(PARAM_ABILITY_MASK) & CAN_DIVE_BIT) > 0;
    }

    public void setCanDive(boolean state) {
        this.setAbilityMaskBit(CAN_DIVE_BIT, state);
    }

    private void setAbilityMaskBit(int bit, boolean state) {
        int value = this.dataManager.get(PARAM_ABILITY_MASK);
        this.dataManager.set(PARAM_ABILITY_MASK, (byte) (state ? value | bit : value & ~bit));
    }
    //endregion

    @Override
    public double getMountedYOffset() {
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

            this.prevRotationYaw = rider.rotationYaw;
            this.prevRotationPitch = rider.rotationPitch;
            this.rotationYaw = rider.rotationYaw;
            this.rotationPitch = rider.rotationPitch;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.rotationYawHead = this.rotationYaw;
            this.renderYawOffset = this.rotationYaw;

            newVector = new Vector3d(rider.moveStrafing * 0.5F, newVector.y, rider.moveForward); //Strafe - Vertical - Forward

            // reduce movement speed by 75% if moving backwards
            if (newVector.getZ() <= 0.0D)
                newVector = new Vector3d(newVector.x, newVector.y, newVector.z * 0.25F);

            if (this.onGround)
                this.isChocoboJumping = false;

            if ((this.canGlide() || this.canFly()) && (!rider.isInWater())) {
                this.jumpMovementFactor = .07f;
            } else {
                if ((rider.isInWater()) || (!this.canGlide() && !this.canFly())) {
                    this.jumpMovementFactor = .05f;
                }
            }

            if (this.canPassengerSteer()) {
                if (rider.isJumping) {
                    if (rider.isSprinting() && this.canFly() && !rider.isInWater() && this.useStamina(ChocoConfig.COMMON.flyStaminaCost.get().floatValue())) {
                        // flight logic
                        Vector3d motion = getMotion();
                        double groundValue = this.onGround ? .5F : .1F;
                        setMotion(new Vector3d(motion.x, motion.y + groundValue, motion.z));
                        if (getMotion().y > 0.5f)
                            setMotion(new Vector3d(motion.x, 0.5f, motion.z));

                        this.setSprinting(false);
                    } else {
                        // jump logic
                        if (!this.isChocoboJumping && this.onGround && this.useStamina(ChocoConfig.COMMON.jumpStaminaCost.get().floatValue())) {
                            Vector3d motion = getMotion();
                            setMotion(new Vector3d(motion.x, .6f, motion.z));
                            this.isChocoboJumping = true;
                        }
                    }
                }

                if (rider.isInWater()) {
                    Vector3d motion = getMotion();
                    if (this.canDive()) {
                        if (rider.isSneaking()) {
                            setMotion(new Vector3d(motion.x, motion.y - 0.05f, motion.z));
                            if (this.getMotion().y < -0.7f)
                                setMotion(new Vector3d(motion.x, 0.7f, motion.z));
                        }

                        if (rider.isJumping) {
                            setMotion(new Vector3d(motion.x, .5f, motion.z));
                        }

                        this.inWater = false;
                        this.setSprinting(false);
                    } else {
                        if (rider.isJumping) {
                            setMotion(new Vector3d(motion.x, .5f, motion.z));
                        } else if (this.getMotion().y < 0) {
                            int distance = WorldUtils.getDistanceToSurface(this.getPosition(), this.getEntityWorld());
                            if (distance > 0)
                                setMotion(new Vector3d(motion.x, .01f + Math.min(0.05f * distance, 0.7), motion.z));
                        }
                    }
                }

                if (!this.onGround && !this.isInWater() && !rider.isSneaking() && this.getMotion().y < 0 && this.canGlide() &&
                        this.useStamina(ChocoConfig.COMMON.glideStaminaCost.get().floatValue())) {
                    Vector3d motion = getMotion();
                    setMotion(new Vector3d(motion.x, motion.y * 0.65F, motion.z));
                }

                if ((this.isSprinting() && !this.useStamina(ChocoConfig.COMMON.sprintStaminaCost.get().floatValue())) || (this.isSprinting() &&
                        this.isInWater() && this.useStamina(ChocoConfig.COMMON.sprintStaminaCost.get().floatValue())) || (this.isSprinting() &&
                        !this.canSprint() && this.useStamina(ChocoConfig.COMMON.sprintStaminaCost.get().floatValue()))) {
                    this.setSprinting(false);
                }

                this.setAIMoveSpeed((float) this.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
                super.travel(newVector);
            }
        } else {
            super.travel(newVector);
        }
    }

    @Override
    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
        if (passenger instanceof MobEntity && this.getControllingPassenger() == passenger) {
            this.renderYawOffset = ((LivingEntity) passenger).renderYawOffset;
        }
    }

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return null;
    }

    @Override
    public boolean canMateWith(AnimalEntity otherAnimal) {
        if (otherAnimal == this || !(otherAnimal instanceof ChocoboEntity)) return false;
        if (!this.isInLove() || !otherAnimal.isInLove()) return false;
        ChocoboEntity otherChocobo = (ChocoboEntity) otherAnimal;
        return otherChocobo.isMale() != this.isMale();
    }

    @Override
    public void setSprinting(boolean sprinting) {
        this.setFlag(3, sprinting);
        ModifiableAttributeInstance attributeInstance = this.getAttribute(Attributes.MOVEMENT_SPEED);

        if (attributeInstance.getModifier(CHOCOBO_SPRINTING_BOOST_ID) != null) {
            attributeInstance.removeModifier(CHOCOBO_SPRINTING_SPEED_BOOST);
        }

        if (sprinting && this.canSprint()) {
            attributeInstance.applyNonPersistentModifier(CHOCOBO_SPRINTING_SPEED_BOOST);
        }
    }

    public void dropFeather() {
        if (this.getEntityWorld().isRemote)
            return;

        if (this.isChild())
            return;

        this.entityDropItem(new ItemStack(ModRegistry.CHOCOBO_FEATHER.get(), 1), 0.0F);
    }

    public int TimeSinceFeatherChance = 0;

    @Override
    protected boolean canBeRidden(Entity entityIn) {
        return !this.getSaddle().isEmpty() && super.canBeRidden(entityIn);
    }

    @Override
    public void livingTick() {
        super.livingTick();

        this.setRotation(this.rotationYaw, this.rotationPitch);

        this.regenerateStamina();

        this.stepHeight = 1f;
        this.fallDistance = 0f;

        if (this.TimeSinceFeatherChance == 3000) {
            this.TimeSinceFeatherChance = 0;

            if ((float) Math.random() < .25) {
                this.dropFeather();
            }
        } else {
            this.TimeSinceFeatherChance++;
        }

        if (!this.getEntityWorld().isRemote) {
            if (this.ticksExisted % 60 == 0)
            {
                if (this.canDive()) {
                    this.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING, 100, 0, true, false));
                    if (this.isBeingRidden()) {
                        Entity controller = this.getControllingPassenger();
                        if (controller instanceof PlayerEntity) {
                            ((PlayerEntity) controller).addPotionEffect(new EffectInstance(Effects.WATER_BREATHING, 100, 0, true, false));
                        }
                    }
                }
                if (this.getChocoboColor() == ChocoboColor.FLAME) {
                    this.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 100, 0, true, false));
                    if (this.isBeingRidden()) {
                        Entity controller = this.getControllingPassenger();
                        if (controller instanceof PlayerEntity) {
                            ((PlayerEntity) controller).addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 100, 0, true, false));
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
                this.prevLimbSwingAmount = this.limbSwingAmount;
                double d1 = this.getPosX() - this.prevPosX;
                double d0 = this.getPosZ() - this.prevPosZ;
                float f4 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;

                if (f4 > 1.0F) {
                    f4 = 1.0F;
                }

                this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
                this.limbSwing += this.limbSwingAmount;
            } else {
                this.limbSwing = 0;
                this.limbSwingAmount = 0;
                this.prevLimbSwingAmount = 0;
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
        Vector3d motion = getMotion();
        if (motion.x != 0 || motion.z != 0)
            regen *= 0.85;

        // TODO: implement regen bonus (another IAttribute?)
        this.useStamina(-regen);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Override
    public ActionResultType applyPlayerInteraction(PlayerEntity player, Vector3d vec, Hand hand) {
        ItemStack heldItemStack = player.getHeldItem(hand);

        if (heldItemStack.getItem() == ModRegistry.CHOCOPEDIA.get()) {
            if(world.isRemote) {
                net.chococraft.client.gui.ChocoboInfoScreen.openScreen(this, player);
            }
            return ActionResultType.SUCCESS;
        }

        if (this.isTamed() && player.isSneaking() && !this.isChild()) {
            if (player instanceof ServerPlayerEntity)
                this.displayChocoboInventory((ServerPlayerEntity) player);
            return ActionResultType.SUCCESS;
        }

        if (this.getEntityWorld().isRemote)
            return ActionResultType.SUCCESS;

        if (this.isSaddled() && heldItemStack.isEmpty() && !player.isSneaking() && !this.isChild()) {
            player.startRiding(this);
            return ActionResultType.SUCCESS;
        }

        if (!this.isTamed() && heldItemStack.getItem() == ModRegistry.GYSAHL_GREEN_ITEM.get()) {
            this.consumeItemFromStack(player, player.inventory.getCurrentItem());
            if ((float) Math.random() < ChocoConfig.COMMON.tameChance.get().floatValue()) {
                this.setOwnerId(player.getUniqueID());
                this.setTamed(true);
                player.sendStatusMessage(new TranslationTextComponent(Chococraft.MODID + ".entity_chocobo.tame_success"), true);
            } else {
                player.sendStatusMessage(new TranslationTextComponent(Chococraft.MODID + ".entity_chocobo.tame_fail"), true);
            }
            return ActionResultType.SUCCESS;
        }

        if (this.isTamed() && heldItemStack.getItem() == ModRegistry.GYSAHL_GREEN_ITEM.get()) {
            if (getHealth() != getMaxHealth()) {
                this.consumeItemFromStack(player, player.inventory.getCurrentItem());
                heal(5);
            } else {
                player.sendStatusMessage(new TranslationTextComponent(Chococraft.MODID + ".entity_chocobo.heal_fail"), true);
            }
        }

        if (this.isTamed() && heldItemStack.getItem() == ModRegistry.CHOCOBO_WHISTLE.get() && !this.isChild()) {
            if (isOwner(player)) {
                if (this.followingmrhuman == 3) {
                    this.playSound(ModSounds.WHISTLE_SOUND_FOLLOW.get(), 1.0F, 1.0F);
                    this.setNoAI(false);
                    this.goalSelector.addGoal(0, this.follow);
                    followingmrhuman = 1;
                    player.sendStatusMessage(new TranslationTextComponent(Chococraft.MODID + ".entity_chocobo.chocobo_followcmd"), true);
                } else if (this.followingmrhuman == 1) {
                    this.playSound(ModSounds.WHISTLE_SOUND_WANDER.get(), 1.0F, 1.0F);
                    this.goalSelector.removeGoal(this.follow);
                    followingmrhuman = 2;
                    player.sendStatusMessage(new TranslationTextComponent(Chococraft.MODID + ".entity_chocobo.chocobo_wandercmd"), true);
                } else if (this.followingmrhuman == 2) {
                    this.playSound(ModSounds.WHISTLE_SOUND_STAY.get(), 1.0F, 1.0F);
                    this.setNoAI(true);
                    followingmrhuman = 3;
                    player.sendStatusMessage(new TranslationTextComponent(Chococraft.MODID + ".entity_chocobo.chocobo_staycmd"), true);
                }
            } else {
                player.sendStatusMessage(new TranslationTextComponent(Chococraft.MODID + ".entity_chocobo.not_owner"), true);
            }
            return ActionResultType.SUCCESS;
        }

        if (this.isTamed() && !this.isInLove() && heldItemStack.getItem() == ModRegistry.LOVELY_GYSAHL_GREEN.get() && !this.isChild()) {
            this.consumeItemFromStack(player, player.inventory.getCurrentItem());
            this.setInLove(player);
            return ActionResultType.SUCCESS;
        }

        if (heldItemStack.getItem() instanceof ChocoboSaddleItem && this.isTamed() && !this.isSaddled() && !this.isChild()) {
            this.saddleItemStackHandler.setStackInSlot(0, heldItemStack.copy().split(1));
            this.setSaddleType(heldItemStack);
            this.consumeItemFromStack(player, heldItemStack);
            return ActionResultType.SUCCESS;
        }

        if (this.isTamed() && !heldItemStack.isEmpty()) {
            Optional<ChocoboColor> color = ChocoboColor.getColorForItemstack(heldItemStack);
            if (color.isPresent()) {
                if (isOwner(player)) {
                    this.consumeItemFromStack(player, heldItemStack);
                    this.setChocoboColor(color.get());
                } else {
                    player.sendStatusMessage(new TranslationTextComponent(Chococraft.MODID + ".entity_chocobo.not_owner"), true);
                }
                return ActionResultType.SUCCESS;
            }
        }

        if (this.isTamed() && heldItemStack.getItem() == Items.NAME_TAG && !isOwner(player)) {
            player.sendStatusMessage(new TranslationTextComponent(Chococraft.MODID + ".entity_chocobo.not_owner"), true);
            return ActionResultType.SUCCESS;
        }

        return super.applyPlayerInteraction(player, vec, hand);
    }

    private void displayChocoboInventory(ServerPlayerEntity player) {
        if (player.openContainer != player.container) {
            player.closeScreen();
        }

        player.getNextWindowId();
        PacketManager.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new OpenChocoboGuiMessage(this, player.currentWindowId));

        player.openContainer = new SaddleBagContainer(player.currentWindowId, player.inventory, this);
        player.openContainer.addListener(player);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.player.PlayerContainerEvent.Open(player, player.openContainer));
    }

    private void reconfigureInventory(ItemStack oldSaddle, ItemStack newSaddle) {

        if (!this.getEntityWorld().isRemote) {
            // TODO: Handle resizing. ItemStackHandler#setSize() clears the internal inventory!
            for (int i = 0; i < this.chocoboInventory.getSlots(); i++) {
                if (this.isAlive()) {
                    ItemStack stack = this.chocoboInventory.extractItem(i, Integer.MAX_VALUE, false);
                    InventoryHelper.spawnItemStack(this.getEntityWorld(), this.getPosX(), this.getPosY() + .5, this.getPosZ(), stack);
                }
            }
        }

        if(!newSaddle.isEmpty() && newSaddle.getItem() instanceof ChocoboSaddleItem) {
            this.chocoboInventory.setSize(((ChocoboSaddleItem)newSaddle.getItem()).getInventorySize());
        }

        for (PlayerEntity player : world.getPlayers()) {
            if (player.openContainer instanceof SaddleBagContainer)
                ((SaddleBagContainer) player.openContainer).refreshSlots(this, player.inventory);
        }
    }

    @Override
    protected void dropLoot(DamageSource damageSourceIn, boolean attackedRecently) {
        super.dropLoot(damageSourceIn, attackedRecently);

        if (this.chocoboInventory != null && this.isSaddled()) {
            for (int i = 0; i < this.chocoboInventory.getSlots(); i++) {
                if (!this.chocoboInventory.getStackInSlot(i).isEmpty())
                    this.entityDropItem(this.chocoboInventory.getStackInSlot(i), 0.0f);
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
    public int getTalkInterval() {
        return (24 * (int) (Math.random() * 100));
    }

    @Override
    public boolean canSpawn(IWorld worldIn, SpawnReason spawnReasonIn) {
        if (BiomeDictionary.getBiomes(BiomeDictionary.Type.NETHER).contains(this.world.getBiome((new BlockPos(getPosition())).down())))
            return true;

        return super.canSpawn(worldIn, spawnReasonIn);
    }
}
