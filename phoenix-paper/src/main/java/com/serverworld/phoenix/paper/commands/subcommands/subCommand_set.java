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

package com.serverworld.phoenix.paper.commands.subcommands;

import com.serverworld.phoenix.paper.PaperPhoenix;
import com.serverworld.worldSocket.paperspigot.util.messagecoder;
import com.serverworld.worldSocket.paperspigot.util.messager;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.CommandSender;

public class subCommand_set {

    public static Boolean cmd(CommandSender commandSender, String[] strings) throws Exception,ArrayIndexOutOfBoundsException {
        messagecoder messagecode = new messagecoder();
        messagecode.setSender(PaperPhoenix.config.servername());
        messagecode.setReceiver("ALL");
        messagecode.setChannel("MISF_PHOENIX");
        messagecode.setType("SET");
        switch (strings[1]) {
            case "weather": {
                if (!commandSender.hasPermission("misf.command.set.weather")) {
                    commandSender.sendMessage(ChatColor.RED + "no permission");
                    return false;
                }
                if (!NumberUtils.isNumber(strings[3])) {
                    commandSender.sendMessage(ChatColor.RED + "Invalid input");
                    return true;
                }
                if (strings[2].toUpperCase().equals("CLEAR")) {
                    messagecode.setMessage("WEATHER," + strings[2] + "," + Long.valueOf(strings[3]));
                    commandSender.sendMessage(ChatColor.GREEN + "Set weather to clear");
                } else if (strings[2].toUpperCase().equals("RAIN")) {
                    messagecode.setMessage("WEATHER," + strings[2] + "," + Long.valueOf(strings[3]));
                    commandSender.sendMessage(ChatColor.GREEN + "Set weather to rain");
                } else if (strings[2].toUpperCase().equals("THUNDER")) {
                    messagecode.setMessage("WEATHER," + strings[2] + "," + Long.valueOf(strings[3]));
                    commandSender.sendMessage(ChatColor.GREEN + "Set weather to thunderstorm");
                } else {
                    commandSender.sendMessage(ChatColor.RED + "Invalid input");
                    return true;
                }
                messager.sendmessage(messagecode.createmessage());
                return true;
            }

            case "time": {
                if (!commandSender.hasPermission("misf.command.set.time")) {
                    commandSender.sendMessage(ChatColor.RED + "no permission");
                    return false;
                }
                if (NumberUtils.isNumber(strings[2])) {
                    messagecode.setMessage("TIME," + strings[2]);
                    commandSender.sendMessage(ChatColor.GREEN + "Set time to " + strings[2]);
                    messager.sendmessage(messagecode.createmessage());
                    return true;
                }
                switch (strings[2].toUpperCase()) {
                    case "DAY": {
                        messagecode.setMessage("TIME,1000");
                        commandSender.sendMessage(ChatColor.GREEN + "Set time to " + strings[2].toLowerCase());
                        messager.sendmessage(messagecode.createmessage());
                        return true;
                    }
                    case "NOON": {
                        messagecode.setMessage("TIME,6000");
                        commandSender.sendMessage(ChatColor.GREEN + "Set time to " + strings[2].toLowerCase());
                        messager.sendmessage(messagecode.createmessage());
                        return true;
                    }
                    case "NIGHT": {
                        messagecode.setMessage("TIME,13000");
                        commandSender.sendMessage(ChatColor.GREEN + "Set time to " + strings[2].toLowerCase());
                        messager.sendmessage(messagecode.createmessage());
                        return true;
                    }
                    case "MIDNIGHT": {
                        messagecode.setMessage("TIME,18000");
                        commandSender.sendMessage(ChatColor.GREEN + "Set time to " + strings[2].toLowerCase());
                        messager.sendmessage(messagecode.createmessage());
                        return true;
                    }
                    default: {
                        commandSender.sendMessage(ChatColor.RED + "Invalid input");
                        return true;
                    }
                }
            }
        }
        return true;
    }

}
