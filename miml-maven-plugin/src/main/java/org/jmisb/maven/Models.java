package org.jmisb.maven;

import java.util.ArrayList;
import java.util.List;

/** Collection of class and enumeration models for this MIML instance. */
public class Models {

    private final List<EnumerationModel> enumerationModels = new ArrayList<>();
    private final List<ClassModel> classModels = new ArrayList<>();
    private final List<String> st1902ClassNames =
            new ArrayList<String>() {
                {
                    add("IMimdMetadataValue");
                    add("MimdId");
                    add("MimdIdReference");
                    add("Tuple");
                }
            };

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

    boolean isClassName(String typeName) {
        return classModels.stream()
                .anyMatch((classModel) -> (classModel.getName().equals(typeName)));
    }

    String getTypePackage(String typeName) {
        for (ClassModel classModel : getClassModels()) {
            if (classModel.getName().equals(typeName)) {
                return classModel.getPackageName();
            }
        }
        for (EnumerationModel enumerationModel : getEnumerationModels()) {
            if (enumerationModel.getName().equals(typeName)) {
                return enumerationModel.getPackageName();
            }
        }

        if (st1902ClassNames.contains(typeName)) {
            return "org.jmisb.api.klv.st1902";
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
}
