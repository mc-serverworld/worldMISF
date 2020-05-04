package com.serverworld.phoenix.bungee.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.HashSet;
import java.util.Set;

public class banplayer extends Command implements TabExecutor{
    public banplayer(Plugin plugin){
        super("misfbungeecord","misfbungeecord");
    }

    //dan playername endtime reason
    public void execute(CommandSender commandSender, String[] strings) {


    }

    @Override
    public Iterable<String> onTabComplete(CommandSender commandSender, String[] strings) {
        String[] commandlist;

    }
}
