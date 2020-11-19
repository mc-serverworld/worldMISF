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

package com.serverworld.phoenix.paper.commands.PlayerCommands;

import com.google.gson.JsonObject;
import com.serverworld.phoenix.paper.PaperPhoenix;
import com.serverworld.phoenix.paper.util.Formats;
import com.serverworld.worldSocket.paperspigot.util.messagecoder;
import com.serverworld.worldSocket.paperspigot.util.messager;
import com.serverworld.worlduserdata.jsondata.UserPhoenixPlayerData;
import com.serverworld.worlduserdata.query.UserPhoenixPlayerDataInquirer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerCommand_Home implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only player can use this command!");
            return false;
        }
        Player player = (Player) sender;
        UserPhoenixPlayerData playerdata = UserPhoenixPlayerDataInquirer.getDataClass(((Player) sender).getPlayer().getUniqueId());//get player data
        UserPhoenixPlayerData playerData = UserPhoenixPlayerDataInquirer.getDataClass(player.getUniqueId());
        playerData.setLastlocation_server(PaperPhoenix.config.servername());
        playerData.setLastlocation_world(player.getWorld().getName());
        playerData.setLastlocation_x(player.getLocation().getX());
        playerData.setLastlocation_y(player.getLocation().getY());
        playerData.setLastlocation_z(player.getLocation().getZ());
        UserPhoenixPlayerDataInquirer.setDataClass(player.getUniqueId() , playerData);//save dead pos to database

        player.sendMessage(Formats.perfix() + ChatColor.GREEN + "將您傳送至家");//TODO: Langauge seleter

        if(!PaperPhoenix.config.servername().equals(playerdata.getLastlocation_server())){
            messagecoder messagecoder = new messagecoder();
            messagecoder.setSender(PaperPhoenix.config.servername());
            messagecoder.setChannel("MISF_PHOENIX");
            messagecoder.setReceiver("PROXY");
            messagecoder.setType("ACTION");
            JsonObject json = new JsonObject();
            json.addProperty("TYPE","SEND_PLAYER_TO_SERVER");
            json.addProperty("PLAYER",((Player) sender).getPlayer().getUniqueId().toString());
            json.addProperty("SERVER",playerdata.getHome_server());
            messagecoder.setMessage(json.toString());
            messager.sendmessage(messagecoder.createmessage());
        }


        Bukkit.getScheduler().scheduleSyncDelayedTask(PaperPhoenix.getInstance(), () -> {
            messagecoder Messagecoder = new messagecoder();
            Messagecoder.setSender(PaperPhoenix.config.servername());
            Messagecoder.setChannel("MISF_PHOENIX");
            Messagecoder.setReceiver(playerdata.getHome_server());
            Messagecoder.setType("ACTION");
            JsonObject Json = new JsonObject();
            Json.addProperty("TYPE","TELEPORT_PLAYER");
            Json.addProperty("PLAYER",sender.getName());
            Json.addProperty("WORLD",playerdata.getHome_world());
            Json.addProperty("LOCATION_X",playerdata.getHome_x());
            Json.addProperty("LOCATION_Y",playerdata.getHome_y());
            Json.addProperty("LOCATION_Z",playerdata.getHome_z());
            Messagecoder.setMessage(Json.toString());
            messager.sendmessage(Messagecoder.createmessage());
        }, 20L);


        return true;
    }
}
