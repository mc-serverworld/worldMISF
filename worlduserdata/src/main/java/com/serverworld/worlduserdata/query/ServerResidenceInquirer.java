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
import com.serverworld.worlduserdata.jsondata.ServerResidenceData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ServerResidenceInquirer {
    public static boolean isExist(String residenceName, Connection connection){
        try {
            Statement statement = connection.createStatement();
            String executeString = "SELECT * FROM worlduserdata_ServerResidenceData WHERE ResidenceName = '%ResidenceName%';";
            executeString = executeString.replace("%ResidenceName%",residenceName);
            ResultSet rs = statement.executeQuery(executeString);
            Boolean exist = false;
            exist = rs.next();
            statement.close();
            return exist;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static ServerResidenceData getDataClass(String residenceName, Connection connection){
        try {
            Statement statement = connection.createStatement();
            String executeString = "SELECT * FROM worlduserdata_ServerResidenceData WHERE ResidenceName = '%ResidenceName%';";
            executeString = executeString.replace("%ResidenceName%",residenceName);
            ResultSet rs = statement.executeQuery(executeString);
            rs.next();
            Gson gson= new Gson();
            ServerResidenceData serverResidenceData = gson.fromJson(rs.getString("ResidenceData"), ServerResidenceData.class);
            statement.close();
            return serverResidenceData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Boolean setDataClass(String residenceName, ServerResidenceData serverResidenceData, Connection connection){
        try {
            Statement statement = connection.createStatement();
            String executeString = "UPDATE worlduserdata_ServerResidenceData SET ResidenceData = '%ResidenceData%' WHERE ResidenceName = '%ResidenceName%';";
            Gson gson = new Gson();
            String stg = gson.toJson(serverResidenceData,ServerResidenceData.class);
            executeString = executeString.replace("%ResidenceData%",stg);
            executeString = executeString.replace("%ResidenceName%",residenceName);
            statement.execute(executeString);
            statement.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static int getDataClassVersion(String residenceName, Connection connection){
        try {
            Statement statement = connection.createStatement();
            String executeString = "SELECT * FROM worlduserdata_ServerResidenceData WHERE ResidenceName = '%ResidenceName%';";
            executeString = executeString.replace("%ResidenceName%",residenceName);
            statement.execute(executeString);
            ResultSet rs = statement.executeQuery(executeString);
            rs.next();
            int ver = rs.getInt("Version");
            statement.close();
            return ver;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Boolean setDataClassVersion(String residenceName, int version, Connection connection){
        try {
            Statement statement = connection.createStatement();
            String executeString = "UPDATE worlduserdata_ServerResidenceData SET version = '%Version%' WHERE ResidenceName = '%ResidenceName%';";
            executeString = executeString.replace("%ResidenceName%",residenceName);
            executeString = executeString.replace("%Version%", String.valueOf(version));
            statement.execute(executeString);
            statement.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
