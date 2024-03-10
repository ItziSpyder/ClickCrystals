package io.github.itzispyder.clickcrystals.gui.elements.browsingmode.module;

import io.github.itzispyder.clickcrystals.gui.GuiElement;
import io.github.itzispyder.clickcrystals.gui.misc.Shades;
import io.github.itzispyder.clickcrystals.modules.ModuleSetting;
import io.github.itzispyder.clickcrystals.modules.settings.SettingSection;
import io.github.itzispyder.clickcrystals.util.minecraft.RenderUtils;
import net.minecraft.client.gui.DrawContext;

public class SettingSectionElement extends GuiElement {

    private final SettingSection settingSection;

    public SettingSectionElement(SettingSection settingSection, int x, int y) {
        super(x, y, 285, 20);
        this.settingSection = settingSection;

        int caret = y + 25;
        for (int i = 0; i < settingSection.getSettings().size(); i++) {
            ModuleSetting<?> setting = settingSection.getSettings().get(i);
            SettingElement<?> e = setting.toGuiElement(x + 5, caret);
            this.addChild(e);
            caret += e.height;
            if (i < settingSection.getSettings().size() - 1) {
                e.setShouldUnderline(true);
            }
        }

        height = caret - y;
    }

    @Override
    public void onRender(DrawContext context, int mouseX, int mouseY) {
        RenderUtils.fillRoundRect(context, x, y, width, height, 3, Shades.TRANS_BLACK);

        String text;
        int caret = y + 5;

        text = settingSection.getName();
        RenderUtils.drawText(context, text, x + 5, caret, 0.8F, false);
        caret += 8;
        RenderUtils.drawHorLine(context, x + 5, caret, width - 10, Shades.GRAY);
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button) {

    }

    public SettingSection getSettingGroup() {
        return settingSection;
    }
}
