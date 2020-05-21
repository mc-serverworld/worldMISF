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

package com.serverworld.phoenix.paper;

import com.serverworld.phoenix.paper.Listeners.Messagecoming;
import com.serverworld.phoenix.paper.Listeners.PlayerDeath;
import com.serverworld.phoenix.paper.Listeners.PlayerRespawn;
import com.serverworld.phoenix.paper.commands.PaperPhoenixCommands;
import com.serverworld.phoenix.paper.util.worldSync;
import org.bukkit.plugin.java.JavaPlugin;

public class PaperPhoenix extends JavaPlugin{

    private static PaperPhoenix paperPhoenix;
    public static PaperPhoenixConfig config;

    @Override
    public void onLoad() {
        config = new PaperPhoenixConfig(this);
        config.loadDefConfig();
        paperPhoenix = this;
    }

    @Override
    public void onEnable() {

        //setup
        setupevent();
        setuputil();
        //commands

        PaperPhoenixCommands PaperPhoenixCommands = new PaperPhoenixCommands(this);
        this.getCommand("misf").setExecutor(PaperPhoenixCommands);
        this.getCommand("misf").setTabCompleter(PaperPhoenixCommands);
    }

    public void setupevent(){
        getServer().getPluginManager().registerEvents(new Messagecoming(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(this), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawn(this), this);
    }

    public void setuputil(){
        worldSync worldsyncer = new worldSync(this);
    }

    public static PaperPhoenix getInstance(){
        return paperPhoenix;
    }
}
