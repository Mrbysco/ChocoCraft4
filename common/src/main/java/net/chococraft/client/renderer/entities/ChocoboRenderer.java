package net.chococraft.client.renderer.entities;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.chococraft.Chococraft;
import net.chococraft.ChococraftClient;
import net.chococraft.client.models.entities.AdultChocoboModel;
import net.chococraft.client.models.entities.ChicoboModel;
import net.chococraft.client.renderer.layers.LayerCollar;
import net.chococraft.client.renderer.layers.LayerPlumage;
import net.chococraft.client.renderer.layers.LayerSaddle;
import net.chococraft.client.renderer.states.ChocoboRenderState;
import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.common.entity.properties.ChocoboColor;
import net.minecraft.Util;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class ChocoboRenderer extends MobRenderer<AbstractChocobo, ChocoboRenderState, EntityModel<ChocoboRenderState>> {
	private static final Map<ChocoboColor, ResourceLocation> CHOCOBO_PER_COLOR = Util.make(Maps.newHashMap(), (map) -> {
		map.put(ChocoboColor.YELLOW, Chococraft.modLoc("textures/entities/chocobos/yellowchocobo.png"));
		map.put(ChocoboColor.GREEN, Chococraft.modLoc("textures/entities/chocobos/greenchocobo.png"));
		map.put(ChocoboColor.BLUE, Chococraft.modLoc("textures/entities/chocobos/bluechocobo.png"));
		map.put(ChocoboColor.WHITE, Chococraft.modLoc("textures/entities/chocobos/whitechocobo.png"));
		map.put(ChocoboColor.BLACK, Chococraft.modLoc("textures/entities/chocobos/blackchocobo.png"));
		map.put(ChocoboColor.GOLD, Chococraft.modLoc("textures/entities/chocobos/goldchocobo.png"));
		map.put(ChocoboColor.PINK, Chococraft.modLoc("textures/entities/chocobos/pinkchocobo.png"));
		map.put(ChocoboColor.RED, Chococraft.modLoc("textures/entities/chocobos/redchocobo.png"));
		map.put(ChocoboColor.PURPLE, Chococraft.modLoc("textures/entities/chocobos/purplechocobo.png"));
		map.put(ChocoboColor.FLAME, Chococraft.modLoc("textures/entities/chocobos/flamechocobo.png"));
	});
	private static final Map<ChocoboColor, ResourceLocation> CHICOBO_PER_COLOR = Util.make(Maps.newHashMap(), (map) -> {
		map.put(ChocoboColor.YELLOW, Chococraft.modLoc("textures/entities/chicobos/yellowchocobo.png"));
		map.put(ChocoboColor.GREEN, Chococraft.modLoc("textures/entities/chicobos/greenchocobo.png"));
		map.put(ChocoboColor.BLUE, Chococraft.modLoc("textures/entities/chicobos/bluechocobo.png"));
		map.put(ChocoboColor.WHITE, Chococraft.modLoc("textures/entities/chicobos/whitechocobo.png"));
		map.put(ChocoboColor.BLACK, Chococraft.modLoc("textures/entities/chicobos/blackchocobo.png"));
		map.put(ChocoboColor.GOLD, Chococraft.modLoc("textures/entities/chicobos/goldchocobo.png"));
		map.put(ChocoboColor.PINK, Chococraft.modLoc("textures/entities/chicobos/pinkchocobo.png"));
		map.put(ChocoboColor.RED, Chococraft.modLoc("textures/entities/chicobos/redchocobo.png"));
		map.put(ChocoboColor.PURPLE, Chococraft.modLoc("textures/entities/chicobos/purplechocobo.png"));
		map.put(ChocoboColor.FLAME, Chococraft.modLoc("textures/entities/chicobos/flamechocobo.png"));
	});

	private final EntityModel<ChocoboRenderState> chicoboModel;
	private final EntityModel<ChocoboRenderState> chocoboModel = this.getModel();

	public ChocoboRenderer(EntityRendererProvider.Context context) {
		super(context, new AdultChocoboModel<>(context.bakeLayer(ChococraftClient.CHOCOBO)), 1.0f);
		this.chicoboModel = new ChicoboModel<>(context.bakeLayer(ChococraftClient.CHICOBO));

		this.addLayer(new LayerCollar(this));
		this.addLayer(new LayerPlumage(this));
		this.addLayer(new LayerSaddle(this));
	}

	@Override
	public void submit(ChocoboRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
		this.model = renderState.isBaby ? chicoboModel : chocoboModel;
		super.submit(renderState, poseStack, submitNodeCollector, cameraRenderState);
	}

	@Override
	public ChocoboRenderState createRenderState() {
		return new ChocoboRenderState();
	}

	@Override
	public void extractRenderState(AbstractChocobo chocobo, ChocoboRenderState renderState, float f) {
		super.extractRenderState(chocobo, renderState, f);
		renderState.color = chocobo.getChocoboColor();
		renderState.isBaby = chocobo.isBaby();
		renderState.onGround = chocobo.onGround();
		renderState.isTame = chocobo.isTame();
		renderState.isMale = chocobo.isMale();
		renderState.isSaddled = chocobo.isSaddled();
		renderState.saddle = chocobo.getSaddle();
		renderState.deltaMovement = chocobo.getDeltaMovement();
	}

	@Override
	public ResourceLocation getTextureLocation(ChocoboRenderState renderState) {
		ChocoboColor color = renderState.color;
		return renderState.isBaby ? CHICOBO_PER_COLOR.get(color) : CHOCOBO_PER_COLOR.get(color);
	}
}
