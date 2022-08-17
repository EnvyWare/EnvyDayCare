package com.envyful.day.care.command;

import com.envyful.api.command.annotate.Child;
import com.envyful.api.command.annotate.Command;
import com.envyful.api.command.annotate.Permissible;
import com.envyful.api.command.annotate.executor.Argument;
import com.envyful.api.command.annotate.executor.CommandProcessor;
import com.envyful.api.command.annotate.executor.Completable;
import com.envyful.api.command.annotate.executor.Sender;
import com.envyful.api.forge.command.completion.player.PlayerTabCompleter;
import com.envyful.api.forge.player.util.UtilPlayer;
import com.envyful.day.care.EnvyDayCare;
import com.envyful.day.care.config.DayCareConfig;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.api.storage.breeding.PlayerDayCare;
import net.minecraft.command.ICommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;

@Command(
        value = "forceupdate",
        description = "Root command",
        aliases = {
                "envydaycare"
        }
)
@Child
@Permissible("envy.day.care.update")
public class ForceUpdateCommand {

    @CommandProcessor
    public void onCommand(@Sender ICommandSource sender, @Completable(PlayerTabCompleter.class) @Argument ServerPlayerEntity target) {
        PlayerPartyStorage party = StorageProxy.getParty(target);
        PlayerDayCare dayCare = party.getDayCare();

        for (DayCareConfig.Rank rank : EnvyDayCare.getInstance().getConfig().getRanks()) {
            if (UtilPlayer.hasPermission(target, rank.getRequiredPermission())) {
                dayCare.setAllowedBoxes(rank.getNumberOfBoxes());
            }
        }

        sender.sendMessage(new StringTextComponent("Refreshed box count for " + target.getDisplayName().getString()), Util.NIL_UUID);
    }
}
