package model;

import java.io.Serial;
import java.io.Serializable;

public class Entity<T> implements Serializable {
    protected T ID;

    @Serial
    private static final long serialVersionUID = 6529685098267751237L;

    public T getID() {
        return ID;
    }

    public void setID(T ID) {
        this.ID = ID;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(!(obj instanceof Entity<?> q)) return false;
        return getID().equals(q.getID());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
