package com.envyful.day.care;

import com.envyful.api.config.yaml.YamlConfigFactory;
import com.envyful.api.forge.command.ForgeCommandFactory;
import com.envyful.day.care.command.DayCareCommand;
import com.envyful.day.care.config.DayCareConfig;
import com.envyful.day.care.listener.PlayerLoginListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import java.io.IOError;
import java.io.IOException;

@Mod("envydaycare")
public class EnvyDayCare {

    private static EnvyDayCare instance;

    private DayCareConfig config;

    private ForgeCommandFactory commandFactory = new ForgeCommandFactory();

    public EnvyDayCare() {
        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStart(FMLServerStartingEvent event) {
        this.reloadConfig();

        MinecraftForge.EVENT_BUS.register(new PlayerLoginListener(this));
    }

    @SubscribeEvent
    public void onCommandRegister(RegisterCommandsEvent event) {
        this.commandFactory.registerCommand(event.getDispatcher(), new DayCareCommand());
    }

    public DayCareConfig getConfig() {
        return this.config;
    }

    public void reloadConfig() {
        try {
            this.config = YamlConfigFactory.getInstance(DayCareConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static EnvyDayCare getInstance() {
        return instance;
    }
}
