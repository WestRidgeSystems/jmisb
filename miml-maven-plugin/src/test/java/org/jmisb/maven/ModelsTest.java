package org.jmisb.maven;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for Models. */
public class ModelsTest {

    public ModelsTest() {}

    @Test
    public void checkEnumerationAdd() {
        Models uut = new Models();
        assertEquals(uut.getClassModels().size(), 0);
        assertEquals(uut.getEnumerationModels().size(), 0);
        uut.addEnumerationModel(new EnumerationModel());
        assertEquals(uut.getEnumerationModels().size(), 1);
        uut.addEnumerationModel(new EnumerationModel());
        assertEquals(uut.getEnumerationModels().size(), 2);
        assertEquals(uut.getClassModels().size(), 0);
    }

    @Test
    public void checkClassAdd() {
        Models uut = new Models();
        assertEquals(uut.getClassModels().size(), 0);
        assertEquals(uut.getEnumerationModels().size(), 0);
        uut.addClassModel(new ClassModel());
        assertEquals(uut.getClassModels().size(), 1);
        uut.addClassModel(new ClassModel());
        assertEquals(uut.getClassModels().size(), 2);
        assertEquals(uut.getEnumerationModels().size(), 0);
    }

    @Test
    void checkEnumerationLookups() {
        Models uut = new Models();
        uut.addEnumerationModel(makeEnumerationModel1());
        uut.addEnumerationModel(makeEnumerationModel2());
        assertEquals(uut.getEnumerationModels().size(), 2);
        assertTrue(uut.isEnumerationName("Test2"));
        assertTrue(uut.isEnumerationName("Test1"));
        assertFalse(uut.isEnumerationName("Test3"));
        assertFalse(uut.isEnumerationName("test2"));
        assertFalse(uut.isEnumerationName("Test"));
    }

    @Test
    void checkPackageLookups() {
        Models uut = new Models();
        ClassModel class1 = makeClassModel("Class1", "ST1901", "mimd.tests");
        uut.addClassModel(class1);
        uut.addClassModel(makeClassModel("Class2", "ST1904", "mimd.tests"));
        ClassModel class3 = makeClassModel("Class3", "ST1903", "mimd.tests");
        uut.addClassModel(class3);
        ClassModel class4 = makeClassModel("Class4", "ST1904", "mimd.tests");
        uut.addClassModel(class4);
        assertEquals(uut.getClassModels().size(), 4);
        assertEquals(class3.getPackageName(), "mimd.tests.st1903");
        assertEquals(uut.getTypePackage("Class2"), "mimd.tests.st1904");
        assertEquals(class3.getTypePackage("Class2"), "mimd.tests.st1904");
        assertEquals(uut.getTypePackage("Class1"), "mimd.tests.st1901");
        assertEquals(class3.getTypePackage("Class1"), "mimd.tests.st1901");
        assertEquals(class1.getPackageName(), "mimd.tests.st1901");
        assertEquals(class1.getTypePackage("Class2"), "mimd.tests.st1904");
        assertEquals(uut.getTypePackage("Class3"), "mimd.tests.st1903");
        assertEquals(uut.findClassByName("Class3").getName(), "Class3");
        assertEquals(class1.getTypePackage("Class3"), "mimd.tests.st1903");
        assertNull(class1.getTypePackage("NotAClass"));
        assertNull(uut.findClassByName("NotAClass"));
    }

    private EnumerationModel makeEnumerationModel1() {
        EnumerationModel enumerationModel1 = new EnumerationModel();
        enumerationModel1.setName("Test1");
        enumerationModel1.setDocument("ST1901");
        enumerationModel1.setPackageNameBase("mimd.tests");
        enumerationModel1.addEntry(makeModelEntry(33, "x33"));
        enumerationModel1.addEntry(makeModelEntry(34, "x34"));
        return enumerationModel1;
    }

    private EnumerationModel makeEnumerationModel2() {
        EnumerationModel enumerationModel2 = new EnumerationModel();
        enumerationModel2.setName("Test2");
        enumerationModel2.setDocument("ST1902");
        enumerationModel2.setPackageNameBase("mimd.tests");
        enumerationModel2.addEntry(makeModelEntry(33, "y33"));
        enumerationModel2.addEntry(makeModelEntry(34, "y34"));
        return enumerationModel2;
    }

    private EnumerationModelEntry makeModelEntry(int number, String name) {
        EnumerationModelEntry entry = new EnumerationModelEntry();
        entry.setNumber(number);
        entry.setName(name);
        entry.setDescription("");
        return entry;
    }

    private ClassModel makeClassModel(String name, String document, String packageNameBase) {
        ClassModel classModel = new ClassModel();
        classModel.setName(name);
        classModel.setDocument(document);
        classModel.setPackageNameBase(packageNameBase);
        return classModel;
    }
}
