package io.github.itzispyder.clickcrystals.gui.hud.moveables;

import io.github.itzispyder.clickcrystals.gui.hud.Hud;
import io.github.itzispyder.clickcrystals.modules.Module;
import io.github.itzispyder.clickcrystals.modules.modules.clickcrystals.InGameHuds;
import io.github.itzispyder.clickcrystals.util.minecraft.InvUtils;
import io.github.itzispyder.clickcrystals.util.minecraft.RenderUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class ResourceRelativeHud extends Hud {

    public ResourceRelativeHud() {
        super("resource-hud", 200, 30, 20, 20);
    }

    @Override
    public void render(DrawContext context) {
        if (!canRender()) {
            return;
        }

        renderBackdrop(context);

        int y = getY();
        int g = 2;
        int next = 16 + g;
        int margin = getX() + g;
        int caret = y + g;

        drawItem(context, Items.TOTEM_OF_UNDYING, margin, caret);
        caret += next;
        drawItem(context, Items.END_CRYSTAL, margin, caret);
        caret += next;
        drawItem(context, Items.OBSIDIAN, margin, caret);
        caret += next;
        drawItem(context, Items.EXPERIENCE_BOTTLE, margin, caret);
        caret += next;
        drawArrowItem(context, margin, caret);
        caret += next + g;
        setHeight(caret - y);
    }

    private void drawItem(DrawContext context, Item item, int x, int y) {
        // Check if the player has the item before rendering
        if (InvUtils.count(item) > 0) {
            RenderUtils.drawItem(context, item.getDefaultStack(), x, y, 1.0F, InvUtils.count(item) + "");
        }
    }

    private void drawArrowItem(DrawContext context, int x, int y) {
        Item arrowItem = Items.ARROW;
        // Check if the player has arrows before rendering
        if (InvUtils.count(stack -> stack.getTranslationKey().contains("arrow")) > 0) {
            RenderUtils.drawItem(context, arrowItem.getDefaultStack(), x, y, 1.0F, InvUtils.count(stack -> stack.getTranslationKey().contains("arrow")) + "");
        }
    }

    @Override
    public boolean canRender() {
        return super.canRender() && (
                InvUtils.count(Items.TOTEM_OF_UNDYING) > 0 ||
                        InvUtils.count(Items.END_CRYSTAL) > 0 ||
                        InvUtils.count(Items.OBSIDIAN) > 0 ||
                        InvUtils.count(Items.EXPERIENCE_BOTTLE) > 0 ||
                        InvUtils.count(stack -> stack.getTranslationKey().contains("arrow")) > 0
        );
    }

    @Override
    public int getArgb() {
        return Module.getFrom(InGameHuds.class, m -> m.getArgb());
    }

    @Override
    public boolean canRenderBorder() {
        return Module.getFrom(InGameHuds.class, m -> m.renderHudBorders.getVal());
    }
}
