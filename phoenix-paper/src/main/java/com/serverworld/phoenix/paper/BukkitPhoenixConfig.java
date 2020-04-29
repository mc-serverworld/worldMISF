/*
 * DiscordSRV - A Minecraft to Discord and back link plugin
 * Copyright (C) 2016-2019 Austin "Scarsz" Shapiro
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.serverworld.phoenix.paper;
public class BukkitPhoenixConfig {
    private BukkitPhoenix plugin;
    public BukkitPhoenixConfig(BukkitPhoenix i){
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
