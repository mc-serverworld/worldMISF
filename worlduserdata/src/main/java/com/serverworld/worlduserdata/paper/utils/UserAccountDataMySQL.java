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
import com.serverworld.worlduserdata.jsondata.UserAccountData;
import com.serverworld.worlduserdata.paper.PaperSQLDatabase;
import com.serverworld.worlduserdata.paper.PaperworldUserData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserAccountDataMySQL {

    public static String Adder(Object object){
        return "'" + object + "'";
    }

    public static boolean SetUp(String UUID){
        try {
            Statement statement = PaperSQLDatabase.getConnection().createStatement();
            statement.execute("INSERT INTO worlduserdata_useraccountdata (PlayerUUID, version, accountdata, signed) VALUES ('" + UUID + "', '1', 'notsign', '0');");
            statement.close();
            return true;
        } catch (Exception e) {
            DebugMessage.sendWarring(e.toString());
            return false;
        }
    }

    public static boolean Joinbefore(String UUID){
        try {
            Statement statement = PaperSQLDatabase.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worlduserdata_useraccountdata WHERE PlayerUUID = '" + UUID + "';");
            Boolean joinbefore = false;
            joinbefore = rs.next();
            statement.close();
            return joinbefore;
        } catch (Exception e) {
            DebugMessage.sendWarring(e.toString());
            return false;
        }
    }

    public static boolean getSigned(String UUID){
        try {
            Statement statement = PaperSQLDatabase.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worlduserdata_useraccountdata WHERE PlayerUUID = '" + UUID +  "' LIMIT 1;");
            rs.next();
            boolean rsb = rs.getBoolean("signed");
            statement.close();
            return rsb;
        } catch (Exception e) {
            DebugMessage.sendWarring(e.toString());
            return false;
        }
    }

    public static boolean setSigned(String UUID ,Boolean status){
        try {
            Statement statement = PaperSQLDatabase.getConnection().createStatement();
            statement.execute("UPDATE worlduserdata_useraccountdata SET signed = '" + status.compareTo(false) + "' WHERE PlayerUUID = '" + UUID + "';");
            statement.close();
            return true;
        } catch (Exception e) {
            DebugMessage.sendWarring(e.toString());
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
            DebugMessage.sendWarring(e.toString());
            return null;
        }
    }

    public static Boolean setDataClass(String UUID, UserAccountData userAccountData){
        try {
            Statement statement = PaperSQLDatabase.getConnection().createStatement();
            Gson gson = new Gson();
            String stg = gson.toJson(userAccountData,UserAccountData.class);
            statement.execute("UPDATE worlduserdata_useraccountdata SET accountdata = '" + stg + "' WHERE PlayerUUID = '" + UUID + "'");
            return true;
        } catch (Exception e) {
            DebugMessage.sendWarring(e.toString());
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
            DebugMessage.sendWarring(e.toString());
            return 0;
        }
    }

    public static Boolean setDataClassVersion(String UUID, int version){
        try {
            Statement statement = PaperSQLDatabase.getConnection().createStatement();
            statement.execute("UPDATE worlduserdata_useraccountdata SET version = '" + version + "' WHERE PlayerUUID = " + UUID);
            statement.close();
            return true;
        } catch (Exception e) {
            DebugMessage.sendWarring(e.toString());
            return false;
        }
    }



}
