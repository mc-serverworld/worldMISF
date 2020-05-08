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

package com.serverworld.phoenix.bungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.*;

public class BungeePhoenixCommands extends Command implements TabExecutor{
    public BungeePhoenixCommands(Plugin plugin){
        super("misfbungeecord","misfbungeecord");
    }

    //dan playername endtime reason
    public void execute(CommandSender commandSender, String[] strings) {

    }

    public static boolean startsWithIgnoreCase(String s, String start) {
        return s.regionMatches(true, 0, start, 0, start.length());
    }


    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();

        if (args.length == 1) {
            if (sender.hasPermission("misfbungeecord.command.help"))
                commands.add("help");
            if (sender.hasPermission("misfbungeecord.command.info"))
                commands.add("info");
            if (sender.hasPermission("misfbungeecord.command.sudo.setup"))
                commands.add("setup");
            if (sender.hasPermission("misfpaperspigot.command.login"))
                commands.add("login");

            for (String subCmd : commands)
                if (startsWithIgnoreCase(subCmd, args[0])) completions.add(subCmd);
        }/*else if (args.length == 2) {

            if (args[0].equals("set")) {
                if (sender.hasPermission("misfpaperspigot.command.set.time"))
                    commands.add("time");
            }

            for (String subCmd : commands)
                if (startsWithIgnoreCase(subCmd, args[1])) completions.add(subCmd);
        } else if (args.length == 3) {

            if (args[0].equals("set")&&args[1].equals("weather")) {
                commands.add("clear");
            } else if (args[0].equals("set")&&args[1].equals("time")){
                commands.add("day");
            }

            for (String subCmd : commands)
                if (startsWithIgnoreCase(subCmd, args[2])) completions.add(subCmd);
        }*/
        Collections.sort(completions);
        return completions;
    }
}
