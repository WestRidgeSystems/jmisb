package org.jmisb.maven.miml;

/** Shared code between ClassModel and EnumerationModel. */
public class AbstractModel {

    private String packageNameBase;
    private String name;
    private String document;

    /**
     * Get the package name.
     *
     * <p>This is the Java package that the resulting class or enumeration will be within.
     *
     * @return the package name as a String.
     */
    public String getPackageName() {
        if (packageNameBase == null) {
            return null;
        }
        return packageNameBase + "." + document.toLowerCase();
    }

    /**
     * Set the base of package name.
     *
     * <p>This is the Java package that the resulting class or enumeration will be within. The
     * document will be appended to the package name base to make the package name.
     *
     * @return the package name base as a String.
     */
    public String getPackageNameBase() {
        return packageNameBase;
    }

    /**
     * Set the base of package name.
     *
     * <p>This is the Java package that the resulting class or enumeration will be within. The
     * document will be appended to the package name base to make the package name. For example, if
     * the package name base is {@code com.blah} and the document is ST1903, the resulting package
     * name will be {@code com.blah.st1903}.
     *
     * @param packageNameBase the package name base as a String.
     */
    void setPackageNameBase(String packageNameBase) {
        this.packageNameBase = packageNameBase;
    }

    /**
     * Get the class or enumeration name.
     *
     * @return the name of the class or enumeration.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the class or enumeration.
     *
     * <p>Names may be slightly sanitized.
     *
     * @param name the name of the class or enumeration as a String.
     */
    void setName(String name) {
        if (name.equals("Object")) {
            this.name = "Objct";
        } else {
            this.name = name;
        }
    }

    /**
     * Get the document that the class or enumeration is provided in.
     *
     * @return the document.
     */
    public String getDocument() {
        return document;
    }

    /**
     * Set the document that the class or enumeration is provided in.
     *
     * @param document the document identifier (e.g. {@code ST1903}.
     */
    void setDocument(String document) {
        this.document = document;
    }

    /**
     * Get a relative directory for the package part.
     *
     * @return the document in the form of a relative directory (e.g. {@code st1903/}.
     */
    String getDirectoryPackagePart() {
        return getDocument().toLowerCase() + "/";
    }
}
