package net.chococraft.fabric.common.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Config(name = "chococraft-4-breeding")
public class FabricBreedingConfig implements ConfigData {
	@ConfigEntry.Gui.Excluded
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	@ConfigEntry.Gui.Excluded
	public static final LinkedTreeMap<String, LinkedTreeMap<String, List<LinkedTreeMap<String, String>>>> defaultBreedingInfo = new LinkedTreeMap<>();

	@Comment("Controls the Patch Size [Default: 64]")
	public LinkedTreeMap<String, LinkedTreeMap<String, List<LinkedTreeMap<String, String>>>> breedingInfo = defaultBreedingInfo;

	static {
		final LinkedTreeMap<String, LinkedTreeMap<String, List<LinkedTreeMap<String, String>>>> breedingMap = new LinkedTreeMap<>();

		InputStream inputStream = FabricBreedingConfig.class.getResourceAsStream("/breedingDefault.json");
		assert inputStream != null;
		Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
		breedingMap.putAll(GSON.fromJson(reader, Map.class));
		if (!breedingMap.isEmpty()) {
			defaultBreedingInfo.putAll(breedingMap);
		}
	}
}