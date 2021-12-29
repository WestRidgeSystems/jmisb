package org.jmisb.examples.mimdgenerator;

import org.jmisb.api.common.KlvParseException;

/** Simple MIMD file generator */
public class GeneratorCLI {

    /** @param args the command line arguments */
    public static void main(String[] args) {
        try {
            Generator generator = new Generator();
            generator.generate();
        } catch (KlvParseException ex) {
            System.out.println("Failed to generate MIMD example: " + ex.getMessage());
        }
    }
}
