package api.view.gui.model;

import javafx.beans.property.SimpleStringProperty;

public class SimpleTableViewEntry {
    public SimpleStringProperty first;
    public SimpleStringProperty second;

    public SimpleTableViewEntry(Object first, Object second) {
        this.first  = new SimpleStringProperty(first.toString());
        this.second = new SimpleStringProperty(second.toString());
    }

    public void setFirst(String first) {
        this.first.set(first);
    }

    public String getFirst() {
        return first.get();
    }

    public void setSecond(String second) {
        this.second.set(second);
    }

    public String getSecond() {
        return second.get();
    }
}