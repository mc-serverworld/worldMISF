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

package com.serverworld.worldUserProfile.bungeecord;

import com.serverworld.worldUserProfile.bungeecord.uitls.DebugMessage;
import net.md_5.bungee.api.ChatColor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BungeeSqlDatabase {
    BungeeworldUserProfile bungeeworldUserProfile;
    BungeeworldUserProfileConfig config;

    public static Connection connection;
    private String host, database, username, password;
    private int port;

    public BungeeSqlDatabase(BungeeworldUserProfile bungeeworldUserProfile){
        this.bungeeworldUserProfile = bungeeworldUserProfile;
        config = bungeeworldUserProfile.config;
        if(config.type().toLowerCase().equals("mysql")){
            DebugMessage.sendInfo("Using mysql");
            MYSQLlogin();
        }else if(config.type().toLowerCase().equals("mariadb")){
            DebugMessage.sendInfo("Using mariadb");
            MYSQLlogin();
        }else {
            DebugMessage.sendWarring(ChatColor.RED + "Not supported this database type");
        }

    }

    public void MYSQLlogin(){
        host = config.host();
        port = config.port();
        database = config.database();
        username = config.username();
        password = config.password();

        try {
            MYSQLopenConnection();
            Statement statement = connection.createStatement();
            //create database
            //useraccountdata
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `worlduserporfile_useraccountdata` (`PlayerUUID` char(36), `version` INT, `accountdata` TEXT, `signed` BOOLEAN, PRIMARY KEY(PlayerUUID))");
            //userphoenixdata
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `worlduserporfile_userphoenixdata` (`PlayerUUID` char(36), `version` INT, `lastlocation` TEXT, `logoutlocation` TEXT, `homes` TEXT, PRIMARY KEY(PlayerUUID))");
            //notuse
            //statement.executeUpdate("CREATE TABLE IF NOT EXISTS `worlduserporfile_userlastlocation` (`PlayerUUID` char(36), `Server` varchar(8), PRIMARY KEY(PlayerUUID),INDEX (Lang))");


        }catch (Exception e){
            e.printStackTrace();
            DebugMessage.sendWarring(ChatColor.RED + "Error while connection to database");
        }
        try {
            MYSQLopenConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void MYSQLopenConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            DebugMessage.sendInfo(ChatColor.GREEN + "Connected to database!");
            return;
        }

        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database+"?autoReconnect=true&characterEncoding=utf-8", this.username, this.password);
        }
    }
}