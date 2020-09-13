package org.jmisb.maven;

/** Parser configuration settings. */
class ParserConfiguration {

    private String packageNameBase;
    private String topLevelClassName;

    public String getPackageNameBase() {
        return packageNameBase;
    }

    public void setPackageNameBase(String packageNameBase) {
        this.packageNameBase = packageNameBase;
    }

    public String getTopLevelClassName() {
        return topLevelClassName;
    }

    public void setTopLevelClassName(String topLevelClassName) {
        this.topLevelClassName = topLevelClassName;
    }
}
