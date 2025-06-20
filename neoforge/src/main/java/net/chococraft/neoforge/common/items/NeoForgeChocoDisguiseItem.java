package net.chococraft.neoforge.common.items;

import net.chococraft.Chococraft;
import net.chococraft.ChococraftClient;
import net.chococraft.client.models.armor.ChocoDisguiseModel;
import net.chococraft.common.items.armor.AbstractChocoDisguiseItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class NeoForgeChocoDisguiseItem extends AbstractChocoDisguiseItem {

	public NeoForgeChocoDisguiseItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
		super(material, type, properties);
	}

	@Override
	@Nullable
	public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
		return ResourceLocation.fromNamespaceAndPath(Chococraft.MOD_ID, "textures/models/armor/chocodisguise.png");
	}

	@SuppressWarnings("removal")
	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			private final LazyLoadedValue<HumanoidModel<?>> model = new LazyLoadedValue<>(() -> this.provideArmorModelForSlot(type));

			public HumanoidModel<?> provideArmorModelForSlot(ArmorItem.Type type) {
				return new ChocoDisguiseModel(Minecraft.getInstance().getEntityModels().bakeLayer(ChococraftClient.CHOCO_DISGUISE), type);
			}

			@Override
			public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
				return model.get();
			}
		});
	}
}
