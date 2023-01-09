package net.chococraft.client.renderer.entities;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.chococraft.Chococraft;
import net.chococraft.client.ClientHandler;
import net.chococraft.client.models.entities.AdultChocoboModel;
import net.chococraft.client.models.entities.ChicoboModel;
import net.chococraft.client.renderer.layers.LayerCollar;
import net.chococraft.client.renderer.layers.LayerPlumage;
import net.chococraft.client.renderer.layers.LayerSaddle;
import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.common.entity.properties.ChocoboColor;
import net.minecraft.Util;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class ChocoboRenderer extends MobRenderer<AbstractChocobo, EntityModel<AbstractChocobo>> {
	private static final Map<ChocoboColor, ResourceLocation> CHOCOBO_PER_COLOR = Util.make(Maps.newHashMap(), (map) -> {
		map.put(ChocoboColor.YELLOW, new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chocobos/yellowchocobo.png"));
		map.put(ChocoboColor.GREEN, new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chocobos/greenchocobo.png"));
		map.put(ChocoboColor.BLUE, new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chocobos/bluechocobo.png"));
		map.put(ChocoboColor.WHITE, new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chocobos/whitechocobo.png"));
		map.put(ChocoboColor.BLACK, new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chocobos/blackchocobo.png"));
		map.put(ChocoboColor.GOLD, new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chocobos/goldchocobo.png"));
		map.put(ChocoboColor.PINK, new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chocobos/pinkchocobo.png"));
		map.put(ChocoboColor.RED, new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chocobos/redchocobo.png"));
		map.put(ChocoboColor.PURPLE, new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chocobos/purplechocobo.png"));
		map.put(ChocoboColor.FLAME, new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chocobos/flamechocobo.png"));
	});
	private static final Map<ChocoboColor, ResourceLocation> CHICOBO_PER_COLOR = Util.make(Maps.newHashMap(), (map) -> {
		map.put(ChocoboColor.YELLOW, new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chicobos/yellowchocobo.png"));
		map.put(ChocoboColor.GREEN, new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chicobos/greenchocobo.png"));
		map.put(ChocoboColor.BLUE, new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chicobos/bluechocobo.png"));
		map.put(ChocoboColor.WHITE, new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chicobos/whitechocobo.png"));
		map.put(ChocoboColor.BLACK, new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chicobos/blackchocobo.png"));
		map.put(ChocoboColor.GOLD, new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chicobos/goldchocobo.png"));
		map.put(ChocoboColor.PINK, new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chicobos/pinkchocobo.png"));
		map.put(ChocoboColor.RED, new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chicobos/redchocobo.png"));
		map.put(ChocoboColor.PURPLE, new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chicobos/purplechocobo.png"));
		map.put(ChocoboColor.FLAME, new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chicobos/flamechocobo.png"));
	});

	private final EntityModel<AbstractChocobo> chicoboModel;
	private final EntityModel<AbstractChocobo> chocoboModel = this.getModel();

	public ChocoboRenderer(EntityRendererProvider.Context context) {
		super(context, new AdultChocoboModel(context.bakeLayer(ClientHandler.CHOCOBO)), 1.0f);
		this.chicoboModel = new ChicoboModel<>(context.bakeLayer(ClientHandler.CHICOBO));

		this.addLayer(new LayerCollar(this));
		this.addLayer(new LayerPlumage(this));
		this.addLayer(new LayerSaddle(this));
	}

	@Override
	public void render(AbstractChocobo chocobo, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
		this.model = chocobo.isBaby() ? chicoboModel : chocoboModel;
		super.render(chocobo, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(AbstractChocobo chocobo) {
		ChocoboColor color = chocobo.getChocoboColor();
		return chocobo.isBaby() ? CHICOBO_PER_COLOR.get(color) : CHOCOBO_PER_COLOR.get(color);
	}
}
