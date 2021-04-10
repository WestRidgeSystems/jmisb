package org.jmisb.maven;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/** Class to turn models into code using templates. */
public class CodeGeneratorListener implements MIML_v3Listener {

    private final CodeGeneratorConfiguration generatorConf;
    private Configuration templateConfiguration;

    private File generatedSourceDirectory;
    private File generatedTestDirectory;
    private final Models models = new Models();
    private ClassModel currentClassModel = null;
    private ClassModelEntry currentClassModelEntry = null;
    private EnumerationModel currentEnumerationModel = null;

    CodeGeneratorListener(CodeGeneratorConfiguration configuration) throws IOException {
        this.generatorConf = configuration;
        createOutputDirectories();
        setupTemplateEngine();
    }

    private void createOutputDirectories() throws IOException {
        String packagePath = generatorConf.getPackageNameBase().replace('.', '/');
        generatedSourceDirectory = new File(generatorConf.getOutputFile(), packagePath);
        if (!generatedSourceDirectory.exists() && !generatedSourceDirectory.mkdirs()) {
            throw new IOException(
                    "Failed to create directories for "
                            + generatedSourceDirectory.getAbsolutePath());
        }
        generatedTestDirectory = new File(generatorConf.getOutputTestDirectory(), packagePath);
        if (!generatedTestDirectory.exists() && !generatedTestDirectory.mkdirs()) {
            throw new IOException(
                    "Failed to create directories for " + generatedTestDirectory.getAbsolutePath());
        }
    }

    private void generateJava(EnumerationModel enumerationModel)
            throws TemplateException, IOException {
        generateEnumeration(enumerationModel);
        generateEnumerationTests(enumerationModel);
    }

    private void generateEnumeration(EnumerationModel enumeration)
            throws TemplateException, IOException {
        try {
            String packagePart = enumeration.getDirectoryPackagePart();
            File targetDirectory = new File(generatedSourceDirectory, packagePart);
            if ((!targetDirectory.exists()) && (!targetDirectory.mkdirs())) {
                throw new IOException(
                        "Failed to create directories for " + targetDirectory.getAbsolutePath());
            }
            Template temp = templateConfiguration.getTemplate("enumeration.ftl");
            File enumerationFile = new File(targetDirectory, enumeration.getName() + ".java");
            try (Writer out =
                    new OutputStreamWriter(
                            new FileOutputStream(enumerationFile), StandardCharsets.UTF_8)) {
                temp.process(enumeration, out);
            }
        } catch (TemplateException | IOException ex) {
            log("Failed to generate enumeration for " + enumeration.getName());
            throw ex;
        }
    }

    private void generateEnumerationTests(EnumerationModel enumeration)
            throws TemplateException, IOException {
        try {
            Template temp = templateConfiguration.getTemplate("enumerationTest.ftl");
            File targetDirectory = makeOutputTestDirectory(enumeration);
            File enumerationTestFile =
                    new File(targetDirectory, enumeration.getName() + "Test.java");
            try (Writer out =
                    new OutputStreamWriter(
                            new FileOutputStream(enumerationTestFile), StandardCharsets.UTF_8)) {
                temp.process(enumeration, out);
            }
        } catch (TemplateException | IOException ex) {
            log("Failed to generate enumeration tests: " + ex.getMessage());
            throw ex;
        }
    }

    public void generateJavaClasses() throws IOException, TemplateException {
        for (EnumerationModel model : models.getEnumerationModels()) {
            generateJava(model);
        }
        models.getClassModels().forEach(model -> addListFlags(model));
        for (ClassModel model : models.getClassModels()) {
            generateJava(model);
        }
        generatePrimitiveClassesForBase();
    }

    private void generateJava(ClassModel classModel) throws TemplateException, IOException {
        try {
            // log("Generating classes for " + classModel.getName());
            String packagePart = classModel.getDirectoryPackagePart();
            File outputSourceDirectory = new File(generatedSourceDirectory, packagePart);
            if ((!outputSourceDirectory.exists()) && (!outputSourceDirectory.mkdirs())) {
                throw new IOException(
                        "Failed to create directories for "
                                + outputSourceDirectory.getAbsolutePath());
            }
            File outputTestDirectory = makeOutputTestDirectory(classModel);
            incorporateIncludes(classModel);
            if (!classModel.getName().equals("Base")) {
                generateMetadataKey(outputSourceDirectory, classModel);
                generateMetadataKeyTests(outputTestDirectory, classModel);
                generateLocalSet(outputSourceDirectory, classModel);
                generateLocalSetTests(outputTestDirectory, classModel);
            }
            generateComponentClasses(outputSourceDirectory, classModel);
            generateComponentClassTests(outputTestDirectory, classModel);
        } catch (TemplateException | IOException ex) {
            log("Failed to generate classes for " + classModel.getName());
            log(ex.getMessage());
            throw ex;
        }
    }

