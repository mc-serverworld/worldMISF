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

import com.serverworld.phoenix.paper.PaperPhoenix;
import com.serverworld.phoenix.paper.util.DebugMessage;
import com.serverworld.worlduserdata.jsondata.UserPhoenixPlayerData;
import com.serverworld.worlduserdata.paper.utils.UserPhoenixPlayerDataMySQL;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerCommand_Sethome implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only player can use this command!");
            return false;
        }
        Player player = (Player) sender;
        UserPhoenixPlayerData playerdata = UserPhoenixPlayerDataMySQL.getDataClass(((Player) sender).getPlayer().getUniqueId().toString());//get player data
        UserPhoenixPlayerData playerData = UserPhoenixPlayerDataMySQL.getDataClass(player.getUniqueId().toString());
        playerData.setHome_server(PaperPhoenix.config.servername());
        playerData.setHome_world(player.getWorld().getName());
        playerData.setHome_x(player.getLocation().getX());
        playerData.setHome_y(player.getLocation().getY());
        playerData.setHome_z(player.getLocation().getZ());
        UserPhoenixPlayerDataMySQL.setDataClass(player.getUniqueId().toString() , playerData);//save dead pos to database

        player.sendMessage(ChatColor.GREEN + "設定您的家於此");//TODO: Langauge seleter
        DebugMessage.sendIfHasPermission(sender,"Server: " + PaperPhoenix.config.servername() + " World: " + player.getWorld().getName());

        return false;//
    }
}
