package org.jmisb.maven;

import java.util.ArrayList;
import java.util.List;

public class ClassModel {
    private String packageNameBase;
    private String name;
    private String includes;
    private String document;
    private List<ClassModelEntry> entries = new ArrayList<>();

    public String getPackagename() {
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

    public String getIncludes() {
        return includes;
    }

    public void setIncludes(String includes) {
        this.includes = includes;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public List<ClassModelEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<ClassModelEntry> entries) {
        this.entries = entries;
    }

    public void addEntry(ClassModelEntry entry) {
        this.entries.add(entry);
    }

    void parseClassLine(String line) {
        String[] lineParts = line.split(" ");
        setName(lineParts[1].trim());
        if (line.contains("includes")) {
            setIncludes(lineParts[3].trim());
            // TODO: include Base
        }
    }
}
