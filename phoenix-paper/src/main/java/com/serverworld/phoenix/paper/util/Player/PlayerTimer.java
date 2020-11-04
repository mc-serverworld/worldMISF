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

package com.serverworld.phoenix.paper.util.Player;

import com.serverworld.phoenix.paper.PaperPhoenix;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerTimer {
    public PlayerTimer(){
        PlayTime();
    }
    public void PlayTime(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(PaperPhoenix.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(PaperPhoenix.getInstance().getServer().getOnlinePlayers().isEmpty())
                    return;
                for (Player player:PaperPhoenix.getInstance().getServer().getOnlinePlayers()) {
                    //if(!PaperPhoenix.getEssentialsPlugin().getUser(player).isAfk())
                        //PlayerData.addPlayTime(player,60L);
                }
            }
        },0L,1200L);

    }


}
