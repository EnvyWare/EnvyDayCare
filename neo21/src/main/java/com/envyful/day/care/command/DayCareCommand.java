package com.envyful.day.care.command;

import com.envyful.api.command.annotate.Command;
import com.envyful.api.command.annotate.SubCommands;
import com.envyful.api.command.annotate.executor.CommandProcessor;
import com.envyful.api.command.annotate.executor.Sender;
import com.envyful.api.command.annotate.permission.Permissible;
import com.envyful.api.neoforge.chat.UtilChatColour;
import com.envyful.day.care.EnvyDayCare;
import net.minecraft.commands.CommandSource;

@Command(
        value = {
                "daycare",
                "envydaycare"
        }
)
@SubCommands(ForceUpdateCommand.class)
@Permissible("envy.day.care.reload")
public class DayCareCommand {

    @CommandProcessor
    public void onCommand(@Sender CommandSource sender, String[] args) {
        EnvyDayCare.getInstance().reloadConfig();
        sender.sendSystemMessage(UtilChatColour.colour("Reloaded config"));
    }
}
