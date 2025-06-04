package com.envyful.day.care.command;

import com.envyful.api.command.annotate.Command;
import com.envyful.api.command.annotate.executor.Argument;
import com.envyful.api.command.annotate.executor.CommandProcessor;
import com.envyful.api.command.annotate.executor.Completable;
import com.envyful.api.command.annotate.executor.Sender;
import com.envyful.api.command.annotate.permission.Permissible;
import com.envyful.api.neoforge.chat.UtilChatColour;
import com.envyful.api.neoforge.command.completion.player.PlayerTabCompleter;
import com.envyful.api.neoforge.player.util.UtilPlayer;
import com.envyful.day.care.EnvyDayCare;
import com.envyful.day.care.config.DayCareConfig;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.api.storage.breeding.PlayerDayCare;
import net.minecraft.commands.CommandSource;
import net.minecraft.server.level.ServerPlayer;

@Command(
        value = {
                "forceupdate",
                "envydaycare"
        }
)
@Permissible("envy.day.care.update")
public class ForceUpdateCommand {

    @CommandProcessor
    public void onCommand(@Sender CommandSource sender, @Completable(PlayerTabCompleter.class) @Argument ServerPlayer target) {
        PlayerPartyStorage party = StorageProxy.getPartyNow(target);
        PlayerDayCare dayCare = party.getDayCare();

        for (DayCareConfig.Rank rank : EnvyDayCare.getInstance().getConfig().getRanks()) {
            if (UtilPlayer.hasPermission(target, rank.getRequiredPermission())) {
                dayCare.setAllowedBoxes(rank.getNumberOfBoxes());
            }
        }

        sender.sendSystemMessage(UtilChatColour.colour("Refreshed box count for " + target.getDisplayName().getString()));
    }
}
