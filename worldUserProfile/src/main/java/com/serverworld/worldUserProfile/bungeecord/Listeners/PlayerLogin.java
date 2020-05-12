package com.serverworld.worldUserProfile.bungeecord.Listeners;

import com.serverworld.worldIdiot.api.BanQuery;
import com.serverworld.worldUserProfile.bungeecord.BungeeworldUserProfile;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;


public class PlayerLogin implements Listener {
    BungeeworldUserProfile worldUserProfile;
    public PlayerLogin(Plugin plugin,BungeeworldUserProfile bungeeworldUserProfile) {
        ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
        this.worldUserProfile = bungeeworldUserProfile;
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        if (BanQuery.isBanned("awdda")){
            worldUserProfile.getLogger().info("Hello world!!!!!!!");
        }

    }

}
