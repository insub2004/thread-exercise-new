package com.nhnacademy.serialize;

import java.io.*;

public class ReadMain {
    public static void main(String[] args) {

        String fileName = "person.txt";

        try (
                FileInputStream fin = new FileInputStream(fileName);
                ObjectInputStream in = new ObjectInputStream(fin)
        ) {
            Object o = in.readObject();
            if (o instanceof Person person) {
                System.out.println(person);
            }
            long serialVersionUID = ObjectStreamClass.lookup(Person.class).getSerialVersionUID();
            System.out.println("SUID : " + serialVersionUID);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
