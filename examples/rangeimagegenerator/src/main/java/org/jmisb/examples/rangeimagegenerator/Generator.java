package org.jmisb.examples.rangeimagegenerator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st1204.CoreIdentifier;
import org.jmisb.api.video.CodecIdentifier;
import org.jmisb.api.video.IVideoFileOutput;
import org.jmisb.api.video.KlvFormat;
import org.jmisb.api.video.MetadataFrame;
import org.jmisb.api.video.VideoFileOutput;
import org.jmisb.api.video.VideoFrame;
import org.jmisb.api.video.VideoOutputOptions;
import org.jmisb.core.video.TimingUtils;
import org.jmisb.st1002.GeneralizedTransformation;
import org.jmisb.st1002.IRangeImageMetadataValue;
import org.jmisb.st1002.RangeImageCompressionMethod;
import org.jmisb.st1002.RangeImageEnumerations;
import org.jmisb.st1002.RangeImageLocalSet;
import org.jmisb.st1002.RangeImageMetadataKey;
import org.jmisb.st1002.RangeImageSource;
import org.jmisb.st1002.RangeImageryDataType;
import org.jmisb.st1002.ST1002PrecisionTimeStamp;
import org.jmisb.st1002.ST1002VersionNumber;
import org.jmisb.st1002.SinglePointRangeMeasurement;
import org.jmisb.st1002.SinglePointRangeMeasurementColumn;
import org.jmisb.st1002.SinglePointRangeMeasurementRow;
import org.jmisb.st1010.SDCC;
import org.jmisb.st1202.Denominator_X;
import org.jmisb.st1202.Denominator_Y;
import org.jmisb.st1202.GeneralizedTransformationLocalSet;
import org.jmisb.st1202.GeneralizedTransformationParametersKey;
import org.jmisb.st1202.IGeneralizedTransformationMetadataValue;
import org.jmisb.st1202.SDCC_FLP;
import org.jmisb.st1202.ST1202DocumentVersion;
import org.jmisb.st1202.TransformationEnumeration;
import org.jmisb.st1202.X_Numerator_Constant;
import org.jmisb.st1202.X_Numerator_X;
import org.jmisb.st1202.X_Numerator_Y;
import org.jmisb.st1202.Y_Numerator_Constant;
import org.jmisb.st1202.Y_Numerator_X;
import org.jmisb.st1202.Y_Numerator_Y;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Generator {

    private static Logger LOG = LoggerFactory.getLogger(Generator.class);
    private final int width = 1280;
    private final int height = 960;
    private final int bitRate = 500_000;
    private final int gopSize = 30;
    private final double frameRate = 15.0;
    private final double frameDuration = 1.0 / frameRate;
    private final int duration = 60;
    private KlvFormat klvFormat = KlvFormat.Synchronous;
    private CodecIdentifier codec = CodecIdentifier.H264;
    private String filename = "rangeimage.ts";

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
                output.addVideoFrame(new VideoFrame(image, pts * 1.0e-6));
                SortedMap<RangeImageMetadataKey, IRangeImageMetadataValue> values = new TreeMap<>();
                values.put(
                        RangeImageMetadataKey.PrecisionTimeStamp,
                        new ST1002PrecisionTimeStamp((long) pts));
                values.put(RangeImageMetadataKey.DocumentVersion, new ST1002VersionNumber(2));
                values.put(
                        RangeImageMetadataKey.RangeImageEnumerations,
                        new RangeImageEnumerations(
                                RangeImageCompressionMethod.NO_COMPRESSION,
                                RangeImageryDataType.PERSPECTIVE,
                                RangeImageSource.RANGE_SENSOR));
                values.put(
                        RangeImageMetadataKey.SinglePointRangeMeasurement,
                        new SinglePointRangeMeasurement(8000));
                values.put(
                        RangeImageMetadataKey.SinglePointRangeMeasurementRowCoordinate,
                        new SinglePointRangeMeasurementRow(403));
                values.put(
                        RangeImageMetadataKey.SinglePointRangeMeasurementColumnCoordinate,
                        new SinglePointRangeMeasurementColumn(803));
                Map<GeneralizedTransformationParametersKey, IGeneralizedTransformationMetadataValue>
                        st1202values = new TreeMap<>();
                st1202values.put(
                        GeneralizedTransformationParametersKey.X_Numerator_x, new X_Numerator_X(0));
                st1202values.put(
                        GeneralizedTransformationParametersKey.X_Numerator_y, new X_Numerator_Y(0));
                st1202values.put(
                        GeneralizedTransformationParametersKey.X_Numerator_Constant,
                        new X_Numerator_Constant(0));
                st1202values.put(
                        GeneralizedTransformationParametersKey.Y_Numerator_x, new Y_Numerator_X(0));
                st1202values.put(
                        GeneralizedTransformationParametersKey.Y_Numerator_y, new Y_Numerator_Y(0));
                st1202values.put(
                        GeneralizedTransformationParametersKey.Y_Numerator_Constant,
                        new Y_Numerator_Constant(0));
                st1202values.put(
                        GeneralizedTransformationParametersKey.Denominator_x, new Denominator_X(0));
                st1202values.put(
                        GeneralizedTransformationParametersKey.Denominator_y, new Denominator_Y(0));
                st1202values.put(
                        GeneralizedTransformationParametersKey.DocumentVersion,
                        new ST1202DocumentVersion(2));
                SDCC sdcc = new SDCC();
                sdcc.setValues(
                        new double[][] {
                            {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                            {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                            {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                            {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                            {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                            {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                            {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                            {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}
                        });
                st1202values.put(GeneralizedTransformationParametersKey.SDCC, new SDCC_FLP(sdcc));
                st1202values.put(
                        GeneralizedTransformationParametersKey.TransformationEnumeration,
                        TransformationEnumeration.CHILD_PARENT);
                values.put(
                        RangeImageMetadataKey.GeneralizedTransformationLocalSet,
                        new GeneralizedTransformation(
                                new GeneralizedTransformationLocalSet(st1202values)));
                RangeImageLocalSet localSet = new RangeImageLocalSet(values);
                output.addMetadataFrame(new MetadataFrame(localSet, pts));
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
