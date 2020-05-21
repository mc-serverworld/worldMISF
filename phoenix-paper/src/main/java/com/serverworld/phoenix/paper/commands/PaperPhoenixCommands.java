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

package com.serverworld.phoenix.paper.commands;

import com.serverworld.phoenix.paper.PaperPhoenix;
import com.serverworld.phoenix.paper.PaperPhoenixConfig;
import com.serverworld.phoenix.paper.commands.subcommands.subCommand_set;
import com.serverworld.phoenix.paper.util.DebugMessage;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PaperPhoenixCommands implements CommandExecutor , TabCompleter {
    private PaperPhoenix paperPhoenix;
    private PaperPhoenixConfig config;
    private static final List<String> sub_commands = Arrays.asList("set");
    private static final List<String> sub_commands_set = Arrays.asList("weather", " time");


    public PaperPhoenixCommands(PaperPhoenix i){
        this.paperPhoenix = i;
        this.config = paperPhoenix.config;
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
                        Player player = (Player)commandSender;
                        player.setAllowFlight(true);
                        return false;
                    }

                    commandSender.sendMessage(ChatColor.BLUE + "======info======");
                    commandSender.sendMessage(ChatColor.AQUA + "Servername: " + ChatColor.GREEN + config.servername());
                    return true;
                }
                case "set": {
                    subCommand_set.cmd(commandSender,strings);
                }
                //=======================
            }
        }catch (ArrayIndexOutOfBoundsException e){
            DebugMessage.sendWarringIfDebug(ChatColor.RED + "Error while executing command " + e.getMessage());
            commandSender.sendMessage(ChatColor.RED + "Invalid input");
        }catch (Exception e){
            DebugMessage.sendWarring(ChatColor.RED + "Error while executing command " + e.getMessage());
            commandSender.sendMessage(ChatColor.RED + "Error while executing command " + e.getMessage());
        }
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
