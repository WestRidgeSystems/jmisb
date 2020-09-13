package org.jmisb.maven;

import java.io.File;

/** CodeGenerator configuration settings. */
class CodeGeneratorConfiguration {
    private File outputFile;
    private File outputTestDirectory;
    private Models models;
    private String packageNameBase;

    public File getOutputFile() {
        return outputFile;
    }

    public void setOutputDirectory(File outputFile) {
        this.outputFile = outputFile;
    }

    public File getOutputTestDirectory() {
        return outputTestDirectory;
    }

    public void setOutputTestDirectory(File outputTestDirectory) {
        this.outputTestDirectory = outputTestDirectory;
    }

    public Models getModels() {
        return models;
    }

    public void setModels(Models models) {
        this.models = models;
    }

    public String getPackageNameBase() {
        return packageNameBase;
    }

    public void setPackageNameBase(String packageNameBase) {
        this.packageNameBase = packageNameBase;
    }
}
