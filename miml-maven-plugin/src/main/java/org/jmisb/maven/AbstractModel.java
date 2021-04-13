package org.jmisb.maven;

/** Shared code between ClassModel and EnumerationModel. */
public class AbstractModel {

    private String packageNameBase;
    private String name;
    private String document;

    public String getPackageName() {
        if (packageNameBase == null) {
            return null;
        }
        return packageNameBase + "." + document.toLowerCase();
    }

    void setPackageNameBase(String packageNameBase) {
        this.packageNameBase = packageNameBase;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        if (name.equals("Object")) {
            this.name = "Objct";
        } else {
            this.name = name;
        }
    }

    public String getDocument() {
        return document;
    }

    void setDocument(String document) {
        this.document = document;
    }

    String getDirectoryPackagePart() {
        return getDocument().toLowerCase() + "/";
    }
}
