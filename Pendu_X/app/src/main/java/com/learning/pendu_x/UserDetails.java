package com.learning.pendu_x;

public class UserDetails {
    private String username,email,ID;
    private String password;
    private int XCoins;
    private boolean on_off=false;
    private int WIN,LOST;


    public UserDetails(String username, String password, String email, int XCoins,int WIN, int LOST,boolean on_off) {
        this.username = username;
        this.password=password;
        this.email=email;
        this.XCoins=XCoins;
        this.WIN=WIN;
        this.LOST=LOST;
        this.on_off=false;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public boolean getOn_off() {
        return on_off;
    }

    public void setOn_off(boolean on_off) {
        this.on_off = on_off;
    }

    public boolean isOn_off() {
        return on_off;
    }

    public int getWIN() {
        return WIN;
    }

    public void setWIN(int WIN) {
        this.WIN = WIN;
    }

    public int getLOST() {
        return LOST;
    }

    public void setLOST(int LOST) {
        this.LOST = LOST;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getXCoins() {
        return XCoins;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setXCoins(int XCoins) {
        this.XCoins = XCoins;
    }
}
