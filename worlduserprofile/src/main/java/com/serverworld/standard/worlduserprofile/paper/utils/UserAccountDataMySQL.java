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

package com.serverworld.standard.worlduserprofile.paper.utils;

import com.google.gson.Gson;
import com.serverworld.standard.worlduserprofile.jsondata.UserAccountData;
import com.serverworld.standard.worlduserprofile.paper.PaperworldUserProfile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserAccountDataMySQL {

    public static String Adder(Object object){
        return "'" + object + "'";
    }

    public static boolean SetUp(String UUID){
        try {
            Statement statement = PaperworldUserProfile.connection.createStatement();
            statement.execute("INSERT INTO worlduserporfile_useraccountdata (PlayerUUID, version, accountdata, signed) VALUES ('" + UUID + "', '1', 'notsign', '0');");
            return true;
        } catch (SQLException e) {
            DebugMessage.sendWarring(e.toString());
            return false;
        }
    }

    public static boolean Joinbefore(String UUID){
        try {
            Statement statement = PaperworldUserProfile.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worlduserporfile_useraccountdata WHERE PlayerUUID = '" + UUID + "';");
            Boolean joinbefore = false;
            joinbefore = rs.next();
            return joinbefore;
        } catch (SQLException e) {
            DebugMessage.sendWarring(e.toString());
            return false;
        }
    }

    public static boolean getSigned(String UUID){
        try {
            Statement statement = PaperworldUserProfile.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worlduserporfile_useraccountdata WHERE PlayerUUID = '" + UUID +  "' LIMIT 1;");
            rs.next();
            return rs.getBoolean("signed");
        } catch (SQLException e) {
            DebugMessage.sendWarring(e.toString());
            return false;
        }
    }

    public static boolean setSigned(String UUID ,Boolean status){
        try {
            Statement statement = PaperworldUserProfile.connection.createStatement();
            statement.execute("UPDATE worlduserporfile_useraccountdata SET signed = '" + status.compareTo(false) + "' WHERE PlayerUUID = '" + UUID + "';");
            return true;
        } catch (SQLException e) {
            DebugMessage.sendWarring(e.toString());
            return false;
        }
    }

    public static UserAccountData getDataClass(String UUID){
        try {
            Statement statement = PaperworldUserProfile.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worlduserporfile_useraccountdata WHERE PlayerUUID = '" + UUID + "';");
            rs.next();
            Gson gson= new Gson();
            return gson.fromJson(rs.getString("accountdata"), UserAccountData.class);
        } catch (SQLException e) {
            DebugMessage.sendWarring(e.toString());
            return null;
        }
    }

    public static Boolean setDataClass(String UUID, UserAccountData userAccountData){
        try {
            Statement statement = PaperworldUserProfile.connection.createStatement();
            Gson gson = new Gson();
            String stg = gson.toJson(userAccountData,UserAccountData.class);
            statement.execute("UPDATE worlduserporfile_useraccountdata SET accountdata = '" + stg + "' WHERE PlayerUUID = '" + UUID + "'");
            return true;
        } catch (SQLException e) {
            DebugMessage.sendWarring(e.toString());
            return false;
        }
    }

    public static int getDataClassVersion(String UUID){
        try {
            Statement statement = PaperworldUserProfile.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worlduserporfile_useraccountdata WHERE PlayerUUID = '" + UUID + "';");
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
            statement.execute("UPDATE worlduserporfile_useraccountdata SET version = '" + version + "' WHERE PlayerUUID = " + UUID);
            return true;
        } catch (SQLException e) {
            DebugMessage.sendWarring(e.toString());
            return false;
        }
    }



}
