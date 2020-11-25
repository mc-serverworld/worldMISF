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

package com.serverworld.worlduserdata.bungeecord.uitls;

import com.google.gson.Gson;
import com.serverworld.worlduserdata.jsondata.UserPhoenixPlayerData;
import com.serverworld.worlduserdata.bungeecord.BungeeworldUserData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
@Deprecated
public class UserPhoenixPlayerDataMySQL {

    public static String Adder(Object object){
        return "'" + object + "'";
    }

    public static boolean SetUp(String UUID){
        try {
            Statement statement = BungeeworldUserData.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worlduserdata_userphoenixplayerdata WHERE PlayerUUID = '" + UUID + "';");
            Boolean joinbefore = false;
            joinbefore = rs.next();
            if (joinbefore){return true;}
            else {
                UserPhoenixPlayerData userPhoenixPlayerData = new UserPhoenixPlayerData();
                userPhoenixPlayerData.setPlayTime(0L);
                userPhoenixPlayerData.setResidence_Total_Size(0D);
                userPhoenixPlayerData.setResidence_Total_Amount(0);
                userPhoenixPlayerData.setResidence_Max_Size(10000D);
                userPhoenixPlayerData.setResidence_Max_Amount(3);
                userPhoenixPlayerData.setLastLocation_Server("none");
                userPhoenixPlayerData.setLastLocation_World("none");
                userPhoenixPlayerData.setLastLocation_X(0d);
                userPhoenixPlayerData.setLastLocation_Y(0d);
                userPhoenixPlayerData.setLastLocation_Z(0d);
                userPhoenixPlayerData.setLogoutLocation_Server("none");
                userPhoenixPlayerData.setLogoutLocation_World("none");
                userPhoenixPlayerData.setLogoutLocation_X(0d);
                userPhoenixPlayerData.setLogoutLocation_Y(0d);
                userPhoenixPlayerData.setLogoutLocation_Z(0d);
                Gson gson = new Gson();
                String stg = gson.toJson(userPhoenixPlayerData,UserPhoenixPlayerData.class);
                statement.execute("INSERT INTO worlduserdata_userphoenixplayerdata (PlayerUUID, version, playerdata) VALUES ('" + UUID + "', '1', '" + stg + "');");
            }
            return true;
        } catch (SQLException e) {
            DebugMessage.sendWarring(e.toString());
            return false;
        }
    }

    public static boolean Joinbefore(String UUID){
        try {
            Statement statement = BungeeworldUserData.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worlduserdata_userphoenixplayerdata WHERE PlayerUUID = '" + UUID + "';");
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
            Statement statement = BungeeworldUserData.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worlduserdata_userphoenixplayerdata WHERE PlayerUUID = '" + UUID + "';");
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
            Statement statement = BungeeworldUserData.connection.createStatement();
            Gson gson = new Gson();
            String stg = gson.toJson(userPhoenixPlayerData,UserPhoenixPlayerData.class);
            statement.execute("UPDATE worlduserdata_userphoenixplayerdata SET playerdata = '" + stg + "' WHERE PlayerUUID = '" + UUID + "'");
            return true;
        } catch (SQLException e) {
            DebugMessage.sendWarring(e.toString());
            return false;
        }
    }

    public static int getDataClassVersion(String UUID){
        try {
            Statement statement = BungeeworldUserData.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worlduserdata_userphoenixplayerdata WHERE PlayerUUID = '" + UUID + "';");
            rs.next();
            return rs.getInt("version");
        } catch (SQLException e) {
            DebugMessage.sendWarring(e.toString());
            return 0;
        }
    }

    public static Boolean setDataClassVersion(String UUID, int version){
        try {
            Statement statement = BungeeworldUserData.connection.createStatement();
            statement.execute("UPDATE worlduserdata_userphoenixplayerdata SET version = '" + version + "' WHERE PlayerUUID = " + UUID);
            return true;
        } catch (SQLException e) {
            DebugMessage.sendWarring(e.toString());
            return false;
        }
    }
}