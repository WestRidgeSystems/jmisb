package org.jmisb.maven;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

/** Class to turn models into code using templates. */
public class CodeGenerator {

    private final CodeGeneratorConfiguration generatorConf;
    private Configuration templateConfiguration;

    private File generatedSourceDirectory;
    private File generatedTestDirectory;

    CodeGenerator(CodeGeneratorConfiguration configuration) {
        this.generatorConf = configuration;
        setupTemplateEngine();
    }

    private void createOutputDirectories() {
        String packagePath = generatorConf.getPackageNameBase().replace('.', '/');
        generatedSourceDirectory = new File(generatorConf.getOutputFile(), packagePath);
        generatedSourceDirectory.mkdirs();
        generatedTestDirectory = new File(generatorConf.getOutputTestDirectory(), packagePath);
        generatedTestDirectory.mkdirs();
    }

    void generateCode() {
        createOutputDirectories();
        getEnumerationModels()
                .forEach(
                        (enumerationModel) -> {
                            generateJava(enumerationModel);
                        });
        for (ClassModel classModel : getClassModels()) {
            generateJava(classModel);
        }
    }

    private List<EnumerationModel> getEnumerationModels() {
        return generatorConf.getModels().getEnumerationModels();
    }

    private List<ClassModel> getClassModels() {
        return generatorConf.getModels().getClassModels();
    }

    private void generateJava(EnumerationModel enumerationModel) {
        generateEnumeration(enumerationModel);
        generateEnumerationTests(enumerationModel);
    }

    private void generateEnumeration(EnumerationModel enumeration) {
        try {
            String packagePart = enumeration.getDirectoryPackagePart();
            File targetDirectory = new File(generatedSourceDirectory, packagePart);
            targetDirectory.mkdirs();
            Template temp = templateConfiguration.getTemplate("enumeration.ftl");
            File enumerationFile = new File(targetDirectory, enumeration.getName() + ".java");
            Writer out = new FileWriter(enumerationFile);
            temp.process(enumeration, out);
        } catch (TemplateException | IOException ex) {
            log("Failed to generate enumeration for " + enumeration.getName());
        }
    }

    private void generateEnumerationTests(EnumerationModel enumeration) {
        try {
            Template temp = templateConfiguration.getTemplate("enumerationTest.ftl");
            File targetDirectory = makeOutputTestDirectory(enumeration);
            File enumerationTestFile =
                    new File(targetDirectory, enumeration.getName() + "Test.java");
            Writer out = new FileWriter(enumerationTestFile);
            temp.process(enumeration, out);
        } catch (TemplateException | IOException ex) {
            log("Failed to generate enumeration tests: " + ex.getMessage());
        }
    }

    private void generateJava(ClassModel classModel) {
        if (classModel.isIsAbstract()) {
            return;
        }
        try {
            log("Generating classes for " + classModel.getName());
            String packagePart = classModel.getDirectoryPackagePart();
            File outputSourceDirectory = new File(generatedSourceDirectory, packagePart);
            outputSourceDirectory.mkdirs();
            File outputTestDirectory = makeOutputTestDirectory(classModel);
            incorporateIncludes(classModel);
            generateMetadataKey(outputSourceDirectory, classModel);
            generateLocalSet(outputSourceDirectory, classModel);
            generateComponentClasses(outputSourceDirectory, classModel);
            generateMetadataKeyTests(outputTestDirectory, classModel);
            generateLocalSetTests(outputTestDirectory, classModel);
            generateComponentClassTests(outputTestDirectory, classModel);
        } catch (TemplateException | IOException ex) {
            log("Failed to generate classes for " + classModel.getName());
            log(ex.getMessage());
        }
    }

    private void incorporateIncludes(ClassModel classModel) {
        if (!classModel.getIncludes().isEmpty()) {
            String baseModelName = classModel.getIncludes();
            ClassModel baseModel = generatorConf.getModels().findClassByName(baseModelName);
            classModel.applyBaseModel(baseModel);
        }
    }

    private void generateMetadataKeyTests(File outputTestDirectory, ClassModel classModel)
            throws TemplateException, IOException {
        try {
            Template temp = templateConfiguration.getTemplate("metadataKeyTest.ftl");
            File metadataKeyTestFile =
                    new File(outputTestDirectory, classModel.getName() + "MetadataKeyTest.java");
            Writer out = new FileWriter(metadataKeyTestFile);
            temp.process(classModel, out);
        } catch (TemplateException | IOException ex) {
            log("Failed to generate metadata key tests: " + ex.getMessage());
        }
    }

    private File makeOutputTestDirectory(AbstractModel model) {
        String packagePart = model.getDirectoryPackagePart();
        File targetDirectory = new File(generatedTestDirectory, packagePart);
        targetDirectory.mkdirs();
        return targetDirectory;
    }

    private void generateMetadataKey(File targetDirectory, ClassModel classModel)
            throws TemplateException, IOException {
        Template temp = templateConfiguration.getTemplate("metadataKey.ftl");
        File metadataKeyFile = new File(targetDirectory, classModel.getName() + "MetadataKey.java");
        Writer out = new FileWriter(metadataKeyFile);
        temp.process(classModel, out);
    }

    private void generateLocalSet(File targetDirectory, ClassModel classModel)
            throws TemplateException, IOException {
        File testFile = new File(targetDirectory, classModel.getName() + ".java");
        Template temp = templateConfiguration.getTemplate("compositeClass.ftl");
        Writer out = new FileWriter(testFile);
        temp.process(classModel, out);
    }

