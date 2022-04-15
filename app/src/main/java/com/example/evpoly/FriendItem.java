//Rrcycler View 형식지정
package com.example.evpoly;

public class FriendItem {
    String name;
    String message;


    public FriendItem(String name, String message) {
        this.name = name;
        this.message= message;
    }



    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setName(String name) {
        this.name = name;
    }

    }
