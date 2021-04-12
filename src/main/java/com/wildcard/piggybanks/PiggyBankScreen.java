package com.wildcard.piggybanks;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nonnull;

public class PiggyBankScreen extends ContainerScreen<PiggyBankContainer> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(PiggyBanks.MOD_ID, "textures/gui/containers/piggy_bank_3.png");
    public PiggyBankScreen(PiggyBankContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(@Nonnull MatrixStack matrixStack, int x, int y) {
        //Draw the name of the piggy bank and the inventory titles
        this.font.draw(matrixStack, title, 8.0f, 6.0f, 4210752);
        this.font.draw(matrixStack, getMenu().getBalanceText(), 8.0f, 52.0f, 4210752);
        this.font.draw(matrixStack, getMenu().getRateText(), 8.0f, 62.0f, 4210752);
        this.font.draw(matrixStack, inventory.getDisplayName(),8.0f, 72.0f, 4210752);
    }

    @Override
    protected void renderBg(@Nonnull MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        //Place the texture for the piggy bank gui
        assert this.minecraft != null;
        this.minecraft.getTextureManager().bind(TEXTURE);
        blit(matrixStack, leftPos, topPos, 0, 0, imageWidth, imageHeight, 256, 256);
    }
}
