package io.github.itzispyder.clickcrystals.modules.scripts.macros.inventory;

import io.github.itzispyder.clickcrystals.client.clickscript.ScriptArgs;
import io.github.itzispyder.clickcrystals.client.clickscript.ScriptCommand;
import io.github.itzispyder.clickcrystals.client.clickscript.ScriptParser;
import io.github.itzispyder.clickcrystals.modules.scripts.macros.InputCmd;
import io.github.itzispyder.clickcrystals.util.minecraft.InvUtils;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public class GuiQuickMoveCmd extends ScriptCommand {

    public GuiQuickMoveCmd() {
        super("gui_quickmove");
    }

    @Override
    public void onCommand(ScriptCommand command, String line, ScriptArgs args) {
        if (InputCmd.Action.INVENTORY.isActive()) {
            Predicate<ItemStack> item = ScriptParser.parseItemPredicate(args.get(0).toString());
            int slot = InvUtils.search(item);

            if (slot != -1) {
                InvUtils.quickMove(slot);
            }
        }

        if (args.match(1, "then")) {
            args.executeAll(2);
        }
    }
}
