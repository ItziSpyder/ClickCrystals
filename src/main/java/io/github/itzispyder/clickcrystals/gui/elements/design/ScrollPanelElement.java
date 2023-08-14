package io.github.itzispyder.clickcrystals.gui.elements.design;

import io.github.itzispyder.clickcrystals.gui.GuiElement;
import io.github.itzispyder.clickcrystals.gui.GuiScreen;
import io.github.itzispyder.clickcrystals.util.DrawableUtils;
import net.minecraft.client.gui.DrawContext;

public class ScrollPanelElement extends GuiElement {

    private final GuiScreen parentScreen;
    public static final int SCROLL_MULTIPLIER = 15;
    private int remainingUp, remainingDown, limitTop, limitBottom, scrollbarY, scrollbarHeight, prevDrag;
    private boolean scrolling;

    public ScrollPanelElement(GuiScreen parentScreen, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.parentScreen = parentScreen;
        remainingUp = remainingDown = 0;
        limitTop = y;
        limitBottom = y + height;
        scrolling = false;

        // callbacks
        parentScreen.screenRenderListeners.add((context, mouseX, mouseY, delta) -> {
            if (!canScroll()) return;
            if (scrolling) {
                int deltaY = mouseY - prevDrag;
                scrollWithMultiplier(-deltaY, 3);
                prevDrag = mouseY;
            }

            DrawableUtils.fill(context, x + width - 6, y, 6, height, 0xFF272727);

            double fullDoc = remainingUp + remainingDown + this.height;
            double drawStartRatio = remainingUp / fullDoc;
            double ratio = this.height / fullDoc;
            int drawStart = (int)(this.height * drawStartRatio);
            int drawLength = (int)(this.height * ratio);

            DrawableUtils.fill(context, this.x + this.width - 6, this.y + drawStart, 6, drawLength, 0xFFAAAAAA);
            DrawableUtils.fill(context, this.x + this.width - 1, this.y + drawStart, 1, drawLength, 0xFF555555);
            DrawableUtils.fill(context, this.x + this.width - 6, this.y + drawStart + drawLength - 1, 6, 1, 0xFF555555);

            scrollbarY = this.y + drawStart;
            scrollbarHeight = drawLength;
        });
        parentScreen.mouseClickListeners.add((mouseX, mouseY, button, click) -> {
            if (isHovered((int)mouseX, (int)mouseY) && click.isDown()) {
                scrolling = true;
                prevDrag = (int)mouseY;
            }
        });
        parentScreen.mouseReleaseListeners.add((mouseX, mouseY, button, click) -> {
            if (scrolling && click.isRelease()) {
                scrolling = false;
            }
        });
    }

    public static boolean canRenderOnPanel(ScrollPanelElement panel, GuiElement element) {
        return element.y >= panel.y && element.y + element.height <= panel.y + panel.height;
    }

    public boolean canScrollDown() {
        return remainingDown > 0;
    }

    public boolean canScrollUp() {
        return remainingUp > 0;
    }

    public boolean canScroll() {
        return canScrollUp() || canScrollDown();
    }

    public GuiScreen getParentScreen() {
        return parentScreen;
    }

    public boolean canScrollInDirection(int amount) {
        if (amount >= 0) {
            return canScrollUp();
        }
        else {
            return canScrollDown();
        }
    }

    @Override
    public void addChild(GuiElement child) {
        super.addChild(child);
        updateBounds(child);
        child.scrollOnPanel(this, 0);
    }

    public void updateBounds(GuiElement child) {
        if (child.y < limitTop) {
            limitTop = child.y;
        }
        if (child.y + child.height > limitBottom) {
            limitBottom = child.y + child.height;
        }
        remainingUp = y - limitTop;
        remainingDown = limitBottom - (y + height);
    }

    @Override
    public void onRender(DrawContext context, int mouseX, int mouseY) {

    }

    @Override
    public void onClick(double mouseX, double mouseY, int button) {
        for (int i = getChildren().size() - 1; i >= 0; i--) {
            GuiElement child = getChildren().get(i);
            if (child.isHovered((int)mouseX, (int)mouseY)) {
                parentScreen.selected = child;
                child.onClick(mouseX, mouseY, button);
                break;
            }
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver((int)mouseX, (int)mouseY)) {
            onClick(mouseX, mouseY, button);
        }

        for (int i = getChildren().size() - 1; i >= 0; i--) {
            GuiElement child = getChildren().get(i);
            child.mouseClicked(mouseX, mouseY, button);
        }
    }

    /**
     * Redefinition of mouse hover to hovering over the scroll bar, not the entire element.
     * @param mouseX mouse x
     * @param mouseY mouse y
     * @return is mouse hovered
     */
    @Override
    public boolean isHovered(int mouseX, int mouseY) {
        return super.isHovered(mouseX, mouseY) && mouseX > (x + width - 6) && mouseY > scrollbarY && mouseY < scrollbarY + scrollbarHeight;
    }

    public void onScroll(double amount) {
        scrollWithMultiplier(amount, SCROLL_MULTIPLIER);
    }

    private void scrollWithMultiplier(double amount, int multiplier) {
        for (int i = 0; i < multiplier; i++) {
            scrollWithoutMultiplier(amount);
        }
    }

    private void scrollWithoutMultiplier(double amount) {
        if (canScrollInDirection((int)amount)) {
            for (GuiElement child : getChildren()) {
                child.scrollOnPanel(this, (int)amount);
            }

            remainingDown += amount;
            remainingUp -= amount;
        }
    }
}
