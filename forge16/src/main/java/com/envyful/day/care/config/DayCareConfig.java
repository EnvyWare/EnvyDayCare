package com.envyful.day.care.config;

import com.envyful.api.config.data.ConfigPath;
import com.envyful.api.config.yaml.AbstractYamlConfig;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import jdk.nashorn.internal.ir.annotations.Immutable;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.List;
import java.util.Map;

@ConfigSerializable
@ConfigPath("config/EnvyDayCare/config.yml")
public class DayCareConfig extends AbstractYamlConfig {

    private Map<String, Rank> dayCareRanks = ImmutableMap.of(
            "example", new Rank("rank.noob", 1)
    );

    public DayCareConfig() {
        super();
    }

    public List<Rank> getRanks() {
        return Lists.newArrayList(dayCareRanks.values());
    }

    @ConfigSerializable
    public static class Rank {

        private String requiredPermission;
        private int numberOfBoxes;

        public Rank(String requiredPermission, int numberOfBoxes) {
            this.requiredPermission = requiredPermission;
            this.numberOfBoxes = numberOfBoxes;
        }

        public Rank() {
        }

        public String getRequiredPermission() {
            return this.requiredPermission;
        }

        public int getNumberOfBoxes() {
            return this.numberOfBoxes;
        }
    }
}
