package net.chococraft.neoforge.common.items;

import net.chococraft.Chococraft;
import net.chococraft.ChococraftClient;
import net.chococraft.client.models.armor.ChocoDisguiseModel;
import net.chococraft.common.items.armor.AbstractChocoDisguiseItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.fml.DistExecutor;

import java.util.function.Consumer;

public class NeoForgeChocoDisguiseItem extends AbstractChocoDisguiseItem {
	private final LazyLoadedValue<HumanoidModel<?>> model;

	public NeoForgeChocoDisguiseItem(ArmorMaterial material, ArmorItem.Type type, Properties properties) {
		super(material, type, properties);
		this.model = DistExecutor.unsafeRunForDist(() -> () -> new LazyLoadedValue<>(() -> this.provideArmorModelForSlot(this.type)),
				() -> () -> null);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return Chococraft.MOD_ID + ":textures/models/armor/chocodisguise.png";
	}

	@OnlyIn(Dist.CLIENT)
	public HumanoidModel<?> provideArmorModelForSlot(ArmorItem.Type type) {
		return new ChocoDisguiseModel(Minecraft.getInstance().getEntityModels().bakeLayer(ChococraftClient.CHOCO_DISGUISE), type);
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {

			@Override
			public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
				return model.get();
			}
		});
	}
}
