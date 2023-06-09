package io.github.itzispyder.clickcrystals.gui.elements.base;

import io.github.itzispyder.clickcrystals.gui.GuiElement;
import io.github.itzispyder.clickcrystals.gui.TexturesIdentifiers;
import net.minecraft.client.gui.DrawContext;

public class WidgetElement extends GuiElement {

    private Orientation orientation;

    public WidgetElement(int x, int y, int width, int height, Orientation orientation) {
        super(x, y, width, height);
        this.orientation = orientation;
    }

    public WidgetElement(int x, int y, int width, int height) {
        this(x, y, width, height, Orientation.HORIZONTAL);
    }

    @Override
    public void onRender(DrawContext context, int mouseX, int mouseY) {
        switch (orientation) {
            case VERTICAL -> context.drawTexture(TexturesIdentifiers.SMOOTH_VERTICAL_WIDGET, x, y, 0, 0, width, height, width, height);
            case HORIZONTAL -> context.drawTexture(TexturesIdentifiers.SMOOTH_HORIZONTAL_WIDGET, x, y, 0, 0, width, height, width, height);
        }
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button) {

    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }
}
