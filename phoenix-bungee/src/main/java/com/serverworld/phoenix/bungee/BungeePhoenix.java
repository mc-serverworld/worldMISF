package com.serverworld.phoenix.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;

public class BungeePhoenix extends Plugin {

    private File file;
    private BungeePhoenixConfig config;
    public static Configuration configuration;
    public static Connection connection;
    @Override
    public void onEnable() {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        config = new BungeePhoenixConfig(this);
        getLogger().info("Yay! It loads!");
        getLogger().info("Helloworld");

        getProxy().getPluginManager().registerCommand(this,new banplayer(this));
        getProxy().getPluginManager().registerCommand(this,new unbanplayer(this));
    }
}
