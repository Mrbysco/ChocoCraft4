package net.chococraft.common.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import net.chococraft.Chococraft;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class BreedingConfig {
	public static final LinkedTreeMap<String, LinkedTreeMap<String, List<LinkedTreeMap<String, String>>>> breedingInfoHashmap = new LinkedTreeMap<>();

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	public static final File INITIAL_FILE = new File(FMLPaths.CONFIGDIR.get().toFile(), "chococraft-4-breeding.json");

	public static void initializeConfig() {
		if (!INITIAL_FILE.exists()) {
			try {
				InputStream inputStream = ChocoConfig.class.getResourceAsStream("/breedingDefault.json");
				assert inputStream != null;
				FileUtils.copyInputStreamToFile(inputStream, INITIAL_FILE);
				Chococraft.log.debug("Generated a default Breeding Config");

				loadConfig();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void loadConfig() {
		if (INITIAL_FILE.exists()) {
			breedingInfoHashmap.clear();
			String fileName = INITIAL_FILE.getName();
			try (FileReader json = new FileReader(INITIAL_FILE)) {
				final LinkedTreeMap<String, LinkedTreeMap<String, List<LinkedTreeMap<String, String>>>> breedingMap = new LinkedTreeMap<>();
				breedingMap.putAll(GSON.fromJson(json, Map.class));
				if (!breedingMap.isEmpty()) {
					breedingInfoHashmap.putAll(breedingMap);
					Chococraft.log.debug("Loaded JSON config file " + INITIAL_FILE.getAbsolutePath());
				} else {
					Chococraft.log.error("Could not load Breeding Config from {}.", fileName);
				}
			} catch (final Exception e) {
				Chococraft.log.error("Unable to load file {}. Please make sure it's a valid json.", fileName);
				Chococraft.log.catching(e);
			}
		} else {
			Chococraft.log.error("Could not locate Breeding Config from {}.", INITIAL_FILE.getAbsolutePath());
			initializeConfig();
		}
	}
}
