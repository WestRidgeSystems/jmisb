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

    String getDirectoryPackagePart() {
        return getDocument().toLowerCase() + "/";
    }
}
