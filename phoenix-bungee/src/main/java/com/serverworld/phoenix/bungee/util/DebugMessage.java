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

package com.serverworld.phoenix.bungee.util;

import com.serverworld.phoenix.bungee.BungeePhoenix;

public class DebugMessage {

    public static void sendInfo(String msg){
        BungeePhoenix.getInstance().getLogger().info(msg);
    }
    public static void sendWarring(String msg){
        BungeePhoenix.getInstance().getLogger().warning(msg);
    }
    public static void sendInfoIfDebug(String msg){
        if(BungeePhoenix.config.debug())
            BungeePhoenix.getInstance().getLogger().info(msg);

    }
    public static void sendWarringIfDebug(String msg){
        if(BungeePhoenix.config.debug())
            BungeePhoenix.getInstance().getLogger().warning(msg);

    }
}
