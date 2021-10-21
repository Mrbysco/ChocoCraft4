package net.chococraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.chococraft.Chococraft;
import net.chococraft.common.ChocoConfig;
import net.chococraft.common.entities.ChocoboEntity;
import net.chococraft.common.handler.ExperienceHandler;
import net.chococraft.common.init.ModAttributes;
import net.chococraft.common.network.PacketManager;
import net.chococraft.common.network.packets.UpgradeChocoboMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.util.ArrayList;
import java.util.List;

public class ChocoboInfoScreen extends Screen {
    public final static ResourceLocation TEXTURE = new ResourceLocation(Chococraft.MODID, "textures/gui/chocobo_stats.png");

    private final ChocoboEntity chocobo;
    private final PlayerEntity player;

    private int xSize = 176;
    private int ySize = 89;
    private int guiLeft;
    private int guiTop;

    private TexturedButton[] abilityButton = new TexturedButton[4];

    public ChocoboInfoScreen(ChocoboEntity chocobo, PlayerEntity player) {
        super(new TranslationTextComponent("IF YOU SEE THIS, YELL AT BYSCO"));
        this.chocobo = chocobo;
        this.player = player;
    }

    public static void openScreen(ChocoboEntity chocobo, PlayerEntity player) {
        Minecraft.getInstance().setScreen(new ChocoboInfoScreen(chocobo, player));
    }

