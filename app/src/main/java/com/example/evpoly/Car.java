package com.example.evpoly;

import android.media.Image;

public class Car {
    private Integer car_id;
    private String car_num;
    private String in_time;
    private String left_time;
    private Image car_image;

    public Car(){}

    public Car(String car_num, String in_time,String out_time){
        this.car_num=car_num;
        this.in_time=in_time;
        this.left_time=out_time;
    }


    public String getCar_num() {
        return car_num;
    }

    public void setCar_num(String name) {
        this.car_num = car_num;
    }

    public String getIn_time() {
        return in_time;
    }

    public void setIn_time(String in_time) {
        this.in_time = in_time;
    }

    public String getLeft_time() {
        return left_time;
    }

    public void setLeft_time(String left_time) {
        this.left_time = left_time;
    }

}
