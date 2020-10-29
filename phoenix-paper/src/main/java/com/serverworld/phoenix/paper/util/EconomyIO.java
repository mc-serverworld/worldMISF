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

package com.serverworld.phoenix.paper.util;

import com.serverworld.phoenix.paper.PaperPhoenix;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyIO {

    private static Economy econ = null;

    public EconomyIO(){
        RegisteredServiceProvider<Economy> economyProvider = PaperPhoenix.getInstance().getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            econ = economyProvider.getProvider();
        }else DebugMessage.sendWarring(ChatColor.RED + "Missing EconmyAPI!");
    }

    public static Boolean addBalance(Player player,Double bal){
        EconomyResponse r = econ.depositPlayer(player, bal);
        if(r.transactionSuccess())return true;
        player.sendMessage(ChatColor.RED + "ErrorMessage: " + r.errorMessage);
        return false;
    }
    public static Boolean takeBalance(Player player,Double bal){
        EconomyResponse r = econ.withdrawPlayer(player, bal);
        if(r.transactionSuccess())return true;
        player.sendMessage(ChatColor.RED + "ErrorMessage: " + r.errorMessage);
        return false;
    }
    public static Boolean takeIfHasBalance(Player player,Double bal){
        if(hasBlance(player,bal)){
            if(takeBalance(player,bal))return true;
            else {
                return false;
            }
        }else return false;
    }
    public static Boolean setBalance(Player player,Double bal){
        if(takeBalance(player,getBalance(player))){
            if(addBalance(player,bal))return true;
        }
        return false;
    }
    public static Double getBalance(Player player){
        return econ.getBalance(player);
    }
    public static Boolean hasBlance(Player player,Double bal){
        return econ.has(player, bal);
    }
}
