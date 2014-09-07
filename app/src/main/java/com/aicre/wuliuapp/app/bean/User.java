package com.aicre.wuliuapp.app.bean;

/**
 * Created by wei on 14-7-5.
 */
public class User {
    private String account;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean equals(User u) {
        if((this.account == u.account)&&(this.password == u.password)){
            return true;
        }
        return false;
    }
}
