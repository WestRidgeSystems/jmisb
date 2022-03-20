package org.jmisb.examples.annotations;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st1204.CoreIdentifier;
import org.jmisb.api.video.CodecIdentifier;
import org.jmisb.api.video.IVideoFileOutput;
import org.jmisb.api.video.KlvFormat;
import org.jmisb.api.video.MetadataFrame;
import org.jmisb.api.video.VideoFileOutput;
import org.jmisb.api.video.VideoFrame;
import org.jmisb.api.video.VideoOutputOptions;
import org.jmisb.st0102.Classification;
import org.jmisb.st0102.ISecurityMetadataValue;
import org.jmisb.st0102.ObjectCountryCodeString;
import org.jmisb.st0102.ST0102Version;
import org.jmisb.st0102.SecurityMetadataConstants;
import org.jmisb.st0102.SecurityMetadataKey;
import org.jmisb.st0102.SecurityMetadataString;
import org.jmisb.st0102.universalset.ClassificationUniversal;
import org.jmisb.st0102.universalset.SecurityMetadataUniversalSet;
import org.jmisb.st0602.AnnotationMetadataUniversalSet;
import org.jmisb.st0602.AnnotationSource;
import org.jmisb.st0602.layer.Annotation;
import org.jmisb.st0602.layer.AnnotationLayer;
import org.jmisb.st0602.layer.AnnotationLayers;
import org.jmisb.st1301.IMiisMetadataValue;
import org.jmisb.st1301.MiisLocalSet;
import org.jmisb.st1301.MiisMetadataConstants;
import org.jmisb.st1301.MiisMetadataKey;
import org.jmisb.st1301.ST1301CoreIdentifier;
import org.jmisb.st1301.ST1301Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/** Transport Stream generator code for Annotation example. */
public class Generator {

    private static final Logger LOG = LoggerFactory.getLogger(Generator.class);

    private static final int BOX1_X = 200;
    private static final int BOX1_Y = 100;
    private static final int BOX2_X = 800;
    private static final int BOX2_Y = BOX1_Y;
    private static final int BOX3_X = BOX1_X;
    private static final int BOX3_Y = 600;
    private static final int BOX4_X = BOX2_X;
    private static final int BOX4_Y = BOX3_Y;
    private static final int ID_BOX_1 = 3;
    private static final int ID_HIDDEN_BOX_1 = 1;
    private static final int ID_BOX_2 = 7;
    private static final int ID_HIDDEN_BOX_2 = 99;
    private static final int ID_BOX_3 = 6;
    private static final int ID_BOX_4 = 65536;

    private static final int ANNOTATION_BOX_HEIGHT = 80;
    private static final int ANNOTATION_BOX_WIDTH = 120;

    private final int width = 1280;
    private final int height = 960;
    private final int bitRate = 500_000;
    private final int gopSize = 30;
    private final double frameRate = 15.0;
    private final double frameDuration = 1.0 / frameRate;
    private final int duration = 24;
    private KlvFormat klvFormat = KlvFormat.Synchronous;
    private CodecIdentifier codec = CodecIdentifier.H265;
    private byte version0102 = SecurityMetadataConstants.ST_VERSION_NUMBER;
    private String filename = "annotations";
    private final String fileFormat;
    private final UUID minorUUID = UUID.randomUUID();
    private AnnotationLayers layers;
    private AnnotationLayer topLayer;
    private AnnotationLayer lowerLayer;

