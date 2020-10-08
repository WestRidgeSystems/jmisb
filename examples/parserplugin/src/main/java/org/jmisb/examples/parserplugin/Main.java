package org.jmisb.examples.parserplugin;

import java.io.IOException;
import org.jmisb.api.klv.MisbMessageFactory;
import org.jmisb.examples.parserplugin.timemessage.TimeMessageConstants;
import org.jmisb.examples.parserplugin.timemessage.TimeMessageFactory;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Need to specify the file name on the command line");
            System.exit(1);
        }
        TimeMessageFactory timeMessageFactory = new TimeMessageFactory();
        MisbMessageFactory.getInstance()
                .registerHandler(TimeMessageConstants.TIME_STAMP_UL, timeMessageFactory);
        MetadataPlayer player = new MetadataPlayer();
        player.play(args[0]);
    }
}
