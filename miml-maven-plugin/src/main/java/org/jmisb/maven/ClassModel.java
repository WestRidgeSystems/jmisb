package org.jmisb.maven;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassModel {
    private String packageNameBase;
    private String name;
    private String includes;
    private String document;
    private List<ClassModelEntry> entries = new ArrayList<>();
    private Map<String, String> packageLookup = new HashMap<>();
    private boolean topLevel = false;
    private boolean isAbstract;
    private Models parent;

    public String getPackageName() {
        if (packageNameBase == null) {
            return null;
        }
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

    public boolean isTopLevel() {
        return topLevel;
    }

    public void setTopLevel(boolean topLevel) {
        this.topLevel = topLevel;
    }

    public boolean isIsAbstract() {
        return isAbstract;
    }

    public void setIsAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    public List<ClassModelEntry> getEntries() {
        return entries;
    }

    public void addEntry(ClassModelEntry entry) {
        this.entries.add(entry);
    }

    void parseClassLine(String line) {
        String[] lineParts = line.split(" ");
        setName(lineParts[1].trim());
        if (line.contains("includes")) {
            setIncludes(lineParts[3].trim());
        }
    }

    String getTypePackage(String typeName) {
        return parent.getTypePackage(typeName);
    }

    void parseAbstractClassLine(String line) {
        parseClassLine(line.substring("abstract ".length()));
        setIsAbstract(true);
    }

    void applyBaseModel(ClassModel baseModel) {
        ArrayList<ClassModelEntry> baseModelEntries = new ArrayList(baseModel.getEntries());
        Collections.reverse(baseModelEntries);
        for (ClassModelEntry entry : baseModelEntries) {
            System.out.println(
                    "classModel:"
                            + getName()
                            + " adding "
                            + entry.getName()
                            + ", "
                            + entry.getNameSentenceCase());
            entries.add(0, entry);
        }
    }

    public void setParent(Models parent) {
        this.parent = parent;
    }
}
