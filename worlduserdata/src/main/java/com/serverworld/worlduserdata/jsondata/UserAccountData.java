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

package com.serverworld.worlduserdata.jsondata;

public class UserAccountData {
    private String playername;
    private long signeddata;
    private long lastlogin;
    private long playedtime;
    private int worldcoin;
    private String ip;
    private String continent;
    private String country;
    private String city;
    private String isp;

    public void setPlayername(String playerName){
        this.playername = playerName;
    }

    public String getPlayername(){
        return playername;
    }

    public void setSignData(long signData){
        this.signeddata =  signData;
    }

    public long getSignData(){
        return signeddata;
    }

    public void setLastLogin(long lastLogin){
        this.lastlogin = lastLogin;
    }

    public long getLastLogin(){
        return lastlogin;
    }

    public void setPlayedTime(long playedtime){
        this.playedtime = playedtime;
    }

    public long getPlayedTime(){
        return playedtime;
    }

    public void setworldCoin(int worldcoin){
        this.worldcoin = worldcoin;
    }

    public int getworldCoin(){
        return worldcoin;
    }

    public void setIP(String IP){
        this.ip = IP;
    }

    public String getIP(){
        return ip;
    }

    public void setContinent(String continent){
        this.continent = continent;
    }

    public String getContinent(){
        return continent;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public String getCountry(){
        return country;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getCity(){
        return city;
    }

    public void setISP(String isp){
        this.isp = isp;
    }

    public String getISP(){
        return isp;
    }
}
