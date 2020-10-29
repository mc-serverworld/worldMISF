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

package com.serverworld.worldUtils.paper.commands;

import com.serverworld.worldUtils.paper.PaperworldUtils;
import com.serverworld.worldUtils.paper.util.Network;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class RestartUpdate implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      sender.sendMessage("測試");
        String url = "https://api.github.com/repos/mc-serverworld/worldMISF/releases/latest";

        try {

            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            request.addHeader("Accept", "application/vnd.github+json");
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            JSONObject message = new JSONObject(json);
            JSONArray assetslist = new JSONArray(message.get("assets").toString());
            for (int i=0;i<assetslist.length();i++){
                JSONObject assets = (JSONObject) assetslist.get(i);
                assets = new JSONObject(assets.toString(i));
                if(!assets.getString("name").contains("bungee")){
                    if(!assets.getString("name").contains("worldUtil")) {
                        if(Bukkit.getServer().getPluginManager().getPlugin("worldMISF-phoenix-paper").isEnabled()){
                            File file = new File(Bukkit.getWorldContainer().getPath() + "/plugins/worldMISF-phoenix-paper-"+ Bukkit.getPluginManager().getPlugin("worldMISF-phoenix-paper").getDescription().getVersion() +".jar");
                            sender.sendMessage(file.getPath());
                            PaperworldUtils.getInstance().getServer().getPluginManager().disablePlugin(PaperworldUtils.getInstance().getServer().getPluginManager().getPlugin("worldMISF-phoenix-paper"));
                            //System.out.println(file.delete());
                        }
                        Network.downloadNet(assets.getString("browser_download_url"), assets.getString("name"));
                        System.out.println(assets.getString("browser_download_url"));
                    }
                }
            }
            return true;
        } catch (IOException ex) {

        }
        return false;
    }
}