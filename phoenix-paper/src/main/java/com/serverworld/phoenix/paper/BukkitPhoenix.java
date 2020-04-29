/*
 * WorldMISF - cms of mc-serverworld
 * Copyright (C) 2019-2020 mc-serverworld
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.serverworld.phoenix.paper;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.serverworld.phoenix.paper.Listeners.Messagecoming;
import com.serverworld.phoenix.paper.commands.BukkitPhoenixCommands;
import com.serverworld.phoenix.paper.util.worldsyncer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class BukkitPhoenix extends JavaPlugin{

    public BukkitPhoenixConfig config;

    @Override
    public void onLoad() {
        config = new BukkitPhoenixConfig(this);
    }

    @Override
    public void onEnable() {
        config.loadDefConfig();
        getLogger().info( "Plugin enabled!" );
        getServer().getPluginManager().registerEvents(new Messagecoming(this), this);
        if(config.chunk_position_x()==0&&config.chunk_position_y()==0&&config.worldtype().equals("overworld")){
            worldsyncer worldsyncer = new worldsyncer(this);
        }

        //commands
        BukkitPhoenixCommands BukkitPhoenixCommands = new BukkitPhoenixCommands(this);
        this.getCommand("misf").setExecutor(BukkitPhoenixCommands);
        this.getCommand("misf").setTabCompleter(BukkitPhoenixCommands);
    }
}
