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

package com.serverworld.worlduserdata.bungeecord.Listeners;

import com.serverworld.worldIdiot.api.BanQueryAPI;
import com.serverworld.worlduserdata.bungeecord.BungeeworldUserData;
import com.serverworld.worlduserdata.bungeecord.uitls.DebugMessage;
import com.serverworld.worlduserdata.jsondata.UserAccountData;
import com.serverworld.worlduserdata.query.UserAccountDataInquirer;
import com.serverworld.worlduserdata.query.UserPhoenixPlayerDataInquirer;
import com.serverworld.worlduserdata.utils.IPAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class PlayerLogin implements Listener {
    private final BungeeworldUserData bungeeworldUserData;
    public PlayerLogin(Plugin plugin, BungeeworldUserData bungeeworldUserData) {
        ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
        this.bungeeworldUserData = bungeeworldUserData;
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        if(BanQueryAPI.isBanned(event.getPlayer().getUniqueId().toString()))
            return;
        JSONObject jsonObject = IPAPI.getJSON(event.getPlayer().getAddress().getAddress().toString());

        if(!UserAccountDataInquirer.setUp(event.getPlayer().getUniqueId()))
            event.getPlayer().disconnect(ChatColor.YELLOW + "We are updating your data\nplease login again\n\n" + ChatColor.RED + "if this keep happen please contact admin");
        if(!UserPhoenixPlayerDataInquirer.setUp(event.getPlayer().getUniqueId()))
            event.getPlayer().disconnect(ChatColor.YELLOW + "We are updating your data\nplease login again\n\n" + ChatColor.RED + "if this keep happen please contact admin");


        if (jsonObject.getString("status").equals("fail")){
            DebugMessage.sendWarring(ChatColor.RED + "Fail to get country!");
            if(UserAccountDataInquirer.getSigned(event.getPlayer().getUniqueId())){
                UserAccountData userAccountData = UserAccountDataInquirer.getDataClass(event.getPlayer().getUniqueId());
                Date date = new Date();
                userAccountData.setCity("none");
                userAccountData.setContinent("none");
                userAccountData.setCountry("none");
                userAccountData.setIp(event.getPlayer().getAddress().getAddress().toString());
                userAccountData.setIsp("none");
                userAccountData.setLastLogin(date.getTime());
                userAccountData.setPlayerName(event.getPlayer().getName());
                UserAccountDataInquirer.setDataClass(event.getPlayer().getUniqueId(), userAccountData);
            }
        }else {
            DebugMessage.sendInfo("Player " + event.getPlayer().getName() + " from " + jsonObject.getString("country"));
            if (UserAccountDataInquirer.getSigned(event.getPlayer().getUniqueId())) {
                UserAccountData userAccountData = UserAccountDataInquirer.getDataClass(event.getPlayer().getUniqueId());
                Date date = new Date();
                userAccountData.setCity(jsonObject.getString("city"));
                userAccountData.setContinent(jsonObject.getString("continent"));
                userAccountData.setCountry(jsonObject.getString("country"));
                userAccountData.setIp(event.getPlayer().getAddress().getAddress().toString());
                userAccountData.setIsp(jsonObject.getString("org"));
                userAccountData.setLastLogin(date.getTime());
                userAccountData.setPlayerName(event.getPlayer().getName());
                UserAccountDataInquirer.setDataClass(event.getPlayer().getUniqueId(), userAccountData);
            }
        }
            List<String> support_country_list = new ArrayList();
            support_country_list.add("taiwan");
            //support_country_list.add("china");
            support_country_list.add("hong kong");
            support_country_list.add("macao");
            support_country_list.add("singapore");
            ProxyServer.getInstance().createTitle()
                    .reset()
                    .send(event.getPlayer());
            if(!UserAccountDataInquirer.getSigned(event.getPlayer().getUniqueId())){
                try {
                    if(support_country_list.contains(jsonObject.getString("country").toLowerCase())){
                        //support
                        bungeeworldUserData.getProxy().getScheduler().schedule(bungeeworldUserData, new Runnable() {
                            public void run() {
                                ProxyServer.getInstance().createTitle()
                                        .title(new ComponentBuilder("歡迎來到mc-serverworld")
                                                .color(ChatColor.AQUA).create())
                                        .subTitle(new ComponentBuilder("請輸入/sign來簽署協議")
                                                .color(ChatColor.GREEN).create())
                                        .fadeIn(20)
                                        .stay(40)
                                        .fadeOut(20)
                                        .send(event.getPlayer());
                            }
                        }, 5, 5, TimeUnit.SECONDS);
                    }else {
                        //unsupport
                        bungeeworldUserData.getProxy().getScheduler().schedule(bungeeworldUserData, new Runnable() {
                            public void run() {
                                ProxyServer.getInstance().createTitle()
                                        .title(new ComponentBuilder("Wellcome to mc-serverworld")
                                                .color(ChatColor.AQUA).create())
                                        .subTitle(new ComponentBuilder("Please enter /sign to sign the agreement")
                                                .color(ChatColor.GREEN).create())
                                        .fadeIn(20)
                                        .stay(40)
                                        .fadeOut(20)
                                        .send(event.getPlayer());
                            }
                        }, 5, 5,TimeUnit.SECONDS);

                    }
                }catch (Exception e) {
                    //unsupport
                    bungeeworldUserData.getProxy().getScheduler().schedule(bungeeworldUserData, new Runnable() {
                        public void run() {
                            ProxyServer.getInstance().createTitle()
                                    .title(new ComponentBuilder("Wellcome to mc-serverworld")
                                            .color(ChatColor.AQUA).create())
                                    .subTitle(new ComponentBuilder("Please enter /sign to sign the agreement")
                                            .color(ChatColor.GREEN).create())
                                    .fadeIn(20)
                                    .stay(40)
                                    .fadeOut(20)
                                    .send(event.getPlayer());
                        }
                    }, 5, 5,TimeUnit.SECONDS);
                }
            }
        }

    }
