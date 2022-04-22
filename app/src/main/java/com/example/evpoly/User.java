package com.example.evpoly;

public class User {
    private String name;
    private String id;
    private String pw;
    private String num;

    public User(){}

    public User(String id, String pw,String name, String num){
        this.name=name;
        this.id=id;
        this.pw=pw;
        this.num=num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
