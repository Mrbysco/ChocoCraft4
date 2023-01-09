package net.chococraft.fabric.mixins;

import net.chococraft.fabric.event.MountEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

	@Shadow
	@Nullable
	private Entity vehicle;

	@Inject(method = "startRiding(Lnet/minecraft/world/entity/Entity;Z)Z", at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/Entity;canAddPassenger(Lnet/minecraft/world/entity/Entity;)Z",
			shift = At.Shift.BEFORE,
			ordinal = 0), cancellable = true)
	public void chococraft_startRiding(Entity entity, boolean bl, CallbackInfoReturnable<Boolean> cir) {
		//CanMount - Mounting: true
		Entity mountingEntity = (Entity) (Object) this;
		InteractionResult result = MountEvent.MOUNTING.invoker().onMount(mountingEntity, entity, mountingEntity.getLevel(), true);
		if (result == InteractionResult.FAIL) {
			cir.setReturnValue(false);
		}
	}

	@Inject(method = "removeVehicle()V", at = @At(value = "HEAD"), cancellable = true)
	public void chococraft_removeVehicle(CallbackInfo ci) {
		//CanMount - Mounting: false
		if (this.vehicle != null) {
			Entity mountingEntity = (Entity) (Object) this;
			InteractionResult result = MountEvent.MOUNTING.invoker().onMount(mountingEntity, this.vehicle, mountingEntity.getLevel(), false);
			if (result == InteractionResult.FAIL) {
				ci.cancel();
			}
		}
	}
}
