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
import com.serverworld.phoenix.paper.util.Formats;
import com.serverworld.worlduserdata.jsondata.UserPhoenixHome;
import com.serverworld.worlduserdata.query.UserPhoenixPlayerDataInquirer;
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
        if(label.isEmpty()){
            sender.sendMessage(ChatColor.RED + "請輸入家的名稱");//TODO: Langauge seleter
        }
        Player player = (Player) sender;
        UserPhoenixHome userPhoenixHome = new UserPhoenixHome();
        userPhoenixHome.setHome_Name(label);
        userPhoenixHome.setHome_Server(PaperPhoenix.config.servername());
        userPhoenixHome.setHome_World(player.getWorld().toString());
        userPhoenixHome.setHome_X(player.getLocation().getX());
        userPhoenixHome.setHome_Y(player.getLocation().getY());
        userPhoenixHome.setHome_Z(player.getLocation().getZ());
        userPhoenixHome.setHome_Yaw(player.getLocation().getYaw());

        if(UserPhoenixPlayerDataInquirer.addHome(player.getUniqueId(),userPhoenixHome))
            player.sendMessage(Formats.perfix() + ChatColor.GREEN + "設定您的家" + ChatColor.YELLOW + userPhoenixHome.getHome_Name() + ChatColor.GREEN + "於此");//TODO: Langauge seleter
        else
            player.sendMessage(Formats.perfix() + ChatColor.RED + "已達到您可設定的家的數量上線");//TODO: Langauge seleter


        DebugMessage.sendIfHasPermission(sender,Formats.debug_perfix() + "Server: " + PaperPhoenix.config.servername() + " World: " + player.getWorld().getName());

        return true;
    }
}
