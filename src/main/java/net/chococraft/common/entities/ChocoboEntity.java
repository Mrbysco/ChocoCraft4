package net.chococraft.common.entities;

import net.chococraft.Chococraft;
import net.chococraft.common.config.ChocoConfig;
import net.chococraft.common.entities.breeding.BreedingHelper;
import net.chococraft.common.entities.breeding.ChocoboAbilityInfo;
import net.chococraft.common.entities.goal.ChocoboFollowOwnerGoal;
import net.chococraft.common.entities.goal.ChocoboHealInPenGoal;
import net.chococraft.common.entities.properties.ChocoboColor;
import net.chococraft.common.entities.properties.ModDataSerializers;
import net.chococraft.common.entities.properties.MovementType;
import net.chococraft.common.init.ModEntities;
import net.chococraft.common.init.ModRegistry;
import net.chococraft.common.init.ModSounds;
import net.chococraft.common.inventory.SaddleBagMenu;
import net.chococraft.common.inventory.SaddleItemStackHandler;
import net.chococraft.common.items.ChocoDisguiseItem;
import net.chococraft.common.items.ChocoboSaddleItem;
import net.chococraft.common.network.PacketManager;
import net.chococraft.common.network.packets.OpenChocoboGuiMessage;
import net.chococraft.utils.RandomHelper;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class ChocoboEntity extends TameableEntity {
	private static final String NBTKEY_CHOCOBO_COLOR = "Color";
	private static final String NBTKEY_CHOCOBO_IS_MALE = "Male";
	private static final String NBTKEY_MOVEMENTTYPE = "MovementType";
	private static final String NBTKEY_SADDLE_ITEM = "Saddle";
	private static final String NBTKEY_INVENTORY = "Inventory";
	private static final String NBTKEY_CHOCOBO_GENERATION = "Generation";

	private static final DataParameter<ChocoboColor> PARAM_COLOR = EntityDataManager.defineId(ChocoboEntity.class, ModDataSerializers.CHOCOBO_COLOR);
	private static final DataParameter<Boolean> PARAM_IS_MALE = EntityDataManager.defineId(ChocoboEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> PARAM_FED_GOLD_GYSAHL = EntityDataManager.defineId(ChocoboEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<MovementType> PARAM_MOVEMENT_TYPE = EntityDataManager.defineId(ChocoboEntity.class, ModDataSerializers.MOVEMENT_TYPE);
	private static final DataParameter<ItemStack> PARAM_SADDLE_ITEM = EntityDataManager.defineId(ChocoboEntity.class, DataSerializers.ITEM_STACK);

	private final static DataParameter<Integer> PARAM_GENERATION = EntityDataManager.defineId(ChocoboEntity.class, DataSerializers.INT);

	private AvoidEntityGoal<PlayerEntity> chocoboAvoidPlayerGoal;
	private ChocoboHealInPenGoal healInPenGoal;

	public final ItemStackHandler inventory = new ItemStackHandler(45) {
		@Override
		public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
			if (getSaddle().isEmpty()) {
				return false;
			}
			if (getSaddle().getItem() instanceof ChocoboSaddleItem) {
				ChocoboSaddleItem saddleItem = (ChocoboSaddleItem) getSaddle().getItem();
				switch (saddleItem.getInventorySize()) {
					case 18: {
						return ((slot > 10 && slot < 16) || (slot > 19 && slot < 25) || (slot > 28 && slot < 34)) && super.isItemValid(slot, stack);
					}
					case 45: {
						return super.isItemValid(slot, stack);
					}
					default: {
						return false;
					}
				}
			}


			return super.isItemValid(slot, stack);
		}
	};
	private final LazyOptional<IItemHandler> inventoryHolder = LazyOptional.of(() -> inventory);

	public final SaddleItemStackHandler saddleItemStackHandler = new SaddleItemStackHandler() {
		@Override
		public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
			return stack.isEmpty() || stack.getItem() instanceof ChocoboSaddleItem;
		}

		@Override
		protected void onStackChanged() {
			ChocoboEntity.this.setSaddleType(this.itemStack);
		}
	};
	private final LazyOptional<IItemHandler> saddleHolder = LazyOptional.of(() -> saddleItemStackHandler);

	private float wingRotation;
	private float destPos;
	private float wingRotDelta;

	public int timeSinceFeatherChance;

	public ChocoboEntity(EntityType<? extends ChocoboEntity> type, World world) {
		super(type, world);
		timeSinceFeatherChance = 0;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new ChocoboFollowOwnerGoal(this, 1.0D, 5.0F, 5.0F));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 5.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
	}

	private final FollowOwnerGoal follow = new ChocoboFollowOwnerGoal(this, 2.0D, 3.0F, 10.0F);

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.createMobAttributes()
				.add(Attributes.MOVEMENT_SPEED, 20 / 100f)
				.add(Attributes.FLYING_SPEED, 0.0F)
				.add(Attributes.MAX_HEALTH, 30);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(PARAM_COLOR, ChocoboColor.YELLOW);
		this.entityData.define(PARAM_IS_MALE, false);
		this.entityData.define(PARAM_FED_GOLD_GYSAHL, false);
		this.entityData.define(PARAM_MOVEMENT_TYPE, MovementType.WANDER);
		this.entityData.define(PARAM_SADDLE_ITEM, ItemStack.EMPTY);
		this.entityData.define(PARAM_GENERATION, 0);
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld levelAccessor, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		if (this.level.getBiome(blockPosition().below()).getBiomeCategory() == Biome.Category.NETHER) {
			this.setChocoboColor(ChocoboColor.FLAME);
		}

		finalizeChocobo(this);
		return super.finalizeSpawn(levelAccessor, difficultyIn, reason, spawnDataIn, dataTag);
	}

	private void finalizeChocobo(ChocoboEntity chocobo) {
		chocobo.setMale(random.nextBoolean());
		chocobo.getAttribute(Attributes.MAX_HEALTH).setBaseValue(getChocoboColor().getAbilityInfo().getMaxHP());
		chocobo.setHealth(getMaxHealth());
		chocobo.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(getChocoboColor().getAbilityInfo().getLandSpeed() / 100F);
		chocobo.getAttribute(Attributes.FLYING_SPEED).setBaseValue(getChocoboColor().getAbilityInfo().getAirbornSpeed() / 100F);
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
		this.inventory.deserializeNBT(compound.getCompound(NBTKEY_INVENTORY));

		this.setGeneration(compound.getInt(NBTKEY_CHOCOBO_GENERATION));
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		compound.putByte(NBTKEY_CHOCOBO_COLOR, (byte) this.getChocoboColor().ordinal());
		compound.putBoolean(NBTKEY_CHOCOBO_IS_MALE, this.isMale());
		compound.putByte(NBTKEY_MOVEMENTTYPE, (byte) this.getMovementType().ordinal());
		compound.put(NBTKEY_SADDLE_ITEM, this.saddleItemStackHandler.serializeNBT());
		compound.put(NBTKEY_INVENTORY, this.inventory.serializeNBT());

		compound.putInt(NBTKEY_CHOCOBO_GENERATION, this.getGeneration());
	}

	public ChocoboColor getChocoboColor() {
		return this.entityData.get(PARAM_COLOR);
	}

	public void setChocoboColor(ChocoboColor color) {
		this.entityData.set(PARAM_COLOR, color);
	}

	@Override
	public boolean fireImmune() {
		return getChocoboColor().getAbilityInfo().isImmuneToFire();
	}

	public boolean isMale() {
		return this.entityData.get(PARAM_IS_MALE);
	}

	public void setMale(boolean isMale) {
		this.entityData.set(PARAM_IS_MALE, isMale);
	}

	public boolean isFedGoldGysahl() {
		return this.entityData.get(PARAM_FED_GOLD_GYSAHL);
	}

	public void setFedGoldGysahl(boolean value) {
		this.entityData.set(PARAM_FED_GOLD_GYSAHL, value);
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
		ItemStack newStack = saddleStack;
		ItemStack oldStack = getSaddle();
		if (oldStack.getItem() != newStack.getItem()) {
			this.entityData.set(PARAM_SADDLE_ITEM, newStack.copy());
			this.reconfigureInventory(oldStack, newStack);
		}
	}

	private int getSaddleCount(ItemStack stack) {
		if (stack.getItem() instanceof ChocoboSaddleItem) {
			return ((ChocoboSaddleItem) stack.getItem()).getInventorySize();
		}
		return 0;
	}

	public int getGeneration() {
		return this.entityData.get(PARAM_GENERATION);
	}

	public void setGeneration(int value) {
		this.entityData.set(PARAM_GENERATION, value);
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
		if (this.getVehicle() instanceof BoatEntity) {
			this.wasTouchingWater = false;
		} else if (this.updateFluidHeightAndDoFluidPushing(FluidTags.WATER, 0.014D)) {
			if (!this.wasTouchingWater && !this.firstTick) {
				this.doWaterSplashEffect();
			}

			this.fallDistance = 0;
			this.wasTouchingWater = true;
			this.clearFire();
		} else {
			this.wasTouchingWater = false;
		}
	}

	@Override
	public void travel(Vector3d travelVector) {
		if (this.isAlive() && this.isVehicle() && this.canBeControlledByRider() && this.isSaddled()) {
			LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();
			this.yRot = livingentity.yRot;
			this.yRotO = this.yRot;
			this.xRot = livingentity.xRot * 0.5F;
			this.setRot(this.yRot, this.xRot);
			this.yBodyRot = this.yRot;
			this.yHeadRot = this.yBodyRot;
			float strafe = livingentity.xxa * 0.5F;
			float forward = livingentity.zza;
			if (forward <= 0.0F) {
				forward *= 0.25F;
			}

			if (isInWater() && this.getAbilityInfo().canWalkOnWater()) {
				Vector3d delta = getDeltaMovement();
				this.setDeltaMovement(delta.x, 0.4D, delta.y);
				this.moveRelative(getChocoboColor().getAbilityInfo().getWaterSpeed() / 100F, travelVector);
				setJumping(true);
			}

			if (livingentity.jumping && this.getAbilityInfo().getCanFly()) {
				this.jumping = true;
				this.jumpFromGround();
				this.hasImpulse = true;
				this.moveRelative(getChocoboColor().getAbilityInfo().getAirbornSpeed() / 100, travelVector);
			} else if (livingentity.jumping && !this.jumping && this.onGround) {
				this.setDeltaMovement(getDeltaMovement().add(0, 0.75D, 0));
				livingentity.setJumping(false);
				this.setJumping(true);
			}

			if (this.isControlledByLocalInstance()) {
				this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
				super.travel(new Vector3d((double) strafe, travelVector.y, (double) forward));
			} else if (livingentity instanceof PlayerEntity) {
				this.setDeltaMovement(Vector3d.ZERO);
			}

			this.animationSpeedOld = this.animationSpeed;
			double d1 = this.getX() - this.xo;
			double d0 = this.getZ() - this.zo;
			float f4 = MathHelper.sqrt((float) (d1 * d1 + d0 * d0)) * 4.0F;

			if (f4 > 1.0F) {
				f4 = 1.0F;
			}

			this.animationSpeed += (f4 - this.animationSpeed) * 0.4F;
			this.animationPosition += this.animationSpeed;

			if (this.onGround) {
				this.setJumping(false);
			}

			this.calculateEntityAnimation(this, false);
		} else {
			super.travel(travelVector);
		}
	}

	private ChocoboAbilityInfo getAbilityInfo() {
		return getChocoboColor().getAbilityInfo();
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
	public AgeableEntity getBreedOffspring(ServerWorld level, AgeableEntity partner) {
		ChocoboEntity babyChocobo = ModEntities.CHOCOBO.get().create(level);
		babyChocobo.setChocoboColor(BreedingHelper.getColor(this, (ChocoboEntity) partner));
		finalizeChocobo(babyChocobo);

		//Reset golden status
		this.setFedGoldGysahl(false);
		((ChocoboEntity) partner).setFedGoldGysahl(false);
		return babyChocobo;
	}

	@Override
	public void spawnChildFromBreeding(ServerWorld level, AnimalEntity partner) {
		if (partner instanceof ChocoboEntity && this.getChocoboColor() == ChocoboColor.PURPLE && ((ChocoboEntity) partner).getChocoboColor() == ChocoboColor.PURPLE) {
			ChocoboEntity target = (ChocoboEntity) partner;
			this.spawnAtLocation(new ItemStack(ModRegistry.PURPLE_CHOCOBO_SPAWN_EGG.get()), 0);
			this.setAge(6000);
			target.setAge(6000);
			this.resetLove();
			target.resetLove();
		} else {
			super.spawnChildFromBreeding(level, partner);
		}
	}

	@Override
	public boolean canMate(AnimalEntity parent) {
		if (parent == this || !(parent instanceof ChocoboEntity)) return false;
		if (!this.isInLove() || !parent.isInLove()) return false;
		return ((ChocoboEntity) parent).isMale() != this.isMale();
	}

	public void dropFeather() {
		if (this.getCommandSenderWorld().isClientSide)
			return;

		if (this.isBaby())
			return;

		this.spawnAtLocation(new ItemStack(ModRegistry.CHOCOBO_FEATHER.get(), 1), 0.0F);
	}

	@Override
	protected boolean canRide(Entity entityIn) {
		return !this.getSaddle().isEmpty() && super.canRide(entityIn);
	}

	@Override
	public void aiStep() {
		super.aiStep();

		this.maxUpStep = getAbilityInfo().getStepHeight(isVehicle());

		this.setRot(this.yRot, this.xRot);

		fallDistance = 0;

		if (this.timeSinceFeatherChance == 3000) {
			this.timeSinceFeatherChance = 0;

			if ((float) Math.random() < .25) {
				this.dropFeather();
			}
		} else {
			this.timeSinceFeatherChance++;
		}

		if (this.getCommandSenderWorld().isClientSide) {
			// Wing rotations, control packet, client side
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
				float f4 = ((float) Math.sqrt(d1 * d1 + d0 * d0)) * 4.0F;

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

	@Override
	public void tick() {
		super.tick();

		if (isVehicle() && getFirstPassenger() instanceof LivingEntity) {
			LivingEntity passenger = (LivingEntity) getFirstPassenger();
			getAbilityInfo().getRiderAbilities().forEach(ability -> {
				passenger.addEffect(ability.get());
				this.addEffect(ability.get());
			});
		}
	}

	public Entity getFirstPassenger() {
		return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return stack.getItem() == ModRegistry.LOVERLY_GYSAHL_GREEN.get() || stack.getItem() == ModRegistry.GOLD_GYSAHL.get() || stack.getItem() == ModRegistry.GYSAHL_CAKE.get();
	}

	@Override
	public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
		ItemStack heldItemStack = player.getItemInHand(hand);
		if (this.isFood(heldItemStack)) {
			boolean fedCake = heldItemStack.getItem() == ModRegistry.GYSAHL_CAKE.get();
			//Allow the Chocobo to breed by feeding Loverly or Gold Gysahl
			if (this.isBaby()) {
				if (fedCake) {
					this.usePlayerItem(player, heldItemStack);
					this.ageBoundaryReached();
					return ActionResultType.sidedSuccess(this.level.isClientSide);
				}
			} else {
				int i = this.getAge();
				if (!this.level.isClientSide && i == 0 && this.canFallInLove() && !fedCake) {
					if (heldItemStack.getItem() == ModRegistry.GOLD_GYSAHL.get()) {
						//If fed a Gold Gysahl set the "fedGoldGysahl" flag to true
						this.setFedGoldGysahl(true);
					}
					this.usePlayerItem(player, heldItemStack);
					this.setInLove(player);
					return ActionResultType.SUCCESS;
				}
			}


			if (this.level.isClientSide) {
				return ActionResultType.CONSUME;
			}
		}

		if (this.level.isClientSide) {
			return ActionResultType.PASS;
		} else {
			if (this.isTame()) {
				if (this.isSaddled() && player.getMainHandItem().isEmpty() && !player.isShiftKeyDown() && !this.isBaby()) {
					player.startRiding(this);
					return ActionResultType.SUCCESS;
				}

				if (player.isShiftKeyDown() && !this.isBaby() && player.getMainHandItem().isEmpty()) {
					if (player instanceof ServerPlayerEntity) this.displayChocoboInventory((ServerPlayerEntity) player);
					return ActionResultType.SUCCESS;
				}

				//Switch between the Chocobo following, wandering or staying using the Chocobo Whistle
				if (heldItemStack.getItem() == ModRegistry.CHOCOBO_WHISTLE.get() && !this.isBaby()) {
					if (isOwnedBy(player)) {
						if (getMovementType() == MovementType.STANDSTILL) {
							this.playSound(ModSounds.WHISTLE_SOUND_FOLLOW.get(), 1.0F, 1.0F);
							this.setNoAi(false);
							this.goalSelector.addGoal(0, this.follow);
							this.setMovementType(MovementType.FOLLOW_OWNER);
							player.displayClientMessage(new TranslationTextComponent(Chococraft.MODID + ".entity_chocobo.chocobo_followcmd"), true);
						} else if (this.getMovementType() == MovementType.FOLLOW_OWNER) {
							this.playSound(ModSounds.WHISTLE_SOUND_WANDER.get(), 1.0F, 1.0F);
							this.goalSelector.removeGoal(this.follow);
							setMovementType(MovementType.WANDER);
							player.displayClientMessage(new TranslationTextComponent(Chococraft.MODID + ".entity_chocobo.chocobo_wandercmd"), true);
						} else if (this.getMovementType() == MovementType.WANDER) {
							this.playSound(ModSounds.WHISTLE_SOUND_STAY.get(), 1.0F, 1.0F);
							this.setNoAi(true);
							this.setMovementType(MovementType.STANDSTILL);
							player.displayClientMessage(new TranslationTextComponent(Chococraft.MODID + ".entity_chocobo.chocobo_staycmd"), true);
						}
					} else {
						player.displayClientMessage(new TranslationTextComponent(Chococraft.MODID + ".entity_chocobo.not_owner"), true);
					}
					return ActionResultType.SUCCESS;
				}


				//Heal the Chocobo if fed with Gysahl Green after being tamed and not at max health
				if (heldItemStack.getItem() == ModRegistry.GYSAHL_GREEN_ITEM.get()) {
					if (getHealth() != getMaxHealth()) {
						this.usePlayerItem(player, heldItemStack);
						this.heal(5);
						return ActionResultType.SUCCESS;
					} else {
						player.displayClientMessage(new TranslationTextComponent(Chococraft.MODID + ".entity_chocobo.heal_fail"), true);
						return ActionResultType.PASS;
					}
				}

				//Turn Gold Chocobo red or pink depending on the gysahl fed
				if (getChocoboColor() == ChocoboColor.GOLD) {
					if (heldItemStack.getItem() == ModRegistry.RED_GYSAHL.get()) {
						this.usePlayerItem(player, heldItemStack);
						this.setChocoboColor(ChocoboColor.RED);
						return ActionResultType.SUCCESS;
					} else if (heldItemStack.getItem() == ModRegistry.PINK_GYSAHL.get()) {
						this.usePlayerItem(player, heldItemStack);
						this.setChocoboColor(ChocoboColor.PINK);
						return ActionResultType.SUCCESS;
					}
				}

				//Saddle the Chocobo if right-clicked with a saddle
				if (heldItemStack.getItem() instanceof ChocoboSaddleItem && !this.isSaddled() && !this.isBaby()) {
					if (!this.level.isClientSide) {
						this.saddleItemStackHandler.setStackInSlot(0, heldItemStack.copy().split(1));
						this.setSaddleType(heldItemStack);
						this.usePlayerItem(player, heldItemStack);
					}
					return ActionResultType.SUCCESS;
				}
			} else {
				//Chance of taming Chocobo if right-clicked with Gysahl Green
				if (heldItemStack.getItem() == ModRegistry.GYSAHL_GREEN_ITEM.get()) {
					this.usePlayerItem(player, heldItemStack);
					if ((float) Math.random() < ChocoConfig.COMMON.tameChance.get().floatValue()) {
						this.setOwnerUUID(player.getUUID());
						this.setTame(true);
						if (ChocoConfig.COMMON.nameTamedChocobos.get()) {
							if (!hasCustomName()) {
								setCustomName(DefaultNames.getRandomName(random, isMale()));
							}
						}
						player.displayClientMessage(new TranslationTextComponent(Chococraft.MODID + ".entity_chocobo.tame_success"), true);
					} else {
						player.displayClientMessage(new TranslationTextComponent(Chococraft.MODID + ".entity_chocobo.tame_fail"), true);
					}
					return ActionResultType.SUCCESS;
				}
			}
		}
		return ActionResultType.PASS;
	}

	private void displayChocoboInventory(ServerPlayerEntity player) {
		if (player.containerMenu != player.inventoryMenu) {
			player.closeContainer();
		}

		player.nextContainerCounter();
		PacketManager.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new OpenChocoboGuiMessage(this, player.containerCounter));
		player.containerMenu = new SaddleBagMenu(player.containerCounter, player.inventory, this);
		player.containerMenu.addSlotListener(player);
		net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.player.PlayerContainerEvent.Open(player, player.containerMenu));
	}

	private void reconfigureInventory(ItemStack oldSaddle, ItemStack newSaddle) {
		if (!this.getCommandSenderWorld().isClientSide) {
			// TODO: Handle resizing. ItemStackHandler#setSize() clears the internal inventory!
			for (int i = 0; i < this.inventory.getSlots(); i++) {
				if (this.isAlive()) {
					ItemStack stack = this.inventory.extractItem(i, Integer.MAX_VALUE, false);
					InventoryHelper.dropItemStack(this.getCommandSenderWorld(), this.getX(), this.getY() + .5, this.getZ(), stack);
				}
			}
		}

		for (PlayerEntity player : level.players()) {
			if (player.containerMenu instanceof SaddleBagMenu) {
				SaddleBagMenu bagContainer = (SaddleBagMenu) player.containerMenu;
				bagContainer.refreshSlots(bagContainer.getChocobo(), player.inventory);
			}
		}
	}

	@Override
	protected void dropFromLootTable(DamageSource damageSourceIn, boolean attackedRecently) {
		super.dropFromLootTable(damageSourceIn, attackedRecently);

		if (this.inventory != null && this.isSaddled()) {
			for (int i = 0; i < this.inventory.getSlots(); i++) {
				if (!this.inventory.getStackInSlot(i).isEmpty())
					this.spawnAtLocation(this.inventory.getStackInSlot(i), 0.0f);
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
	public float getWalkTargetValue(BlockPos pos, IWorldReader levelReader) {
		if (this.level.getBiome(blockPosition().below()).getBiomeCategory() == Biome.Category.NETHER)
			return 0.0F;

		return super.getWalkTargetValue(pos, levelReader);
	}

	@Override
	protected void reassessTameGoals() {
		super.reassessTameGoals();
		if (chocoboAvoidPlayerGoal == null) {
			chocoboAvoidPlayerGoal = new AvoidEntityGoal<>(this, PlayerEntity.class, 10.0F, 1.0D, 1.2D, livingEntity -> {
				if (livingEntity instanceof PlayerEntity) {
					PlayerEntity player = (PlayerEntity) livingEntity;
					int chance = 0;
					for (ItemStack stack : player.inventory.armor) {
						if (stack != null) {
							if (stack.getItem() instanceof ChocoDisguiseItem)
								chance += 25;
						}
					}

					return !RandomHelper.getChanceResult(getRandom(), chance);
				}
				return false;
			});
		}
		if (healInPenGoal == null) {
			healInPenGoal = new ChocoboHealInPenGoal(this);
		}

		goalSelector.removeGoal(chocoboAvoidPlayerGoal);
		goalSelector.removeGoal(healInPenGoal);

		if (isTame()) {
			goalSelector.addGoal(4, healInPenGoal);
			goalSelector.removeGoal(chocoboAvoidPlayerGoal);
		} else {
			goalSelector.addGoal(5, chocoboAvoidPlayerGoal);
			goalSelector.removeGoal(healInPenGoal);
		}
	}

	@Override
	public EntitySize getDimensions(Pose pose) {
		if (isBaby()) {
			return super.getDimensions(pose).scale(0.5F);
		}
		return super.getDimensions(pose);
	}

	public static boolean checkChocoboSpawnRules(EntityType<? extends ChocoboEntity> entityType, IWorld levelAccessor,
												 SpawnReason spawnType, BlockPos pos, Random randomSource) {
		if (levelAccessor.getBiome(pos.below()).getBiomeCategory() == Biome.Category.NETHER) {
			BlockPos blockpos = pos.below();
			return spawnType == SpawnReason.SPAWNER || levelAccessor.getBlockState(blockpos).isValidSpawn(levelAccessor, blockpos, entityType);
		}

		return levelAccessor.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) && levelAccessor.getRawBrightness(pos, 0) > 8;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		if (this.isAlive() && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == Direction.UP) {
				return inventoryHolder.cast();
			} else {
				return saddleHolder.cast();
			}
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		this.inventoryHolder.invalidate();
		this.saddleHolder.invalidate();
	}
}
