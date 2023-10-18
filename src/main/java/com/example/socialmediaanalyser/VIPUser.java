package com.example.socialmediaanalyser;

public class VIPUser extends User {
    private boolean isVIP;

    public VIPUser(String UserName, String Password, boolean isVIP) {
        super(UserName, Password);
        this.isVIP = isVIP;
    }

    public boolean isVIP() {
        return isVIP;
    }

    public void setVIP(boolean isVIP) {
        this.isVIP = isVIP;
    }
}