    private void incorporateIncludes(ClassModel classModel) {
        if ((classModel.getIncludes() != null) && (!classModel.getIncludes().isEmpty())) {
            String baseModelName = classModel.getIncludes();
            ClassModel baseModel = findClassByName(baseModelName);
            classModel.applyBaseModel(baseModel);
        }
    }

    private void generateMetadataKeyTests(File outputTestDirectory, ClassModel classModel)
            throws TemplateException, IOException {
        try {
            Template temp = templateConfiguration.getTemplate("metadataKeyTest.ftl");
            File metadataKeyTestFile =
                    new File(outputTestDirectory, classModel.getName() + "MetadataKeyTest.java");
            try (Writer out =
                    new OutputStreamWriter(
                            new FileOutputStream(metadataKeyTestFile), StandardCharsets.UTF_8)) {
                temp.process(classModel, out);
            }
        } catch (TemplateException | IOException ex) {
            log("Failed to generate metadata key tests: " + ex.getMessage());
            throw ex;
        }
    }

    private File makeOutputTestDirectory(AbstractModel model) throws IOException {
        String packagePart = model.getDirectoryPackagePart();
        File targetDirectory = new File(generatedTestDirectory, packagePart);
        if ((!targetDirectory.exists()) && (!targetDirectory.mkdirs())) {
            throw new IOException(
                    "Failed to create directories for " + targetDirectory.getAbsolutePath());
        }
        return targetDirectory;
    }

    private void generateMetadataKey(File targetDirectory, ClassModel classModel)
            throws TemplateException, IOException {
        Template temp = templateConfiguration.getTemplate("metadataKey.ftl");
        File metadataKeyFile = new File(targetDirectory, classModel.getName() + "MetadataKey.java");
        try (Writer out =
                new OutputStreamWriter(
                        new FileOutputStream(metadataKeyFile), StandardCharsets.UTF_8)) {
            temp.process(classModel, out);
        }
    }

