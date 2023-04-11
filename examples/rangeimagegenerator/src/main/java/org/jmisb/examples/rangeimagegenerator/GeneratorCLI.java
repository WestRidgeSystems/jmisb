package org.jmisb.examples.rangeimagegenerator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.video.CodecIdentifier;

/** Range image file generator */
public class GeneratorCLI {

    /** @param args the command line arguments */
    public static void main(String[] args) throws KlvParseException {
        final Options commandLineOptions = new Options();
        commandLineOptions.addOption(new Option("o", "outputFile", true, "Output file name"));
        commandLineOptions.addOption(
                new Option(null, "coding", true, "The video coding to use (H.264 or H.265)"));
        commandLineOptions.addOption(new Option("h", "help", false, "Show help message"));
        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine commandLine;
        try {
            commandLine = commandLineParser.parse(commandLineOptions, args);
            Generator generator = new Generator();
            if (commandLine.hasOption("h")) {
                showHelp(commandLineOptions);
                return;
            }
            if (commandLine.hasOption("coding")) {
                String codecName = commandLine.getOptionValue("coding");
                if (codecName.equalsIgnoreCase("H.264") || codecName.equalsIgnoreCase("H264")) {
                    generator.setCodec(CodecIdentifier.H264);
                } else if (codecName.equalsIgnoreCase("H.265")
                        || codecName.equalsIgnoreCase("H265")) {
                    generator.setCodec(CodecIdentifier.H265);
                }
            }
            if (commandLine.hasOption("outputFileBase")) {
                generator.setOutputFile(commandLine.getOptionValue("outputFile"));
            }
            generator.generate();
        } catch (ParseException ex) {
            showHelp(commandLineOptions);
        }
    }

    private static void showHelp(final Options commandLineOptions) {
        String header = "Generate test MISB ST 1002 range image files\n\n";
        String footer =
                "\nPlease report issues at https://github.com/WestRidgeSystems/jmisb/issues";
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("generator", header, commandLineOptions, footer, true);
    }
}
