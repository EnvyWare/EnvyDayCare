package com.envyful.day.care;

import com.envyful.api.config.yaml.YamlConfigFactory;
import com.envyful.day.care.config.DayCareConfig;
import com.envyful.day.care.listener.PlayerLoginListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import java.io.IOError;
import java.io.IOException;

@Mod("envydaycare")
public class EnvyDayCare {

    private DayCareConfig config;

    public EnvyDayCare() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStart(FMLServerStartingEvent event) {
        this.reloadConfig();

        MinecraftForge.EVENT_BUS.register(new PlayerLoginListener(this));
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
}
