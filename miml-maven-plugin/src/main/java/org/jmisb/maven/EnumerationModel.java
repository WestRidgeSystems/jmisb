package org.jmisb.maven;

import java.util.ArrayList;
import java.util.List;

public class EnumerationModel {
    private String packageNameBase;
    private String name;
    private String document;
    private List<EnumerationModelEntry> entries = new ArrayList<>();

    public String getPackageName() {
        return packageNameBase + "." + document.toLowerCase();
    }

    public void setPackageNameBase(String packageNameBase) {
        this.packageNameBase = packageNameBase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public List<EnumerationModelEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<EnumerationModelEntry> entries) {
        this.entries = entries;
    }

    public void addEntry(EnumerationModelEntry entry) {
        this.entries.add(entry);
    }
}
