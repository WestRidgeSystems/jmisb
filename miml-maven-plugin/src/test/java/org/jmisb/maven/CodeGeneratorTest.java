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
        EnumerationModel enumerationModel = createTestEnumerationModel();
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
        ClassModel platformModel = new ClassModel();
        platformModel.setName("Platform");
        platformModel.setPackageNameBase("test2.miml");
        platformModel.setDocument("ST1905");
        models.addClassModel(platformModel);
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
        entry33.setMaxLength(100);
        classModel.addEntry(entry33);
        ClassModelEntry entry34 = new ClassModelEntry();
        entry34.setName("i1");
        entry34.setNumber(34);
        entry34.setTypeName("Integer");
        entry34.setMinValue(0.0);
        entry34.setMaxValue(100.0);
        entry34.setUnits("s");
        classModel.addEntry(entry34);
        ClassModelEntry entry35 = new ClassModelEntry();
        entry35.setName("u1");
        entry35.setNumber(35);
        entry35.setTypeName("UInt");
        entry35.setMinValue(0.0);
        entry35.setMaxValue(100.0);
        entry35.setUnits("%");
        classModel.addEntry(entry35);
        ClassModelEntry entry36 = new ClassModelEntry();
        entry36.setName("r1");
        entry36.setNumber(36);
        entry36.setTypeName("Real");
        entry36.setMinValue(-1000.0);
        entry36.setMaxValue(1000.0);
        entry36.setUnits("µm");
        classModel.addEntry(entry36);
        ClassModelEntry entry2 = new ClassModelEntry();
        entry2.setName("timer");
        entry2.setNumber(2);
        entry2.setTypeName("REF<Timer>");
        classModel.addEntry(entry2);
        ClassModelEntry entry1 = new ClassModelEntry();
        entry1.setName("mimdId");
        entry1.setNumber(1);
        entry1.setTypeName("UInt[<=2]");
        classModel.addEntry(entry1);
        ClassModelEntry entry38 = new ClassModelEntry();
        entry38.setName("e1");
        entry38.setNumber(39);
        entry38.setTypeName("E1");
        classModel.addEntry(entry38);
        ClassModelEntry entry39 = new ClassModelEntry();
        entry39.setName("platforms");
        entry39.setNumber(39);
        entry39.setTypeName("LIST<Platform>");
        classModel.addEntry(entry39);
        ClassModelEntry entry40 = new ClassModelEntry();
        entry40.setName("rArray1");
        entry40.setNumber(40);
        entry40.setTypeName("Real[]");
        entry40.setMinValue(0.0);
        entry40.setMaxValue(100.0);
        entry40.setResolution(0.1);
        entry40.setUnits("%");
        classModel.addEntry(entry40);
        ClassModelEntry entry97 = new ClassModelEntry();
        entry97.setName("badRef");
        entry97.setNumber(97);
        entry97.setTypeName("REF<Platfor");
        classModel.addEntry(entry97);
        ClassModelEntry entry98 = new ClassModelEntry();
        entry98.setName("badList");
        entry98.setNumber(98);
        entry98.setTypeName("LIST<Platfor");
        classModel.addEntry(entry98);
        ClassModelEntry entry99 = new ClassModelEntry();
        entry99.setName("missing");
        entry99.setNumber(99);
        entry99.setTypeName("SomethingWeDontHave");
        classModel.addEntry(entry99);
        models.addClassModel(classModel);
        models.addClassModel(createBase());
        models.addEnumerationModel(createTestEnumerationModel());
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
        File expectedComponentClass3File = new File("target/test2/src/test2/miml/st1909/U1.java");
        assertTrue(expectedComponentClass3File.exists());
        assertTrue(expectedComponentClass3File.length() > 0);
        File expectedComponentClass4File = new File("target/test2/src/test2/miml/st1909/R1.java");
        assertTrue(expectedComponentClass4File.exists());
        assertTrue(expectedComponentClass4File.length() > 0);
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
        File expectedComponentClass3TestFile =
                new File("target/test2/test/test2/miml/st1909/U1Test.java");
        assertTrue(expectedComponentClass3TestFile.exists());
        assertTrue(expectedComponentClass3TestFile.length() > 0);
        File expectedComponentClass4TestFile =
                new File("target/test2/test/test2/miml/st1909/R1Test.java");
        assertTrue(expectedComponentClass4TestFile.exists());
        assertTrue(expectedComponentClass4TestFile.length() > 0);
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

    private EnumerationModel createTestEnumerationModel() {
        EnumerationModel enumerationModel = new EnumerationModel();
        enumerationModel.setName("E1");
        enumerationModel.setDocument("st1909");
        enumerationModel.setPackageNameBase("test1.miml");
        EnumerationModelEntry entry = new EnumerationModelEntry();
        entry.setName("i1");
        entry.setNumber(33);
        entry.setDescription("i1 desc");
        enumerationModel.addEntry(entry);
        return enumerationModel;
    }
}
