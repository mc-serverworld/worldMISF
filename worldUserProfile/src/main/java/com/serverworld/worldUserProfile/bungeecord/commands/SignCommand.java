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

package com.serverworld.worldUserProfile.bungeecord.commands;

import com.serverworld.worldUserProfile.bungeecord.uitls.mysql;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public class SignCommand extends Command {
    public SignCommand(Plugin plugin){
        super("sign");
    }


    public void execute(CommandSender commandSender, String[] strings) {
        try {
            if(commandSender.getName().toLowerCase().equals("console"))
                return;
            ProxiedPlayer p = (ProxiedPlayer)commandSender;
            if(mysql.getSigned(p.getUniqueId().toString())){
                commandSender.sendMessage(ChatColor.YELLOW + "You already signed agreement");
            }
        }catch (Exception e){
            commandSender.sendMessage(ChatColor.RED + "Invalid input");
        }
    }
}
