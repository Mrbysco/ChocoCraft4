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
import net.chococraft.common.entities.Chocobo;
import net.chococraft.common.entities.properties.ChocoboColor;
import net.minecraft.Util;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class ChocoboRenderer extends MobRenderer<Chocobo, EntityModel<Chocobo>> {
	private static final Map<ChocoboColor, ResourceLocation> CHOCOBO_PER_COLOR = Util.make(Maps.newHashMap(), (map) -> {
		map.put(ChocoboColor.YELLOW, new ResourceLocation(Chococraft.MODID, "textures/entities/chocobos/yellowchocobo.png"));
		map.put(ChocoboColor.GREEN, new ResourceLocation(Chococraft.MODID, "textures/entities/chocobos/greenchocobo.png"));
		map.put(ChocoboColor.BLUE, new ResourceLocation(Chococraft.MODID, "textures/entities/chocobos/bluechocobo.png"));
		map.put(ChocoboColor.WHITE, new ResourceLocation(Chococraft.MODID, "textures/entities/chocobos/whitechocobo.png"));
		map.put(ChocoboColor.BLACK, new ResourceLocation(Chococraft.MODID, "textures/entities/chocobos/blackchocobo.png"));
		map.put(ChocoboColor.GOLD, new ResourceLocation(Chococraft.MODID, "textures/entities/chocobos/goldchocobo.png"));
		map.put(ChocoboColor.PINK, new ResourceLocation(Chococraft.MODID, "textures/entities/chocobos/pinkchocobo.png"));
		map.put(ChocoboColor.RED, new ResourceLocation(Chococraft.MODID, "textures/entities/chocobos/redchocobo.png"));
		map.put(ChocoboColor.PURPLE, new ResourceLocation(Chococraft.MODID, "textures/entities/chocobos/purplechocobo.png"));
		map.put(ChocoboColor.FLAME, new ResourceLocation(Chococraft.MODID, "textures/entities/chocobos/flamechocobo.png"));
	});
	private static final Map<ChocoboColor, ResourceLocation> CHICOBO_PER_COLOR = Util.make(Maps.newHashMap(), (map) -> {
		map.put(ChocoboColor.YELLOW, new ResourceLocation(Chococraft.MODID, "textures/entities/chicobos/yellowchocobo.png"));
		map.put(ChocoboColor.GREEN, new ResourceLocation(Chococraft.MODID, "textures/entities/chicobos/greenchocobo.png"));
		map.put(ChocoboColor.BLUE, new ResourceLocation(Chococraft.MODID, "textures/entities/chicobos/bluechocobo.png"));
		map.put(ChocoboColor.WHITE, new ResourceLocation(Chococraft.MODID, "textures/entities/chicobos/whitechocobo.png"));
		map.put(ChocoboColor.BLACK, new ResourceLocation(Chococraft.MODID, "textures/entities/chicobos/blackchocobo.png"));
		map.put(ChocoboColor.GOLD, new ResourceLocation(Chococraft.MODID, "textures/entities/chicobos/goldchocobo.png"));
		map.put(ChocoboColor.PINK, new ResourceLocation(Chococraft.MODID, "textures/entities/chicobos/pinkchocobo.png"));
		map.put(ChocoboColor.RED, new ResourceLocation(Chococraft.MODID, "textures/entities/chicobos/redchocobo.png"));
		map.put(ChocoboColor.PURPLE, new ResourceLocation(Chococraft.MODID, "textures/entities/chicobos/purplechocobo.png"));
		map.put(ChocoboColor.FLAME, new ResourceLocation(Chococraft.MODID, "textures/entities/chicobos/flamechocobo.png"));
	});

	private final EntityModel<Chocobo> chicoboModel;
	private final EntityModel<Chocobo> chocoboModel = this.getModel();

	public ChocoboRenderer(EntityRendererProvider.Context context) {
		super(context, new AdultChocoboModel(context.bakeLayer(ClientHandler.CHOCOBO)), 1.0f);
		this.chicoboModel = new ChicoboModel<>(context.bakeLayer(ClientHandler.CHICOBO));

		this.addLayer(new LayerCollar(this));
		this.addLayer(new LayerPlumage(this));
		this.addLayer(new LayerSaddle(this));
	}

	@Override
	public void render(Chocobo chocobo, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
		this.model = chocobo.isBaby() ? chicoboModel : chocoboModel;
		super.render(chocobo, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(Chocobo chocobo) {
		ChocoboColor color = chocobo.getChocoboColor();
		return chocobo.isBaby() ? CHICOBO_PER_COLOR.get(color) : CHOCOBO_PER_COLOR.get(color);
	}
}
