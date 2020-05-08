package com.serverworld.worldUserProfile.bungeecord.Listeners;

import com.serverworld.worldUserProfile.bungeecord.BungeeworldUserProfile;
import com.serverworld.worldUserProfile.bungeecord.BungeeSqlDatabase;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PlayerLogin implements Listener {

    public PlayerLogin(Plugin plugin) {
        ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        try {
            Statement statement = BungeeSqlDatabase.connection.createStatement();


            ResultSet rs = statement.executeQuery("SELECT PlayerUUID from worldprofile_userlang WHERE PlayerUUID='"+event.getPlayer().getUniqueId()+"';");
            Boolean playerexists =false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
