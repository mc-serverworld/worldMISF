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
import com.serverworld.phoenix.paper.PaperPhoenixConfig;
import com.serverworld.worldSocket.paperspigot.util.messagecoder;
import com.serverworld.worldSocket.paperspigot.util.messager;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.World;

public class worldsyncer {
    private PaperPhoenix paperPhoenix;
    private PaperPhoenixConfig config;

    public worldsyncer(PaperPhoenix PaperPhoenix){
        this.paperPhoenix = PaperPhoenix;
        config = PaperPhoenix.config;
        if(config.chunk_position_x()==0&&config.chunk_position_z()==0&&config.worldtype().equals("overworld")){ }else return;
        weathersyncer();
        timesyncer();
    }

    public void timesyncer() {
        paperPhoenix.getServer().getScheduler().scheduleSyncRepeatingTask(paperPhoenix, new Runnable() {
            @Override
            public void run() {
                World world = paperPhoenix.getServer().getWorld("world");
                messagecoder messagecode = new messagecoder();
                messagecode.setSender(paperPhoenix.config.servername());
                messagecode.setReceiver("ALL");
                messagecode.setChannel("MISF");
                messagecode.setType("SYNC");
                if(!NumberUtils.isNumber(String.valueOf(world.getTime()))){
                    paperPhoenix.getLogger().warning(ChatColor.RED + "Cant get world time!");
                    return;
                }
                messagecode.setMessage("TIME," + world.getTime());
                messager.sendmessage(messagecode.createmessage());
            }
        }, 0L, config.sync_time()*20);
    }

    public void weathersyncer() {
        paperPhoenix.getServer().getScheduler().scheduleSyncRepeatingTask(paperPhoenix, new Runnable() {
            @Override
            public void run() {
                World world = paperPhoenix.getServer().getWorld("world");
                messagecoder messagecode = new messagecoder();
                messagecode.setSender(paperPhoenix.config.servername());
                messagecode.setReceiver("ALL");
                messagecode.setChannel("MISF");
                messagecode.setType("SYNC");
                if(world.hasStorm()&&world.isThundering()){
                    messagecode.setMessage("WEATHER,THUNDER," + world.getWeatherDuration());
                }else if(world.hasStorm()&&!world.isThundering()) {
                    messagecode.setMessage("WEATHER,RAIN," + world.getWeatherDuration());
                }else if(!world.hasStorm()&&!world.isThundering()){
                    messagecode.setMessage("WEATHER,CLEAR," + world.getWeatherDuration());
                }else {
                    messagecode.setMessage("WEATHER,CLEAR," + world.getWeatherDuration());
                }
                messager.sendmessage(messagecode.createmessage());
            }
        }, 0L, config.sync_weather_time()*20);
    }

}
