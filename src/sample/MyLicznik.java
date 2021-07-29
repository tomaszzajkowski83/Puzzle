package sample;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class MyLicznik implements Serializable {
    private int czas;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);


    public MyLicznik() {
        this(0);
    }

    public MyLicznik(int czas) {
        this.czas = czas;
    }

    public int getCzas() {
        return czas;
    }

    public synchronized void addPropertyChangeListener(PropertyChangeListener pcList1) {
        pcs.addPropertyChangeListener(pcList1);
    }

    public synchronized void removePropertyChangeListener(PropertyChangeListener pcList2){
        pcs.removePropertyChangeListener(pcList2);
    }

    public void setCzas(int newCzas) {
        int oldValue = czas;
        czas = newCzas;
        pcs.firePropertyChange("Aktualizacja czasu",oldValue,newCzas);
    }
}