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

import com.earth2me.essentials.Essentials;
import com.serverworld.phoenix.paper.Listeners.Messagecoming;
import com.serverworld.phoenix.paper.Listeners.PlayerDeath;
import com.serverworld.phoenix.paper.Listeners.PlayerRespawn;
import com.serverworld.phoenix.paper.Listeners.Residence.*;
import com.serverworld.phoenix.paper.commands.LobbyCommand;
import com.serverworld.phoenix.paper.commands.PaperPhoenixCommands;
import com.serverworld.phoenix.paper.commands.PlayerCommands.*;
import com.serverworld.phoenix.paper.util.*;
import com.serverworld.phoenix.paper.util.Player.PlayerTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;


public class PaperPhoenix extends JavaPlugin {
    private static PaperPhoenix paperPhoenix;
    private static Essentials essentialsPlugin;
    public static PaperPhoenixConfig config;
    int taskid=0;

    @Override
    public void onLoad() {
        config = new PaperPhoenixConfig(this);
        config.loadDefConfig();
        paperPhoenix = this;
    }

    @Override
    public void onEnable() {

        if (config.servetype().toLowerCase().equals("lobby")) {
            LobbyCommand lobbyCommand = new LobbyCommand(this);
            this.getCommand("misf").setExecutor(lobbyCommand);
            this.getCommand("misf").setTabCompleter(lobbyCommand);
        } else {
            boolean bEssentials = getServer().getPluginManager().isPluginEnabled("Essentials");
            boolean bLuckPerms = getServer().getPluginManager().isPluginEnabled("LuckPerms");
            boolean bPlaceholderAPI = getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");
            //boolean bResidence = Bukkit.getServer().getPluginManager().isPluginEnabled("Residence");
            boolean bVault = getServer().getPluginManager().isPluginEnabled("Vault");
            boolean bWorldBorder = getServer().getPluginManager().isPluginEnabled("WorldBorder");
            boolean bworldMISF_worlduserdata = getServer().getPluginManager().isPluginEnabled("worldMISF-worlduserdata");
            boolean bworldSocket = getServer().getPluginManager().isPluginEnabled("worldSocket");

            //setup
            taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(PaperPhoenix.getInstance(), () -> {
                if(bEssentials){DebugMessage.sendInfo(ChatColor.GREEN + "Essentials Enable");
                    if(bLuckPerms){DebugMessage.sendInfo(ChatColor.GREEN + "LuckPerms Enable");
                        if(bPlaceholderAPI){DebugMessage.sendInfo(ChatColor.GREEN + "PlaceholderAPI Enable");
                            if(bVault){DebugMessage.sendInfo(ChatColor.GREEN + "Vault Enable");
                                if(bWorldBorder){DebugMessage.sendInfo(ChatColor.GREEN + "WorldBorder Enable");
                                    if(bworldMISF_worlduserdata){DebugMessage.sendInfo(ChatColor.GREEN + "MISF_worlduserdata Enable");
                                        if(bworldSocket){ DebugMessage.sendInfo(ChatColor.GREEN + "worldSocket Enable");
                                            DebugMessage.sendInfo(ChatColor.GREEN + "All Plugin Enabled");
                                            DebugMessage.sendInfo(ChatColor.GREEN + "Startup PaperPhoenix...");

                                            setupevent();
                                            setuputil();
                                            new worldSetup();
                                            new BungeeParameter().SyncBungeePlayerList();
                                            //commands

                                            PaperPhoenixCommands PaperPhoenixCommands = new PaperPhoenixCommands(this);
                                            this.getCommand("misf").setExecutor(PaperPhoenixCommands);
                                            this.getCommand("misf").setTabCompleter(PaperPhoenixCommands);

                                            setupPlayerCommands();
                                            setGetPlugin();
                                            DebugMessage.sendInfo(ChatColor.GREEN + "Startup Complete!");
                                            Bukkit.getScheduler().cancelTask(taskid);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }, 100L,40L);
        }

    }

    public void setGetPlugin(){
        essentialsPlugin = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");

    }
    public void setupevent(){
        getServer().getPluginManager().registerEvents(new Messagecoming(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(this), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawn(this), this);

        //residence plugins
        getServer().getPluginManager().registerEvents(new ResidenceCreation(this),this);
        getServer().getPluginManager().registerEvents(new ResidenceDelete(this),this);
        getServer().getPluginManager().registerEvents(new ResidenceFlagChange(this),this);
        getServer().getPluginManager().registerEvents(new ResidenceOwnerChange(this),this);
        getServer().getPluginManager().registerEvents(new ResidenceRename(this),this);
        getServer().getPluginManager().registerEvents(new ResidenceSizeChange(this),this);
    }

    public void setuputil(){
        worldSync worldsyncer = new worldSync(this);
        PlayerTimer playerTimer = new PlayerTimer();
        EconomyIO economyIO = new EconomyIO();
    }


    public void setupPlayerCommands(){
        //player commands
        PlayerCommand_Back PlayerCommand_Back = new PlayerCommand_Back();
        this.getCommand("back").setExecutor(PlayerCommand_Back);

        PlayerCommand_Home PlayerCommand_Home = new PlayerCommand_Home();
        this.getCommand("home").setExecutor(PlayerCommand_Home);

        PlayerCommand_Sethome PlayerCommand_Sethome = new PlayerCommand_Sethome();
        this.getCommand("sethome").setExecutor(PlayerCommand_Sethome);

        PlayerCommand_Spawn PlayerCommand_Spawn = new PlayerCommand_Spawn();
        this.getCommand("spawn").setExecutor(PlayerCommand_Spawn);

        PlayerCommand_Tpa PlayerCommand_Tpa = new PlayerCommand_Tpa();
        this.getCommand("tpa").setExecutor(PlayerCommand_Tpa);
        this.getCommand("tpa").setTabCompleter(PlayerCommand_Tpa);

        PlayerCommand_Tpaccept PlayerCommand_Tpaccept = new PlayerCommand_Tpaccept();
        this.getCommand("tpaccept").setExecutor(PlayerCommand_Tpaccept);

        PlayerCommand_Tpahere PlayerCommand_Tpahere = new PlayerCommand_Tpahere();
        this.getCommand("tpahere").setExecutor(PlayerCommand_Tpahere);

        PlayerCommand_Tpdeny PlayerCommand_Tpdeny = new PlayerCommand_Tpdeny();
        this.getCommand("tpdeny").setExecutor(PlayerCommand_Tpdeny);

    }

    public static PaperPhoenix getInstance(){
        return paperPhoenix;
    }

    public static Essentials getEssentialsPlugin(){
        return essentialsPlugin;
    }

}
