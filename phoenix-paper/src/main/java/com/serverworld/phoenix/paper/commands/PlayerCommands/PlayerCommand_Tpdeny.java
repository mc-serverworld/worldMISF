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
import com.serverworld.worldSocket.paperspigot.util.messagecoder;
import com.serverworld.worldSocket.paperspigot.util.messager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlayerCommand_Tpdeny implements CommandExecutor {

    private static ArrayList<CommandSender> players = new ArrayList<CommandSender>();

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

        if(players.contains(sender)){
            sender.sendMessage(Formats.perfix() + ChatColor.RED + "您最近已發送過請求, 請等待30秒");//TODO: Langauge seleter
            return true;
        }
        players.add(sender);
        Bukkit.getScheduler().scheduleSyncDelayedTask(PaperPhoenix.getInstance(), () -> {
            try {players.remove(sender);}catch (Exception e){ }
        }, 600L);

        JSONObject message = TpQueue.getAndDelQueue(((Player) sender));
        Bukkit.getScheduler().scheduleSyncDelayedTask(PaperPhoenix.getInstance(), () -> {
            messagecoder messagecoder = new messagecoder();
            messagecoder.setSender(PaperPhoenix.config.servername());
            messagecoder.setChannel("MISF_PHOENIX");
            messagecoder.setReceiver("PROXY");
            messagecoder.setType("ACTION");
            JsonObject json = new JsonObject();
            json.addProperty("TYPE","TELEPORT_REQUEST_DENY");
            json.addProperty("PLAYER",sender.getName());
            json.addProperty("TARGET_PLAYER",message.getString("TARGET_PLAYER"));
            messagecoder.setMessage(json.toString());
            messager.sendmessage(messagecoder.createmessage());
        }, 0L);//send teleport status: deny
        return true;
    }
}
