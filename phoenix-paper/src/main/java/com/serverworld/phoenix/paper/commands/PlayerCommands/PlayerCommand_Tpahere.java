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
import com.serverworld.phoenix.paper.util.BungeeParameter;
import com.serverworld.phoenix.paper.util.Formats;
import com.serverworld.worldSocket.paperspigot.util.messagecoder;
import com.serverworld.worldSocket.paperspigot.util.messager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerCommand_Tpahere  implements CommandExecutor , TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only player can use this command!");
            return false;
        }
        if(args.length==0){
            sender.sendMessage(Formats.perfix() + ChatColor.RED + "請輸入正確的玩家名稱");//TODO: Langauge seleter
            return true;
        }
        if(args[0].equals(sender.getName())){
            sender.sendMessage(Formats.perfix() + ChatColor.RED + "你不能傳送到你自己身上");//TODO: Langauge seleter
            return true;
        }
        if(!BungeeParameter.getPlayerlist().contains(args[0])){
            sender.sendMessage(Formats.perfix() + ChatColor.RED + "找不到此玩家");//TODO: Langauge seleter
            return true;
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(PaperPhoenix.getInstance(), () -> {
            messagecoder messagecoder = new messagecoder();
            messagecoder.setSender(PaperPhoenix.config.servername());
            messagecoder.setChannel("MISF_PHOENIX");
            messagecoder.setReceiver("PROXY");
            messagecoder.setType("ACTION");
            JsonObject json = new JsonObject();
            json.addProperty("TYPE","TELEPORT_REQUEST_TPAHERE");
            json.addProperty("PLAYER",sender.getName());
            json.addProperty("TARGET_PLAYER",args[0]);
            //json.addProperty("TELEPORT_LOCATION_X",((Player) sender).getLocation().getX());
            //json.addProperty("TELEPORT_LOCATION_Y",((Player) sender).getLocation().getY());
            //json.addProperty("TELEPORT_LOCATION_Z",((Player) sender).getLocation().getZ());
            messagecoder.setMessage(json.toString());
            messager.sendmessage(messagecoder.createmessage());
        }, 0L);

        sender.sendMessage(Formats.perfix() + ChatColor.GOLD + "向 " + ChatColor.YELLOW + args[0] +ChatColor.GOLD + " 送出傳送請求");//TODO: Langauge seleter
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        List<String> completions = new ArrayList<>();
        List<String> players = new ArrayList<>();
        for (String stuff:BungeeParameter.getPlayerlist())
            players.add(stuff);
        StringUtil.copyPartialMatches(args[0], players, completions);
        Collections.sort(completions);
        return completions;
    }

}
