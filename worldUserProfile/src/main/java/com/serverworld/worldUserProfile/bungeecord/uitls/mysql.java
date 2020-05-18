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

package com.serverworld.worldUserProfile.bungeecord.uitls;

import com.google.gson.Gson;
import com.serverworld.worldUserProfile.bungeecord.BungeeworldUserProfile;
import com.serverworld.worldUserProfile.bungeecord.gsons.UserAccountData;
import net.md_5.bungee.api.ChatColor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class mysql {
    BungeeworldUserProfile bungeeworldUserProfile;
    static Connection connection;
    public mysql(BungeeworldUserProfile bungeeworldUserProfile){
        this.bungeeworldUserProfile = bungeeworldUserProfile;
        connection = BungeeworldUserProfile.connection;
    }

    static private void bugreport(String msg){
        if(BungeeworldUserProfile.config.debug())
        BungeeworldUserProfile.getInstance().getLogger().warning(ChatColor.RED + "Error while executequery: " + msg);
    }
    public static boolean Joinbefore(String UUID){
        try {
            Statement statement = BungeeworldUserProfile.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worlduserporfile_useraccountdata WHERE PlayerUUID = '" + UUID + "';");
            Boolean banbefore = false;
            banbefore = rs.next();
            return banbefore;
        } catch (SQLException e) {
            bugreport(e.toString());
            return false;
        }
    }

    public static boolean Signed(String UUID){
        try {
            Statement statement = BungeeworldUserProfile.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worlduserporfile_useraccountdata WHERE PlayerUUID = '" + UUID + "';");
            Boolean banbefore = false;
            banbefore = rs.next();
            return banbefore;
        } catch (SQLException e) {
            bugreport(e.toString());
            return false;
        }
    }

    public static UserAccountData getDataClass(String UUID){
        try {
            Statement statement = BungeeworldUserProfile.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worlduserporfile_useraccountdata WHERE PlayerUUID = '" + UUID + "';");
            Gson gson= new Gson();
            return gson.fromJson(rs.getString("playerdata"), UserAccountData.class);
        } catch (SQLException e) {
            bugreport(e.toString());
            return null;
        }
    }

    public static Boolean setDataClass(String UUID, UserAccountData userAccountData){
        try {
            Statement statement = BungeeworldUserProfile.connection.createStatement();
            Gson gson = new Gson();
            String stg = gson.toJson(userAccountData,UserAccountData.class);
            statement.execute("UPDATE worlduserporfile_useraccountdata SET accountdata = '" + stg + "' WHERE worldidiot_bandata.PlayerUUID = " +UUID);
            return true;
        } catch (SQLException e) {
            bugreport(e.toString());
            return false;
        }
    }

    public static int getDataClassVersion(String UUID){
        try {
            Statement statement = BungeeworldUserProfile.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worlduserporfile_useraccountdata WHERE PlayerUUID = '" + UUID + "';");
            return rs.getInt("dataversion");
        } catch (SQLException e) {
            bugreport(e.toString());
            return 0;
        }
    }

    public static Boolean setDataClassVersion(String UUID, int version){
        try {
            Statement statement = BungeeworldUserProfile.connection.createStatement();
            statement.execute("UPDATE worlduserporfile_useraccountdata SET version = '" + version + "' WHERE worldidiot_bandata.PlayerUUID = " +UUID);
            return true;
        } catch (SQLException e) {
            bugreport(e.toString());
            return false;
        }
    }

}
