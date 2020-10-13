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
import com.serverworld.phoenix.paper.Listeners.queue.TpQueue;
import com.serverworld.phoenix.paper.PaperPhoenix;
import com.serverworld.phoenix.paper.util.Formats;
import com.serverworld.phoenix.paper.util.Player.PlayerData;
import com.serverworld.worldSocket.paperspigot.util.messagecoder;
import com.serverworld.worldSocket.paperspigot.util.messager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.JSONObject;

public class PlayerCommand_Tpaccept implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only player can use this command!");
            return false;
        }

        if (!TpQueue.hasQueue(((Player) sender))){
            sender.sendMessage(Formats.perfix() + ChatColor.RED + "您沒有待確認的傳送請求");//TODO: Langauge seleter
            return true;
        }

        JSONObject message = TpQueue.getAndDelQueue(((Player) sender));
        if(message.get("TYPE").equals("TELEPORT_REQUEST_TPA")){
            sender.sendMessage(Formats.perfix() + ChatColor.GREEN + "您接受了對方的傳送請求, 正在將對方傳送至您的伺服器");//TODO: Langauge seleter

            Bukkit.getScheduler().scheduleSyncDelayedTask(PaperPhoenix.getInstance(), () -> {
                messagecoder messagecoder = new messagecoder();
                messagecoder.setSender(PaperPhoenix.config.servername());
                messagecoder.setChannel("MISF_PHOENIX");
                messagecoder.setReceiver("PROXY");
                messagecoder.setType("ACTION");
                JsonObject json = new JsonObject();
                json.addProperty("TYPE","TELEPORT_REQUEST_TPA_ACCEPT");
                json.addProperty("PLAYER",message.getString("PLAYER"));
                json.addProperty("TARGET_PLAYER",sender.getName());
                messagecoder.setMessage(json.toString());
                messager.sendmessage(messagecoder.createmessage());
            }, 0L);//send teleport status: accept

            Bukkit.getScheduler().scheduleSyncDelayedTask(PaperPhoenix.getInstance(), () -> {
                messagecoder messagecoder = new messagecoder();
                messagecoder.setSender(PaperPhoenix.getInstance().config.servername());
                messagecoder.setChannel("MISF_PHOENIX");
                messagecoder.setReceiver("PROXY");
                messagecoder.setType("ACTION");
                JsonObject json = new JsonObject();
                json.addProperty("TYPE","SEND_PLAYER_TO_SERVER");
                json.addProperty("PLAYER",message.getString("PLAYER"));
                json.addProperty("SERVER",PaperPhoenix.config.servername());
                messagecoder.setMessage(json.toString());
                messager.sendmessage(messagecoder.createmessage());
            }, 40L);//send player to this server


            Bukkit.getScheduler().scheduleSyncDelayedTask(PaperPhoenix.getInstance(), () -> {
                Player target_player = PaperPhoenix.getInstance().getServer().getPlayer(message.getString("TARGET_PLAYER"));
                Player player = PaperPhoenix.getInstance().getServer().getPlayer(message.getString("PLAYER"));
                player.teleport(target_player);
            }, 60L);

            return true;

        }else if(message.get("TYPE").equals("TELEPORT_REQUEST_TPAHERE")){
            sender.sendMessage(Formats.perfix() + ChatColor.GREEN + "您接受了對方的傳送請求, 正在將您傳送到目標伺服器");//TODO: Langauge seleter

            PlayerData.SaveCurrentLocationAsLast(((Player) sender).getPlayer(), ((Player) sender).getLocation());

            Bukkit.getScheduler().scheduleSyncDelayedTask(PaperPhoenix.getInstance(), () -> {
                messagecoder messagecoder = new messagecoder();
                messagecoder.setSender(PaperPhoenix.config.servername());
                messagecoder.setChannel("MISF_PHOENIX");
                messagecoder.setReceiver("PROXY");
                messagecoder.setType("ACTION");
                JsonObject json = new JsonObject();
                json.addProperty("TYPE","TELEPORT_REQUEST_TPAHERE_ACCEPT");
                json.addProperty("PLAYER",message.getString("PLAYER"));
                json.addProperty("TARGET_PLAYER",sender.getName());
                messagecoder.setMessage(json.toString());
                messager.sendmessage(messagecoder.createmessage());
            }, 20L);//send teleport status: accept

        }
        //message.getString();
        return true;

    }
}
