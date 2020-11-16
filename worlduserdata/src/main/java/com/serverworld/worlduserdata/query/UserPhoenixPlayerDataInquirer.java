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

package com.serverworld.worlduserdata.query;

import com.google.gson.Gson;
import com.serverworld.worlduserdata.jsondata.UserPhoenixPlayerData;
import com.serverworld.worlduserdata.paper.utils.DebugMessage;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

public class UserPhoenixPlayerDataInquirer {

    public static boolean SetUp(UUID uuid){
        try {
            if(joinbefore(uuid))return true;

            UserPhoenixPlayerData userPhoenixPlayerData = new UserPhoenixPlayerData();
            userPhoenixPlayerData.setPlaytinme(0L);
            userPhoenixPlayerData.setResidence_total_size(0D);
            userPhoenixPlayerData.setResidence_total_amount(0);
            userPhoenixPlayerData.setResidence_max_size(10000D);
            userPhoenixPlayerData.setResidence_max_amount(3);
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

            Statement statement = ConnectionManager.getConnection().createStatement();
            String executeString = "INSERT INTO worlduserdata_userphoenixplayerdata (PlayerUUID, version, playerdata) VALUES ('%PlayerUUID%', '1', '%PlayerData%');";
            executeString = executeString.replace("%PlayerUUID%",uuid.toString());
            executeString = executeString.replace("%PlayerData%",stg);
            statement.execute(executeString);
            statement.close();
            return true;
        } catch (Exception e) {
            DebugMessage.sendWarring(e.toString());
            return false;
        }
    }

    public static boolean joinbefore(UUID uuid){
        try {
            Statement statement = ConnectionManager.getConnection().createStatement();
            String executeString = "SELECT * FROM worlduserdata_userphoenixplayerdata WHERE PlayerUUID = '%PlayerUUID%';";
            executeString = executeString.replace("%PlayerUUID%",uuid.toString());
            ResultSet rs = statement.executeQuery(executeString);
            Boolean exist = false;
            exist = rs.next();
            statement.close();
            return exist;
        } catch (Exception e) {
            DebugMessage.sendWarring(e.toString());
            return false;
        }
    }

    public static UserPhoenixPlayerData getDataClass(UUID uuid){
        try {
            Statement statement = ConnectionManager.getConnection().createStatement();
            String executeString = "SELECT * FROM worlduserdata_userphoenixplayerdata WHERE PlayerUUID = '%PlayerUUID%';";
            executeString = executeString.replace("%PlayerUUID%",uuid.toString());
            ResultSet rs = statement.executeQuery(executeString);
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

    public static Boolean setDataClass(UUID uuid, UserPhoenixPlayerData userPhoenixPlayerData){
        try {
            Statement statement = ConnectionManager.getConnection().createStatement();
            String executeString = "UPDATE worlduserdata_userphoenixplayerdata SET playerdata = '%PlayerData%' WHERE PlayerUUID = '%PlayerUUID%';";
            Gson gson = new Gson();
            String stg = gson.toJson(userPhoenixPlayerData,UserPhoenixPlayerData.class);
            executeString = executeString.replace("%PlayerData%",stg);
            executeString = executeString.replace("%PlayerUUID%",uuid.toString());
            statement.execute(executeString);
            statement.close();
            return true;
        } catch (Exception e) {
            DebugMessage.sendWarring(e.toString());
            return false;
        }
    }

    public static int getDataClassVersion(UUID uuid){
        try {
            Statement statement = ConnectionManager.getConnection().createStatement();
            String executeString = "SELECT * FROM worlduserdata_userphoenixplayerdata WHERE PlayerUUID = '%PlayerUUID%';";
            executeString = executeString.replace("%PlayerUUID%",uuid.toString());
            ResultSet rs = statement.executeQuery(executeString);
            rs.next();
            int ver = rs.getInt("version");
            statement.close();
            return ver;
        } catch (Exception e) {
            DebugMessage.sendWarring(e.toString());
            return 0;
        }
    }

    public static Boolean setDataClassVersion(UUID uuid, int version){
        try {
            Statement statement = ConnectionManager.getConnection().createStatement();
            String executeString = "UPDATE worlduserdata_userphoenixplayerdata SET version = %Version%'' WHERE PlayerUUID = '%PlayerUUID%';";
            executeString = executeString.replace("%Version%",String.valueOf(version));
            executeString = executeString.replace("%PlayerUUID%",uuid.toString());
            statement.execute(executeString);
            statement.close();
            return true;
        } catch (Exception e) {
            DebugMessage.sendWarring(e.toString());
            return false;
        }
    }
}