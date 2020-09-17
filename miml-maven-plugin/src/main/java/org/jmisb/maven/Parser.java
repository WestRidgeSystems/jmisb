package org.jmisb.maven;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/** MIML Parser */
class Parser {

    private final ParserConfiguration conf;

    Parser(ParserConfiguration configuration) {
        conf = configuration;
    }

    Models processFiles(File directoryContainingFiles) {
        Models models = new Models();
        File[] files = directoryContainingFiles.listFiles();
        for (File inFile : files) {
            if (inFile.getName().endsWith(".miml")) {
                models.mergeAll(processMimlFile(inFile));
            }
        }
        return models;
    }

    Models processMimlFile(File inFile) {
        try {
            System.out.println("Processing: " + inFile.getAbsolutePath());
            List<String> lines =
                    Files.readAllLines(Paths.get(inFile.getAbsolutePath()), StandardCharsets.UTF_8);
            List<MimlTextBlock> textBlocks = getBlocks(lines);
            return processBlocks(textBlocks);
        } catch (IOException ex) {
            System.out.println("Failed to read lines from " + inFile);
        }
        return new Models();
    }

    private List<MimlTextBlock> getBlocks(List<String> lines) {
        List<MimlTextBlock> blocks = new ArrayList<>();
        MimlTextBlock block = new MimlTextBlock();
        for (String line : lines) {
            String trimmedLine = line.trim();
            block.addLine(trimmedLine);
            if ("}".equals(trimmedLine)) {
                blocks.add(block);
                block = new MimlTextBlock();
            }
        }
        return blocks;
    }

    private Models processBlocks(List<MimlTextBlock> textBlocks) {
        Models models = new Models();
        for (MimlTextBlock textBlock : textBlocks) {
            models.merge(processBlock(textBlock));
        }
        return models;
    }

    AbstractModel processBlock(MimlTextBlock textBlock) {
        List<String> lines = textBlock.getText();
        for (String line : lines) {
            if (line.startsWith("MIML_Grammar")) {
                continue;
            }
            if (line.startsWith("enumeration")) {
                EnumerationModel enumeration = processEnumerationBlock(textBlock);
                enumeration.setPackageNameBase(conf.getPackageNameBase());
                return enumeration;
            } else if (line.startsWith("class") || line.startsWith("abstract class")) {
                ClassModel classModel = processClassBlock(textBlock);
                classModel.setTopLevel(classModel.getName().equals(conf.getTopLevelClassName()));
                classModel.setPackageNameBase(conf.getPackageNameBase());
                return classModel;
            } else {
                throw new UnsupportedOperationException("Don't know how to handle: " + line);
            }
        }
        return null;
    }

    static EnumerationModel processEnumerationBlock(MimlTextBlock textBlock) {
        List<String> lines = textBlock.getText();
        EnumerationModel enumeration = new EnumerationModel();
        for (String line : lines) {
            if (line.equals("}")) {
                break;
            }
            if (line.startsWith("enumeration")) {
                enumeration.setName(parseEnumerationName(line));
                continue;
            }
            if (line.startsWith("Document")) {
                enumeration.setDocument(parseDocumentName(line));
                continue;
            }
            if (line.contains(" = ")) {
                enumeration.addEntry(parseEnumerationEntry(line));
                continue;
            }
        }
        return enumeration;
    }

    static ClassModel processClassBlock(MimlTextBlock textBlock) {
        // System.out.println("processing class block line: " + textBlock);
        List<String> lines = textBlock.getText();
        ClassModel classModel = new ClassModel();
        for (String line : lines) {
            if (line.equals("}")) {
                break;
            }
            if (line.startsWith("class")) {
                classModel.parseClassLine(line);
                continue;
            }
            if (line.startsWith("abstract class")) {
                classModel.parseAbstractClassLine(line);
                continue;
            }
            if (line.startsWith("Document")) {
                classModel.setDocument(parseDocumentName(line));
                continue;
            }
            if (line.contains(" : ")) {
                ClassModelEntry entry = parseClassEntry(line);
                classModel.addEntry(entry);
                continue;
            }
        }
        return classModel;
    }

    static String parseEnumerationName(String line) {
        String[] lineParts = line.split(" ");
        return lineParts[1];
    }

    static String parseDocumentName(String line) {
        String[] lineParts = line.split("=");
        String documentPart = lineParts[1].trim();
        documentPart = documentPart.substring(0, documentPart.length() - 1);
        return documentPart;
    }

    static EnumerationModelEntry parseEnumerationEntry(String line) {
        EnumerationModelEntry entry = new EnumerationModelEntry();
        String[] partsEquals = line.split(" = ");
        entry.setNumber(Integer.parseInt(partsEquals[0].trim()));
        String[] partsBracket = partsEquals[1].split("\\{");
        entry.setName(partsBracket[0].trim());
        String[] descriptionSplit = partsBracket[1].split("}");
        entry.setDescription(descriptionSplit[0]);
        return entry;
    }

