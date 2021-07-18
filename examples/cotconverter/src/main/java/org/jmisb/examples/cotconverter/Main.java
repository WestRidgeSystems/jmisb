package org.jmisb.examples.cotconverter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Need to specify the file name on the command line");
            System.exit(1);
        }
        Converter converter = new Converter();
        converter.play(args[0]);
    }
}
