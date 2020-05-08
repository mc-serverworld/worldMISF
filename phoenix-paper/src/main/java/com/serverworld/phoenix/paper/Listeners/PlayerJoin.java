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

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlayerJoin implements Listener {
/*
    private final worldProfile plugin;
    public Playerjoin(worldProfile plg) {
        plugin=plg;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onPLayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        try {
            Statement statement = worldProfile.connection.createStatement();
            String checker = "INSERT INTO worldprofile_userlang (PlayerUUID, PlayerID,Lang) VALUES ("+"'" + player.getUniqueId() + "','" + player.getName() + "','"+      lang.Language.getLanguage(player)+"')" +" ON DUPLICATE KEY UPDATE"+" PlayerID='"+player.getName()+"';";
            ResultSet rs = statement.executeQuery("SELECT PlayerUUID from worldprofile_userlang WHERE PlayerUUID='"+player.getUniqueId()+"';");
            Boolean playerexists =false;
            playerexists = rs.next();
            if (playerexists){
                Bukkit.getServer().getLogger().info(String.format(plugin.getName()+ ChatColor.GREEN+ " Player "+player.getName()+" Found!"));
                statement.executeUpdate(checker);
            }else {
                Bukkit.getServer().getLogger().info(String.format(plugin.getName()+ ChatColor.YELLOW+  " Player "+player.getName()+" Not Found! Creading new one."));
                statement.executeUpdate(checker);
            }
        } catch (SQLException ee) {
            ee.printStackTrace();
        }
    }*/
}