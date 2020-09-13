package org.jmisb.maven;

import static org.testng.Assert.*;

import java.io.File;
import org.testng.annotations.Test;

/** Unit tests for MIML CodeGenerator */
public class CodeGeneratorTest {

    public CodeGeneratorTest() {}

    @Test
    public void enumeration() {
        Models models = new Models();
        EnumerationModel enumerationModel = new EnumerationModel();
        enumerationModel.setName("E1");
        enumerationModel.setDocument("st1909");
        enumerationModel.setPackageNameBase("test1.miml");
        EnumerationModelEntry entry = new EnumerationModelEntry();
        entry.setName("i1");
        entry.setNumber(33);
        entry.setDescription("i1 desc");
        enumerationModel.addEntry(entry);
        models.addEnumerationModel(enumerationModel);
        CodeGeneratorConfiguration conf = new CodeGeneratorConfiguration();
        conf.setOutputDirectory(new File("target/test1/src"));
        conf.setOutputTestDirectory(new File("target/test1/test"));
        conf.setModels(models);
        conf.setPackageNameBase("test1.miml");
        CodeGenerator uut = new CodeGenerator(conf);
        assertNotNull(uut);
        uut.generateCode();
        File expectedSourceFile = new File("target/test1/src/test1/miml/st1909/E1.java");
        assertTrue(expectedSourceFile.exists());
        assertTrue(expectedSourceFile.length() > 0);
        File expectedTestFile = new File("target/test1/test/test1/miml/st1909/E1Test.java");
        assertTrue(expectedTestFile.exists());
        assertTrue(expectedTestFile.length() > 0);
    }

    @Test
    public void generateClass() {
        Models models = new Models();
        ClassModel classModel = new ClassModel();
        classModel.setName("C1");
        classModel.setDocument("st1909");
        classModel.setPackageNameBase("test2.miml");
        classModel.setIncludes("Base");
        classModel.setIsAbstract(false);
        classModel.setTopLevel(false);
        ClassModelEntry entry33 = new ClassModelEntry();
        entry33.setName("s1");
        entry33.setNumber(33);
        entry33.setTypeName("String");
        entry33.setParent(classModel);
        entry33.setMaxLength(100);
        classModel.addEntry(entry33);
        ClassModelEntry entry34 = new ClassModelEntry();
        entry34.setName("i1");
        entry34.setNumber(34);
        entry34.setTypeName("Integer");
        entry34.setParent(classModel);
        entry34.setMinValue(0.0);
        entry34.setMaxValue(100.0);
        entry34.setUnits("s");
        classModel.addEntry(entry34);
        models.addClassModel(classModel);
        models.addClassModel(createBase());
        CodeGeneratorConfiguration conf = new CodeGeneratorConfiguration();
        conf.setOutputDirectory(new File("target/test2/src"));
        conf.setOutputTestDirectory(new File("target/test2/test"));
        conf.setModels(models);
        conf.setPackageNameBase("test2.miml");
        CodeGenerator uut = new CodeGenerator(conf);
        assertNotNull(uut);
        uut.generateCode();
        File expectedClassFile = new File("target/test2/src/test2/miml/st1909/C1.java");
        assertTrue(expectedClassFile.exists());
        assertTrue(expectedClassFile.length() > 0);
        File expectedMetadataKeyFile =
                new File("target/test2/src/test2/miml/st1909/C1MetadataKey.java");
        assertTrue(expectedMetadataKeyFile.exists());
        assertTrue(expectedMetadataKeyFile.length() > 0);
        File expectedComponentClass1File = new File("target/test2/src/test2/miml/st1909/S1.java");
        assertTrue(expectedComponentClass1File.exists());
        assertTrue(expectedComponentClass1File.length() > 0);
        File expectedComponentClass2File = new File("target/test2/src/test2/miml/st1909/I1.java");
        assertTrue(expectedComponentClass2File.exists());
        assertTrue(expectedComponentClass2File.length() > 0);
        File expectedTestFile = new File("target/test2/test/test2/miml/st1909/C1Test.java");
        assertTrue(expectedTestFile.exists());
        assertTrue(expectedTestFile.length() > 0);
        File expectedMetadataKeyTestFile =
                new File("target/test2/test/test2/miml/st1909/C1MetadataKeyTest.java");
        assertTrue(expectedMetadataKeyTestFile.exists());
        assertTrue(expectedMetadataKeyTestFile.length() > 0);
        File expectedComponentClass1TestFile =
                new File("target/test2/test/test2/miml/st1909/S1Test.java");
        assertTrue(expectedComponentClass1TestFile.exists());
        assertTrue(expectedComponentClass1TestFile.length() > 0);
        File expectedComponentClass2TestFile =
                new File("target/test2/test/test2/miml/st1909/I1Test.java");
        assertTrue(expectedComponentClass2TestFile.exists());
        assertTrue(expectedComponentClass2TestFile.length() > 0);
    }

    private ClassModel createBase() {
        ClassModel baseClass = new ClassModel();
        baseClass.setName("Base");
        baseClass.setDocument("ST1903");
        baseClass.setPackageNameBase("test2.miml");
        baseClass.setTopLevel(false);
        baseClass.setIsAbstract(true);
        return baseClass;
    }
}
