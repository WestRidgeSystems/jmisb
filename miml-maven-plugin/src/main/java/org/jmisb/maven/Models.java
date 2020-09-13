package org.jmisb.maven;

import java.util.ArrayList;
import java.util.List;

/** Collection of class and enumeration models for this MIML instance. */
public class Models {

    private final List<EnumerationModel> enumerationModels = new ArrayList<>();
    private final List<String> knownEnumerationValues = new ArrayList<>();
    private final List<ClassModel> classModels = new ArrayList<>();

    Iterable<EnumerationModel> getEnumerationModels() {
        return enumerationModels;
    }

    Iterable<ClassModel> getClassModels() {
        return classModels;
    }

    void addEnumerationModel(EnumerationModel enumeration) {
        enumerationModels.add(enumeration);
    }

    void addClassModel(ClassModel classModel) {
        classModels.add(classModel);
    }

    void addKnownEnumerationValue(String name) {
        knownEnumerationValues.add(name);
    }

    boolean isEnumerationName(String typeName) {
        return knownEnumerationValues.contains(typeName);
    }
}
