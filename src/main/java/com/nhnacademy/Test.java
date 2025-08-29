package com.nhnacademy;

public class Test {

    public static void main(String[] args) {
        int i = 4;

        switch (i) {
            case 1:
                System.out.println(1);
                break;
            case 2:
                System.out.println(2);
                break;
            case 3:
                System.out.println(3);
                break;
            default:
                assert true : "sdsd";
                System.out.println("default");
        }
    }
}