    /*
	SKILL ID Numbers
	1	-	SPRINT
	2	-	GLIDE
	3	-	DIVE
	4	-	FLY
    */
    @Override
    public void init() {
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

        abilityButton[0] = this.addButton(new TexturedButton(25, 52, 18, 18, 0, 107, 0, TEXTURE, 256, 256, (button) -> {
            UpgradeChocoboMessage packet = new UpgradeChocoboMessage(chocobo, 1);
            PacketManager.CHANNEL.sendToServer(packet);
        }, (button, matrixStack, mouseX, mouseY) -> {
            boolean flag = chocobo.canSprint();
            String abilityText = I18n.get(getAbilityFromButton(0));
            int centerX = width / 2;
            String tooltip;
            if (flag) {
                tooltip = I18n.get("gui.chocoinfo.button.already_unlocked_ability", abilityText);
            } else {
                tooltip = I18n.get("gui.chocoinfo.button.ability", ExperienceHandler.getExperience(player), getAbilityXPCost(0), abilityText);
            }
            int width = this.font.width(tooltip);
            this.font.draw(matrixStack, tooltip, 90 - centerX, 93, -1);
            List<ITextComponent> text = new ArrayList<>();
            text.add(new TranslationTextComponent("gui.chocoinfo.button.button_format", new TranslationTextComponent(getAbilityFromButton(0))));
            GuiUtils.drawHoveringText(matrixStack, text, mouseX, mouseY, width, height, -1, font);
        }, new StringTextComponent("Sprint")));
        abilityButton[1] = this.addButton(new TexturedButton(61, 52, 18, 18, 18, 107, 0, TEXTURE, 256, 256, (button) -> {
            UpgradeChocoboMessage packet = new UpgradeChocoboMessage(chocobo, 2);
            PacketManager.CHANNEL.sendToServer(packet);
        }, (button, matrixStack, mouseX, mouseY) -> {
            boolean flag = chocobo.canGlide();
            String abilityText = I18n.get(getAbilityFromButton(1));
            int centerX = width / 2;
            String tooltip;
            if (flag) {
                tooltip = I18n.get("gui.chocoinfo.button.already_unlocked_ability", abilityText);
            } else {
                tooltip = I18n.get("gui.chocoinfo.button.ability", ExperienceHandler.getExperience(player), getAbilityXPCost(1), abilityText);
            }
            int width = this.font.width(tooltip);
            this.font.draw(matrixStack, tooltip, 90 - centerX, 93, -1);
            List<ITextComponent> text = new ArrayList<>();
            text.add(new TranslationTextComponent("gui.chocoinfo.button.button_format", new TranslationTextComponent(getAbilityFromButton(1))));
            GuiUtils.drawHoveringText(matrixStack, text, mouseX, mouseY, width, height, -1, font);
        }, new StringTextComponent("Glide")));
        abilityButton[2] = this.addButton(new TexturedButton(97, 52, 18, 18, 36, 107, 0, TEXTURE, 256, 256, (button) -> {
            UpgradeChocoboMessage packet = new UpgradeChocoboMessage(chocobo, 3);
            PacketManager.CHANNEL.sendToServer(packet);
        }, (button, matrixStack, mouseX, mouseY) -> {
            boolean flag = chocobo.canDive();
            String abilityText = I18n.get(getAbilityFromButton(2));
            int centerX = width / 2;
            String tooltip;
            if (flag) {
                tooltip = I18n.get("gui.chocoinfo.button.already_unlocked_ability", abilityText);
            } else {
                tooltip = I18n.get("gui.chocoinfo.button.ability", ExperienceHandler.getExperience(player), getAbilityXPCost(2), abilityText);
            }
            int width = this.font.width(tooltip);
            this.font.draw(matrixStack, tooltip, 90 - centerX, 93, -1);
            List<ITextComponent> text = new ArrayList<>();
            text.add(new TranslationTextComponent("gui.chocoinfo.button.button_format", new TranslationTextComponent(getAbilityFromButton(2))));
            GuiUtils.drawHoveringText(matrixStack, text, mouseX, mouseY, width, height, -1, font);
        }, new StringTextComponent("Dive")));
        abilityButton[3] = this.addButton(new TexturedButton(133, 52, 18, 18, 54, 107, 0, TEXTURE, 256, 256, (button) -> {
            UpgradeChocoboMessage packet = new UpgradeChocoboMessage(chocobo, 4);
            PacketManager.CHANNEL.sendToServer(packet);
        }, (button, matrixStack, mouseX, mouseY) -> {
            boolean flag = chocobo.canFly();
            String abilityText = I18n.get(getAbilityFromButton(3));
            int centerX = width / 2;
            String tooltip;
            if (flag) {
                tooltip = I18n.get("gui.chocoinfo.button.already_unlocked_ability", abilityText);
            } else {
                tooltip = I18n.get("gui.chocoinfo.button.ability", ExperienceHandler.getExperience(player), getAbilityXPCost(3), abilityText);
            }
            int width = this.font.width(tooltip);
            this.font.draw(matrixStack, tooltip, 90 - centerX, 93, -1);
            List<ITextComponent> text = new ArrayList<>();
            text.add(new TranslationTextComponent("gui.chocoinfo.button.button_format", new TranslationTextComponent(getAbilityFromButton(3))));
            GuiUtils.drawHoveringText(matrixStack, text, mouseX, mouseY, width, height, -1, font);
        }, new StringTextComponent("Fly")));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        mouseX -= this.guiLeft;
        mouseY -= this.guiTop;

        this.renderBackground(matrixStack);
        this.minecraft.getTextureManager().bind(TEXTURE);

        RenderSystem.pushMatrix();
        RenderSystem.translatef(this.guiLeft, this.guiTop, 0);

        this.blit(matrixStack, 0, 0, 0, 0, this.xSize, this.ySize);

        String name = this.chocobo.getDisplayName().getString();
        int nameLength = this.font.width(name);
        this.font.drawShadow(matrixStack, name, (this.xSize / 2) - (nameLength / 2), 4, -1);

        String ownerText = I18n.get("gui.chocoinfo.text.not_tamed");
        if (chocobo.isTame()) {
            LivingEntity owner = chocobo.getOwner();
            if (owner == null)
                ownerText = I18n.get("gui.chocoinfo.text.unknown_owner");
            else
                ownerText = I18n.get("gui.chocoinfo.text.owner_format", owner.getDisplayName().getString());
        }
        int ownerTextLength = this.font.width(ownerText);
        this.font.drawShadow(matrixStack, ownerText, (this.xSize / 2) - (ownerTextLength / 2), 74, -1);

        this.minecraft.getTextureManager().bind(TEXTURE);
        this.drawGenderInfo(matrixStack);
        this.drawHealthInfo(matrixStack);
        this.drawSpeedInfo(matrixStack);
        this.drawStaminaInfo(matrixStack);

        this.updateButtonTextures();

        super.render(matrixStack, mouseX, mouseY, partialTicks);

        this.drawHover(matrixStack, mouseX, mouseY);

        RenderSystem.popMatrix();
    }

    private void updateButtonTextures() {
        for(int i = 0; i < abilityButton.length; i++) {
//            abilityButton[i].setTexture(TEXTURE, i * 18 - 1, canUseAbility(i) ? 89 : 107, 256, 256);
        }
    }

