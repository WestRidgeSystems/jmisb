package org.jmisb.maven;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClassModel extends AbstractModel {

    private String includes;
    private String extendsFrom;
    private final List<ClassModelEntry> entries = new ArrayList<>();
    private boolean topLevel = false;
    private boolean needListForm = false;
    private Models parent;

    public String getIncludes() {
        return includes;
    }

    public void setIncludes(String includes) {
        this.includes = includes;
    }

    public String getExtends() {
        return extendsFrom;
    }

    public void setExtends(String extendsFrom) {
        this.extendsFrom = extendsFrom;
    }

    public boolean isTopLevel() {
        return topLevel;
    }

    public void setTopLevel(boolean topLevel) {
        this.topLevel = topLevel;
    }

    public List<ClassModelEntry> getEntries() {
        return entries;
    }

    public void addEntry(ClassModelEntry entry) {
        entry.setParent(this);
        this.entries.add(entry);
    }

    String getTypePackage(String typeName) {
        return parent.getTypePackage(typeName);
    }

    void applyBaseModel(ClassModel baseModel) {
        ArrayList<ClassModelEntry> baseModelEntries = new ArrayList<>(baseModel.getEntries());
        Collections.reverse(baseModelEntries);
        for (ClassModelEntry entry : baseModelEntries) {
            /*
            System.out.println(
                    "classModel:"
                            + getName()
                            + " adding "
                            + entry.getName()
                            + ", "
                            + entry.getNameSentenceCase());
            */
            entries.add(0, entry);
        }
    }

    public boolean getHasDeprecatedAttribute() {
        boolean deprecatedFound = false;
        for (ClassModelEntry entry : entries) {
            if (entry.isDeprecated()) {
                deprecatedFound = true;
                break;
            }
        }
        return deprecatedFound;
    }

    public boolean needListForm() {
        return needListForm;
    }

    public void setNeedListForm(boolean needListForm) {
        this.needListForm = needListForm;
    }

    public void setParent(Models parent) {
        this.parent = parent;
    }
}
