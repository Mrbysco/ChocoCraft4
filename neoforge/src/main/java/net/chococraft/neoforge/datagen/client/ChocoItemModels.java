package net.chococraft.neoforge.datagen.client;

import net.chococraft.Chococraft;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ChocoItemModels extends ItemModelProvider {
	public ChocoItemModels(PackOutput packOutput, ExistingFileHelper helper) {
		super(packOutput, Chococraft.MOD_ID, helper);
	}

	@Override
	protected void registerModels() {

	}
}
