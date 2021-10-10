package com.example.planner;

public class User {
    public String name, email , snumber;
    private String smoney;
    private String password;


    public User(String name, String email, String snumber , String smoney , String password) {
        this.name = name;
        this.email = email;
        this.snumber = snumber;
        this.smoney = smoney;
        this.password = password;
    }
    public String getName(){
        return name;
    }

    public String getMoney(){
        return smoney;
    }



    public String getEmail(){
        return email;
    }


}
