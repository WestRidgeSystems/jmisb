package org.jmisb.maven;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Collection of class and enumeration models for this MIML instance. */
public class Models {

    private final List<EnumerationModel> enumerationModels = new ArrayList<>();
    private final List<ClassModel> classModels = new ArrayList<>();
    private final Map<String, String> packageNameLookups = new HashMap<>();

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

    // TODO: instead of this, we could just set a Models parent on each class and do the lookup off
    // the classes.
    void buildPackageNameLookupTable() {
        for (ClassModel classModel : getClassModels()) {
            addPackageNameLookup(classModel);
        }
        for (ClassModel classModel : getClassModels()) {
            classModel.setPackageLookup(packageNameLookups);
        }
    }

    private void addPackageNameLookup(ClassModel classModel) {
        packageNameLookups.put(classModel.getName(), classModel.getPackageName());
    }
}
