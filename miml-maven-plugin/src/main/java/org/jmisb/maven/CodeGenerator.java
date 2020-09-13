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

/** Class to turn models into code using templates. */
public class CodeGenerator {

    private final File generatedSourceDirectory;
    private final File generatedTestDirectory;
    private final Models models;
    private Configuration templateConfiguration;

    CodeGenerator(File sourceDirectory, File testDirectory, Models models) {
        this.generatedSourceDirectory = sourceDirectory;
        this.generatedTestDirectory = testDirectory;
        this.models = models;
        setupTemplateEngine();
    }

    void generateCode() {
        for (EnumerationModel enumerationModel : models.getEnumerationModels()) {
            generateJava(enumerationModel);
        }
        for (ClassModel classModel : models.getClassModels()) {
            if (!classModel.isIsAbstract()) {
                generateJava(classModel);
            }
        }
    }

    private void generateJava(EnumerationModel enumerationModel) {
        generateEnumeration(enumerationModel);
        generateEnumerationTests(enumerationModel);
    }

    private void generateEnumeration(EnumerationModel enumeration) {
        try {
            String packagePart = enumeration.getDocument().toLowerCase() + "/";
            File targetDirectory = new File(generatedSourceDirectory, packagePart);
            targetDirectory.mkdirs();
            Template temp = templateConfiguration.getTemplate("enumeration.ftl");
            File enumerationFile = new File(targetDirectory, enumeration.getName() + ".java");
            Writer out = new FileWriter(enumerationFile);
            temp.process(enumeration, out);
        } catch (TemplateException | IOException ex) {
            System.out.println("Failed t generate enumeration for " + enumeration.getName());
        }
    }
        
    private void generateEnumerationTests(EnumerationModel enumeration) {
        try {
            Template temp = templateConfiguration.getTemplate("enumerationTest.ftl");
            String packagePart = enumeration.getDocument().toLowerCase() + "/";
            File targetDirectory = new File(generatedTestDirectory, packagePart);
            targetDirectory.mkdirs();
            File enumerationTestFile =
                    new File(targetDirectory, enumeration.getName() + "Test.java");
            Writer out = new FileWriter(enumerationTestFile);
            temp.process(enumeration, out);
        } catch (TemplateException | IOException ex) {
            System.out.println("Failed to generate enumeration tests: " + ex.getMessage());
        }
    }
    
    private void generateJava(ClassModel classModel) {
        try {
            System.out.println("Generating classes for " + classModel.getName());
            String packagePart = classModel.getDocument().toLowerCase() + "/";
            File targetDirectory = new File(generatedSourceDirectory, packagePart);
            targetDirectory.mkdirs();
            incorporateIncludes(classModel);
            generateMetadataKey(targetDirectory, classModel);
            generateLocalSet(targetDirectory, classModel);
            generateComponentClasses(targetDirectory, classModel);
            generateMetadataKeyTests(classModel);
            generateLocalSetTests(classModel);
            generateComponentClassTests(classModel);
        } catch (TemplateException | IOException ex) {
            System.out.println("Failed to generate classes for " + classModel.getName());
        }
    }

    private void incorporateIncludes(ClassModel classModel) {
        if (!classModel.getIncludes().isEmpty()) {
            String baseModelName = classModel.getIncludes();
            ClassModel baseModel = models.findClassByName(baseModelName);
            classModel.applyBaseModel(baseModel);
        }
    }

    private void generateMetadataKeyTests(ClassModel classModel)
            throws TemplateException, IOException {
        try {
            Template temp = templateConfiguration.getTemplate("metadataKeyTest.ftl");
            String packagePart = classModel.getDocument().toLowerCase() + "/";
            File targetDirectory = new File(generatedTestDirectory, packagePart);
            targetDirectory.mkdirs();
            File metadataKeyTestFile =
                    new File(targetDirectory, classModel.getName() + "MetadataKeyTest.java");
            Writer out = new FileWriter(metadataKeyTestFile);
            temp.process(classModel, out);
        } catch (TemplateException | IOException ex) {
            System.out.println("Failed to generate metadata key tests: " + ex.getMessage());
        }
    }

    private void generateMetadataKey(File targetDirectory, ClassModel classModel)
            throws TemplateException, IOException {
        Template temp = templateConfiguration.getTemplate("metadataKey.ftl");
        File metadataKeyFile = new File(targetDirectory, classModel.getName() + "MetadataKey.java");
        Writer out = new FileWriter(metadataKeyFile);
        temp.process(classModel, out);
        // out = new StringWriter();
        // temp.process(classModel, out);
        // System.out.println(out);
    }



    private void generateLocalSet(File targetDirectory, ClassModel classModel)
            throws TemplateException, IOException {
        File testFile = new File(targetDirectory, classModel.getName() + ".java");
        Template temp = templateConfiguration.getTemplate("compositeClass.ftl");
        Writer out = new FileWriter(testFile);
        temp.process(classModel, out);
    }

    private void generateLocalSetTests(ClassModel classModel)
            throws TemplateException, IOException {
        String packagePart = classModel.getDocument().toLowerCase() + "/";
        File targetDirectory = new File(generatedTestDirectory, packagePart);
        targetDirectory.mkdirs();
        File testFile = new File(targetDirectory, classModel.getName() + "Test.java");
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
            } else if (models.isEnumerationName(entry.getTypeName())) {
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
                System.out.println(
                        "Need to implement component class for "
                                + entry.getName()
                                + " - "
                                + entry.getTypeName());
            }
        }
    }

    private void generateComponentClassTests(ClassModel classModel)
            throws TemplateException, IOException {
        String packagePart = classModel.getDocument().toLowerCase() + "/";
        File targetDirectory = new File(generatedTestDirectory, packagePart);
        targetDirectory.mkdirs();
        for (ClassModelEntry entry : classModel.getEntries()) {
            if (entry.getTypeName().equals("String")) {
                processClassTestTemplate(targetDirectory, entry, "stringClassTest.ftl");
            } else if (entry.getTypeName().equals("UInt")) {
                processClassTestTemplate(targetDirectory, entry, "uintClassTest.ftl");
            } else if (entry.getTypeName().equals("Integer")) {
                processClassTestTemplate(targetDirectory, entry, "intClassTest.ftl");
            } else if (entry.getTypeName().equals("Real")) {
                processClassTestTemplate(targetDirectory, entry, "realClassTest.ftl");
            } else if (models.isEnumerationName(entry.getTypeName())) {
                // Nothing - we've got this already
            } else if (entry.getTypeName().startsWith("REF<")) {
                // special case for this
            } else if (entry.getTypeName().startsWith("LIST<")) {
                processClassTestTemplate(targetDirectory, entry, "listClassTest.ftl");
            } else if (entry.getName().equals("mimdId")) {
                // Nothing - special case, hand coded tests
            } else {
                System.out.println(
                        "Need to implement component class test for "
                                + entry.getName()
                                + " - "
                                + entry.getTypeName());
            }
        }
    }

    private void processClassTemplate(
            File targetDirectory, ClassModelEntry entry, String templateFile)
            throws IOException, TemplateException {
        System.out.println(
                "Processing class template "
                        + templateFile
                        + " for "
                        + entry.getNameSentenceCase());
        File outputFile = new File(targetDirectory, entry.getNameSentenceCase() + ".java");
        processTemplate(templateFile, outputFile, entry);
    }

    private void processListItemIdentifierTemplate(
            File targetDirectory, ClassModelEntry entry, String templateFile)
            throws IOException, TemplateException {
        System.out.println(
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
        System.out.println(
                "Processing test template " + templateFile + " for " + entry.getNameSentenceCase());
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
}
