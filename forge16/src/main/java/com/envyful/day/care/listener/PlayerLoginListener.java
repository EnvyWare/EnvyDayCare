package com.envyful.day.care.listener;

import com.envyful.api.forge.concurrency.UtilForgeConcurrency;
import com.envyful.api.forge.player.util.UtilPlayer;
import com.envyful.day.care.EnvyDayCare;
import com.envyful.day.care.config.DayCareConfig;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.api.storage.breeding.PlayerDayCare;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerLoginListener {

    private final EnvyDayCare mod;

    public PlayerLoginListener(EnvyDayCare mod) {
        this.mod = mod;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        UtilForgeConcurrency.runLater(() -> {
            PlayerPartyStorage party = StorageProxy.getParty((ServerPlayerEntity) event.getPlayer());
            PlayerDayCare dayCare = party.getDayCare();

            for (DayCareConfig.Rank rank : this.mod.getConfig().getRanks()) {
                if (UtilPlayer.hasPermission((ServerPlayerEntity) event.getPlayer(), rank.getRequiredPermission())) {
                    dayCare.setAllowedBoxes(rank.getNumberOfBoxes());
                }
            }
        }, 10);
    }
}
