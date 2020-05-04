package com.serverworld.phoenix.bungee;

public class BungeePhoenixConfig {
    private com.serverworld.phoenix.bungee.BungeePhoenix plugin;

    public BungeePhoenixConfig(com.serverworld.phoenix.bungee.BungeePhoenix i){
        plugin = i;
    }
    public void loadDefConfig(){ }
    public String host() {
        return plugin.configuration.getString("data.mysql.host");
    }
    public int port() {
        return plugin.configuration.getInt("data.mysql.port");
    }
    public String username() {
        return plugin.configuration.getString("data.mysql.username");
    }
    public String password() {
        return plugin.configuration.getString("data.mysql.password");
    }
    public String database() {
        return plugin.configuration.getString("data.mysql.database");
    }
}
