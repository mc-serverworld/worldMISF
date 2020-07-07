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

package com.serverworld.worlduserprofile.bungeecord;

public class BungeeworldUserProfileConfig {
    private BungeeworldUserProfile plugin;

    public BungeeworldUserProfileConfig(BungeeworldUserProfile i){
        plugin = i;
    }
    public void loadDefConfig(){ }

    public int apiversion() { return plugin.configuration.getInt("configinfo.api-version"); }
    public boolean debug() { return plugin.configuration.getBoolean("configinfo.debug"); }

    public String type() {
        return plugin.configuration.getString("database.type");
    }
    public String host() {
        return plugin.configuration.getString("database.host");
    }
    public int port() {
        return plugin.configuration.getInt("database.port");
    }
    public String database() {
        return plugin.configuration.getString("database.database");
    }
    public String username() {
        return plugin.configuration.getString("database.username");
    }
    public String password() {
        return plugin.configuration.getString("database.password");
    }
}
