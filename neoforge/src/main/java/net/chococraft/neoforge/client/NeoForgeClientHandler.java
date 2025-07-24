package net.chococraft.neoforge.client;

import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.chococraft.Chococraft;
import net.chococraft.ChococraftClient;
import net.chococraft.client.gui.ChocoboInventoryScreen;
import net.chococraft.client.models.armor.ChocoDisguiseModel;
import net.chococraft.client.models.entities.AdultChocoboModel;
import net.chococraft.client.models.entities.ChicoboModel;
import net.chococraft.client.renderer.entities.ChocoboRenderer;
import net.chococraft.registry.ModEntities;
import net.chococraft.registry.ModMenus;
import net.chococraft.registry.ModRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.resources.model.EquipmentClientInfo.Layer;
import net.minecraft.client.resources.model.EquipmentClientInfo.LayerType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class NeoForgeClientHandler {
	public static void onClientSetup(final FMLClientSetupEvent event) {
		RenderTypeRegistry.register(ChunkSectionLayer.CUTOUT, ModRegistry.GYSAHL_GREEN.get());
	}

	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ModEntities.CHOCOBO.get(), ChocoboRenderer::new);
	}

	public static void registerMenuScreen(RegisterMenuScreensEvent event) {
		event.register(ModMenus.CHOCOBO.get(), ChocoboInventoryScreen::new);
	}

	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ChococraftClient.CHOCOBO, AdultChocoboModel::createBodyLayer);
		event.registerLayerDefinition(ChococraftClient.CHICOBO, ChicoboModel::createBodyLayer);
		event.registerLayerDefinition(ChococraftClient.CHOCO_DISGUISE, ChocoDisguiseModel::createArmorDefinition);
	}

	public static void registerClientExtension(RegisterClientExtensionsEvent event) {
		ResourceLocation chocoDisguiseTexture = Chococraft.modLoc("textures/models/armor/chocodisguise.png");
		event.registerItem(
				new IClientItemExtensions() {
					private final LazyLoadedValue<HumanoidModel<?>> model = new LazyLoadedValue<>(() -> this.provideArmorModelForSlot(ArmorType.HELMET));

					public HumanoidModel<?> provideArmorModelForSlot(ArmorType type) {
						return new ChocoDisguiseModel(Minecraft.getInstance().getEntityModels().bakeLayer(ChococraftClient.CHOCO_DISGUISE), type);
					}

					@Override
					public ResourceLocation getArmorTexture(@NotNull ItemStack stack, @NotNull LayerType type,
					                                        @NotNull Layer layer, @NotNull ResourceLocation _default) {
						return chocoDisguiseTexture;
					}

					@NotNull
					@Override
					public Model getHumanoidArmorModel(@NotNull ItemStack itemStack, @NotNull LayerType layerType,
					                                   @NotNull Model original) {
						return model.get();
					}
				},
				ModRegistry.CHOCO_DISGUISE_HELMET.get()
		);
		event.registerItem(
				new IClientItemExtensions() {
					private final LazyLoadedValue<HumanoidModel<?>> model = new LazyLoadedValue<>(() -> this.provideArmorModelForSlot(ArmorType.CHESTPLATE));

					public HumanoidModel<?> provideArmorModelForSlot(ArmorType type) {
						return new ChocoDisguiseModel(Minecraft.getInstance().getEntityModels().bakeLayer(ChococraftClient.CHOCO_DISGUISE), type);
					}

					@Override
					public ResourceLocation getArmorTexture(@NotNull ItemStack stack, @NotNull LayerType type,
					                                        @NotNull Layer layer, @NotNull ResourceLocation _default) {
						return chocoDisguiseTexture;
					}

					@NotNull
					@Override
					public Model getHumanoidArmorModel(@NotNull ItemStack itemStack, @NotNull LayerType layerType,
					                                   @NotNull Model original) {
						return model.get();
					}
				},
				ModRegistry.CHOCO_DISGUISE_CHESTPLATE.get()
		);
		event.registerItem(
				new IClientItemExtensions() {
					private final LazyLoadedValue<HumanoidModel<?>> model = new LazyLoadedValue<>(() -> this.provideArmorModelForSlot(ArmorType.LEGGINGS));

					public HumanoidModel<?> provideArmorModelForSlot(ArmorType type) {
						return new ChocoDisguiseModel(Minecraft.getInstance().getEntityModels().bakeLayer(ChococraftClient.CHOCO_DISGUISE), type);
					}

					@Override
					public ResourceLocation getArmorTexture(@NotNull ItemStack stack, @NotNull LayerType type,
					                                        @NotNull Layer layer, @NotNull ResourceLocation _default) {
						return chocoDisguiseTexture;
					}

					@NotNull
					@Override
					public Model getHumanoidArmorModel(@NotNull ItemStack itemStack, @NotNull LayerType layerType,
					                                   @NotNull Model original) {
						return model.get();
					}
				},
				ModRegistry.CHOCO_DISGUISE_LEGGINGS.get()
		);
		event.registerItem(
				new IClientItemExtensions() {
					private final LazyLoadedValue<HumanoidModel<?>> model = new LazyLoadedValue<>(() -> this.provideArmorModelForSlot(ArmorType.BOOTS));

					public HumanoidModel<?> provideArmorModelForSlot(ArmorType type) {
						return new ChocoDisguiseModel(Minecraft.getInstance().getEntityModels().bakeLayer(ChococraftClient.CHOCO_DISGUISE), type);
					}

					@Override
					public ResourceLocation getArmorTexture(@NotNull ItemStack stack, @NotNull LayerType type,
					                                        @NotNull Layer layer, @NotNull ResourceLocation _default) {
						return chocoDisguiseTexture;
					}

					@NotNull
					@Override
					public Model getHumanoidArmorModel(@NotNull ItemStack itemStack, @NotNull LayerType layerType,
					                                   @NotNull Model original) {
						return model.get();
					}
				},
				ModRegistry.CHOCO_DISGUISE_BOOTS.get()
		);
	}
}
