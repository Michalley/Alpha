package com.example.alpha;

public class Alpha {

    private String name, info;

    public Alpha (){}

    public Alpha (String name, String info){
        this.name=name;
        this.info=info;
    }

    public String getName (){return name;}
    public void setName (){this.name=name;}

    public String getInfo (){return info;}
    public void setInfo (){this.info=info;}
}
