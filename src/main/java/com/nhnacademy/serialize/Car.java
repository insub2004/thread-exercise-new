package com.nhnacademy.serialize;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Car extends Vehicle implements Serializable {

    int modelId;
    String modelName;

    public Car(int modelId, String modelName) {
        this.modelId = modelId;
        this.modelName = modelName;
    }

    public Car(int tire, String window, boolean door, int modelId, String modelName) {
        super(tire, window, door);
        this.modelId = modelId;
        this.modelName = modelName;
    }

    // 직렬화
    private void writeObject(ObjectOutputStream out) throws IOException {
        // 부모 필드 직렬화
        out.writeInt(tire);
        out.writeUTF(window);
        out.writeBoolean(door);

        // 자신(자식) 필드 직렬화 (하지 않으면 자신의 필드가 기본값으로 처리됨)
        out.defaultWriteObject();
    }

    //역직렬화
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // 부모 필드 역직렬화
        tire = in.readInt();
        window = in.readUTF();
        door = in.readBoolean();

        // 자신(자식) 필드 역직렬화
        in.defaultReadObject();
    }

    @Override
    public String toString() {
        return "Car{" +
                "tire=" + tire +
                ", window=" + window +
                ", door=" + door +
                ", modelId=" + modelId +
                ", modelName='" + modelName + '\'' +
                '}';
    }
}
