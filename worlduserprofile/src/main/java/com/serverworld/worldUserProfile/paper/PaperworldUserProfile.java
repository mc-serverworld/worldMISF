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

package com.serverworld.worlduserprofile.paper;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;

public class PaperworldUserProfile extends JavaPlugin {
    private static PaperworldUserProfile paperworldUserProfile;
    public static Connection connection;
    public static PaperworldUserProfileConfig config;

    @Override
    public void onLoad() {
        config = new PaperworldUserProfileConfig(this);
        config.loadDefConfig();
        paperworldUserProfile = this;
        setSQL();
    }

    @Override
    public void onEnable() {
        //setup
        //setSQL();

    }

    public void setSQL(){
        PaperSQLDatabase paperSQLDatabase = new PaperSQLDatabase(this);
    }

    public static PaperworldUserProfile getInstance(){
        return paperworldUserProfile;
    }
}
