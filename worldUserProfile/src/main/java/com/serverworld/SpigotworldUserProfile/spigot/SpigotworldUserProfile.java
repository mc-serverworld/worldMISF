package com.serverworld.SpigotworldUserProfile.spigot;

import org.bukkit.plugin.java.JavaPlugin;

public class SpigotworldUserProfile extends JavaPlugin {
    public SpigotworldUserProfileConfig config;

    @Override
    public void onLoad() {
        config = new SpigotworldUserProfileConfig(this);
    }

    @Override
    public void onEnable() {
        config.loadDefConfig();

        //setup
        setSQL();

    }

    public void setSQL(){
        SpigotSqlDatabase spigotSqlDatabase = new SpigotSqlDatabase(this);
    }
}
