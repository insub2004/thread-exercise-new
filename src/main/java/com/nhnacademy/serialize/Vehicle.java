package com.nhnacademy.serialize;

public class Vehicle {

    protected int tire;
    protected String window;
    protected boolean door;

    // 기본 생성자 없으면 InvalidClassException : no valid constructor 발생
    public Vehicle() {
    }

    public Vehicle(int tire, String window, boolean door) {
        this.tire = tire;
        this.window = window;
        this.door = door;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "tire=" + tire +
                ", window='" + window + '\'' +
                ", door=" + door +
                '}';
    }
}
