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

package com.serverworld.worlduserdata.jsondata.history.v2;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class UserAccountData_v2 {
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private String PlayerName;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private long SignedData;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private long LastLogin;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private long PlayedTime;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private int WorldCoin;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private String ip;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private String continent;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private String country;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private String city;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private String isp;
}
