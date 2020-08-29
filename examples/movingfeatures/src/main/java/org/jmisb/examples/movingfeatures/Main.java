package org.jmisb.examples.movingfeatures;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Need to specify the input file name on the command line");
            System.exit(1);
        }
        MovingFeaturesConverter player = new MovingFeaturesConverter();
        player.play(args[0]);
    }
}
