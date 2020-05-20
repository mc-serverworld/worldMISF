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

package com.serverworld.worldUserProfile.jsondata;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class UserPhoenixPlayerData {

    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private Long playtinme;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private Long residence_total_size;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private Long residence_total_amount;

    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private String home_server;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private String home_world;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private Double home_x;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private Double home_y;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private Double home_z;

    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private String lastlocation_server;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private String lastlocation_world;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private Double lastlocation_x;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private Double lastlocation_y;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private Double lastlocation_z;

    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private String logoutlocation_server;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private String logoutlocation_world;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private Double logoutlocation_x;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private Double logoutlocation_y;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private Double logoutlocation_z;

}