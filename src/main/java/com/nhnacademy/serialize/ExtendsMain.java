package com.nhnacademy.serialize;

import java.io.*;

public class ExtendsMain {
    public static void main(String[] args) {
        String fileName = "extends.txt";

        Vehicle vehicle = new Vehicle(4,"window",true);
        Car car = new Car(vehicle.tire, vehicle.window, vehicle.door, 12345, "테슬라");

        try (
                FileOutputStream fout = new FileOutputStream(fileName);
                ObjectOutputStream out = new ObjectOutputStream(fout);
        ) {
            out.writeObject(car);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try (
                FileInputStream fin = new FileInputStream(fileName);
                ObjectInputStream in = new ObjectInputStream(fin);
        ) {
            Object o = in.readObject();
            if (o instanceof Vehicle vehicle1) {
                System.out.println(vehicle1);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
