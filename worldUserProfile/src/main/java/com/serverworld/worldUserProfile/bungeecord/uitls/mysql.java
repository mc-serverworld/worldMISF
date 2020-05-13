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

import com.serverworld.worldUserProfile.bungeecord.BungeeworldUserProfile;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class mysql {
    BungeeworldUserProfile bungeeworldUserProfile;
    Connection connection;
    public mysql(BungeeworldUserProfile bungeeworldUserProfile){
        this.bungeeworldUserProfile = bungeeworldUserProfile;
        connection = BungeeworldUserProfile.connection;
    }

    public boolean isBanned(String UUID){
        try {
            Statement statement =connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM worldidiot_bandata WHERE PlayerUUID='"+ UUID +"'ORDER BY worldidiot_bandata.id DESC LIMIT 1;");
            Boolean banbefore =false;
            banbefore = rs.next();
            Date date = new Date();
            if(banbefore)
                if(Long.valueOf(rs.getString("end" )) > date.getTime())
                    return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
