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
import org.bukkit.entity.Player;

public class LobbyCommand implements CommandExecutor{
    private PaperPhoenix paperPhoenix;
    private PaperPhoenixConfig config;


    public LobbyCommand(PaperPhoenix i){
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

                    String prefix = (ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "MISF" + ChatColor.GRAY + "]" + ChatColor.WHITE + "  -  ");
                    String prefix_sub = (ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "MISF" + ChatColor.GRAY + "]" + ChatColor.WHITE + "    -  ");
                    commandSender.sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "MISF" + ChatColor.GRAY + "]" + ChatColor.GREEN + "worldMISF info");
                    commandSender.sendMessage(prefix + ChatColor.AQUA + "worldMISF");
                    commandSender.sendMessage(prefix_sub + ChatColor.DARK_AQUA + "Plugin version: " + ChatColor.WHITE + PaperPhoenix.getInstance().getDescription().getVersion());
                    commandSender.sendMessage(prefix_sub + ChatColor.DARK_AQUA + "Server name(config): " + ChatColor.WHITE + config.servername());
                    commandSender.sendMessage(prefix_sub + ChatColor.DARK_AQUA + "Server name(Bungeecord): " + ChatColor.WHITE + config.serversprefix() + config.worldtype().toUpperCase() + "_" + config.chunk_position_x() + "_" + config.chunk_position_z());
                    commandSender.sendMessage(prefix_sub + ChatColor.DARK_AQUA + "Server type: " + ChatColor.WHITE + config.servetype());
                    commandSender.sendMessage(prefix_sub + ChatColor.DARK_AQUA + "Server position: " + ChatColor.WHITE + "(" + config.chunk_position_x() + "," + config.chunk_position_z() + ")");
                    commandSender.sendMessage(prefix_sub + ChatColor.DARK_AQUA + "World type: " + ChatColor.WHITE + config.worldtype());
                    commandSender.sendMessage(prefix_sub + ChatColor.DARK_AQUA + "World size: " + ChatColor.WHITE + config.worldsize());
                    commandSender.sendMessage();
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
}
