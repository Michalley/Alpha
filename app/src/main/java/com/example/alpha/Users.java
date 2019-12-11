package com.example.alpha;

public class Users {
    private String name,email,phoneN, uid;

    public Users (){}

    public Users (String name,String email, String phoneN, String uid){
        this.name=name;
        this.email=email;
        this.phoneN=phoneN;
        this.uid=uid;
    }

    public String getName(){return name;}

    public void setName(){this.name=name;}

    public String getEmail(){ return email;}

    public void setEmail (){this.email=email;}

    public String getPhoneN (){return phoneN;}

    public void setPhoneN (){this.phoneN=phoneN;}

    public String getUid (){return uid;}

    public void setUid (){this.uid=uid;}
}
