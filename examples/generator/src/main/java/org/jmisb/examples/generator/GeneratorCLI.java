package org.jmisb.examples.generator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jmisb.api.video.KlvFormat;

/** Simple MISB file generator */
public class GeneratorCLI {

    /** @param args the command line arguments */
    public static void main(String[] args) {
        final Options commandLineOptions = new Options();
        commandLineOptions.addOption(
                new Option(null, "st0102version", true, "The ST0102 version (default is 12"));
        commandLineOptions.addOption(
                new Option(null, "st0601version", true, "The ST0601 version (default is 16"));
        commandLineOptions.addOption(
                new Option(null, "st0903version", true, "The ST0903 version (default is 5"));
        commandLineOptions.addOption(new Option("o", "outputFile", true, "Output file name"));
        commandLineOptions.addOption(
                new Option("s", "synchronousMultiplex", false, "Use synchronous multiplexing."));
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
            if (commandLine.hasOption("st0102version")) {
                generator.setVersion0102(
                        Byte.parseByte(commandLine.getOptionValue("st0102version")));
            }
            if (commandLine.hasOption("st0601version")) {
                generator.setVersion0601(
                        Byte.parseByte(commandLine.getOptionValue("st0601version")));
            }
            if (commandLine.hasOption("st0903version")) {
                generator.setVersion0903(
                        Byte.parseByte(commandLine.getOptionValue("st0903version")));
            }
            if (commandLine.hasOption("outputFile")) {
                generator.setOutputFile(commandLine.getOptionValue("outputFile"));
            }
            if (commandLine.hasOption("s")) {
                generator.setKlvFormat(KlvFormat.Synchronous);
            }
            generator.generate();
        } catch (ParseException ex) {
            showHelp(commandLineOptions);
        }
    }

    private static void showHelp(final Options commandLineOptions) {
        String header = "Generate test MISB metadata file\n\n";
        String footer =
                "\nPlease report issues at https://github.com/WestRidgeSystems/jmisb/issues";
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("generator", header, commandLineOptions, footer, true);
    }
}
