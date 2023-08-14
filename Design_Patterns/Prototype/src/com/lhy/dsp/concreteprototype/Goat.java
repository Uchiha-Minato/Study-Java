package com.lhy.dsp.concreteprototype;

import com.lhy.dsp.prototype.IPrototype;

import java.io.*;

public class Goat implements IPrototype, Serializable {

    StringBuffer color;

    public StringBuffer getColor() {
        return color;
    }

    public void setColor(StringBuffer color) {
        this.color = color;
    }

    @Override
    public Object cloneMe() throws CloneNotSupportedException {
        Object object;
        try {
            ByteArrayOutputStream ous1 = new ByteArrayOutputStream();
            ObjectOutputStream ous2 = new ObjectOutputStream(ous1);
            ous2.writeObject(this);

            ByteArrayInputStream ins1 = new ByteArrayInputStream(ous1.toByteArray());
            ObjectInputStream ins2 = new ObjectInputStream(ins1);
            object = ins2.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return object;
    }
}
