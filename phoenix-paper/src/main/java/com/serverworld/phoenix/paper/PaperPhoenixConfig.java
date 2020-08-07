/*
 *
 *  * WorldMISF - cms of mc-serverworld
 *  * Copyright (C) 2019-2020 mc-serverworld
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.serverworld.phoenix.paper;
public class PaperPhoenixConfig {
    private PaperPhoenix plugin;
    public PaperPhoenixConfig(PaperPhoenix i){
        plugin = i;
    }
    public void loadDefConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        if(servetype().toUpperCase().equals("LOBBY"))
            plugin.getConfig().set("serverinfo.servername","LOBBY");
        else
            plugin.getConfig().set("serverinfo.servername", serversprefix() + worldtype().toUpperCase() + "_" + chunk_position_x() + "_" + chunk_position_z());
        plugin.saveConfig();
        plugin.reloadConfig();
    }
    public int apiversion() { return plugin.getConfig().getInt("configinfo.api-version"); }
    public Boolean debug() { return plugin.getConfig().getBoolean("configinfo.debug"); }
    public String serversprefix() {
        return plugin.getConfig().getString("configinfo.servers-prefix");
    }

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
    public int chunk_position_z() { return plugin.getConfig().getInt("worldinfo.position.z"); }
    public double spawnx() { return plugin.getConfig().getDouble("worldinfo.spawn.x"); }
    public double spawny() {
        return plugin.getConfig().getDouble("worldinfo.spawn.y");
    }
    public double spawnz() { return plugin.getConfig().getDouble("worldinfo.spawn.z"); }

    public long sync_weather_time () { return plugin.getConfig().getLong("sync-time.weather"); }
    public long sync_time () { return plugin.getConfig().getLong("sync-time.time"); }
}
