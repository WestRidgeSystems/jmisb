package org.jmisb.examples.mimdgenerator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.jmisb.api.klv.st1204.CoreIdentifier;
import org.jmisb.api.klv.st1903.MIMD;
import org.jmisb.api.klv.st1903.MIMDMetadataKey;
import org.jmisb.api.klv.st1903.TimeTransferMethod;
import org.jmisb.api.klv.st1903.Timer;
import org.jmisb.api.klv.st1903.TimerIdentifier;
import org.jmisb.api.klv.st1903.TimerMetadataKey;
import org.jmisb.api.klv.st1903.Timers;
import org.jmisb.api.klv.st1903.UtcLeapSeconds;
import org.jmisb.api.klv.st1903.Version;
import org.jmisb.api.klv.st190x.IMimdMetadataValue;
import org.jmisb.api.video.IVideoFileOutput;
import org.jmisb.api.video.MetadataFrame;
import org.jmisb.api.video.VideoFileOutput;
import org.jmisb.api.video.VideoFrame;
import org.jmisb.api.video.VideoOutputOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Generator {

    private final int width = 1280;
    private final int height = 960;
    private final int bitRate = 500_000;
    private final int gopSize = 30;
    private final double frameRate = 15.0;
    private final double frameDuration = 1.0 / frameRate;
    private final int duration = 90;
    private final String filename = "mimd.mpeg";

    private static final Logger LOG = LoggerFactory.getLogger(Generator.class);

    public Generator() {}

    public void generate() {

        CoreIdentifier coreIdentifier = new CoreIdentifier();
        coreIdentifier.setMinorUUID(UUID.randomUUID());
        coreIdentifier.setVersion(1);

        VideoOutputOptions options =
                new VideoOutputOptions(width, height, bitRate, frameRate, gopSize, true);
        try (IVideoFileOutput output = new VideoFileOutput(options)) {
            output.open(filename);

            // Write some frames
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            try {
                image = ImageIO.read(new File("test1280.jpg"));
            } catch (IOException e) {
                // TODO: log
            }

            final long numFrames = duration * Math.round(frameRate);
            double pts = 1000.0 * System.currentTimeMillis(); // Close enough for this.
            for (long i = 0; i < numFrames; ++i) {
                SortedMap<MIMDMetadataKey, IMimdMetadataValue> values = new TreeMap<>();
                values.put(MIMDMetadataKey.version, new Version("1"));
                values.put(MIMDMetadataKey.timers, this.getTimers());
                MIMD message = new MIMD(values);
                output.addVideoFrame(new VideoFrame(image, pts * 1.0e-6));
                output.addMetadataFrame(new MetadataFrame(message, pts));
                pts += frameDuration * 1.0e6;
            }

        } catch (IOException e) {
            LOG.error("Failed to write file", e);
        }
    }

    private Timers getTimers() {
        Map<TimerIdentifier, Timer> timerList = new HashMap<>();
        timerList.put(new TimerIdentifier(0), this.getTimer());
        Timers timers = new Timers(timerList);
        return timers;
    }

    private Timer getTimer() {
        SortedMap<TimerMetadataKey, IMimdMetadataValue> values = new TreeMap<>();
        values.put(TimerMetadataKey.utcLeapSeconds, new UtcLeapSeconds(37));
        values.put(TimerMetadataKey.timeTransferMethod, TimeTransferMethod.NTP_V3_3);
        Timer timer = new Timer(values);
        return timer;
    }
}