    /**
     * Constructor.
     *
     * @param fileFormat the file format to use for annotation, in the form of a mime type (e.g.
     *     "image/png").
     */
    public Generator(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    /**
     * Set the KLV format.
     *
     * <p>The default is Synchronous.
     *
     * @param klvFormat the KLV format.
     */
    public void setKlvFormat(KlvFormat klvFormat) {
        this.klvFormat = klvFormat;
    }

    /**
     * Set the video codec.
     *
     * <p>The default is H.265.
     *
     * @param codec the video encoding
     */
    public void setCodec(CodecIdentifier codec) {
        this.codec = codec;
    }

    /**
     * Set the version of ST 0102 to use.
     *
     * <p>The default is the latest available.
     *
     * @param version0102 the ST 0102 version.
     */
    public void setVersion0102(byte version0102) {
        this.version0102 = version0102;
    }

    /**
     * Set the output file base name.
     *
     * <p>The file format for the annotation and .mpeg suffix will be appended.
     *
     * @param filename the output file name
     */
    public void setOutputFile(String filename) {
        this.filename = filename;
    }

    /**
     * Generate the transport stream.
     *
     * @throws KlvParseException if the input values are not valid.
     */
    public void generate() throws KlvParseException {

        showConfiguration();

        switch (fileFormat) {
            case "image/png":
                generateFile(filename + "-png.mpeg");
                break;
            case "image/jpeg":
                generateFile(filename + "-jpeg.mpeg");
                break;
            case "image/x-ms-bmp":
                generateFile(filename + "-bmp.mpeg");
                break;
            case "image/svg+xml":
                generateFile(filename + "-svg.mpeg");
                break;
            default:
                throw new IllegalArgumentException("Unsupport file format");
        }
    }

    private void generateFile(String filename) throws KlvParseException {
        // TODO: rework to make this a command line option
        try (IVideoFileOutput output =
                new VideoFileOutput(
                        new VideoOutputOptions(
                                width, height, bitRate, frameRate, gopSize, klvFormat, codec))) {
            output.open(filename);

            final long numFrames = duration * Math.round(frameRate);
            double pts = 1000.0 * System.currentTimeMillis(); // Close enough for this.

            layers = new AnnotationLayers(this.width, this.height);
            layers.setDefaultAuthor("Brad Hards");
            topLayer = layers.addLayer();
            lowerLayer = layers.addLayer(40);

            pts = addPhase0ToOutput(output, pts, numFrames);
            pts = addPhase1ToOutput(output, pts, numFrames);
            pts = addPhase2ToOutput(output, pts, numFrames);
            pts = addPhase3ToOutput(output, pts, numFrames);
            pts = addPhase4ToOutput(output, pts, numFrames);
            addPhase5ToOutput(output, pts, numFrames);
        } catch (IOException e) {
            LOG.error("Failed to write file", e);
        }
    }

    private double addPhase0ToOutput(
            final IVideoFileOutput output, double pts, final long numFrames) throws IOException {
        output.addMetadataFrame(new MetadataFrame(getMiisLocalSet(), pts * 1.0e-6));
        output.addMetadataFrame(new MetadataFrame(getSecurityUniversalSet(), pts * 1.0e-6));
        BufferedImage image0 = makeBackgroundImage0();
        for (long i = 0; i < numFrames / 6; ++i) {
            VideoFrame videoFrame = new VideoFrame(image0, pts * 1.0e-6);
            output.addVideoFrame(videoFrame);
            pts += frameDuration * 1.0e6;
        }
        return pts;
    }

    private double addPhase1ToOutput(
            final IVideoFileOutput output, double pts, final long numFrames) throws IOException {
        output.addMetadataFrame(new MetadataFrame(getMiisLocalSet(), pts * 1.0e-6));
        output.addMetadataFrame(new MetadataFrame(getSecurityUniversalSet(), pts * 1.0e-6));
        topLayer.addAnnotationEntry(ID_BOX_1, getAnnotation(BOX1_X, BOX1_Y, "Annotation 1"));
        topLayer.addAnnotationEntry(ID_BOX_2, getAnnotation(BOX2_X, BOX2_Y, "Annotation 2"));
        lowerLayer.addAnnotationEntry(ID_HIDDEN_BOX_1, getAnnotation(BOX1_X, BOX1_Y, "Hidden"));
        lowerLayer.addAnnotationEntry(ID_HIDDEN_BOX_2, getAnnotation(BOX2_X, BOX2_Y, "Hidden"));
        for (AnnotationMetadataUniversalSet set : layers.getUniversalSets()) {
            output.addMetadataFrame(new MetadataFrame(set, pts * 1.0e-6));
        }
        BufferedImage image1 = makeBackgroundImage1();
        for (long i = numFrames / 6; i < 2 * numFrames / 6; ++i) {
            output.addVideoFrame(new VideoFrame(image1, pts * 1.0e-6));
            pts += frameDuration * 1.0e6;
        }
        return pts;
    }

    private double addPhase2ToOutput(
            final IVideoFileOutput output, double pts, final long numFrames) throws IOException {
        output.addMetadataFrame(new MetadataFrame(getMiisLocalSet(), pts * 1.0e-6));
        output.addMetadataFrame(new MetadataFrame(getSecurityUniversalSet(), pts * 1.0e-6));
        lowerLayer.deleteAnnotationEntry(ID_HIDDEN_BOX_2);
        topLayer.deleteAnnotationEntry(ID_BOX_2);
        topLayer.addAnnotationEntry(ID_BOX_3, getAnnotation(BOX3_X, BOX3_Y, "Annotation 3"));
        for (AnnotationMetadataUniversalSet set : layers.getUniversalSets()) {
            output.addMetadataFrame(new MetadataFrame(set, pts * 1.0e-6));
        }
        BufferedImage image2 = makeBackgroundImage2();
        for (long i = 2 * numFrames / 6; i < 3 * numFrames / 6; ++i) {
            output.addVideoFrame(new VideoFrame(image2, pts * 1.0e-6));
            pts += frameDuration * 1.0e6;
        }
        return pts;
    }

    private double addPhase3ToOutput(
            final IVideoFileOutput output, double pts, final long numFrames) throws IOException {
        output.addMetadataFrame(new MetadataFrame(getMiisLocalSet(), pts * 1.0e-6));
        lowerLayer.deleteAnnotationEntry(ID_HIDDEN_BOX_1);
        topLayer.deleteAnnotationEntry(ID_BOX_1);
        topLayer.addAnnotationEntry(ID_BOX_2, getAnnotation(BOX2_X, BOX2_Y, "Annotation 2"));
        topLayer.moveAnnotation(ID_BOX_3, BOX4_X, BOX4_Y);
        for (AnnotationMetadataUniversalSet set : layers.getUniversalSets()) {
            output.addMetadataFrame(new MetadataFrame(set, pts * 1.0e-6));
        }
        output.addMetadataFrame(new MetadataFrame(getSecurityUniversalSet(), pts * 1.0e-6));
        BufferedImage image3 = makeBackgroundImage3();
        for (long i = numFrames * 3 / 6; i < numFrames * 4 / 6; ++i) {
            VideoFrame videoFrame = new VideoFrame(image3, pts * 1.0e-6);
            output.addVideoFrame(videoFrame);
            pts += frameDuration * 1.0e6;
        }
        return pts;
    }

    private double addPhase4ToOutput(
            final IVideoFileOutput output, double pts, final long numFrames) throws IOException {
        output.addMetadataFrame(new MetadataFrame(getMiisLocalSet(), pts * 1.0e-6));
        topLayer.addAnnotationEntry(ID_BOX_2, getAnnotation(BOX2_X, BOX2_Y, "Annotation 2"));
        topLayer.addAnnotationEntry(ID_BOX_4, getAnnotation(BOX4_X, BOX4_Y, "Annotation 4"));
        topLayer.addAnnotationEntry(ID_BOX_1, getAnnotation(BOX1_X, BOX1_Y, "Annotation 1"));
        topLayer.modifyAnnotation(
                ID_BOX_3,
                fileFormat,
                getAnnotationDataBytes(fileFormat, "Annotation 3 Mod.", Color.BLUE));
        topLayer.moveAnnotation(ID_BOX_3, BOX3_X, BOX3_Y);
        for (AnnotationMetadataUniversalSet set : layers.getUniversalSets()) {
            output.addMetadataFrame(new MetadataFrame(set, pts * 1.0e-6));
        }
        output.addMetadataFrame(new MetadataFrame(getSecurityUniversalSet(), pts * 1.0e-6));
        BufferedImage image4 = makeBackgroundImage4();
        for (long i = numFrames * 4 / 6; i < numFrames * 5 / 6; ++i) {
            output.addVideoFrame(new VideoFrame(image4, pts * 1.0e-6));
            pts += frameDuration * 1.0e6;
        }
        return pts;
    }

    private void addPhase5ToOutput(final IVideoFileOutput output, double pts, final long numFrames)
            throws IOException {
        output.addMetadataFrame(new MetadataFrame(getMiisLocalSet(), pts * 1.0e-6));
        topLayer.deleteAnnotationEntry(ID_BOX_1);
        topLayer.deleteAnnotationEntry(ID_BOX_2);
        topLayer.deleteAnnotationEntry(ID_BOX_3);
        topLayer.deleteAnnotationEntry(ID_BOX_4);
        for (AnnotationMetadataUniversalSet set : layers.getUniversalSets()) {
            output.addMetadataFrame(new MetadataFrame(set, pts * 1.0e-6));
        }
        output.addMetadataFrame(new MetadataFrame(getSecurityUniversalSet(), pts * 1.0e-6));
        BufferedImage image0 = makeBackgroundImage0();
        for (long i = numFrames * 5 / 6; i < numFrames; ++i) {
            output.addVideoFrame(new VideoFrame(image0, pts * 1.0e-6));
            pts += frameDuration * 1.0e6;
        }
        output.addMetadataFrame(new MetadataFrame(getMiisLocalSet(), pts * 1.0e-6));
    }

    private BufferedImage makeBackgroundImage0() {
        BufferedImage image0 = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = (Graphics2D) image0.getGraphics();
        drawBorderBoxFor(g, BOX1_X, BOX1_Y);
        drawBorderBoxFor(g, BOX2_X, BOX2_Y);
        drawBorderBoxFor(g, BOX3_X, BOX3_Y);
        drawBorderBoxFor(g, BOX4_X, BOX4_Y);
        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g.drawString("Boxes should be empty (black with white border)", 50, height / 2);
        return image0;
    }

    private BufferedImage makeBackgroundImage1() {
        BufferedImage image1 = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = (Graphics2D) image1.getGraphics();
        drawFilledBorderedBoxAt(g, BOX1_X, BOX1_Y);
        drawFilledBorderedBoxAt(g, BOX2_X, BOX2_Y);
        drawBorderBoxFor(g, BOX3_X, BOX3_Y);
        drawBorderBoxFor(g, BOX4_X, BOX4_Y);
        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g.drawString(
                "Top Boxes should be filled (blue with white border), no red or green visible",
                50,
                height / 2);
        return image1;
    }

    private BufferedImage makeBackgroundImage2() {
        BufferedImage image2 = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = (Graphics2D) image2.getGraphics();
        drawFilledBorderedBoxAt(g, BOX1_X, BOX1_Y);
        drawBorderBoxFor(g, BOX2_X, BOX2_Y);
        drawFilledBorderedBoxAt(g, BOX3_X, BOX3_Y);
        drawBorderBoxFor(g, BOX4_X, BOX4_Y);
        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g.drawString(
                "2 left boxes should be filled (blue with white border), right boxes empty, no red or green visible",
                50,
                height / 2);
        return image2;
    }

    private BufferedImage makeBackgroundImage3() {
        BufferedImage image3 = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = (Graphics2D) image3.getGraphics();
        drawBorderBoxFor(g, BOX1_X, BOX1_Y);
        drawFilledBorderedBoxAt(g, BOX2_X, BOX2_Y);
        drawBorderBoxFor(g, BOX3_X, BOX3_Y);
        drawFilledBorderedBoxAt(g, BOX4_X, BOX4_Y);
        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g.drawString(
                "2 right boxes should be filled (blue with white border), left boxes empty, no red  or green visible",
                50,
                height / 2);
        return image3;
    }

    private BufferedImage makeBackgroundImage4() {
        BufferedImage image4 = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = (Graphics2D) image4.getGraphics();
        drawFilledBorderedBoxAt(g, BOX1_X, BOX1_Y);
        drawFilledBorderedBoxAt(g, BOX2_X, BOX2_Y);
        drawFilledBorderedBoxAt(g, BOX3_X, BOX3_Y);
        drawFilledBorderedBoxAt(g, BOX4_X, BOX4_Y);
        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g.drawString(
                "All boxes should be filled (blue with white border), no red or green visible",
                50,
                height / 2);
        return image4;
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

    private Annotation getAnnotation(int xPosition, int yPosition, String annotationText) {
        Annotation annotation = new Annotation(xPosition, yPosition);
        annotation.setMediaType(fileFormat);
        annotation.setSource(AnnotationSource.AUTOMATED_FROM_PIXEL_INTELLIGENCE);
        annotation.setMediaData(getAnnotationDataBytes(fileFormat, annotationText, Color.BLUE));
        return annotation;
    }

    private static byte[] getAnnotationDataBytes(
            String fileFormat, String annotationText, Color fillColour) {
        if (fileFormat.equals("image/svg+xml")) {
            byte[] bytes = getSvgAnnotation(annotationText, fillColour);
            // System.out.println(new String(bytes, StandardCharsets.UTF_8));
            return bytes;
        } else {
            try {
                BufferedImage bufferedImage =
                        new BufferedImage(
                                ANNOTATION_BOX_WIDTH,
                                ANNOTATION_BOX_HEIGHT,
                                BufferedImage.TYPE_3BYTE_BGR);
                Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
                g.setRenderingHint(
                        RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
                g.setColor(fillColour);
                g.fillRect(0, 0, ANNOTATION_BOX_WIDTH, ANNOTATION_BOX_HEIGHT);
                g.setColor(Color.YELLOW);
                g.drawString(annotationText, ANNOTATION_BOX_WIDTH / 10, ANNOTATION_BOX_HEIGHT / 2);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                switch (fileFormat) {
                    case "image/png":
                        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
                        break;
                    case "image/jpeg":
                        ImageIO.write(bufferedImage, "jpeg", byteArrayOutputStream);
                        break;
                    case "image/x-ms-bmp":
                        ImageIO.write(bufferedImage, "bmp", byteArrayOutputStream);
                        break;
                    default:
                        throw new IllegalArgumentException("unsupported file format");
                }
                return byteArrayOutputStream.toByteArray();
            } catch (IOException ex) {
                LOG.error("Failed to build annotation: " + ex.toString());
            }
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

    private MiisLocalSet getMiisLocalSet() {
        Map<MiisMetadataKey, IMiisMetadataValue> map = new HashMap<>();
        CoreIdentifier coreIdentifier = new CoreIdentifier();
        coreIdentifier.setMinorUUID(minorUUID);
        coreIdentifier.setVersion(1);
        map.put(
                MiisMetadataKey.Version,
                new ST1301Version(MiisMetadataConstants.ST_VERSION_NUMBER));
        map.put(MiisMetadataKey.CoreIdentifier, new ST1301CoreIdentifier(coreIdentifier));
        MiisLocalSet localSet = new MiisLocalSet(map);
        return localSet;
    }

    private static byte[] getSvgAnnotation(String annotationText, Color fillColour) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("svg");
            rootElement.setAttribute("version", "1.1");
            rootElement.setAttribute("baseProfile", "full");
            // rootElement.setAttribute("viewBox", "0 0 1200 800");
            doc.appendChild(rootElement);
            Element description = doc.createElement("desc");
            description.setTextContent("Visual annotation for ST 0602.5");
            rootElement.appendChild(description);
            rootElement.appendChild(getSvgRectangle(doc, fillColour));
            rootElement.appendChild(getSvgText(doc, annotationText));
            try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(output);
                transformer.transform(source, result);
                return output.toByteArray();
            } catch (IOException | TransformerException ex) {
                // TODO: log and return
                ex.printStackTrace();
            }
        } catch (ParserConfigurationException ex) {
            // TODO: log and return
            ex.printStackTrace();
        }
        return null;
    }

    private static Element getSvgRectangle(Document document, Color fillColour) {
        Element element = document.createElement("rect");
        element.setAttribute("x", "0");
        element.setAttribute("y", "0");
        element.setAttribute("width", Integer.toString(ANNOTATION_BOX_WIDTH));
        element.setAttribute("height", Integer.toString(ANNOTATION_BOX_HEIGHT));
        element.setAttribute(
                "fill",
                String.format(
                        "rgb(%d,%d,%d)",
                        fillColour.getRed(), fillColour.getGreen(), fillColour.getBlue()));
        return element;
    }

    private static Element getSvgText(Document document, String annotationText) {
        Element element = document.createElement("text");
        element.setAttribute("fill", "yellow");
        element.setAttribute("x", Integer.toString(ANNOTATION_BOX_WIDTH / 10));
        element.setAttribute("y", Integer.toString(ANNOTATION_BOX_HEIGHT / 2));
        element.setTextContent(annotationText);
        return element;
    }
}
