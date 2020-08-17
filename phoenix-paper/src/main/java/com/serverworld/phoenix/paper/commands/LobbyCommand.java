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
import com.serverworld.phoenix.paper.util.DebugMessage;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LobbyCommand implements CommandExecutor , TabExecutor {
    private PaperPhoenix paperPhoenix;
    private PaperPhoenixConfig config;


    public LobbyCommand(PaperPhoenix i){
        this.paperPhoenix = i;
        this.config = paperPhoenix.config;
    }
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        try {
            switch (strings[0]) {
                case "info": {
                    if(!commandSender.hasPermission("misf.command.info")){
                        commandSender.sendMessage( ChatColor.RED + "no permission");
                        Player player = (Player)commandSender;
                        player.setAllowFlight(true);
                        return false;
                    }

                    String prefix = (ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "MISF" + ChatColor.GRAY + "]" + ChatColor.WHITE + "  -  ");
                    String prefix_sub = (ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "MISF" + ChatColor.GRAY + "]" + ChatColor.WHITE + "    -  ");
                    commandSender.sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "MISF" + ChatColor.GRAY + "]" + ChatColor.GREEN + "worldMISF info");
                    commandSender.sendMessage(prefix + ChatColor.AQUA + "worldMISF");
                    commandSender.sendMessage(prefix_sub + ChatColor.DARK_AQUA + "Plugin version: " + ChatColor.WHITE + PaperPhoenix.getInstance().getDescription().getVersion());
                    commandSender.sendMessage(prefix_sub + ChatColor.DARK_AQUA + "Server name(config): " + ChatColor.WHITE + config.servername());
                    commandSender.sendMessage(prefix_sub + ChatColor.DARK_AQUA + "Server name(Bungeecord): " + ChatColor.WHITE + config.servername());
                    commandSender.sendMessage(prefix_sub + ChatColor.DARK_AQUA + "Server type: " + ChatColor.WHITE + config.servetype());
                    commandSender.sendMessage();
                    return true;
                }
                default:{
                    commandSender.sendMessage(ChatColor.RED + "Invalid input");
                    return true;
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
            if (sender.hasPermission("misf.command.info"))
                commands.add("info");
        }
            StringUtil.copyPartialMatches(args[0], commands, completions);
        Collections.sort(completions);
        return completions;
    }
}
