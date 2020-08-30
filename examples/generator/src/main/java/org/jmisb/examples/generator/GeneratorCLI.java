package org.jmisb.examples.generator;

/** Simple MISB file generator */
public class GeneratorCLI {

    /** @param args the command line arguments */
    public static void main(String[] args) {
        Generator generator = new Generator();
        generator.generate();
    }
}
