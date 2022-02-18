package org.jmisb.examples.systemout;

import java.io.IOException;

/** Main class. */
public class Main {
    /**
     * Main entry point.
     *
     * <p>The file to open must be specified as a command line argument.
     *
     * @param args command line arguments
     * @throws IOException if there was a problem parsing the specified file.
     */
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Need to specify the file name on the command line");
            System.exit(1);
        }
        MetadataPlayer player = new MetadataPlayer();
        player.play(args[0]);
    }
}
