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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class BungeeParameter {

    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) static Integer totalservers ;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) static List<String> serverlist;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) static Integer totalplayers;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) static List<String> playerlist;

    public void SyncBungeePlayerList(){
        PaperPhoenix.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(PaperPhoenix.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(PaperPhoenix.getInstance().getServer().getOnlinePlayers().isEmpty())
                    return;
                messagecoder messagecode = new messagecoder();
                messagecode.setSender(PaperPhoenix.config.servername());
                messagecode.setReceiver("PROXY");
                messagecode.setChannel("MISF_PHOENIX");
                messagecode.setType("SYNC_V2");
                messagecode.setMessage("REQUEST_BUNGEE_INFO_V1");
                messager.sendmessage(messagecode.createmessage());
            }
        },0L,200L);//Sync Bungee player list from proxy
    }
}
