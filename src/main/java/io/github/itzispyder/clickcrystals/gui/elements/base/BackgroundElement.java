package io.github.itzispyder.clickcrystals.gui.elements.base;

import io.github.itzispyder.clickcrystals.gui.GuiElement;
import io.github.itzispyder.clickcrystals.gui.TexturesIdentifiers;
import net.minecraft.client.gui.DrawContext;

public class BackgroundElement extends GuiElement {

    public BackgroundElement(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void onRender(DrawContext context, int mouseX, int mouseY) {
        context.drawTexture(TexturesIdentifiers.SMOOTH_BACKGROUND, x, y, 0, 0, width, height, width, height);
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button) {

    }
}