    private void generateLocalSet(File targetDirectory, ClassModel classModel)
            throws TemplateException, IOException {
        File outputFile = new File(targetDirectory, classModel.getName() + ".java");
        Template temp = templateConfiguration.getTemplate("compositeClass.ftl");
        try (Writer out =
                new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8)) {
            temp.process(classModel, out);
        }
    }

    private void generateLocalSetTests(File outputTestDirectory, ClassModel classModel)
            throws TemplateException, IOException {
        File testFile = new File(outputTestDirectory, classModel.getName() + "Test.java");
        Template temp = templateConfiguration.getTemplate("compositeClassTest.ftl");
        try (Writer out =
                new OutputStreamWriter(new FileOutputStream(testFile), StandardCharsets.UTF_8)) {
            temp.process(classModel, out);
        }
    }

    private void generateComponentClasses(File targetDirectory, ClassModel classModel)
            throws TemplateException, IOException {
        for (ClassModelEntry entry : classModel.getEntries()) {
            if (entry.getNumber() < 33) {
                // We don't want to regenerate "Base" components in every namespace
                continue;
            }
            processEntry(entry, targetDirectory);
        }
    }

    private void processEntry(ClassModelEntry entry, File targetDirectory)
            throws TemplateException, IOException {
        if (entry.isArray()) {
            if ((entry.isRef()
                            || entry.getTypeName().equals("Real")
                            || entry.getTypeName().equals("UInt"))
                    && (entry.isArray1D())) {
                processClassTemplate(targetDirectory, entry, "primitiveArray1D.ftl");
                processItemKeyTemplate(targetDirectory, entry, "itemKey.ftl");
            } else if ((entry.getTypeName().equals("Boolean")
                            || entry.getTypeName().equals("Integer")
                            || entry.getTypeName().equals("Real")
                            || entry.getTypeName().equals("UInt"))
                    && (entry.isArray2D())) {
                processClassTemplate(targetDirectory, entry, "primitiveArray2D.ftl");
            } else {
                log(
                        "Need to implement array class for "
                                + entry.getName()
                                + " - "
                                + entry.getTypeName());
            }
        } else if (entry.getTypeName().equals("String")
                || entry.getTypeName().equals("UInt")
                || entry.getTypeName().equals("Integer")
                || entry.getTypeName().equals("Real")) {
            processClassTemplate(targetDirectory, entry, "primitiveClass.ftl");
        } else if (entry.getTypeName().equals("Tuple")) {
            if (!entry.getName().equals("mimdId")) {
                // Special case for mimdId
                processClassTemplate(targetDirectory, entry, "tupleClass.ftl");
            }
        } else if (entry.isList()) {
            processClassTemplate(targetDirectory, entry, "listClass.ftl");
            processItemKeyTemplate(targetDirectory, entry, "itemKey.ftl");
        } else if (isEnumerationName(entry.getTypeName())) {
            // Nothing - we've got this already
        } else if (isClassName(entry.getTypeName())) {
            // Nothing - we've got this already
        } else if (entry.isRef()) {
            // special case for this
        } else {
            log(
                    "Need to implement component class for "
                            + entry.getName()
                            + " - "
                            + entry.getTypeName());
            throw new RuntimeException("Not enough code");
        }
    }

    private void generateComponentClassTests(File outputTestDirectory, ClassModel classModel)
            throws TemplateException, IOException {
        for (ClassModelEntry entry : classModel.getEntries()) {
            if (entry.getNumber() < 33) {
                // We don't want to regenerate "Base" component tests in every namespace
                continue;
            }
            processEntryTest(entry, outputTestDirectory);
        }
    }

    private void processEntryTest(ClassModelEntry entry, File outputTestDirectory)
            throws TemplateException, IOException {
        if (entry.isArray()) {
            if ((entry.getTypeName().equals("Boolean")) && (entry.isArray2D())) {
                processClassTestTemplate(outputTestDirectory, entry, "booleanArray2DTest.ftl");
            } else if ((entry.getTypeName().equals("Integer")) && (entry.isArray2D())) {
                processClassTestTemplate(outputTestDirectory, entry, "intArray2DTest.ftl");
            } else if ((entry.getTypeName().equals("Real")) && (entry.isArray1D())) {
                processClassTestTemplate(outputTestDirectory, entry, "realArray1DTest.ftl");
                processItemKeyTestTemplate(outputTestDirectory, entry, "itemKeyTest.ftl");
            } else if ((entry.getTypeName().equals("Real")) && (entry.isArray2D())) {
                processClassTestTemplate(outputTestDirectory, entry, "realArray2DTest.ftl");
            } else if ((entry.getTypeName().equals("UInt")) && (entry.isArray1D())) {
                processClassTestTemplate(outputTestDirectory, entry, "uintArray1DTest.ftl");
                processItemKeyTestTemplate(outputTestDirectory, entry, "itemKeyTest.ftl");
            } else if ((entry.getTypeName().equals("UInt")) && (entry.isArray2D())) {
                processClassTestTemplate(outputTestDirectory, entry, "uintArray2DTest.ftl");
            } else if ((entry.isRef()) && (entry.isArray1D())) {
                processClassTestTemplate(outputTestDirectory, entry, "refArrayTest.ftl");
                processItemKeyTestTemplate(outputTestDirectory, entry, "itemKeyTest.ftl");
            } else {
                log(
                        "Need to implement array class test for "
                                + entry.getName()
                                + " - "
                                + entry.getTypeName());
            }
        } else if (entry.getTypeName().equals("String")) {
            processClassTestTemplate(outputTestDirectory, entry, "stringClassTest.ftl");
        } else if (entry.getTypeName().equals("UInt")) {
            processClassTestTemplate(outputTestDirectory, entry, "uintClassTest.ftl");
        } else if (entry.getTypeName().equals("Integer")) {
            processClassTestTemplate(outputTestDirectory, entry, "intClassTest.ftl");
        } else if (entry.getTypeName().equals("Real")) {
            processClassTestTemplate(outputTestDirectory, entry, "realClassTest.ftl");
        } else if (entry.getTypeName().equals("Tuple")) {
            if (!entry.getName().equals("mimdId")) {
                // Special case for mimdId
                processClassTestTemplate(outputTestDirectory, entry, "tupleClassTest.ftl");
            }
        } else if (isEnumerationName(entry.getTypeName())) {
            // Nothing - we've got this already
        } else if (entry.isList()) {
            processClassTestTemplate(outputTestDirectory, entry, "listClassTest.ftl");
            processItemKeyTestTemplate(outputTestDirectory, entry, "itemKeyTest.ftl");
        } else if (isClassName(entry.getTypeName())) {
            // Nothing - we've got this already
        } else if (entry.isRef()) {
            // special case for this
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

    private void processClassTemplate(
            File outputSourceDirectory, ClassModelEntry entry, String templateFile)
            throws IOException, TemplateException {
        log("Processing class template " + templateFile + " for " + entry.getNameSentenceCase());
        File outputFile = new File(outputSourceDirectory, entry.getNamespacedName() + ".java");
        processTemplate(templateFile, outputFile, entry);
    }

    private void processItemKeyTemplate(
            File outputSourceDirectory, ClassModelEntry entry, String templateFile)
            throws IOException, TemplateException {
        log("Processing item key template " + templateFile + " for " + entry.getNameSentenceCase());
        File outputFile =
                new File(outputSourceDirectory, entry.getNamespacedName() + "ItemKey.java");
        processTemplate(templateFile, outputFile, entry);
    }

    private void processClassTestTemplate(
            File targetDirectory, ClassModelEntry entry, String templateFile)
            throws IOException, TemplateException {
        log("Processing test template " + templateFile + " for " + entry.getNamespacedName());
        File outputFile = new File(targetDirectory, entry.getNamespacedName() + "Test.java");
        processTemplate(templateFile, outputFile, entry);
    }

    private void processItemKeyTestTemplate(
            File targetDirectory, ClassModelEntry entry, String templateFile)
            throws IOException, TemplateException {
        log(
                "Processing array item key test template "
                        + templateFile
                        + " for "
                        + entry.getNameSentenceCase());
        File outputFile = new File(targetDirectory, entry.getNamespacedName() + "ItemKeyTest.java");
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

    @Override
    public void enterDeclaration(MIML_v3Parser.DeclarationContext ctx) {}

    @Override
    public void exitDeclaration(MIML_v3Parser.DeclarationContext ctx) {}

    @Override
    public void enterFileOptions(MIML_v3Parser.FileOptionsContext ctx) {}

    @Override
    public void exitFileOptions(MIML_v3Parser.FileOptionsContext ctx) {}

    @Override
    public void enterGrammarVer(MIML_v3Parser.GrammarVerContext ctx) {
        String mimlVersion = ctx.MIMLVER().getText();
        if (!mimlVersion.equals("3.0")) {
            throw new RuntimeException(
                    "We only support Grammar Version 3.0, but this one claims to be "
                            + mimlVersion);
        }
        System.out.println("Parsing as grammar version " + mimlVersion);
    }

    @Override
    public void exitGrammarVer(MIML_v3Parser.GrammarVerContext ctx) {}

    @Override
    public void enterModelVer(MIML_v3Parser.ModelVerContext ctx) {
        System.out.println("Model version: " + ctx.MODELVERNUM());
    }

    @Override
    public void exitModelVer(MIML_v3Parser.ModelVerContext ctx) {}

    @Override
    public void enterMimdclass(MIML_v3Parser.MimdclassContext ctx) {
        this.currentClassModel = new ClassModel();
        this.currentClassModel.setPackageNameBase(this.generatorConf.getPackageNameBase());
        String name = ctx.CLASSNAME().toString();
        currentClassModel.setName(name);
        currentClassModel.setTopLevel(name.equals("MIMD"));
    }

    @Override
    public void exitMimdclass(MIML_v3Parser.MimdclassContext ctx) {
        models.addClassModel(currentClassModel);
        this.currentClassModel = null;
    }

    @Override
    public void enterExtender(MIML_v3Parser.ExtenderContext ctx) {
        String className = ctx.CLASSNAME().toString();
        this.currentClassModel.setExtends(className);
    }

    @Override
    public void exitExtender(MIML_v3Parser.ExtenderContext ctx) {}

    @Override
    public void enterIncludes(MIML_v3Parser.IncludesContext ctx) {
        String classname = ctx.CLASSNAME().toString();
        if (this.currentClassModel == null) {
            throw new RuntimeException(
                    "Got class 'includes' but do not have a valid class in work");
        } else {
            this.currentClassModel.setIncludes(classname);
        }
    }

    @Override
    public void exitIncludes(MIML_v3Parser.IncludesContext ctx) {}

    @Override
    public void enterBody(MIML_v3Parser.BodyContext ctx) {}

    @Override
    public void exitBody(MIML_v3Parser.BodyContext ctx) {}

    @Override
    public void enterClassdoc(MIML_v3Parser.ClassdocContext ctx) {
        String classDocument = ctx.DOCNAME().toString();
        if (this.currentClassModel == null) {
            throw new RuntimeException(
                    "Got class documentation entry but do not have a valid class in work");
        } else {
            this.currentClassModel.setDocument(classDocument);
        }
    }

    @Override
    public void exitClassdoc(MIML_v3Parser.ClassdocContext ctx) {}

    @Override
    public void enterAttrib(MIML_v3Parser.AttribContext ctx) {
        int tagId = Integer.parseInt(ctx.TAGID().toString());
        String ident = ctx.IDENT().toString();
        this.currentClassModelEntry = new ClassModelEntry();
        this.currentClassModelEntry.setNumber(tagId);
        this.currentClassModelEntry.setName(ident);
    }

    @Override
    public void exitAttrib(MIML_v3Parser.AttribContext ctx) {
        if (!currentClassModelEntry.getTypeName().equals("RESERVED")) {
            this.currentClassModel.addEntry(this.currentClassModelEntry);
        }
        this.currentClassModelEntry = null;
    }

    @Override
    public void enterTypeinfo(MIML_v3Parser.TypeinfoContext ctx) {}

    @Override
    public void exitTypeinfo(MIML_v3Parser.TypeinfoContext ctx) {}

    @Override
    public void enterSingularTuple(MIML_v3Parser.SingularTupleContext ctx) {
        TerminalNode tupleTerminal = ctx.TUPLESTR();
        if (tupleTerminal != null) {
            this.currentClassModelEntry.setTypeName(tupleTerminal.toString());
        }
    }

    @Override
    public void exitSingularTuple(MIML_v3Parser.SingularTupleContext ctx) {}

    @Override
    public void enterSingularString(MIML_v3Parser.SingularStringContext ctx) {
        String typeName = ctx.STRINGSTR().toString();
        this.currentClassModelEntry.setTypeName(typeName);
    }

    @Override
    public void exitSingularString(MIML_v3Parser.SingularStringContext ctx) {}

    @Override
    public void enterSingularReal(MIML_v3Parser.SingularRealContext ctx) {
        String typeName = ctx.REALSTR().toString();
        this.currentClassModelEntry.setTypeName(typeName);
    }

    @Override
    public void exitSingularReal(MIML_v3Parser.SingularRealContext ctx) {}

    @Override
    public void enterSingularInteger(MIML_v3Parser.SingularIntegerContext ctx) {
        String typeName = ctx.INTSTR().toString();
        this.currentClassModelEntry.setTypeName(typeName);
    }

    @Override
    public void exitSingularInteger(MIML_v3Parser.SingularIntegerContext ctx) {}

    @Override
    public void enterSingularUInt(MIML_v3Parser.SingularUIntContext ctx) {
        String typeName = ctx.UINTSTR().toString();
        this.currentClassModelEntry.setTypeName(typeName);
    }

    @Override
    public void exitSingularUInt(MIML_v3Parser.SingularUIntContext ctx) {}

    @Override
    public void enterSingularBinary(MIML_v3Parser.SingularBinaryContext ctx) {
        String typeName = ctx.BINARYSTR().toString();
        this.currentClassModelEntry.setTypeName(typeName);
    }

    @Override
    public void exitSingularBinary(MIML_v3Parser.SingularBinaryContext ctx) {}

    @Override
    public void enterSingularReserved(MIML_v3Parser.SingularReservedContext ctx) {
        String typeName = ctx.RESERVEDSTR().toString();
        this.currentClassModelEntry.setTypeName(typeName);
    }

    @Override
    public void exitSingularReserved(MIML_v3Parser.SingularReservedContext ctx) {}

    @Override
    public void enterRealMinMaxRes(MIML_v3Parser.RealMinMaxResContext ctx) {
        List<MIML_v3Parser.RealNoneContext> values = ctx.realNone();
        if (values.size() >= 1) {
            double minValue = parseRealNoneContextValue(values.get(0));
            this.currentClassModelEntry.setMinValue(minValue);
        }
        if (values.size() >= 2) {
            double maxValue = parseRealNoneContextValue(values.get(1));
            this.currentClassModelEntry.setMaxValue(maxValue);
        }
        if (values.size() >= 3) {
            double resValue = parseRealNoneContextValue(values.get(2));
            this.currentClassModelEntry.setResolution(resValue);
        }
    }

    @Override
    public void exitRealMinMaxRes(MIML_v3Parser.RealMinMaxResContext ctx) {}

    @Override
    public void enterRealNone(MIML_v3Parser.RealNoneContext ctx) {
        // These were handled in enterRealMinMaxRes() because we need the positional information
    }

    @Override
    public void exitRealNone(MIML_v3Parser.RealNoneContext ctx) {}

    @Override
    public void enterIntMinMaxRes(MIML_v3Parser.IntMinMaxResContext ctx) {
        List<MIML_v3Parser.IntNoneContext> values = ctx.intNone();
        if (values.size() >= 1) {
            double minValue = parseIntNoneContextValue(values.get(0));
            this.currentClassModelEntry.setMinValue(minValue);
        }
        if (values.size() >= 2) {
            double maxValue = parseIntNoneContextValue(values.get(1));
            this.currentClassModelEntry.setMaxValue(maxValue);
        }
    }

    @Override
    public void exitIntMinMaxRes(MIML_v3Parser.IntMinMaxResContext ctx) {}

    @Override
    public void enterIntNone(MIML_v3Parser.IntNoneContext ctx) {
        // These were handled in enterIntMinMax() because we need the positional information
    }

    @Override
    public void exitIntNone(MIML_v3Parser.IntNoneContext ctx) {}

    @Override
    public void enterIntVal(MIML_v3Parser.IntValContext ctx) {
        // These were handled in enterIntMinMax() because we need the positional information.
    }

    @Override
    public void exitIntVal(MIML_v3Parser.IntValContext ctx) {}

    @Override
    public void enterUIntMinMax(MIML_v3Parser.UIntMinMaxContext ctx) {
        List<MIML_v3Parser.UintNoneContext> values = ctx.uintNone();
        if (values.size() >= 1) {
            double minValue = parseUintNoneContextValue(values.get(0));
            this.currentClassModelEntry.setMinValue(minValue);
        }
        if (values.size() >= 2) {
            double maxValue = parseUintNoneContextValue(values.get(1));
            this.currentClassModelEntry.setMaxValue(maxValue);
        }
    }

    @Override
    public void exitUIntMinMax(MIML_v3Parser.UIntMinMaxContext ctx) {}

    @Override
    public void enterUintNone(MIML_v3Parser.UintNoneContext ctx) {
        // These were handled in enterUIntMinMax() because we need the positional information
    }

    @Override
    public void exitUintNone(MIML_v3Parser.UintNoneContext ctx) {}

    @Override
    public void enterMaxLen(MIML_v3Parser.MaxLenContext ctx) {
        int maxLength = Integer.parseInt(ctx.UINTVAL().toString());
        this.currentClassModelEntry.setMaxLength(maxLength);
    }

    @Override
    public void exitMaxLen(MIML_v3Parser.MaxLenContext ctx) {}

    @Override
    public void enterArray(MIML_v3Parser.ArrayContext ctx) {
        TerminalNode arrayLengthNode = ctx.UINTVAL();
        ArrayDimension arrayDimension = new ArrayDimension();
        if (arrayLengthNode != null) {
            int arrayLength = Integer.parseInt(arrayLengthNode.toString());
            arrayDimension.setMaxLength(arrayLength);
        }
        this.currentClassModelEntry.addArrayDimension(arrayDimension);
    }

    @Override
    public void exitArray(MIML_v3Parser.ArrayContext ctx) {}

    @Override
    public void enterObject(MIML_v3Parser.ObjectContext ctx) {
        String className = ctx.CLASSIDENT().toString();
        this.currentClassModelEntry.setTypeName(className);
    }

    @Override
    public void exitObject(MIML_v3Parser.ObjectContext ctx) {}

    @Override
    public void enterSpecialList(MIML_v3Parser.SpecialListContext ctx) {
        String className = ctx.CLASSIDENT().toString();
        this.currentClassModelEntry.setTypeName(className);
        this.currentClassModelEntry.setIsList(true);
    }

    @Override
    public void exitSpecialList(MIML_v3Parser.SpecialListContext ctx) {}

    @Override
    public void enterSpecialSpec(MIML_v3Parser.SpecialSpecContext ctx) {
        throw new UnsupportedOperationException(
                "Not supported yet."); // To change body of generated methods, choose Tools |
        // Templates.
    }

    @Override
    public void exitSpecialSpec(MIML_v3Parser.SpecialSpecContext ctx) {}

    @Override
    public void enterSpecialRef(MIML_v3Parser.SpecialRefContext ctx) {
        String classIdent = ctx.CLASSIDENT().toString();
        this.currentClassModelEntry.setTypeName(classIdent);
        this.currentClassModelEntry.setIsRef(true);
    }

    @Override
    public void exitSpecialRef(MIML_v3Parser.SpecialRefContext ctx) {}

    @Override
    public void enterMultiplicity(MIML_v3Parser.MultiplicityContext ctx) {
        TerminalNode minOccurs = ctx.UINTVAL();
        if (minOccurs != null) {
            this.currentClassModelEntry.setMinLength(Integer.parseInt(minOccurs.toString()));
        }
    }

    @Override
    public void exitMultiplicity(MIML_v3Parser.MultiplicityContext ctx) {}

    @Override
    public void enterUintStar(MIML_v3Parser.UintStarContext ctx) {
        TerminalNode upperLimit = ctx.UINTVAL();
        if (upperLimit != null) {
            int upperLength = Integer.parseInt(upperLimit.toString());
            this.currentClassModelEntry.setMaxLength(upperLength);
        } else {
            if (ctx.STAR() == null) {
                throw new RuntimeException("Expected to get *, but not found");
            }
            // We don't need to set the upper limit.
        }
    }

    @Override
    public void exitUintStar(MIML_v3Parser.UintStarContext ctx) {}

    @Override
    public void enterAttrInfo(MIML_v3Parser.AttrInfoContext ctx) {
        TerminalNode deprecateNode = ctx.DEPRECATE();
        if (deprecateNode != null) {
            this.currentClassModelEntry.setDeprecated(true);
        }
    }

    @Override
    public void exitAttrInfo(MIML_v3Parser.AttrInfoContext ctx) {}

    @Override
    public void enterUnitVal(MIML_v3Parser.UnitValContext ctx) {
        TerminalNode noUnits = ctx.NOUNITS();
        if (noUnits != null) {
            this.currentClassModelEntry.addUnitWord("None");
        }
    }

    @Override
    public void exitUnitVal(MIML_v3Parser.UnitValContext ctx) {}

    @Override
    public void enterUnits(MIML_v3Parser.UnitsContext ctx) {
        if (ctx.UNITSTAR() != null) {
            String units = ctx.UNITSTAR().toString();
            this.currentClassModelEntry.addUnitSymbol(units);
        }
        if (ctx.UNITCARROT() != null) {
            String units = ctx.UNITCARROT().toString();
            this.currentClassModelEntry.addUnitSymbol(units);
        }
        if (ctx.UNITSLASH() != null) {
            String units = ctx.UNITSLASH().toString();
            this.currentClassModelEntry.addUnitSymbol(units);
        }
        if (ctx.WORD() != null) {
            String units = ctx.WORD().toString();
            this.currentClassModelEntry.addUnitWord(units);
        }
    }

    @Override
    public void exitUnits(MIML_v3Parser.UnitsContext ctx) {}

    @Override
    public void enterReference(MIML_v3Parser.ReferenceContext ctx) {
        throw new UnsupportedOperationException(
                "Not supported yet."); // To change body of generated methods, choose Tools |
        // Templates.
    }

    @Override
    public void exitReference(MIML_v3Parser.ReferenceContext ctx) {}

    @Override
    public void enterEnumeration(MIML_v3Parser.EnumerationContext ctx) {
        this.currentEnumerationModel = new EnumerationModel();
        this.currentEnumerationModel.setPackageNameBase(this.generatorConf.getPackageNameBase());
        this.currentEnumerationModel.setName(ctx.ENUMNAME().toString());
    }

    @Override
    public void exitEnumeration(MIML_v3Parser.EnumerationContext ctx) {
        models.addEnumerationModel(this.currentEnumerationModel);
        this.currentEnumerationModel = null;
    }

    @Override
    public void enterEnumbody(MIML_v3Parser.EnumbodyContext ctx) {}

    @Override
    public void exitEnumbody(MIML_v3Parser.EnumbodyContext ctx) {}

    @Override
    public void enterEnumdoc(MIML_v3Parser.EnumdocContext ctx) {
        String enumDocument = ctx.ENUMDOCNAME().toString();
        if (this.currentEnumerationModel == null) {
            throw new RuntimeException(
                    "Got enumeration documentation entry but do not have a valid enumeration in work");
        } else {
            this.currentEnumerationModel.setDocument(enumDocument);
        }
    }

    @Override
    public void exitEnumdoc(MIML_v3Parser.EnumdocContext ctx) {}

    @Override
    public void enterEnumvals(MIML_v3Parser.EnumvalsContext ctx) {
        EnumerationModelEntry entry = new EnumerationModelEntry();
        entry.setNumber(Integer.parseInt(ctx.VALUE().toString()));
        entry.setName(ctx.NAME().toString());
        if (ctx.ENUMTEXT() != null) {
            String enumText = ctx.ENUMTEXT().toString().trim();
            if ((enumText.startsWith("{")) && (enumText.endsWith("}"))) {
                enumText = enumText.substring(1, enumText.length() - 1).trim();
            }
            entry.setDescription(enumText);
        }
        this.currentEnumerationModel.addEntry(entry);
    }

    @Override
    public void exitEnumvals(MIML_v3Parser.EnumvalsContext ctx) {}

    @Override
    public void visitTerminal(TerminalNode tn) {}

    @Override
    public void visitErrorNode(ErrorNode en) {
        System.out.println("Error");
    }

    @Override
    public void enterEveryRule(ParserRuleContext prc) {}

    @Override
    public void exitEveryRule(ParserRuleContext prc) {}

    private double parseRealNoneContextValue(MIML_v3Parser.RealNoneContext ctxValue) {
        if (ctxValue.REALNUM() != null) {
            return Double.parseDouble(ctxValue.REALNUM().toString());
        }
        if (ctxValue.NONE() != null) {
            return Double.NaN;
        }
        double sign = 1.0;
        if (ctxValue.PLUSMINUS() != null) {
            if (ctxValue.PLUSMINUS().toString().equals("-")) {
                sign = -1.0;
            }
        }
        if (ctxValue.HALFPISTR() != null) {
            return (Math.PI / 2 * sign);
        }
        if (ctxValue.PISTR() != null) {
            return (Math.PI * sign);
        }
        if (ctxValue.TWOPISTR() != null) {
            return (2.0 * Math.PI * sign);
        }
        throw new RuntimeException("Unhandled parseRealNoneContextValue()");
    }

    private double parseIntNoneContextValue(MIML_v3Parser.IntNoneContext ctxValue) {
        if (ctxValue.intVal() != null) {
            double sign = 1.0;
            if (ctxValue.intVal().PLUSMINUS() != null) {
                if (ctxValue.intVal().PLUSMINUS().toString().equals("-")) {
                    sign = -1;
                }
            }
            return sign * Double.parseDouble(ctxValue.intVal().UINTVAL().toString());
        }
        if (ctxValue.NONE() != null) {
            return Double.NaN;
        }
        throw new RuntimeException("Unhandled parseIntNoneContextValue()");
    }

    private double parseUintNoneContextValue(MIML_v3Parser.UintNoneContext ctxValue) {
        if (ctxValue.UINTVAL() != null) {
            return Double.parseDouble(ctxValue.UINTVAL().toString());
        }
        if (ctxValue.NONE() != null) {
            return Double.NaN;
        }
        throw new RuntimeException("Unhandled parseUintNoneContextValue()");
    }

    private ClassModel findClassByName(String className) {
        return models.findClassByName(className);
    }

    private boolean isEnumerationName(String typeName) {
        return models.isEnumerationName(typeName);
    }

    private boolean isClassName(String typeName) {
        return models.isClassName(typeName);
    }

    private void generatePrimitiveClassesForBase() throws TemplateException, IOException {
        // TODO: this use of "Base" is a hack
        ClassModel base = findClassByName("Base");
        try {
            String packagePart = base.getDirectoryPackagePart();
            File outputSourceDirectory = new File(generatedSourceDirectory, packagePart);
            File outputTestDirectory = makeOutputTestDirectory(base);
            if ((!outputSourceDirectory.exists()) && (!outputSourceDirectory.mkdirs())) {
                throw new IOException(
                        "Failed to create directories for "
                                + outputSourceDirectory.getAbsolutePath());
            }
            for (ClassModelEntry entry : base.getEntries()) {
                processEntry(entry, outputSourceDirectory);
                processEntryTest(entry, outputTestDirectory);
            }
        } catch (TemplateException | IOException ex) {
            log("Failed to generate classes for " + base.getName());
            log(ex.getMessage());
            throw ex;
        }
    }

    private void addListFlags(ClassModel classModel) {
        for (ClassModelEntry entry : classModel.getEntries()) {
            if (entry.isList()) {
                String listBaseClassName = entry.getTypeName();
                ClassModel listBaseClass = findClassByName(listBaseClassName);
                listBaseClass.setNeedListForm(true);
            }
        }
    }
}
