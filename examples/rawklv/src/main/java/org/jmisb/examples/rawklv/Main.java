package org.jmisb.examples.rawklv;

import java.io.IOException;
import org.jmisb.api.common.KlvParseException;

public class Main {
    public static void main(String[] args) throws IOException, KlvParseException {
        if (args.length < 1) {
            System.out.println("Need to specify the file name on the command line");
            System.exit(1);
        }
        MetadataPlayer player = new MetadataPlayer();
        player.play(args[0]);
    }
}
