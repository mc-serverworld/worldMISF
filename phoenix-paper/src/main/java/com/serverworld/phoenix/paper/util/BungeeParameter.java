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
import com.serverworld.worldSocket.paperspigot.util.messagecoder;
import com.serverworld.worldSocket.paperspigot.util.messager;

public class BungeeParameter {

    static int totalservers ;
    static String[] serverlist;
    static int totalplayers;
    static String[] playerlist;

    public static int getTotalServer(){ return totalservers;}

    public static String[] getServerList(){ return serverlist;}

    public static int getTotalPlayer(){ return totalplayers;}

    public static String[] getPlayerList(){ return playerlist;}

    public void SyncBungeePlayerList(){
        PaperPhoenix.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(PaperPhoenix.getInstance(), new Runnable() {
            @Override
            public void run() {
                messagecoder messagecode = new messagecoder();
                messagecode.setSender(PaperPhoenix.config.servername());
                messagecode.setReceiver("PROXY");
                messagecode.setChannel("MISF_PHOENIX");
                messagecode.setType("SYNC_V2");
                messagecode.setMessage("REQUEST_INFO");
                messager.sendmessage(messagecode.createmessage());
            }
        },0L,200L);//Sync Bungee player list from proxy
    }
}
