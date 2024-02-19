package net.chococraft.forge.datagen.client;

import net.chococraft.Chococraft;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ChocoItemModels extends ItemModelProvider {
	public ChocoItemModels(PackOutput packOutput, ExistingFileHelper helper) {
		super(packOutput, Chococraft.MOD_ID, helper);
	}

	@Override
	protected void registerModels() {

	}
}
