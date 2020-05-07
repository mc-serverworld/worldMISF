package com.serverworld.SpigotworldUserProfile.gsons;

public class useraccountdata {
    private String playername;
    private double signeddata;
    private double lastlogin;
    private double playedtime;
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

    public void setSignData(double signData){
        this.signeddata =  signData;
    }

    public double getSignData(){
        return signeddata;
    }

    public void setLastLogin(double lastLogin){
        this.lastlogin = lastLogin;
    }

    public double getLastLogin(){
        return lastlogin;
    }

    public void setPlayedTime(double playedtime){
        this.playedtime = playedtime;
    }

    public double getPlayedTime(){
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
