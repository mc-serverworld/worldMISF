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

package com.serverworld.phoenix.paper.Listeners.Residence;

import com.bekvon.bukkit.residence.event.ResidenceCreationEvent;
import com.serverworld.phoenix.paper.PaperPhoenix;
import com.serverworld.worlduserdata.jsondata.UserPhoenixPlayerData;
import com.serverworld.worlduserdata.paper.utils.UserPhoenixPlayerDataMySQL;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ResidenceCreation implements Listener {

    public ResidenceCreation(PaperPhoenix paperPhoenix){ }

    @EventHandler
    public void onResidenceCreationEvent(ResidenceCreationEvent event){
        if(event.isCancelled())
            return;

        UserPhoenixPlayerData playerData = UserPhoenixPlayerDataMySQL.getDataClass(event.getPlayer().getUniqueId().toString());
        //UserPhoenixPlayerDataMySQL.setDataClass(eventplayer.getUniqueId().toString() , playerData);
        if(playerData.getResidence_total_amount() >= playerData.getResidence_max_amount()){

        }else if(playerData.getResidence_total_size() >= playerData.getResidence_max_size()){

        }

    }

}
