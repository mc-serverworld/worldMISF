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

package com.serverworld.phoenix.paper.Listeners;
import com.google.gson.JsonObject;
import com.serverworld.phoenix.paper.PaperPhoenix;
import com.serverworld.phoenix.paper.util.Formats;
import com.serverworld.phoenix.paper.util.Player.PlayerData;
import com.serverworld.worldSocket.paperspigot.util.messagecoder;
import com.serverworld.worldSocket.paperspigot.util.messager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

    private PaperPhoenix paperPhoenix;

    public PlayerDeath(PaperPhoenix paperPhoenix){
        this.paperPhoenix = paperPhoenix;
    }

    @EventHandler
    public void onPlayerDearh(PlayerDeathEvent event){
        if(event.getEntity() instanceof Player) {
            PlayerData.SaveCurrentLocationAsLast(event.getEntity().getPlayer(),event.getEntity().getLocation());
            event.getEntity().sendMessage(Formats.perfix() + ChatColor.GOLD + "輸入/back 可傳送回上一個位置");//TODO: Langauge seleter
            Bukkit.getScheduler().scheduleSyncDelayedTask(PaperPhoenix.getInstance(), () -> event.getEntity().getPlayer().spigot().respawn(), 5L);
            if(PaperPhoenix.config.servername().equals(PaperPhoenix.config.serversprefix() + "OVERWORLD_0_0"))
                return;
            Bukkit.getScheduler().scheduleSyncDelayedTask(PaperPhoenix.getInstance(), () -> {
                messagecoder messagecoder = new messagecoder();
                messagecoder.setSender(PaperPhoenix.config.servername());
                messagecoder.setChannel("MISF_PHOENIX");
                messagecoder.setReceiver("PROXY");
                messagecoder.setType("ACTION");
                JsonObject json = new JsonObject();
                json.addProperty("TYPE","SEND_PLAYER_TO_SERVER");
                json.addProperty("PLAYER",event.getEntity().getUniqueId().toString());
                json.addProperty("SERVER",PaperPhoenix.config.serversprefix() + "OVERWORLD_0_0");
                messagecoder.setMessage(json.toString());
                messager.sendmessage(messagecoder.createmessage());
                }, 5L);//send player to spawn

            Bukkit.getScheduler().scheduleSyncDelayedTask(PaperPhoenix.getInstance(), () -> {
                messagecoder messagecoder = new messagecoder();
                messagecoder.setSender(PaperPhoenix.config.servername());
                messagecoder.setChannel("MISF_PHOENIX");
                messagecoder.setReceiver(PaperPhoenix.config.serversprefix() + "OVERWORLD_0_0");
                messagecoder.setType("ACTION");
                JsonObject json = new JsonObject();
                json.addProperty("TYPE","RESPAWN_PLAYER");
                json.addProperty("PLAYER",event.getEntity().getPlayer().getName());
                messagecoder.setMessage(json.toString());
                messager.sendmessage(messagecoder.createmessage());
            }, 20L);//tell spawn server to respawn player
        }
    }
}
