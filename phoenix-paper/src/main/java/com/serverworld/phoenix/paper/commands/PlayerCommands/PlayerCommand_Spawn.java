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
import com.serverworld.phoenix.paper.util.DebugMessage;
import com.serverworld.phoenix.paper.util.Formats;
import com.serverworld.worldSocket.paperspigot.util.messagecoder;
import com.serverworld.worldSocket.paperspigot.util.messager;
import com.serverworld.worlduserdata.jsondata.UserPhoenixPlayerData;
import com.serverworld.worlduserdata.paper.utils.UserPhoenixPlayerDataMySQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerCommand_Spawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only player can use this command!");
            return false;
        }
        Player player = (Player) sender;
        UserPhoenixPlayerData playerdata = UserPhoenixPlayerDataMySQL.getDataClass(((Player) sender).getPlayer().getUniqueId().toString());//get player data
        UserPhoenixPlayerData playerData = UserPhoenixPlayerDataMySQL.getDataClass(player.getUniqueId().toString());
        playerData.setLastlocation_server(PaperPhoenix.config.servername());
        playerData.setLastlocation_world(player.getWorld().getName());
        playerData.setLastlocation_x(player.getLocation().getX());
        playerData.setLastlocation_y(player.getLocation().getY());
        playerData.setLastlocation_z(player.getLocation().getZ());
        UserPhoenixPlayerDataMySQL.setDataClass(player.getUniqueId().toString() , playerData);//save dead pos to database

        player.sendMessage(Formats.perfix() + ChatColor.GREEN + "將您傳送至重生點");//TODO: Langauge seleter

        if(PaperPhoenix.config.servername().equals(PaperPhoenix.config.serversprefix() + "OVERWORLD_0_0")){
            World world = PaperPhoenix.getInstance().getServer().getWorld("world");
            Location spawn = new Location(world,PaperPhoenix.config.spawnx(), PaperPhoenix.config.spawny(),PaperPhoenix.config.spawnz());
            DebugMessage.sendInfoIfDebug("Send Player " + player.getName() + " to spawn");
            player.teleport(spawn);
            return true;
        }//if server is x0z0

        Bukkit.getScheduler().scheduleSyncDelayedTask(PaperPhoenix.getInstance(), () -> {
            messagecoder messagecoder = new messagecoder();
            messagecoder.setSender(PaperPhoenix.config.servername());
            messagecoder.setChannel("MISF_PHOENIX");
            messagecoder.setReceiver("PROXY");
            messagecoder.setType("ACTION");
            JsonObject json = new JsonObject();
            json.addProperty("TYPE","SEND_PLAYER_TO_SERVER");
            json.addProperty("PLAYER",player.getUniqueId().toString());
            json.addProperty("SERVER",PaperPhoenix.config.serversprefix() + "OVERWORLD_0_0");
            messagecoder.setMessage(json.toString());
            messager.sendmessage(messagecoder.createmessage());
        }, 5L);//send player to spawn

        Bukkit.getScheduler().scheduleSyncDelayedTask(PaperPhoenix.getInstance(), () -> {
            messagecoder messagecoder = new messagecoder();
            messagecoder.setSender(PaperPhoenix.config.servername());
            messagecoder.setChannel("MISF_PHOENIX");
            messagecoder.setReceiver(PaperPhoenix.config.serversprefix() + "OVERWORLD_0_0");
            messagecoder.setType("ACTION");
            JsonObject json = new JsonObject();
            json.addProperty("TYPE","RESPAWN_PLAYER");
            json.addProperty("PLAYER",player.getName());
            messagecoder.setMessage(json.toString());
            messager.sendmessage(messagecoder.createmessage());
        }, 20L);//tell spawn server to respawn player

        return true;
    }
}
