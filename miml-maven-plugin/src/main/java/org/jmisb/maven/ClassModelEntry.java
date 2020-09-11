package org.jmisb.maven;

public class ClassModelEntry {

    private int number;
    private String name;
    private String typeName;
    private Double minValue = null;
    private Double maxValue = null;
    private Double resolution = null;
    private Integer minLength = null;
    private Integer maxLength = null;
    private String units;
    private ClassModel parent = null;

    public String getPackageName() {
        return parent.getPackageName();
    }

    public String getDocument() {
        return parent.getDocument();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNameSentenceCase() {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    // Only valid if this is a REF
    public String getRefItemType() {
        return typeName.split("\\<")[1].split("\\>")[0];
    }

    // Only valid if this is a list
    public String getListItemType() {
        return typeName.split("\\<")[1].split("\\>")[0];
    }

    // Only valid if this is a list.
    public String getListItemTypePackage() {
        // System.out.println(getListItemType());
        return parent.getTypePackage(getListItemType());
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Double getResolution() {
        return resolution;
    }

    public void setResolution(Double resolution) {
        this.resolution = resolution;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public String getUnits() {
        return units;
    }

    public String getEscapedUnits() {
        if (units.equals("%")) {
            return "%%";
        }
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return "ClassModelEntry{"
                + "number="
                + number
                + ", name="
                + name
                + ", typeName="
                + typeName
                + ", minValue="
                + minValue
                + ", maxValue="
                + maxValue
                + ", resolution="
                + resolution
                + ", minLength="
                + minLength
                + ", maxLength="
                + maxLength
                + ", units="
                + units
                + '}';
    }

    void setParent(ClassModel classModel) {
        this.parent = classModel;
    }
}
