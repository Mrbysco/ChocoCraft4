package net.chococraft.common.items;

import net.chococraft.Chococraft;
import net.chococraft.client.ClientHandler;
import net.chococraft.client.models.armor.ChocoDisguiseModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.fml.DistExecutor;

import java.util.function.Consumer;

public class ChocoDisguiseItem extends ArmorItem {
	private final LazyLoadedValue<HumanoidModel<?>> model;

	public ChocoDisguiseItem(ArmorMaterial material, EquipmentSlot slot, Properties properties) {
		super(material, slot, properties);
		this.model = DistExecutor.unsafeRunForDist(() -> () -> new LazyLoadedValue<>(() -> this.provideArmorModelForSlot(this.slot)),
				() -> () -> null);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return Chococraft.MODID + ":textures/models/armor/chocodisguise.png";
	}

	@OnlyIn(Dist.CLIENT)
	public HumanoidModel<?> provideArmorModelForSlot(EquipmentSlot slot) {
		return new ChocoDisguiseModel(Minecraft.getInstance().getEntityModels().bakeLayer(ClientHandler.CHOCO_DISGUISE), slot);
	}

	@Override
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		consumer.accept(new IItemRenderProperties() {

			@Override
			public HumanoidModel<?> getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
				return model.get();
			}
		});
	}
}
