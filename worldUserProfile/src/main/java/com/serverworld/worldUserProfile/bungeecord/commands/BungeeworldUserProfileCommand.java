package com.serverworld.worldUserProfile.bungeecord.commands;

import com.serverworld.worldUserProfile.bungeecord.uitls.mysql;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.HashSet;
import java.util.Set;

public class BungeeworldUserProfileCommand extends Command implements TabExecutor {
    public BungeeworldUserProfileCommand(Plugin plugin){
        super("sign");
    }


    public void execute(CommandSender commandSender, String[] strings) {
        try {
            if(commandSender.getName().toLowerCase().equals("console"))
                return;
            ProxiedPlayer p = (ProxiedPlayer)commandSender;
            if(mysql.getSigned(p.getUniqueId().toString())){
                commandSender.sendMessage(ChatColor.YELLOW + "You already signed agreement");
            }else {

            }
        }catch (Exception e){
            commandSender.sendMessage(ChatColor.RED + "Invalid input");
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender commandSender, String[] strings) {

        Set<String> match = new HashSet();
        return match;
    }
}
