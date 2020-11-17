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
import com.serverworld.worlduserdata.jsondata.UserAccountData;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;


public class UserAccountDataInquirer {

    public static boolean SetUp(UUID uuid){
        try {
            Statement statement = ConnectionManager.getConnection().createStatement();
            String executeString = "INSERT INTO worlduserdata_useraccountdata (PlayerUUID, version, accountdata, signed) VALUES ('%UUID%', '1', 'notsign', '0');";
            executeString = executeString.replace("%UUID%",uuid.toString());
            statement.execute(executeString);
            statement.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean Joinbefore(UUID uuid){
        try {
            Statement statement = ConnectionManager.getConnection().createStatement();
            String executeString = "SELECT * FROM worlduserdata_useraccountdata WHERE PlayerUUID = '%UUID%';";
            executeString = executeString.replace("%UUID%",uuid.toString());
            ResultSet rs = statement.executeQuery(executeString);
            Boolean joinbefore = false;
            joinbefore = rs.next();
            statement.close();
            return joinbefore;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getSigned(UUID uuid){
        try {
            Statement statement = ConnectionManager.getConnection().createStatement();
            String executeString ="SELECT * FROM worlduserdata_useraccountdata WHERE PlayerUUID = '%UUID%' LIMIT 1;";
            executeString = executeString.replace("%UUID%",uuid.toString());
            ResultSet rs = statement.executeQuery(executeString);
            rs.next();
            boolean rsb = rs.getBoolean("signed");
            statement.close();
            return rsb;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean setSigned(UUID uuid ,Boolean status){
        try {
            Statement statement = ConnectionManager.getConnection().createStatement();
            String executeString ="UPDATE worlduserdata_useraccountdata SET signed = '%STATUS%' WHERE PlayerUUID = '%UUID%';";
            executeString = executeString.replace("%UUID%",uuid.toString());
            executeString = executeString.replace("%STATUS%",String.valueOf(status));
            statement.execute(executeString);
            statement.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static UserAccountData getDataClass(String UUID){
        try {
            Statement statement = PaperSQLDatabase.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worlduserdata_useraccountdata WHERE PlayerUUID = '" + UUID + "';");
            rs.next();
            Gson gson= new Gson();
            UserAccountData data = gson.fromJson(rs.getString("accountdata"), UserAccountData.class);
            statement.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean setDataClass(String UUID, UserAccountData userAccountData){
        try {
            Statement statement = PaperSQLDatabase.getConnection().createStatement();
            Gson gson = new Gson();
            String stg = gson.toJson(userAccountData,UserAccountData.class);
            statement.execute("UPDATE worlduserdata_useraccountdata SET accountdata = '" + stg + "' WHERE PlayerUUID = '" + UUID + "'");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getDataClassVersion(String UUID){
        try {
            Statement statement = PaperSQLDatabase.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worlduserdata_useraccountdata WHERE PlayerUUID = '" + UUID + "';");
            rs.next();
            int res = rs.getInt("version");
            statement.close();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getDataClassVersion(UUID UUID){
        try {
            Statement statement = PaperSQLDatabase.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worlduserdata_useraccountdata WHERE PlayerUUID = '" + UUID + "';");
            rs.next();
            int res = rs.getInt("version");
            statement.close();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean setDataClassVersion(String UUID, int version){
        try {
            Statement statement = PaperSQLDatabase.getConnection().createStatement();
            statement.execute("UPDATE worlduserdata_useraccountdata SET version = '" + version + "' WHERE PlayerUUID = " + UUID);
            statement.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean setDataClassVersion(UUID UUID, int version){
        try {
            Statement statement = PaperSQLDatabase.getConnection().createStatement();
            statement.execute("UPDATE worlduserdata_useraccountdata SET version = '" + version + "' WHERE PlayerUUID = " + UUID);
            statement.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



}
