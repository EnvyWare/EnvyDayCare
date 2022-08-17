package com.envyful.day.care.command;

import com.envyful.api.command.annotate.Command;
import com.envyful.api.command.annotate.Permissible;
import com.envyful.api.command.annotate.SubCommands;
import com.envyful.api.command.annotate.executor.CommandProcessor;
import com.envyful.api.command.annotate.executor.Sender;
import com.envyful.day.care.EnvyDayCare;
import net.minecraft.command.ICommandSource;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;

@Command(
        value = "daycare",
        description = "Root command",
        aliases = {
                "envydaycare"
        }
)
@SubCommands(ForceUpdateCommand.class)
@Permissible("envy.day.care.reload")
public class DayCareCommand {

    @CommandProcessor
    public void onCommand(@Sender ICommandSource sender, String[] args) {
        EnvyDayCare.getInstance().reloadConfig();
        sender.sendMessage(new StringTextComponent("Reloaded config"), Util.NIL_UUID);
    }
}
