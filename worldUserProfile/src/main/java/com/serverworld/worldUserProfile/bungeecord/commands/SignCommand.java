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

import com.serverworld.worldUserProfile.bungeecord.BungeeworldUserProfile;
import com.serverworld.worldUserProfile.bungeecord.uitls.mysql;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SignCommand extends Command {
    public SignCommand(Plugin plugin){
        super("sign");
    }

    private static Set<ProxiedPlayer> players = new HashSet<>();


    public void execute(CommandSender commandSender, String[] strings) {
        try {
            if(commandSender.getName().toLowerCase().equals("console"))
                return;
            ProxiedPlayer player = (ProxiedPlayer)commandSender;
            if(mysql.getSigned(player.getUniqueId().toString())){
                commandSender.sendMessage(ChatColor.YELLOW + "You already signed agreement");
            }else if(players.contains(player)){
                if(strings[0].equals("confirm")) {
                    synchronized (players) {
                        if (players.contains(player)) {
                            player.disconnect(ChatColor.GREEN + "You has signed the agreeement\n\n" + ChatColor.AQUA + "Please Rejoin the server");
                        }
                    }
                }
            }else {
                TextComponent agreement = new TextComponent("BY CLICKING ON YES, YOU ACKNOWLEDGE THAT YOU, HAVE READ, UNDERSTAND, AND AGREE TO THE ");
                agreement.setColor( ChatColor.RED );
                TextComponent EulaURLComponent = new TextComponent( "EULA" );
                EulaURLComponent.setColor( ChatColor.AQUA );
                EulaURLComponent.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "Open in browser" ).create() ) );
                EulaURLComponent.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "https://www.mc-serverworld.com/rules" ) );
                agreement.addExtra(EulaURLComponent);
                player.sendMessage(agreement);
                players.add(player);
                BungeeworldUserProfile.bungeeworldUserProfile.getProxy().getScheduler().schedule(BungeeworldUserProfile.bungeeworldUserProfile, new Runnable() {
                    public void run() {
                        players.remove(player);
                    }
                }, 10, TimeUnit.SECONDS);
            }
        }catch (Exception e){
            commandSender.sendMessage(ChatColor.RED + "Invalid input");
            e.printStackTrace();
        }
    }
}
