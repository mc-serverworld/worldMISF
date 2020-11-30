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

package com.serverworld.phoenix.paper.util.Player;

import com.serverworld.phoenix.paper.PaperPhoenix;
import com.serverworld.phoenix.paper.util.DebugMessage;
import com.serverworld.worlduserdata.jsondata.UserPhoenixPlayerData;
import com.serverworld.worlduserdata.query.UserPhoenixPlayerDataInquirer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerData {
    public static void SaveCurrentLocationAsLast(Player player, Location location){
        try{
            UserPhoenixPlayerData playerData = UserPhoenixPlayerDataInquirer.getDataClass(player.getUniqueId());
            playerData.setLastLocation_Server(PaperPhoenix.config.servername());
            playerData.setLastLocation_World(player.getWorld().getName());
            playerData.setLastLocation_X(player.getLocation().getX());
            playerData.setLastLocation_Y(player.getLocation().getY());
            playerData.setLastLocation_Z(player.getLocation().getZ());
            playerData.setLastLocation_Yaw(player.getLocation().getYaw());
            UserPhoenixPlayerDataInquirer.setDataClass(player.getUniqueId() , playerData);
            return;
        }catch (Exception e){
            e.printStackTrace();
            DebugMessage.sendWarring("Some thing go wrong!");
        }
    }

    public static void addPlayTime(Player player,long time){
        try{
            UserPhoenixPlayerData playerData = UserPhoenixPlayerDataInquirer.getDataClass(player.getUniqueId());
            playerData.setPlayTime(playerData.getPlayTime() + time);

            if(playerData.getResidence_Max_Size()<=250000)
                playerData.setResidence_Max_Size(playerData.getResidence_Max_Size()+100);
            if(playerData.getResidence_Max_Size()<=500000)
                playerData.setResidence_Max_Size(playerData.getResidence_Max_Size()+50);
            if(playerData.getResidence_Max_Size()<=1000000)
                playerData.setResidence_Max_Size(playerData.getResidence_Max_Size()+25);


            UserPhoenixPlayerDataInquirer.setDataClass(player.getUniqueId() , playerData);
            return;
        }catch (Exception e){
            e.printStackTrace();
            DebugMessage.sendWarring("Some thing go wrong!");
        }
    }
    
    public static void checkResidenceLimit(Player player){
        UserPhoenixPlayerData playerData = UserPhoenixPlayerDataInquirer.getDataClass(player.getUniqueId());
        if(playerData.getPlayTime()==0)
            return;
    }
    public static void checkResidenceLimit(UUID uuid){
        UserPhoenixPlayerData playerData = UserPhoenixPlayerDataInquirer.getDataClass(uuid);

    }
    public static void checkResidenceLimit(String uuid){
        UserPhoenixPlayerData playerData = UserPhoenixPlayerDataInquirer.getDataClass(UUID.fromString(uuid));

    }
}
