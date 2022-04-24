package com.example.evpoly;

public class Car {
    private Integer car_id;
    private String car_num;
    private String in_time;
    private String out_time;
    private String image;


    public Car(){
        this.car_num=car_num;
        this.in_time=in_time;
        this.out_time=out_time;
        this.image=image;
    }


    public String getCar_num() {
        return car_num;
    }

    public void setCar_num(String car_num) {
        this.car_num = car_num;
    }

    public String getIn_time() {
        return in_time;
    }

    public void setIn_time(String in_time) {
        this.in_time = in_time;
    }

    public String getLeft_time() {
        return out_time;
    }

    public void setLeft_time(String out_time) {
        this.out_time = out_time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
