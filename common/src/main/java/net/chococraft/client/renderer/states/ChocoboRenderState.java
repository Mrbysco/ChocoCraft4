package net.chococraft.client.renderer.states;

import net.chococraft.common.entity.properties.ChocoboColor;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class ChocoboRenderState extends LivingEntityRenderState {
	public ChocoboColor color;
	public boolean isMale;
	public boolean isBaby;
	public boolean onGround;
	public boolean isTame;
	public boolean isSaddled;
	public ItemStack saddle;
	public Vec3 deltaMovement = Vec3.ZERO;
}
