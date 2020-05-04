package com.serverworld.phoenix.bungee.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public class unbanplayer extends Command{
    public unbanplayer(Plugin plugin){
        super("undan","undan");
    }
    //undan banid
    public void execute(CommandSender commandSender, String[] strings){
        if(strings.length>=2&&strings[1].equals("confirm")) {
            mysql.unban(strings[0]);
            commandSender.sendMessage(new TextComponent(ChatColor.GREEN + "unban banid: "+strings[0]));
        }else {
            commandSender.sendMessage(new TextComponent(ChatColor.RED + "wtf this not right input"));
            commandSender.sendMessage(new TextComponent(ChatColor.YELLOW + "/undan banid confirm"));
        }
    }

}
