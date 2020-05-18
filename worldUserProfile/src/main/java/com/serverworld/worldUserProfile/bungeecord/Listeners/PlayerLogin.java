package com.serverworld.worldUserProfile.bungeecord.Listeners;

import com.serverworld.worldIdiot.api.BanQueryAPI;
import com.serverworld.worldUserProfile.bungeecord.BungeeworldUserProfile;
import com.serverworld.worldUserProfile.bungeecord.uitls.DebugMessage;
import com.serverworld.worldUserProfile.bungeecord.uitls.IPAPI;
import com.serverworld.worldUserProfile.bungeecord.uitls.mysql;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import org.json.JSONObject;

import java.util.ArrayList;


public class PlayerLogin implements Listener {
    BungeeworldUserProfile worldUserProfile;
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

        if(!mysql.Joinbefore(event.getPlayer().getUniqueId().toString())){
            Title reseter = null;
            reseter.clear();

            ArrayList support_country_list = new ArrayList();
            support_country_list.add("Tawian");
            support_country_list.add("China");
            support_country_list.add("Hong Kong");
            support_country_list.add("Macao");
            support_country_list.add("Singapore");

            if(support_country_list.contains(jsonObject.getString("country").toLowerCase())){
                //support
                ProxyServer.getInstance().createTitle()
                        .title(new ComponentBuilder("歡迎來到mc-serverworld")
                                .color(ChatColor.BLUE).create())
                        .subTitle(new ComponentBuilder("請輸入/sign來簽署協議")
                                .color(ChatColor.GREEN).create())
                        .fadeIn(40)
                        .stay(18000000)
                        .fadeOut(40)
                        .send(event.getPlayer());
            }else {
                //unsupport
                ProxyServer.getInstance().createTitle()
                        .title(new ComponentBuilder("Wellcome to mc-serverworld")
                                .color(ChatColor.BLUE).create())
                        .subTitle(new ComponentBuilder("Please enter /sign to sign the agreement")
                                .color(ChatColor.GREEN).create())
                        .fadeIn(40)
                        .stay(18000000)
                        .fadeOut(40)
                        .send(event.getPlayer());
            }
        }

    }

}
