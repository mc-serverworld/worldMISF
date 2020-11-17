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

package com.serverworld.phoenix.paper.Listeners.Residence;

import com.bekvon.bukkit.residence.event.ResidenceCreationEvent;
import com.serverworld.phoenix.paper.PaperPhoenix;
import com.serverworld.phoenix.paper.util.EconomyIO;
import com.serverworld.worlduserdata.jsondata.ServerResidenceData;
import com.serverworld.worlduserdata.jsondata.UserPhoenixPlayerData;
import com.serverworld.worlduserdata.query.ServerResidenceInquirer;
import com.serverworld.worlduserdata.query.UserPhoenixPlayerDataInquirer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ResidenceCreation implements Listener {

    public ResidenceCreation(PaperPhoenix paperPhoenix) {
    }

    @EventHandler
    public void onResidenceCreationEvent(ResidenceCreationEvent event) {
        event.getPlayer().sendMessage("Event triggered");
        if (event.isCancelled())
            return;
        UserPhoenixPlayerData playerData = UserPhoenixPlayerDataInquirer.getDataClass(event.getPlayer().getUniqueId());
        //UserPhoenixPlayerDataMySQL.setDataClass(eventplayer.getUniqueId().toString() , playerData);
        if (playerData.getResidence_total_amount() >= playerData.getResidence_max_amount()) {
            event.getPlayer().sendMessage(ChatColor.RED + "超過您可持有的保護區上限");//TODO: Langauge seleter
            event.setCancelled(true);
            return;
        } else if (playerData.getResidence_total_size() + event.getResidence().getXZSize() >= playerData.getResidence_max_size()) {
            event.getPlayer().sendMessage(ChatColor.RED + "超過您的保護區格數上限");//TODO: Langauge seleter
            event.setCancelled(true);
            return;
        }else if(ServerResidenceInquirer.isExist(event.getResidenceName())){
            event.getPlayer().sendMessage(ChatColor.RED + "保護區名稱已被使用");//TODO: Langauge seleter
            event.setCancelled(true);
            return;
        }

        if (playerData.getResidence_total_size() + event.getResidence().getXZSize() <= 10000) {
            playerData.setResidence_total_size(playerData.getResidence_total_size() + event.getResidence().getXZSize());
            double letfFreeSize = (10000 - playerData.getResidence_total_size());
            event.getPlayer().sendMessage(ChatColor.GREEN + "保護區創建成功");//TODO: Langauge seleter
            event.getPlayer().sendMessage(ChatColor.YELLOW + "您還有 " + letfFreeSize + "格免費領地");
            playerData.setResidence_total_amount(playerData.getResidence_total_amount()+1);
            addResidence(event);
            UserPhoenixPlayerDataInquirer.setDataClass(event.getPlayer().getUniqueId(), playerData);
            return;
        }

        if (10000 - playerData.getResidence_total_size() > 0) {
            Double overSize = (playerData.getResidence_total_size() + event.getResidence().getXZSize() - 10000);
            if (EconomyIO.takeIfHasBalance(event.getPlayer(), overSize)) {
                playerData.setResidence_total_size(playerData.getResidence_total_size() + overSize);
                double leftSize = playerData.getResidence_max_size() - playerData.getResidence_total_size();
                event.getPlayer().sendMessage(ChatColor.GREEN + "保護區創建成功");//TODO: Langauge seleter
                event.getPlayer().sendMessage(ChatColor.YELLOW + "您還有 " + EconomyIO.getBalance(event.getPlayer()) + "$");
                event.getPlayer().sendMessage(ChatColor.YELLOW + "與 " + leftSize + "格領地額度");
                playerData.setResidence_total_amount(playerData.getResidence_total_amount()+1);
                addResidence(event);
                UserPhoenixPlayerDataInquirer.setDataClass(event.getPlayer().getUniqueId(), playerData);
                return;
            }
        }

        if (EconomyIO.takeIfHasBalance(event.getPlayer(), (double) event.getResidence().getXZSize())) {
            playerData.setResidence_total_size(playerData.getResidence_total_size() + event.getResidence().getXZSize());
            double leftSize = playerData.getResidence_max_size() - playerData.getResidence_total_size();
            event.getPlayer().sendMessage(ChatColor.GREEN + "保護區創建成功");//TODO: Langauge seleter
            event.getPlayer().sendMessage(ChatColor.YELLOW + "您還有 " + EconomyIO.getBalance(event.getPlayer()) + "$");
            event.getPlayer().sendMessage(ChatColor.YELLOW + "與 " + leftSize + "格領地額度");
            playerData.setResidence_total_amount(playerData.getResidence_total_amount()+1);
            addResidence(event);
            UserPhoenixPlayerDataInquirer.setDataClass(event.getPlayer().getUniqueId(), playerData);
            return;
        }

        event.getPlayer().sendMessage(ChatColor.RED + "餘額不足");//TODO: Langauge seleter
        event.setCancelled(true);
    }

    public void addResidence(ResidenceCreationEvent event){
        ServerResidenceData residenceData = new ServerResidenceData();
        residenceData.setServer(PaperPhoenix.config.servername());
        residenceData.setWorld(event.getResidence().getWorld());
        residenceData.setResidenceName(event.getResidenceName());
        residenceData.setCreateTime(event.getResidence().getCreateTime());
        residenceData.setXYSize(event.getResidence().getXZSize());
        residenceData.setAllowGlobalTeleport(false);

        residenceData.setOwnerName(event.getPlayer().getName());
        residenceData.setOwnerUUID(event.getPlayer().getUniqueId());
        residenceData.setTeleportLocation_x(event.getResidence().getTeleportLocation(event.getPlayer()).getX());
        residenceData.setTeleportLocation_y(event.getResidence().getTeleportLocation(event.getPlayer()).getY());
        residenceData.setTeleportLocation_z(event.getResidence().getTeleportLocation(event.getPlayer()).getZ());
        ServerResidenceInquirer.addDataClass(residenceData,1);
    }

}
