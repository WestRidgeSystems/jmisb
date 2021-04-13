package org.jmisb.maven;

import java.util.ArrayList;
import java.util.List;

public class EnumerationModel extends AbstractModel {
    private List<EnumerationModelEntry> entries = new ArrayList<>();

    public List<EnumerationModelEntry> getEntries() {
        return entries;
    }

    public void addEntry(EnumerationModelEntry entry) {
        this.entries.add(entry);
    }
}
