package org.jmisb.examples.timetransfer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import javax.imageio.ImageIO;
import net.frogmouth.chronyjava.Command;
import net.frogmouth.chronyjava.Request;
import net.frogmouth.chronyjava.Tracking;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0603.NanoPrecisionTimeStamp;
import org.jmisb.api.klv.st1204.CoreIdentifier;
import org.jmisb.api.video.CodecIdentifier;
import org.jmisb.api.video.IVideoFileOutput;
import org.jmisb.api.video.KlvFormat;
import org.jmisb.api.video.MetadataFrame;
import org.jmisb.api.video.VideoFileOutput;
import org.jmisb.api.video.VideoFrame;
import org.jmisb.api.video.VideoOutputOptions;
import org.jmisb.core.video.TimingUtils;
import org.jmisb.st1603.localset.CorrectionMethod;
import org.jmisb.st1603.localset.ITimeTransferValue;
import org.jmisb.st1603.localset.LastSynchronizationDifference;
import org.jmisb.st1603.localset.ReferenceSource;
import org.jmisb.st1603.localset.ST1603DocumentVersion;
import org.jmisb.st1603.localset.SynchronizationPulseFrequency;
import org.jmisb.st1603.localset.TimeTransferKey;
import org.jmisb.st1603.localset.TimeTransferLocalSet;
import org.jmisb.st1603.localset.TimeTransferMethod;
import org.jmisb.st1603.localset.TimeTransferParameters;
import org.jmisb.st1603.localset.UTCLeapSecondOffset;
import org.jmisb.st1603.nanopack.NanoTimeTransferPack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Generator {

    private static Logger LOG = LoggerFactory.getLogger(Generator.class);
    private static final int SECONDS_TO_NANOSECONDS = 1000 * 1000 * 1000;
    private static final int MILLIS_TO_NANOSECONDS = 1000 * 1000;
    private static final int LEAP_SECONDS = 37;
    private final int width = 1280;
    private final int height = 960;
    private final int bitRate = 500_000;
    private final int gopSize = 30;
    private final double frameRate = 15.0;
    private final int intervalBetweenTimeTransferPacksInSeconds = 3;
    private final double frameDuration = 1.0 / frameRate;
    private final int duration = 60;
    private KlvFormat klvFormat = KlvFormat.Synchronous;
    private CodecIdentifier codec = CodecIdentifier.H264;
    private String filename = "timetransfer.ts";

    public Generator() throws KlvParseException {}

    public void setKlvFormat(KlvFormat klvFormat) {
        this.klvFormat = klvFormat;
    }

    public void setCodec(CodecIdentifier codec) {
        this.codec = codec;
    }

    public void setOutputFile(String filename) {
        this.filename = filename;
    }

    public void generate() throws KlvParseException {
        showConfiguration();
        CoreIdentifier coreIdentifier = new CoreIdentifier();
        coreIdentifier.setMinorUUID(UUID.randomUUID());
        coreIdentifier.setVersion(1);

        try (IVideoFileOutput output =
                new VideoFileOutput(
                        new VideoOutputOptions(
                                width, height, bitRate, frameRate, gopSize, klvFormat, codec))) {
            output.open(filename);

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            try {
                image = ImageIO.read(new File("test1280.jpg"));
            } catch (IOException e) {
                // TODO: log
            }

            final long numFrames = duration * Math.round(frameRate);
            long startTime = System.currentTimeMillis();
            double pts = 1000.0 * System.currentTimeMillis(); // Close enough for this.
            for (long i = 0; i < numFrames; ++i) {
                VideoFrame videoFrame = new VideoFrame(image, pts * 1.0e-6);
                videoFrame.setMiisCoreId(coreIdentifier);
                output.addVideoFrame(videoFrame);
                if (i % (intervalBetweenTimeTransferPacksInSeconds * frameRate) == 0) {
                    Request trackingRequest = new Request();
                    trackingRequest.setCommand(Command.Tracking);
                    byte[] trackingReplyBytes = trackingRequest.sendRequest();
                    Tracking ntpTracking = Tracking.fromBytes(trackingReplyBytes);
                    SortedMap<TimeTransferKey, ITimeTransferValue> values = new TreeMap<>();
                    values.put(TimeTransferKey.DocumentVersion, new ST1603DocumentVersion(2));
                    values.put(
                            TimeTransferKey.UTCLeapSecondOffset,
                            new UTCLeapSecondOffset(LEAP_SECONDS));
                    values.put(
                            TimeTransferKey.TimeTransferParameters,
                            new TimeTransferParameters(
                                    ReferenceSource.Synchronized,
                                    CorrectionMethod.SlewCorrection,
                                    TimeTransferMethod.NetworkTimeProtocolV4));
                    values.put(
                            TimeTransferKey.LastSynchronizationDifference,
                            new LastSynchronizationDifference(
                                    Math.abs(
                                            (long)
                                                    (ntpTracking.getLastOffset()
                                                            * SECONDS_TO_NANOSECONDS))));
                    values.put(
                            TimeTransferKey.SynchronizationPulseFrequency,
                            new SynchronizationPulseFrequency(
                                    1.0 / ntpTracking.getLastUpdateInterval()));
                    TimeTransferLocalSet localSet = new TimeTransferLocalSet(values);
                    long nanosOfEpoch =
                            (System.currentTimeMillis() * MILLIS_TO_NANOSECONDS)
                                    - (LEAP_SECONDS * SECONDS_TO_NANOSECONDS);
                    NanoPrecisionTimeStamp timeStamp = new NanoPrecisionTimeStamp(nanosOfEpoch);
                    NanoTimeTransferPack message = new NanoTimeTransferPack(timeStamp, localSet);
                    output.addMetadataFrame(new MetadataFrame(message, pts));
                }
                pts += frameDuration * 1.0e6;
                long elapsedTime = System.currentTimeMillis() - startTime;
                long requiredElapsedTime = (long) ((i + 1) * frameDuration * 1000.0);
                long waitTime = requiredElapsedTime - elapsedTime;
                if (waitTime > 0) {
                    TimingUtils.shortWait(waitTime);
                }
            }

        } catch (IOException e) {
            LOG.error("Failed to write file", e);
        }
    }

    private void showConfiguration() {
        System.out.println("Generating with configuration:");
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "Generator{"
                + "width="
                + width
                + ", height="
                + height
                + ", bitRate="
                + bitRate
                + ", gopSize="
                + gopSize
                + ", frameRate="
                + frameRate
                + ", frameDuration="
                + frameDuration
                + ", duration="
                + duration
                + ",\nklvFormat="
                + klvFormat
                + ",\nfilename="
                + filename
                + '}';
    }
}
