package org.jmisb.maven;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EnumerationModel extends AbstractModel {
    private final List<EnumerationModelEntry> entries = new ArrayList<>();

    public List<EnumerationModelEntry> getEntries() {
        return entries.stream().collect(Collectors.toList());
    }

    public void addEntry(EnumerationModelEntry entry) {
        this.entries.add(entry);
    }
}
