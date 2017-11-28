package model;

import org.linguafranca.pwdb.kdbx.simple.SimpleEntry;
import org.linguafranca.pwdb.kdbx.simple.SimpleGroup;

public class ViewEntry {
    private SimpleEntry entry;
    private SimpleGroup group;
    private boolean isEntry;

    public ViewEntry(SimpleEntry entry) {
        isEntry = true;
        this.entry = entry;
    }

    public ViewEntry(SimpleGroup group) {
        isEntry = false;
        this.group = group;
    }

    public SimpleEntry getEntry() {
        return entry;
    }

    public SimpleGroup getGroup() {
        return group;
    }

    public boolean isEntry() {
        return isEntry;
    }
}
