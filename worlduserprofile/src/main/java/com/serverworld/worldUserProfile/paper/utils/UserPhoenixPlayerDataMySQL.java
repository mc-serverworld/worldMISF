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

package com.serverworld.worldUserProfile.paper.utils;

import com.google.gson.Gson;
import com.serverworld.worldUserProfile.jsondata.UserPhoenixPlayerData;
import com.serverworld.worldUserProfile.paper.PaperworldUserProfile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserPhoenixPlayerDataMySQL {

    public static String Adder(Object object){
        return "'" + object + "'";
    }

    public static boolean SetUp(String UUID){
        try {
            Statement statement = PaperworldUserProfile.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worlduserporfile_userphoenixplayerdata WHERE PlayerUUID = '" + UUID + "';");
            Boolean joinbefore = false;
            joinbefore = rs.next();
            if (joinbefore){return true;}
            else {
                UserPhoenixPlayerData userPhoenixPlayerData = new UserPhoenixPlayerData();
                userPhoenixPlayerData.setPlaytinme(0L);
                userPhoenixPlayerData.setResidence_total_size(0l);
                userPhoenixPlayerData.setResidence_total_amount(0l);
                userPhoenixPlayerData.setResidence_max_size(10000l);
                userPhoenixPlayerData.setResidence_max_amount(3l);
                userPhoenixPlayerData.setHome_server("none");
                userPhoenixPlayerData.setHome_world("none");
                userPhoenixPlayerData.setHome_x(0d);
                userPhoenixPlayerData.setHome_y(0d);
                userPhoenixPlayerData.setHome_z(0d);
                userPhoenixPlayerData.setLastlocation_server("none");
                userPhoenixPlayerData.setLastlocation_world("none");
                userPhoenixPlayerData.setLastlocation_x(0d);
                userPhoenixPlayerData.setLastlocation_y(0d);
                userPhoenixPlayerData.setLastlocation_z(0d);
                userPhoenixPlayerData.setLogoutlocation_server("none");
                userPhoenixPlayerData.setLogoutlocation_world("none");
                userPhoenixPlayerData.setLogoutlocation_x(0d);
                userPhoenixPlayerData.setLogoutlocation_y(0d);
                userPhoenixPlayerData.setLogoutlocation_z(0d);
                Gson gson = new Gson();
                String stg = gson.toJson(userPhoenixPlayerData,UserPhoenixPlayerData.class);
                statement.execute("INSERT INTO worlduserporfile_userphoenixplayerdata (PlayerUUID, version, playerdata) VALUES ('" + UUID + "', '1', '" + stg + "');");
            }
            return true;
        } catch (SQLException e) {
            DebugMessage.sendWarring(e.toString());
            return false;
        }
    }

    public static boolean Joinbefore(String UUID){
        try {
            Statement statement = PaperworldUserProfile.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worlduserporfile_userphoenixplayerdata WHERE PlayerUUID = '" + UUID + "';");
            Boolean joinbefore = false;
            joinbefore = rs.next();
            return joinbefore;
        } catch (SQLException e) {
            DebugMessage.sendWarring(e.toString());
            return false;
        }
    }

    public static UserPhoenixPlayerData getDataClass(String UUID){
        try {
            Statement statement = PaperworldUserProfile.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worlduserporfile_userphoenixplayerdata WHERE PlayerUUID = '" + UUID + "';");
            rs.next();
            Gson gson= new Gson();
            return gson.fromJson(rs.getString("playerdata"), UserPhoenixPlayerData.class);
        } catch (SQLException e) {
            DebugMessage.sendWarring(e.toString());
            return null;
        }
    }

    public static Boolean setDataClass(String UUID, UserPhoenixPlayerData userPhoenixPlayerData){
        try {
            Statement statement = PaperworldUserProfile.connection.createStatement();
            Gson gson = new Gson();
            String stg = gson.toJson(userPhoenixPlayerData,UserPhoenixPlayerData.class);
            statement.execute("UPDATE worlduserporfile_userphoenixplayerdata SET playerdata = '" + stg + "' WHERE PlayerUUID = '" + UUID + "'");
            return true;
        } catch (SQLException e) {
            DebugMessage.sendWarring(e.toString());
            return false;
        }
    }

    public static int getDataClassVersion(String UUID){
        try {
            Statement statement = PaperworldUserProfile.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worlduserporfile_userphoenixplayerdata WHERE PlayerUUID = '" + UUID + "';");
            rs.next();
            return rs.getInt("version");
        } catch (SQLException e) {
            DebugMessage.sendWarring(e.toString());
            return 0;
        }
    }

    public static Boolean setDataClassVersion(String UUID, int version){
        try {
            Statement statement = PaperworldUserProfile.connection.createStatement();
            statement.execute("UPDATE worlduserporfile_userphoenixplayerdata SET version = '" + version + "' WHERE PlayerUUID = " + UUID);
            return true;
        } catch (SQLException e) {
            DebugMessage.sendWarring(e.toString());
            return false;
        }
    }
}