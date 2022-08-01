package net.chococraft;

import net.chococraft.client.ClientHandler;
import net.chococraft.common.config.BreedingConfig;
import net.chococraft.common.config.BreedingConfigReloadManager;
import net.chococraft.common.config.ChocoConfig;
import net.chococraft.common.entities.properties.ModDataSerializers;
import net.chococraft.common.init.ModEntities;
import net.chococraft.common.init.ModRegistry;
import net.chococraft.common.init.ModSounds;
import net.chococraft.common.network.PacketManager;
import net.chococraft.common.world.worldgen.ModWorldgen;
import net.chococraft.utils.Log4jFilter;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
@Mod(Chococraft.MODID)
public class Chococraft {
	public static final String MODID = "chococraft";

	public final static Logger log = LogManager.getLogger(MODID);

	public static final CreativeModeTab creativeTab = new CreativeModeTab(MODID) {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ModRegistry.GYSAHL_GREEN.get());
		}
	};

	public Chococraft() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ChocoConfig.commonSpec);
		eventBus.register(ChocoConfig.class);

		eventBus.addListener(this::setup);
		eventBus.addListener(this::loadComplete);

		ModRegistry.BLOCKS.register(eventBus);
		ModRegistry.ITEMS.register(eventBus);
		ModEntities.ENTITIES.register(eventBus);
		ModSounds.SOUND_EVENTS.register(eventBus);

		MinecraftForge.EVENT_BUS.register(new ModWorldgen());
		MinecraftForge.EVENT_BUS.addListener(ModEntities::addSpawns);
		MinecraftForge.EVENT_BUS.register(new BreedingConfigReloadManager());
		eventBus.addListener(ModEntities::registerEntityAttributes);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			eventBus.addListener(ClientHandler::onClientSetup);
			eventBus.addListener(ClientHandler::registerEntityRenders);
			eventBus.addListener(ClientHandler::registerLayerDefinitions);
		});
	}

	private void setup(final FMLCommonSetupEvent event) {
		ModDataSerializers.init();
		PacketManager.init();
		Log4jFilter.init();
	}

	private void loadComplete(final FMLLoadCompleteEvent event) {
		BreedingConfig.initializeConfig();
	}
}