    @Override
    public void tick() {
        for(int i = 0; i < abilityButton.length; i++) {
            abilityButton[0].active = (getAbilityXPCost(i) <= ExperienceHandler.getExperience(player)) && !canUseAbility(i);
        }
    }

    private void drawGenderInfo(MatrixStack matrixStack) {
        this.blit(matrixStack, 26, 18, 176, this.chocobo.isMale() ? 16 : 0, 16, 16);

        String value = I18n.get(this.chocobo.isMale() ? "gui.chocoinfo.texture.male" : "gui.chocoinfo.texture.female");
        int width = this.font.width(value);
        this.font.drawShadow(matrixStack, value, 35 - (width / 2), 36, -1);
    }

    private void drawHealthInfo(MatrixStack matrixStack) {
        String value = String.valueOf((int) this.chocobo.getAttribute(Attributes.MAX_HEALTH).getBaseValue());
        int width = this.font.width(value);
        this.font.drawShadow(matrixStack, value, 70 - (width / 2), 36, -1);
    }

    private void drawSpeedInfo(MatrixStack matrixStack) {
        String value = String.valueOf((int) Math.round(this.chocobo.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue() * 100));
        int width = this.font.width(value);
        this.font.drawShadow(matrixStack, value, 106 - (width / 2), 36, -1);
    }

    private void drawStaminaInfo(MatrixStack matrixStack) {
        String value = String.valueOf((int) this.chocobo.getAttribute(ModAttributes.MAX_STAMINA.get()).getBaseValue());
        int width = this.font.width(value);
        this.font.drawShadow(matrixStack, value, 142 - (width / 2), 36, -1);
    }

    private void drawHover(MatrixStack matrixStack, int mouseX, int mouseY) {
        if (mouseX >= 25 && mouseY >= 17 && mouseX < 25 + 18 && mouseY < 17 + 18) {
            List<ITextComponent> text = new ArrayList<>();
            text.add(new TranslationTextComponent("gui.chocoinfo.texture.gender"));
            GuiUtils.drawHoveringText(matrixStack, text, mouseX, mouseY, width, height, -1, font);
        }

        if (mouseX >= 61 && mouseY >= 17 && mouseX < 61 + 18 && mouseY < 17 + 18) {
            List<ITextComponent> text = new ArrayList<>();
            text.add(new TranslationTextComponent("gui.chocoinfo.texture.health"));
            GuiUtils.drawHoveringText(matrixStack, text, mouseX, mouseY, width, height, -1, font);
        }

        if (mouseX >= 97 && mouseY >= 17 && mouseX < 97 + 18 && mouseY < 17 + 18) {
            List<ITextComponent> text = new ArrayList<>();
            text.add(new TranslationTextComponent("gui.chocoinfo.texture.speed"));
            GuiUtils.drawHoveringText(matrixStack, text, mouseX, mouseY, width, height, -1, font);
        }

        if (mouseX >= 133 && mouseY >= 17 && mouseX < 133 + 18 && mouseY < 17 + 18) {
            List<ITextComponent> text = new ArrayList<>();
            text.add(new TranslationTextComponent("gui.chocoinfo.texture.stamina"));
            GuiUtils.drawHoveringText(matrixStack, text, mouseX, mouseY, width, height, -1, font);
        }
    }

    private boolean canUseAbility(int i) {
        switch (i) {
            case 0:
                return chocobo.canSprint();
            case 1:
                return chocobo.canGlide();
            case 2:
                return chocobo.canDive();
            case 3:
                return chocobo.canFly();
        }

        return false;
    }

    private String getAbilityFromButton(int i) {
        String key = "gui.chocoinfo.button.";
        switch (i) {
            case 0:
                return key + "sprint";
            case 1:
                return key + "glide";
            case 2:
                return key + "dive";
            case 3:
                return key + "fly";
            default:
                return key + "";
        }
    }

    private int getAbilityXPCost(int i) {
        switch (i) {
            case 0:
                return ChocoConfig.COMMON.ExpCostSprint.get();
            case 1:
                return ChocoConfig.COMMON.ExpCostGlide.get();
            case 2:
                return ChocoConfig.COMMON.ExpCostDive.get();
            case 3:
                return ChocoConfig.COMMON.ExpCostFly.get();
            default:
                return 0;
        }
    }
}
