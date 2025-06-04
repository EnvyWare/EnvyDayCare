package com.envyful.day.care.listener;

import com.envyful.api.neoforge.player.util.UtilPlayer;
import com.envyful.day.care.EnvyDayCare;
import com.envyful.day.care.config.DayCareConfig;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.api.storage.breeding.PlayerDayCare;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class PlayerLoginListener {

    private final EnvyDayCare mod;

    public PlayerLoginListener(EnvyDayCare mod) {
        this.mod = mod;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        StorageProxy.getParty((ServerPlayer) event.getEntity()).whenComplete((party, throwable) -> {
            PlayerDayCare dayCare = party.getDayCare();

            for (DayCareConfig.Rank rank : this.mod.getConfig().getRanks()) {
                if (UtilPlayer.hasPermission((ServerPlayer) event.getEntity(), rank.getRequiredPermission())) {
                    dayCare.setAllowedBoxes(rank.getNumberOfBoxes());
                }
            }
        });
    }
}