    private void generateLocalSetTests(File outputTestDirectory, ClassModel classModel)
            throws TemplateException, IOException {
        File testFile = new File(outputTestDirectory, classModel.getName() + "Test.java");
        Template temp = templateConfiguration.getTemplate("compositeClassTest.ftl");
        Writer out = new OutputStreamWriter(new FileOutputStream(testFile), StandardCharsets.UTF_8);
        temp.process(classModel, out);
    }

    private void generateComponentClasses(File targetDirectory, ClassModel classModel)
            throws TemplateException, IOException {
        for (ClassModelEntry entry : classModel.getEntries()) {
            if (entry.getTypeName().equals("String")) {
                processClassTemplate(targetDirectory, entry, "stringClass.ftl");
            } else if (entry.getTypeName().equals("UInt")) {
                processClassTemplate(targetDirectory, entry, "uintClass.ftl");
            } else if (entry.getTypeName().equals("Integer")) {
                processClassTemplate(targetDirectory, entry, "intClass.ftl");
            } else if (entry.getTypeName().equals("Real")) {
                processClassTemplate(targetDirectory, entry, "realClass.ftl");
            } else if (generatorConf.getModels().isEnumerationName(entry.getTypeName())) {
                // Nothing - we've got this already
            } else if (entry.getTypeName().startsWith("REF<")
                    && entry.getTypeName().endsWith(">")) {
                // special case for this
            } else if (entry.getTypeName().startsWith("LIST<")
                    && entry.getTypeName().endsWith(">")) {
                processClassTemplate(targetDirectory, entry, "listClass.ftl");
                processListItemIdentifierTemplate(targetDirectory, entry, "listItemIdentifier.ftl");
            } else if (entry.getName().equals("mimdId")) {
                // Nothing - special case
            } else {
                log(
                        "Need to implement component class for "
                                + entry.getName()
                                + " - "
                                + entry.getTypeName());
            }
        }
    }

    private void generateComponentClassTests(File outputTestDirectory, ClassModel classModel)
            throws TemplateException, IOException {
        String packagePart = classModel.getDirectoryPackagePart();
        for (ClassModelEntry entry : classModel.getEntries()) {
            if (entry.getTypeName().equals("String")) {
                processClassTestTemplate(outputTestDirectory, entry, "stringClassTest.ftl");
            } else if (entry.getTypeName().equals("UInt")) {
                processClassTestTemplate(outputTestDirectory, entry, "uintClassTest.ftl");
            } else if (entry.getTypeName().equals("Integer")) {
                processClassTestTemplate(outputTestDirectory, entry, "intClassTest.ftl");
            } else if (entry.getTypeName().equals("Real")) {
                processClassTestTemplate(outputTestDirectory, entry, "realClassTest.ftl");
            } else if (generatorConf.getModels().isEnumerationName(entry.getTypeName())) {
                // Nothing - we've got this already
            } else if (entry.getTypeName().startsWith("REF<")) {
                // special case for this
            } else if (entry.getTypeName().startsWith("LIST<")) {
                processClassTestTemplate(outputTestDirectory, entry, "listClassTest.ftl");
            } else if (entry.getName().equals("mimdId")) {
                // Nothing - special case, hand coded tests
            } else {
                log(
                        "Need to implement component class test for "
                                + entry.getName()
                                + " - "
                                + entry.getTypeName());
            }
        }
    }

    private void processClassTemplate(
            File outputSourceDirectory, ClassModelEntry entry, String templateFile)
            throws IOException, TemplateException {
        log("Processing class template " + templateFile + " for " + entry.getNameSentenceCase());
        File outputFile = new File(outputSourceDirectory, entry.getNameSentenceCase() + ".java");
        processTemplate(templateFile, outputFile, entry);
    }

    private void processListItemIdentifierTemplate(
            File targetDirectory, ClassModelEntry entry, String templateFile)
            throws IOException, TemplateException {
        log(
                "Processing list item identiifer template "
                        + templateFile
                        + " for "
                        + entry.getNameSentenceCase());
        File outputFile = new File(targetDirectory, entry.getListItemType() + "Identifier.java");
        processTemplate(templateFile, outputFile, entry);
    }

    private void processClassTestTemplate(
            File targetDirectory, ClassModelEntry entry, String templateFile)
            throws IOException, TemplateException {
        log("Processing test template " + templateFile + " for " + entry.getNameSentenceCase());
        File outputFile = new File(targetDirectory, entry.getNameSentenceCase() + "Test.java");
        processTemplate(templateFile, outputFile, entry);
    }

    private void processTemplate(String templateFile, File outputFile, ClassModelEntry entry)
            throws FileNotFoundException, IOException, TemplateException {
        Template temp = templateConfiguration.getTemplate(templateFile);
        Writer out =
                new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8);
        temp.process(entry, out);
    }

    private void setupTemplateEngine() {
        templateConfiguration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        templateConfiguration.setClassForTemplateLoading(getClass(), "/templates");
        templateConfiguration.setDefaultEncoding("UTF-8");
        templateConfiguration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        templateConfiguration.setLogTemplateExceptions(false);
        templateConfiguration.setWrapUncheckedExceptions(true);
        templateConfiguration.setFallbackOnNullLoopVariable(false);
    }

    private void log(String message) {
        System.out.println(message);
    }
}
