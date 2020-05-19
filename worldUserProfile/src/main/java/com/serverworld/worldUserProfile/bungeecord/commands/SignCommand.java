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
import com.serverworld.worldUserProfile.jsondata.UserAccountData;
import com.serverworld.worldUserProfile.bungeecord.uitls.DebugMessage;
import com.serverworld.worldUserProfile.utils.IPAPI;
import com.serverworld.worldUserProfile.bungeecord.uitls.UserAccountDataMySQL;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SignCommand extends Command {
    public SignCommand(Plugin plugin){
        super("sign");
    }

    private static Set<CommandSender> players = new HashSet<>();


    public void execute(CommandSender commandSender, String[] strings) {
        try {
            if(commandSender.getName().toLowerCase().equals("console"))
                return;
            ProxiedPlayer player = (ProxiedPlayer)commandSender;
            if(UserAccountDataMySQL.getSigned(player.getUniqueId().toString())){
                commandSender.sendMessage(ChatColor.YELLOW + "You already signed agreement");
            }else if(players.contains(commandSender)){
                if(strings[0].equals("confirm")) {
                    synchronized (players) {
                        if (players.contains(commandSender)) {

                            Date date = new Date();
                            UserAccountData userAccountData= new UserAccountData();
                            JSONObject json = IPAPI.getJSON(player.getAddress().getAddress().toString());//status,continent,country,regionName,city,org,mobile,proxy
                            if(json.getString("status").equals("fail")){
                                DebugMessage.sendWarringIfDebug(ChatColor.YELLOW + "Fail to get json!" + json.toString());
                                ((ProxiedPlayer) commandSender).disconnect(ChatColor.RED + "\nFain while saving data, please concat server admin.");
                                return;
                            }
                            UserAccountDataMySQL.setSigned(player.getUniqueId().toString(),true);
                            userAccountData.setCity(json.getString("city"));
                            userAccountData.setContinent("continent");
                            userAccountData.setCountry("country");
                            userAccountData.setIP(player.getAddress().toString());
                            userAccountData.setISP("org");
                            userAccountData.setLastLogin(date.getTime());
                            userAccountData.setPlayedTime(0l);
                            userAccountData.setPlayername(player.getName());
                            userAccountData.setSignData(date.getTime());
                            userAccountData.setworldCoin(0);
                            UserAccountDataMySQL.setDataClass(player.getUniqueId().toString(), userAccountData);
                            ((ProxiedPlayer) commandSender).disconnect(ChatColor.GREEN + "\nYou has signed the agreeement\n\n" + ChatColor.AQUA + "Please Rejoin the server");
                        }
                    }
                }
                else if(strings[0].equals("no")){
                    ((ProxiedPlayer) commandSender).disconnect(ChatColor.RED + "\nYou must signed the agreeement if you want to playing this server\n\n" + ChatColor.AQUA + "Please rejoin the server and sign");
                }
            }else {
                TextComponent agreement = new TextComponent("BY CLICKING ON YES, YOU ACKNOWLEDGE THAT YOU, HAVE READ, UNDERSTAND, AND AGREE TO THE ");
                agreement.setColor(ChatColor.RED);
                agreement.setBold(true);
                TextComponent agreement2 = new TextComponent(" OF THIS SERVER\n\n       ");
                agreement2.setColor(ChatColor.RED);
                agreement2.setBold(true);
                TextComponent EulaURLComponent = new TextComponent( "EULA" );
                EulaURLComponent.setColor( ChatColor.AQUA );
                EulaURLComponent.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "Open in browser" ).create() ) );
                EulaURLComponent.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "https://www.mc-serverworld.com/rules" ) );

                TextComponent ButtonYESComponent = new TextComponent( "yes【✔】" );
                ButtonYESComponent.setColor( ChatColor.GREEN );
                ButtonYESComponent.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "I agree the agreement" ).create() ) );
                ButtonYESComponent.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/sign confirm" ) );

                TextComponent ButtonNOComponent = new TextComponent( "no【✖】" );
                ButtonNOComponent.setColor( ChatColor.RED );
                ButtonNOComponent.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "I dont agree the agreement" ).create() ) );
                ButtonNOComponent.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/sign no" ) );

                agreement.addExtra(EulaURLComponent);
                agreement.addExtra(agreement2);
                agreement.addExtra(ButtonYESComponent);
                agreement.addExtra("              ");
                agreement.addExtra(ButtonNOComponent);
                commandSender.sendMessage(agreement);
                players.add(commandSender);
                BungeeworldUserProfile.bungeeworldUserProfile.getProxy().getScheduler().schedule(BungeeworldUserProfile.bungeeworldUserProfile, new Runnable() {
                    public void run() {
                        players.remove(commandSender);
                    }
                }, 30, TimeUnit.SECONDS);
            }
        }catch (Exception e){
            commandSender.sendMessage(ChatColor.RED + "Invalid input");
            DebugMessage.sendWarringIfDebug(e.toString());
        }
    }
}