    static ClassModelEntry parseClassEntry(String line) {
        ClassModelEntry entry = new ClassModelEntry();
        String[] partsEquals = line.split(" : ");
        String idAndNamePart = partsEquals[0];
        String[] idAndNameParts = idAndNamePart.split("_");
        String idAsString = idAndNameParts[0].trim();
        int id = Integer.parseInt(idAsString);
        entry.setNumber(id);
        String name = idAndNameParts[1].trim();
        entry.setName(name);
        String typePart = partsEquals[1];
        String[] partsBracket = typePart.split("\\{");
        String[] unitsSplit = partsBracket[1].split("}");
        // TODO: there is a case where this can be "<units>, DEPRECATED"
        entry.setUnits(unitsSplit[0].trim());
        String[] typeSplit = partsBracket[0].split("\\(");
        String typeName = typeSplit[0].trim();
        entry.setTypeName(typeName);
        if (typeSplit.length > 1) {
            String typeModifiers = typeSplit[1].replace(")", "").trim();
            // getLog().info(typeModifiers.toString());
            parseTypeModifierPartsToEntry(entry, typeModifiers);
        }
        // getLog().info(entry.toString());
        return entry;
    }

    static void parseTypeModifierPartsToEntry(ClassModelEntry entry, String typeModifiers) {
        String[] typeModifierParts = typeModifiers.split(",", -1);
        if ("String".equals(entry.getTypeName())) {
            parseTypeModifierPartsForString(typeModifierParts, entry, typeModifiers);
        } else if ("Real".equals(entry.getTypeName())) {
            parseTypeModifierPartsForReal(typeModifierParts, entry, typeModifiers);
        } else if (entry.getTypeName().startsWith("Real[]")) {
            if (typeModifierParts.length == 3) {
                double minValue = stringToDouble(typeModifierParts[0]);
                entry.setMinValue(minValue);
                double maxValue = stringToDouble(typeModifierParts[1]);
                entry.setMaxValue(maxValue);
                double resolution = stringToDouble(typeModifierParts[2]);
                entry.setResolution(resolution);
            } else {
                System.out.println("Unhandled Real typeModifierParts: " + typeModifiers);
            }
        } else if (entry.getTypeName().startsWith("LIST<")) {
            if (typeModifierParts.length == 2) {
                int minLength = Integer.parseInt(typeModifierParts[0]);
                entry.setMinLength(minLength);
                if (!typeModifierParts[1].trim().equals("*")) {
                    int maxLength = Integer.parseInt(typeModifierParts[1].trim());
                    entry.setMaxLength(maxLength);
                }
            } else {
                System.out.println("Unhandled Real typeModifierParts: " + typeModifiers);
            }
        } else if (entry.getName().equals("mimdId")) {
            // Nothing - special case
        } else {
            System.out.println(
                    "Unhandled type / typeModifierParts: "
                            + entry.getTypeName()
                            + " / "
                            + typeModifiers);
        }
    }

    private static void parseTypeModifierPartsForString(
            String[] typeModifierParts, ClassModelEntry entry, String typeModifiers)
            throws NumberFormatException, IllegalArgumentException {
        if (typeModifierParts.length == 1) {
            int maxLength = Integer.parseInt(typeModifierParts[0]);
            entry.setMaxLength(maxLength);
        } else {
            throw new IllegalArgumentException(
                    "Unhandled String typeModifierParts: " + typeModifiers);
        }
    }

    private static void parseTypeModifierPartsForReal(
            String[] typeModifierParts, ClassModelEntry entry, String typeModifiers)
            throws NumberFormatException {
        switch (typeModifierParts.length) {
            case 1:
                {
                    double minValue = stringToDouble(typeModifierParts[0]);
                    entry.setMinValue(minValue);
                    break;
                }
            case 2:
                {
                    double minValue = stringToDouble(typeModifierParts[0]);
                    entry.setMinValue(minValue);
                    double maxValue = stringToDouble(typeModifierParts[1]);
                    entry.setMaxValue(maxValue);
                    break;
                }
            case 3:
                {
                    double minValue = stringToDouble(typeModifierParts[0]);
                    entry.setMinValue(minValue);
                    double maxValue = stringToDouble(typeModifierParts[1]);
                    entry.setMaxValue(maxValue);
                    double resolution = stringToDouble(typeModifierParts[2]);
                    entry.setResolution(resolution);
                    break;
                }
            default:
                System.out.println("Unhandled Real typeModifierParts: " + typeModifiers);
                break;
        }
    }

    private static double stringToDouble(String typeModifierPart) throws NumberFormatException {
        switch (typeModifierPart.trim()) {
            case "TWO_PI":
                return 2.0 * Math.PI;
            case "PI":
                return Math.PI;
            case "HALF_PI":
                return Math.PI / 2.0;
            default:
                return Double.parseDouble(typeModifierPart);
        }
    }
}
