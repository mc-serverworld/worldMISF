package com.serverworld.phoenix.paper;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.serverworld.phoenix.paper.Listeners.Messagecoming;
import com.serverworld.phoenix.paper.commands.worldMISFpaperspigotCommands;
import com.serverworld.phoenix.paper.util.worldsyncer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.*;

public class worldMISFpaperspigot extends JavaPlugin implements PluginMessageListener {

    public worldMISFpaperspigotconfig config;

    @Override
    public void onLoad() {
        config = new worldMISFpaperspigotconfig(this);
    }

    @Override
    public void onEnable() {
        config.loadDefConfig();
        getLogger().info( "Plugin enabled!" );
        getServer().getPluginManager().registerEvents(new Messagecoming(this), this);
        getServer().getMessenger().registerIncomingPluginChannel(this, "misf:testing", this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "misf:testing");
        if(config.chunk_position_x()==0&&config.chunk_position_y()==0&&config.worldtype().equals("overworld")){
            worldsyncer worldsyncer = new worldsyncer(this);
        }

        //commands
        worldMISFpaperspigotCommands worldMISFpaperspigotCommands = new worldMISFpaperspigotCommands(this);
        this.getCommand("misf").setExecutor(worldMISFpaperspigotCommands);
        this.getCommand("misf").setTabCompleter(worldMISFpaperspigotCommands);
    }
    public void onPluginMessageReceived(String channel, Player player, byte[] message){
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("misf:testing")) {
            short len = in.readShort();
            byte[] msgbytes = new byte[len];
            in.readFully(msgbytes);
            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
            String somedata = null; // Read the data in the same way you wrote it
            try {
                somedata = msgin.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            getLogger().info(somedata);
        }
    }
}
