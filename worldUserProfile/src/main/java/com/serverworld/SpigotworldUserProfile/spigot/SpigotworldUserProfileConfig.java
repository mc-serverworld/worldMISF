package com.serverworld.SpigotworldUserProfile.spigot;

public class SpigotworldUserProfileConfig {
    private SpigotworldUserProfile plugin;
    public SpigotworldUserProfileConfig(SpigotworldUserProfile i){
        plugin = i;
    }
    public void loadDefConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
    }
    public int apiversion() { return plugin.getConfig().getInt("configinfo.api-version"); }
    public boolean debug() { return plugin.getConfig().getBoolean("configinfo.debug"); }

    public String type() {
        return plugin.getConfig().getString("database.type");
    }
    public String host() {
        return plugin.getConfig().getString("database.host");
    }
    public int port() {
        return plugin.getConfig().getInt("database.port");
    }
    public String database() {
        return plugin.getConfig().getString("database.database");
    }
    public String username() {
        return plugin.getConfig().getString("database.username");
    }
    public String password() {
        return plugin.getConfig().getString("database.password");
    }
}
