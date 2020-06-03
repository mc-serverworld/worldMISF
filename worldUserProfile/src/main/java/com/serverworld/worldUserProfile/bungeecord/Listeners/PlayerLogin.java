package com.serverworld.worldUserProfile.bungeecord.Listeners;

import com.serverworld.worldIdiot.api.BanQueryAPI;
import com.serverworld.worldUserProfile.bungeecord.BungeeworldUserProfile;
import com.serverworld.worldUserProfile.bungeecord.uitls.DebugMessage;
import com.serverworld.worldUserProfile.bungeecord.uitls.UserAccountDataMySQL;
import com.serverworld.worldUserProfile.bungeecord.uitls.UserPhoenixPlayerDataMySQL;
import com.serverworld.worldUserProfile.jsondata.UserAccountData;
import com.serverworld.worldUserProfile.utils.IPAPI;
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
    private BungeeworldUserProfile worldUserProfile;
    public PlayerLogin(Plugin plugin,BungeeworldUserProfile bungeeworldUserProfile) {
        ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
        this.worldUserProfile = bungeeworldUserProfile;
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        if(BanQueryAPI.isBanned(event.getPlayer().getUniqueId().toString()))
            return;
        JSONObject jsonObject = IPAPI.getJSON(event.getPlayer().getAddress().getAddress().toString());
        if (jsonObject.getString("status").equals("fail")){
            DebugMessage.sendWarring(ChatColor.RED + "Fail to get country!");
            return;
        }
        DebugMessage.sendInfo("Player " + event.getPlayer().getName() + " from " + jsonObject.getString("country"));
        if(!UserAccountDataMySQL.Joinbefore(event.getPlayer().getUniqueId().toString()))
            UserAccountDataMySQL.SetUp(event.getPlayer().getUniqueId().toString());
        if(!UserPhoenixPlayerDataMySQL.Joinbefore(event.getPlayer().getUniqueId().toString()))
            UserPhoenixPlayerDataMySQL.SetUp(event.getPlayer().getUniqueId().toString());

        if(UserAccountDataMySQL.getSigned(event.getPlayer().getUniqueId().toString())){
            UserAccountData userAccountData = UserAccountDataMySQL.getDataClass(event.getPlayer().getUniqueId().toString());
            Date date = new Date();
            userAccountData.setCity(jsonObject.getString("city"));
            userAccountData.setContinent(jsonObject.getString("continent"));
            userAccountData.setCountry(jsonObject.getString("country"));
            userAccountData.setIP(event.getPlayer().getAddress().toString());
            userAccountData.setISP(jsonObject.getString("org"));
            userAccountData.setLastLogin(date.getTime());
            userAccountData.setPlayername(event.getPlayer().getName());
            UserAccountDataMySQL.setDataClass(event.getPlayer().getUniqueId().toString(), userAccountData);
        }else {
            List<String> support_country_list = new ArrayList();
            support_country_list.add("taiwan");
            support_country_list.add("china");
            support_country_list.add("hong kong");
            support_country_list.add("macao");
            support_country_list.add("singapore");
            ProxyServer.getInstance().createTitle()
                    .reset()
                    .send(event.getPlayer());

            if(support_country_list.contains(jsonObject.getString("country").toLowerCase())){
                //support
                worldUserProfile.getProxy().getScheduler().schedule(worldUserProfile, new Runnable() {
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
                worldUserProfile.getProxy().getScheduler().schedule(worldUserProfile, new Runnable() {
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