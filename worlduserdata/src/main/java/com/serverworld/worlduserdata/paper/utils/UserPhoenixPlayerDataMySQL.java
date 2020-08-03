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

package com.serverworld.worlduserdata.paper.utils;

import com.google.gson.Gson;
import com.serverworld.worlduserdata.jsondata.UserPhoenixPlayerData;
import com.serverworld.worlduserdata.paper.PaperSQLDatabase;
import com.serverworld.worlduserdata.paper.PaperworldUserData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserPhoenixPlayerDataMySQL {

    public static String Adder(Object object){
        return "'" + object + "'";
    }

    public static boolean SetUp(String UUID){
        try {
            Statement statement = PaperSQLDatabase.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worlduserdata_userphoenixplayerdata WHERE PlayerUUID = '" + UUID + "';");
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
                statement.execute("INSERT INTO worlduserdata_userphoenixplayerdata (PlayerUUID, version, playerdata) VALUES ('" + UUID + "', '1', '" + stg + "');");
                statement.close();
            }
            return true;
        } catch (Exception e) {
            DebugMessage.sendWarring(e.toString());
            return false;
        }
    }

    public static boolean Joinbefore(String UUID){
        try {
            Statement statement = PaperSQLDatabase.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worlduserdata_userphoenixplayerdata WHERE PlayerUUID = '" + UUID + "';");
            Boolean joinbefore = false;
            joinbefore = rs.next();
            statement.close();
            return joinbefore;
        } catch (Exception e) {
            DebugMessage.sendWarring(e.toString());
            return false;
        }
    }

    public static UserPhoenixPlayerData getDataClass(String UUID){
        try {
            Statement statement = PaperSQLDatabase.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worlduserdata_userphoenixplayerdata WHERE PlayerUUID = '" + UUID + "';");
            rs.next();
            Gson gson= new Gson();
            UserPhoenixPlayerData playerdataclass = gson.fromJson(rs.getString("playerdata"), UserPhoenixPlayerData.class);
            statement.close();
            return playerdataclass;
        } catch (Exception e) {
            DebugMessage.sendWarring(e.toString());
            return null;
        }
    }

    public static Boolean setDataClass(String UUID, UserPhoenixPlayerData userPhoenixPlayerData){
        try {
            Statement statement = PaperSQLDatabase.getConnection().createStatement();
            Gson gson = new Gson();
            String stg = gson.toJson(userPhoenixPlayerData,UserPhoenixPlayerData.class);
            statement.execute("UPDATE worlduserdata_userphoenixplayerdata SET playerdata = '" + stg + "' WHERE PlayerUUID = '" + UUID + "'");
            statement.close();
            return true;
        } catch (Exception e) {
            DebugMessage.sendWarring(e.toString());
            return false;
        }
    }

    public static int getDataClassVersion(String UUID){
        try {
            Statement statement = PaperSQLDatabase.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worlduserdata_userphoenixplayerdata WHERE PlayerUUID = '" + UUID + "';");
            rs.next();
            int ver = rs.getInt("version");
            statement.close();
            return ver;
        } catch (Exception e) {
            DebugMessage.sendWarring(e.toString());
            return 0;
        }
    }

    public static Boolean setDataClassVersion(String UUID, int version){
        try {
            Statement statement = PaperSQLDatabase.getConnection().createStatement();
            statement.execute("UPDATE worlduserdata_userphoenixplayerdata SET version = '" + version + "' WHERE PlayerUUID = " + UUID);
            statement.close();
            return true;
        } catch (Exception e) {
            DebugMessage.sendWarring(e.toString());
            return false;
        }
    }
}