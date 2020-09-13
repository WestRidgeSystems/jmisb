package org.jmisb.maven;

import java.util.ArrayList;
import java.util.List;

/** Collection of class and enumeration models for this MIML instance. */
public class Models {

    private final List<EnumerationModel> enumerationModels = new ArrayList<>();
    private final List<ClassModel> classModels = new ArrayList<>();

    List<EnumerationModel> getEnumerationModels() {
        return enumerationModels;
    }

    List<ClassModel> getClassModels() {
        return classModels;
    }

    void addEnumerationModel(EnumerationModel enumeration) {
        enumerationModels.add(enumeration);
    }

    void addClassModel(ClassModel classModel) {
        classModel.setParent(this);
        classModels.add(classModel);
    }

    boolean isEnumerationName(String typeName) {
        return enumerationModels.stream()
                .anyMatch((enumerationModel) -> (enumerationModel.getName().equals(typeName)));
    }

    String getTypePackage(String typeName) {
        for (ClassModel classModel : getClassModels()) {
            if (classModel.getName().equals(typeName)) {
                return classModel.getPackageName();
            }
        }
        return null;
    }

    ClassModel findClassByName(String className) {
        for (ClassModel classModel : getClassModels()) {
            if (classModel.getName().equals(className)) {
                return classModel;
            }
        }
        System.out.println("Failed to look up " + className);
        return null;
    }

    void merge(AbstractModel model) {
        if (model instanceof ClassModel) {
            classModels.add((ClassModel) model);
        } else if (model instanceof EnumerationModel) {
            enumerationModels.add((EnumerationModel) model);
        }
    }

    void mergeAll(Models models) {
        this.enumerationModels.addAll(models.getEnumerationModels());
        for (ClassModel classModel : models.getClassModels()) {
            this.addClassModel(classModel);
        }
    }
}
