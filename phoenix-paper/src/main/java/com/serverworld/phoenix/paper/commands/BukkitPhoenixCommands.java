/*
 * WorldMISF - cms of mc-serverworld
 * Copyright (C) 2019-2020 mc-serverworld
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.serverworld.phoenix.paper.commands;

import com.serverworld.phoenix.paper.BukkitPhoenix;
import com.serverworld.phoenix.paper.BukkitPhoenixConfig;
import com.serverworld.worldSocket.paperspigot.util.messagecoder;
import com.serverworld.worldSocket.paperspigot.util.messager;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BukkitPhoenixCommands implements CommandExecutor , TabCompleter {
    private BukkitPhoenix bukkitPhoenix;
    static private final List<String> sub_commands = Arrays.asList("set");
    static private final List<String> sub_commands_set = Arrays.asList("weather", " time");


    public BukkitPhoenixCommands(BukkitPhoenix i){
        this.bukkitPhoenix = i;
    }
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        try {
            switch (strings[0]) {
                default:{
                    commandSender.sendMessage(ChatColor.RED + "Invalid input");
                    return true;
                }
                case "info": {
                    if(!commandSender.hasPermission("misf.command.info")){
                        commandSender.sendMessage( ChatColor.RED + "no permission");
                        return false;
                    }
                    BukkitPhoenixConfig config = bukkitPhoenix.config;
                    commandSender.sendMessage(ChatColor.BLUE + "======info======");
                    commandSender.sendMessage(ChatColor.AQUA + "Servername: " + ChatColor.GREEN + config.servername());
                }
                case "set": {
                    messagecoder messagecode = new messagecoder();
                    messagecode.setSender(bukkitPhoenix.config.servername());
                    messagecode.setReceiver("ALL");
                    messagecode.setChannel("MISF");
                    messagecode.setType("SET");
                    switch (strings[1]){
                        case "weather":{
                            if(!commandSender.hasPermission("misf.command.set. weather")){
                                commandSender.sendMessage( ChatColor.RED + "no permission");
                                return false;
                            }
                            if(!NumberUtils.isNumber(strings[3])){
                                commandSender.sendMessage(ChatColor.RED + "Invalid input");
                                return true;
                            }
                            if(strings[2].toUpperCase().equals("CLEAR")){
                                messagecode.setMessage("WEATHER," + strings[2] + "," + Long.valueOf(strings[3]));
                                commandSender.sendMessage(ChatColor.GREEN + "Set weather to clear");
                            }else if(strings[2].toUpperCase().equals("RAIN")){
                                messagecode.setMessage("WEATHER," + strings[2] + "," + Long.valueOf(strings[3]));
                                commandSender.sendMessage(ChatColor.GREEN + "Set weather to rain");
                            }else if(strings[2].toUpperCase().equals("THUNDER")) {
                                messagecode.setMessage("WEATHER," + strings[2] + "," + Long.valueOf(strings[3]));
                                commandSender.sendMessage(ChatColor.GREEN + "Set weather to thunderstorm");
                            }else {
                                commandSender.sendMessage(ChatColor.RED + "Invalid input");
                                return true;
                            }
                            messager.sendmessage(messagecode.createmessage());
                            return true;
                        }

                        case "time":{
                            if(!commandSender.hasPermission("misf.command.set.time")){
                                commandSender.sendMessage( ChatColor.RED + "no permission");
                                return false;
                            }
                            if(NumberUtils.isNumber(strings[2])){
                                messagecode.setMessage("TIME," + strings[2]);
                                commandSender.sendMessage(ChatColor.GREEN + "Set time to " + strings[2]);
                                messager.sendmessage(messagecode.createmessage());
                                return true;
                            }
                            switch (strings[2].toUpperCase()){
                                case "DAY":{
                                    messagecode.setMessage("TIME,1000");
                                    commandSender.sendMessage(ChatColor.GREEN + "Set time to " + strings[2].toLowerCase());
                                    messager.sendmessage(messagecode.createmessage());
                                    return true;
                                }
                                case "NOON":{
                                    messagecode.setMessage("TIME,6000");
                                    commandSender.sendMessage(ChatColor.GREEN + "Set time to " + strings[2].toLowerCase());
                                    messager.sendmessage(messagecode.createmessage());
                                    return true;
                                }
                                case "NIGHT":{
                                    messagecode.setMessage("TIME,13000");
                                    commandSender.sendMessage(ChatColor.GREEN + "Set time to " + strings[2].toLowerCase());
                                    messager.sendmessage(messagecode.createmessage());
                                    return true;
                                }
                                case "MIDNIGHT":{
                                    messagecode.setMessage("TIME,18000");
                                    commandSender.sendMessage(ChatColor.GREEN + "Set time to " + strings[2].toLowerCase());
                                    messager.sendmessage(messagecode.createmessage());
                                    return true;
                                }
                                default:{
                                    commandSender.sendMessage(ChatColor.RED + "Invalid input");
                                    return true;
                                }
                            }
                        }
                    }
                }
                //=======================

            }
        }catch (ArrayIndexOutOfBoundsException e){
            commandSender.sendMessage(ChatColor.RED + "Invalid input");
        }


        /*ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Forward"); // So BungeeCord knows to forward it
        out.writeUTF("ALL");
        out.writeUTF("MISF"); // The channel name to check if this your data

        ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
        DataOutputStream msgout = new DataOutputStream(msgbytes);
        try {
            msgout.writeUTF(strings[0]); // You can do anything you want with msgout
        } catch (IOException exception){
            exception.printStackTrace();
        }
        out.write(msgbytes.toByteArray());*/
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();

        if (args.length == 1) {
            if (sender.hasPermission("misfpaperspigot.command.help"))
                commands.add("help");
            if (sender.hasPermission("misfpaperspigot.command.info"))
                commands.add("info");
            if (sender.hasPermission("misfpaperspigot.command.tpserver"))
                commands.add("tpserver");
            if (sender.hasPermission("misfpaperspigot.command.login"))
                commands.add("login");
            if (sender.hasPermission("misfpaperspigot.command.set"))
                commands.add("set");
            if (sender.hasPermission("misfpaperspigot.command.warpsv"))
                commands.add("warpsv");
            StringUtil.copyPartialMatches(args[0], commands, completions);
        } else if (args.length == 2) {
            if (args[0].equals("set")) {
                if (sender.hasPermission("misfpaperspigot.command.set.time"))
                    commands.add("time");
                if (sender.hasPermission("misfpaperspigot.command.set.weather"))
                    commands.add("weather");
            }
            StringUtil.copyPartialMatches(args[1], commands, completions);
        } else if (args.length == 3) {
            if (args[0].equals("set")&&args[1].equals("weather")) {
                commands.add("clear");
                commands.add("rain");
                commands.add("thunder");
            } else if (args[0].equals("set")&&args[1].equals("time")){
                commands.add("day");
                commands.add("noon");
                commands.add("night");
                commands.add("midnight");
            }
            StringUtil.copyPartialMatches(args[2], commands, completions);
        }
        Collections.sort(completions);
        return completions;
    }
}
