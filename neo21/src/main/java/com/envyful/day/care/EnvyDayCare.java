package com.envyful.day.care;

import com.envyful.api.config.yaml.YamlConfigFactory;
import com.envyful.api.neoforge.command.ForgeCommandFactory;
import com.envyful.api.neoforge.command.parser.ForgeAnnotationCommandParser;
import com.envyful.api.neoforge.player.util.UtilPlayer;
import com.envyful.day.care.command.DayCareCommand;
import com.envyful.day.care.config.DayCareConfig;
import com.envyful.day.care.listener.PlayerLoginListener;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.io.IOException;

@Mod("envydaycare")
public class EnvyDayCare {

    private static EnvyDayCare instance;

    private DayCareConfig config;

    private ForgeCommandFactory commandFactory = new ForgeCommandFactory(ForgeAnnotationCommandParser::new, null);

    public EnvyDayCare() {
        instance = this;
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStart(ServerStartingEvent event) {
        this.reloadConfig();

        NeoForge.EVENT_BUS.register(new PlayerLoginListener(this));
    }

    @SubscribeEvent
    public void onCommandRegister(RegisterCommandsEvent event) {
        this.commandFactory.registerCommand(event.getDispatcher(), this.commandFactory.parseCommand(new DayCareCommand()));
    }

    public DayCareConfig getConfig() {
        return this.config;
    }

    public void reloadConfig() {
        try {
            this.config = YamlConfigFactory.getInstance(DayCareConfig.class);

            for (DayCareConfig.Rank rank : this.config.getRanks()) {
                UtilPlayer.registerPermission(rank.getRequiredPermission());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static EnvyDayCare getInstance() {
        return instance;
    }
}
