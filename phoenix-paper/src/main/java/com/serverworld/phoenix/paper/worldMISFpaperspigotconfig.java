package com.serverworld.phoenix.paper;
public class worldMISFpaperspigotconfig {
    private worldMISFpaperspigot plugin;
    public worldMISFpaperspigotconfig(worldMISFpaperspigot i){
        plugin = i;
    }
    public void loadDefConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
    }
    public int apiversion() { return plugin.getConfig().getInt("configinfo.api-version"); }
    public String servername() {
        return plugin.getConfig().getString("serverinfo.servername");
    }
    public String servetype() {
        return plugin.getConfig().getString("serverinfo.servertype");
    }

    public String worldtype() {
        return plugin.getConfig().getString("worldinfo.worldtype");
    }
    public int chunksize() { return plugin.getConfig().getInt("worldinfo.chunksize"); }
    public int worldsize() { return plugin.getConfig().getInt("worldinfo.worldsize"); }
    public int chunk_position_x() {
        return plugin.getConfig().getInt("worldinfo.position.x");
    }
    public int chunk_position_y() { return plugin.getConfig().getInt("worldinfo.position.y"); }

    public long sync_weather_time () { return plugin.getConfig().getLong("sync-time.weather"); }
    public long sync_time () { return plugin.getConfig().getLong("sync-time.time"); }
}
