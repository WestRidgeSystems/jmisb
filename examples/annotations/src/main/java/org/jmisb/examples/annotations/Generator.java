package org.jmisb.examples.annotations;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import org.jmisb.api.klv.st0102.Classification;
import org.jmisb.api.klv.st0102.ISecurityMetadataValue;
import org.jmisb.api.klv.st0102.ObjectCountryCodeString;
import org.jmisb.api.klv.st0102.ST0102Version;
import org.jmisb.api.klv.st0102.SecurityMetadataKey;
import org.jmisb.api.klv.st0102.SecurityMetadataString;
import org.jmisb.api.klv.st0102.universalset.ClassificationUniversal;
import org.jmisb.api.klv.st0102.universalset.SecurityMetadataUniversalSet;
import org.jmisb.api.klv.st0602.ActiveLinesPerFrame;
import org.jmisb.api.klv.st0602.ActiveSamplesPerLine;
import org.jmisb.api.klv.st0602.AnnotationMetadataKey;
import org.jmisb.api.klv.st0602.AnnotationMetadataUniversalSet;
import org.jmisb.api.klv.st0602.AnnotationSource;
import org.jmisb.api.klv.st0602.EventIndication;
import org.jmisb.api.klv.st0602.EventIndicationKind;
import org.jmisb.api.klv.st0602.IAnnotationMetadataValue;
import org.jmisb.api.klv.st0602.LocallyUniqueIdentifier;
import org.jmisb.api.klv.st0602.MIMEData;
import org.jmisb.api.klv.st0602.MIMEMediaType;
import org.jmisb.api.klv.st0602.ModificationHistory;
import org.jmisb.api.klv.st0602.XViewportPosition;
import org.jmisb.api.klv.st0602.YViewportPosition;
import org.jmisb.api.klv.st0602.ZOrder;
import org.jmisb.api.klv.st1204.CoreIdentifier;
import org.jmisb.api.video.CodecIdentifier;
import org.jmisb.api.video.IVideoFileOutput;
import org.jmisb.api.video.KlvFormat;
import org.jmisb.api.video.MetadataFrame;
import org.jmisb.api.video.VideoFileOutput;
import org.jmisb.api.video.VideoFrame;
import org.jmisb.api.video.VideoOutputOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Generator {

    private static final int BOX1_X = 200;
    private static final int BOX1_Y = 100;
    private static final int BOX2_X = 800;
    private static final int BOX2_Y = BOX1_Y;
    private static final int BOX3_X = BOX1_X;
    private static final int BOX3_Y = 600;
    private static final int BOX4_X = BOX2_X;
    private static final int BOX4_Y = BOX3_Y;
    private final LocallyUniqueIdentifier identifierAnnotationBox1 = new LocallyUniqueIdentifier(3);
    private final LocallyUniqueIdentifier identifierAnnotationBox2 = new LocallyUniqueIdentifier(7);
    private final LocallyUniqueIdentifier identifierAnnotationBox3 = new LocallyUniqueIdentifier(6);

    private static final int ANNOTATION_BOX_HEIGHT = 80;
    private static final int ANNOTATION_BOX_WIDTH = 120;

    private final int width = 1280;
    private final int height = 960;
    private final int bitRate = 500_000;
    private final int gopSize = 30;
    private final double frameRate = 15.0;
    private final double frameDuration = 1.0 / frameRate;
    private final int duration = 30;
    private KlvFormat klvFormat = KlvFormat.Synchronous;
    private CodecIdentifier codec = CodecIdentifier.H265;
    private byte version0102 = 12;
    private String filename = "annotations";

    private static final Logger LOG = LoggerFactory.getLogger(Generator.class);

    public Generator() {}

    public void setKlvFormat(KlvFormat klvFormat) {
        this.klvFormat = klvFormat;
    }

    public void setCodec(CodecIdentifier codec) {
        this.codec = codec;
    }

    public void setVersion0102(byte version0102) {
        this.version0102 = version0102;
    }

    public void setOutputFile(String filename) {
        this.filename = filename;
    }

    public void generate() {

        showConfiguration();
        CoreIdentifier coreIdentifier = new CoreIdentifier();
        coreIdentifier.setMinorUUID(UUID.randomUUID());
        coreIdentifier.setVersion(1);

        // TODO: rework to make this a command line option
        try (IVideoFileOutput output =
                new VideoFileOutput(
                        new VideoOutputOptions(
                                width, height, bitRate, frameRate, gopSize, klvFormat, codec))) {
            output.open(filename + "-png.mpeg");

            BufferedImage image0 = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            drawBorderBoxFor(image0.getGraphics(), BOX1_X, BOX1_Y);
            drawBorderBoxFor(image0.getGraphics(), BOX2_X, BOX2_Y);
            drawBorderBoxFor(image0.getGraphics(), BOX3_X, BOX3_Y);
            drawBorderBoxFor(image0.getGraphics(), BOX4_X, BOX4_Y);
            image0.getGraphics()
                    .drawString("Boxes should be empty (black with white border)", 50, height / 2);
            BufferedImage image1 = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            drawFilledBorderedBoxAt(image1.getGraphics(), BOX1_X, BOX1_Y);
            drawFilledBorderedBoxAt(image1.getGraphics(), BOX2_X, BOX2_Y);
            drawBorderBoxFor(image1.getGraphics(), BOX3_X, BOX3_Y);
            drawBorderBoxFor(image1.getGraphics(), BOX4_X, BOX4_Y);
            image1.getGraphics()
                    .drawString(
                            "Top Boxes should be filled (blue with white border), no red visible",
                            50,
                            height / 2);
            BufferedImage image2 = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            drawFilledBorderedBoxAt(image2.getGraphics(), BOX1_X, BOX1_Y);
            drawBorderBoxFor(image2.getGraphics(), BOX2_X, BOX2_Y);
            drawFilledBorderedBoxAt(image2.getGraphics(), BOX3_X, BOX3_Y);
            drawBorderBoxFor(image2.getGraphics(), BOX4_X, BOX4_Y);
            image2.getGraphics()
                    .drawString(
                            "2 left boxes should be filled (blue with white border), right boxes empty, no red visible",
                            50,
                            height / 2);
            BufferedImage image3 = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            drawBorderBoxFor(image3.getGraphics(), BOX1_X, BOX1_Y);
            drawFilledBorderedBoxAt(image3.getGraphics(), BOX2_X, BOX2_Y);
            drawBorderBoxFor(image3.getGraphics(), BOX3_X, BOX3_Y);
            drawFilledBorderedBoxAt(image3.getGraphics(), BOX4_X, BOX4_Y);
            image3.getGraphics()
                    .drawString(
                            "2 right boxes should be filled (blue with white border), left boxes empty, no red visible",
                            50,
                            height / 2);
            final long numFrames = duration * Math.round(frameRate);
            double pts = 1000.0 * System.currentTimeMillis(); // Close enough for this.
            output.addMetadataFrame(new MetadataFrame(getSecurityUniversalSet(), pts * 1.0e-6));
            for (long i = 0; i < numFrames / 4; ++i) {
                output.addVideoFrame(new VideoFrame(image0, pts * 1.0e-6));
                pts += frameDuration * 1.0e6;
            }
            output.addMetadataFrame(new MetadataFrame(getSecurityUniversalSet(), pts * 1.0e-6));
            output.addMetadataFrame(new MetadataFrame(getNewAnnotationMessageBox1(), pts * 1.0e-6));
            output.addMetadataFrame(new MetadataFrame(getNewAnnotationMessageBox2(), pts * 1.0e-6));
            for (long i = numFrames / 4; i < numFrames / 2; ++i) {
                output.addVideoFrame(new VideoFrame(image1, pts * 1.0e-6));
                pts += frameDuration * 1.0e6;
            }
            output.addMetadataFrame(new MetadataFrame(getSecurityUniversalSet(), pts * 1.0e-6));
            // TODO: status on annotation 1
            output.addMetadataFrame(
                    new MetadataFrame(getDeleteAnnotation(identifierAnnotationBox2), pts * 1.0e-6));
            output.addMetadataFrame(new MetadataFrame(getNewAnnotationMessageBox3(), pts * 1.0e-6));
            for (long i = numFrames / 2; i < numFrames * 3 / 4; ++i) {
                output.addVideoFrame(new VideoFrame(image2, pts * 1.0e-6));
                pts += frameDuration * 1.0e6;
            }
            output.addMetadataFrame(
                    new MetadataFrame(getDeleteAnnotation(identifierAnnotationBox1), pts * 1.0e-6));
            output.addMetadataFrame(new MetadataFrame(getNewAnnotationMessageBox2(), pts * 1.0e-6));
            output.addMetadataFrame(new MetadataFrame(getSecurityUniversalSet(), pts * 1.0e-6));
            // TODO: move annotation 3 to box 4 position
            for (long i = numFrames * 3 / 4; i < numFrames; ++i) {
                output.addVideoFrame(new VideoFrame(image3, pts * 1.0e-6));
                pts += frameDuration * 1.0e6;
            }

        } catch (IOException e) {
            LOG.error("Failed to write file", e);
        }
    }

    private void drawFilledBorderedBoxAt(Graphics g1, int x, int y) {
        g1.setColor(Color.RED);
        g1.fillRect(x, y, ANNOTATION_BOX_WIDTH, ANNOTATION_BOX_HEIGHT);
        drawBorderBoxFor(g1, x, y);
    }

    private void drawBorderBoxFor(Graphics g1, int x, int y) {
        g1.setColor(Color.WHITE);
        g1.drawRect(x - 1, y - 1, ANNOTATION_BOX_WIDTH + 2, ANNOTATION_BOX_HEIGHT + 2);
    }

    private void showConfiguration() {
        System.out.println("Generating with configuration:");
        System.out.println(toString());
    }

    private AnnotationMetadataUniversalSet getDeleteAnnotation(
            LocallyUniqueIdentifier locallyUniqueIdentifier) {
        SortedMap<AnnotationMetadataKey, IAnnotationMetadataValue> values = new TreeMap<>();
        values.put(
                AnnotationMetadataKey.EventIndication,
                new EventIndication(EventIndicationKind.DELETE));
        values.put(AnnotationMetadataKey.LocallyUniqueIdentifier, locallyUniqueIdentifier);
        values.put(
                AnnotationMetadataKey.ModificationHistory, new ModificationHistory("Brad Hards"));
        return new AnnotationMetadataUniversalSet(values);
    }

    private AnnotationMetadataUniversalSet getNewAnnotationMessageBox1() {
        return getNewAnnotationBox(
                identifierAnnotationBox1, "Annotation 1", (short) BOX1_X, (short) BOX1_Y);
    }

    private AnnotationMetadataUniversalSet getNewAnnotationMessageBox2() {
        return getNewAnnotationBox(
                identifierAnnotationBox2, "Annotation 2", (short) BOX2_X, (short) BOX2_Y);
    }

    private AnnotationMetadataUniversalSet getNewAnnotationMessageBox3() {
        return getNewAnnotationBox(
                identifierAnnotationBox3, "Annotation 3", (short) BOX3_X, (short) BOX3_Y);
    }

    private AnnotationMetadataUniversalSet getNewAnnotationBox(
            LocallyUniqueIdentifier identifier,
            String annotationText,
            short xPosition,
            short yPosition) {
        SortedMap<AnnotationMetadataKey, IAnnotationMetadataValue> values = new TreeMap<>();
        values.put(
                AnnotationMetadataKey.EventIndication,
                new EventIndication(EventIndicationKind.NEW));
        values.put(AnnotationMetadataKey.LinesPerFrame, new ActiveLinesPerFrame(height));
        values.put(AnnotationMetadataKey.SamplesPerFrame, new ActiveSamplesPerLine(width));
        values.put(AnnotationMetadataKey.LocallyUniqueIdentifier, identifier);
        values.put(AnnotationMetadataKey.AnnotationSource, new AnnotationSource(4));
        values.put(AnnotationMetadataKey.MIMEMediaType, new MIMEMediaType("image/png"));
        values.put(AnnotationMetadataKey.MIMEData, new MIMEData(getAnnotationPNG(annotationText)));
        values.put(
                AnnotationMetadataKey.ModificationHistory, new ModificationHistory("Brad Hards"));
        values.put(AnnotationMetadataKey.XViewportPosition, new XViewportPosition(xPosition));
        values.put(AnnotationMetadataKey.YViewportPosition, new YViewportPosition(yPosition));
        values.put(AnnotationMetadataKey.ZOrder, new ZOrder(1));
        return new AnnotationMetadataUniversalSet(values);
    }

    private byte[] getAnnotationPNG(String annotationText) {
        try {
            BufferedImage bufferedImage =
                    new BufferedImage(
                            ANNOTATION_BOX_WIDTH,
                            ANNOTATION_BOX_HEIGHT,
                            BufferedImage.TYPE_3BYTE_BGR);
            Graphics g = bufferedImage.getGraphics();
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, ANNOTATION_BOX_WIDTH, ANNOTATION_BOX_HEIGHT);
            g.setColor(Color.YELLOW);
            g.drawString(annotationText, ANNOTATION_BOX_WIDTH / 10, ANNOTATION_BOX_HEIGHT / 2);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Generator.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private SecurityMetadataUniversalSet getSecurityUniversalSet() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationUniversal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new SecurityMetadataString(
                        SecurityMetadataString.COUNTRY_CODING_METHOD, "GENC Two Letter"));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AU"));
        values.put(SecurityMetadataKey.Version, new ST0102Version(version0102));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new SecurityMetadataString(
                        SecurityMetadataString.OBJECT_COUNTRY_CODING_METHOD, "GENC Two Letter"));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AU"));
        return new SecurityMetadataUniversalSet(values);
    }
}
