package org.jmisb.api.awt.st0602;

import static org.testng.Assert.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
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
import org.jmisb.st0602.ActiveLinesPerFrame;
import org.jmisb.st0602.ActiveSamplesPerLine;
import org.jmisb.st0602.AnnotationMetadataKey;
import org.jmisb.st0602.AnnotationMetadataUniversalSet;
import org.jmisb.st0602.AnnotationSource;
import org.jmisb.st0602.EventIndication;
import org.jmisb.st0602.EventIndicationKind;
import org.jmisb.st0602.IAnnotationMetadataValue;
import org.jmisb.st0602.LocallyUniqueIdentifier;
import org.jmisb.st0602.MIMEData;
import org.jmisb.st0602.MIMEMediaType;
import org.jmisb.st0602.ModificationHistory;
import org.jmisb.st0602.XViewportPosition;
import org.jmisb.st0602.YViewportPosition;
import org.jmisb.st0602.ZOrder;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Unit tests for AnnotationsRenderer.
 *
 * <p>These tests do not check for visual results, which should be inspected using the viewer
 * application and representative test data.
 */
public class AnnotationsRendererTest {

    private static final int IMAGE_HEIGHT = 1080;
    private static final int IMAGE_WIDTH = 1920;
    private static final short X_POSITION = 100;
    private static final int ANNOTATION_WIDTH = 150;
    private static final short Y_POSITION = 200;
    private static final int ANNOTATION_HEIGHT = 250;
    private static final int Z_ORDER = 10;

    public AnnotationsRendererTest() {}

    @Test
    public void checkRenderEmpty() {
        AnnotationsRenderer renderer = new AnnotationsRenderer();
        BufferedImage image =
                new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        renderer.render(image.createGraphics());
        assertNotNull(image);
    }

    @Test
    public void checkRenderAfterClear() {
        AnnotationsRenderer renderer = new AnnotationsRenderer();
        renderer.clear();
        BufferedImage image =
                new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        renderer.render(image.createGraphics());
        assertNotNull(image);
    }

    @Test
    public void checkAddMetadataSetBMP() throws KlvParseException, IOException {
        AnnotationsRenderer renderer = new AnnotationsRenderer();
        renderer.updateSet(createNewMetadataSet("bmp"));
        BufferedImage image =
                new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(Color.YELLOW);
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        renderer.render(image.createGraphics());
        assertNotNull(image);
        for (int y = Y_POSITION; y < Y_POSITION + ANNOTATION_HEIGHT; y++) {
            for (int x = X_POSITION; x < X_POSITION + ANNOTATION_WIDTH; x++) {
                assertEquals(image.getRGB(x, y), Color.GREEN.getRGB());
            }
        }
    }

    @Test
    public void checkAddMetadataSetJPEG() throws KlvParseException, IOException {
        AnnotationsRenderer renderer = new AnnotationsRenderer();
        renderer.updateSet(createNewMetadataSet("jpeg"));
        BufferedImage image =
                new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(Color.YELLOW);
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        renderer.render(image.createGraphics());
        assertNotNull(image);
        for (int y = Y_POSITION; y < Y_POSITION + ANNOTATION_HEIGHT; y++) {
            for (int x = X_POSITION; x < X_POSITION + ANNOTATION_WIDTH; x++) {
                assertTrue(Math.abs(image.getRGB(x, y) - Color.GREEN.getRGB()) < 3);
            }
        }
    }

    @Test
    public void checkAddMetadataSetPNG() throws KlvParseException, IOException {
        AnnotationsRenderer renderer = new AnnotationsRenderer();
        renderer.updateSet(createNewMetadataSet("png"));
        BufferedImage image =
                new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(Color.YELLOW);
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        renderer.render(image.createGraphics());
        assertNotNull(image);
        for (int y = Y_POSITION; y < Y_POSITION + ANNOTATION_HEIGHT; y++) {
            for (int x = X_POSITION; x < X_POSITION + ANNOTATION_WIDTH; x++) {
                assertEquals(image.getRGB(x, y), Color.GREEN.getRGB());
            }
        }
    }

    @Test
    public void checkAddMetadataSetPNGNoMediaType() throws KlvParseException, IOException {
        AnnotationsRenderer renderer = new AnnotationsRenderer();
        renderer.updateSet(createNewMetadataSetNoMediaType("png"));
        BufferedImage image =
                new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(Color.YELLOW);
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        renderer.render(image.createGraphics());
        assertNotNull(image);
        for (int y = Y_POSITION; y < Y_POSITION + ANNOTATION_HEIGHT; y++) {
            for (int x = X_POSITION; x < X_POSITION + ANNOTATION_WIDTH; x++) {
                assertEquals(image.getRGB(x, y), Color.YELLOW.getRGB());
            }
        }
    }

    @Test
    public void checkAddMetadataSetPNGNoEventIndication() throws KlvParseException, IOException {
        AnnotationsRenderer renderer = new AnnotationsRenderer();
        renderer.updateSet(createMetadataSetNoEventIndication("png"));
        BufferedImage image =
                new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(Color.YELLOW);
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        renderer.render(image.createGraphics());
        assertNotNull(image);
        for (int y = Y_POSITION; y < Y_POSITION + ANNOTATION_HEIGHT; y++) {
            for (int x = X_POSITION; x < X_POSITION + ANNOTATION_WIDTH; x++) {
                assertEquals(image.getRGB(x, y), Color.YELLOW.getRGB());
            }
        }
    }

    @Test
    public void checkAddMetadataSetPNGUnknownEventIndication()
            throws KlvParseException, IOException {
        AnnotationsRenderer renderer = new AnnotationsRenderer();
        renderer.updateSet(createMetadataSetUnknownEventIndication("png"));
        BufferedImage image =
                new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(Color.YELLOW);
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        renderer.render(image.createGraphics());
        assertNotNull(image);
        for (int y = Y_POSITION; y < Y_POSITION + ANNOTATION_HEIGHT; y++) {
            for (int x = X_POSITION; x < X_POSITION + ANNOTATION_WIDTH; x++) {
                assertEquals(image.getRGB(x, y), Color.YELLOW.getRGB());
            }
        }
    }

    @Test
    public void checkAddMetadataSetPNGNoUniqueId() throws KlvParseException, IOException {
        AnnotationsRenderer renderer = new AnnotationsRenderer();
        renderer.updateSet(createNewMetadataSetNoUniqueId("png"));
        BufferedImage image =
                new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(Color.YELLOW);
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        renderer.render(image.createGraphics());
        assertNotNull(image);
        for (int y = Y_POSITION; y < Y_POSITION + ANNOTATION_HEIGHT; y++) {
            for (int x = X_POSITION; x < X_POSITION + ANNOTATION_WIDTH; x++) {
                assertEquals(image.getRGB(x, y), Color.YELLOW.getRGB());
            }
        }
    }

    @Test
    public void checkAddMetadataSetPNGNoData() throws KlvParseException, IOException {
        AnnotationsRenderer renderer = new AnnotationsRenderer();
        renderer.updateSet(createNewMetadataSetNoData("png"));
        BufferedImage image =
                new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(Color.YELLOW);
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        renderer.render(image.createGraphics());
        assertNotNull(image);
        for (int y = Y_POSITION; y < Y_POSITION + ANNOTATION_HEIGHT; y++) {
            for (int x = X_POSITION; x < X_POSITION + ANNOTATION_WIDTH; x++) {
                assertEquals(image.getRGB(x, y), Color.YELLOW.getRGB());
            }
        }
    }

    @Test
    public void checkAddMetadataSetCGM() throws KlvParseException, IOException {
        AnnotationsRenderer renderer = new AnnotationsRenderer();
        renderer.updateSet(createNewMetadataSetCGM());
        BufferedImage image =
                new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(Color.YELLOW);
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        renderer.render(image.createGraphics());
        assertNotNull(image);
        // TODO: this will need to be updated when we can render CGM
        // For now, it just checks that we didn't break stuff.
        for (int y = Y_POSITION; y < Y_POSITION + ANNOTATION_HEIGHT; y++) {
            for (int x = X_POSITION; x < X_POSITION + ANNOTATION_WIDTH; x++) {
                assertEquals(image.getRGB(x, y), Color.YELLOW.getRGB());
            }
        }
    }

    @Test
    public void checkModifyMetadataSet() throws KlvParseException, IOException {
        AnnotationsRenderer renderer = new AnnotationsRenderer();
        renderer.updateSet(createNewMetadataSet("bmp"));
        renderer.updateSet(createModifyMetadataSet());
        BufferedImage image =
                new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(Color.YELLOW);
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        renderer.render(image.createGraphics());
        assertNotNull(image);
        for (int y = Y_POSITION; y < Y_POSITION + ANNOTATION_HEIGHT; y++) {
            for (int x = X_POSITION; x < X_POSITION + ANNOTATION_WIDTH; x++) {
                assertEquals(image.getRGB(x, y), Color.BLUE.getRGB());
            }
        }
    }

    @Test
    public void checkModifyWithoutNewMetadataSet() throws KlvParseException, IOException {
        AnnotationsRenderer renderer = new AnnotationsRenderer();
        renderer.updateSet(createModifyMetadataSet());
        BufferedImage image =
                new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(Color.YELLOW);
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        renderer.render(image.createGraphics());
        assertNotNull(image);
        for (int y = Y_POSITION; y < Y_POSITION + ANNOTATION_HEIGHT; y++) {
            for (int x = X_POSITION; x < X_POSITION + ANNOTATION_WIDTH; x++) {
                assertEquals(image.getRGB(x, y), Color.BLUE.getRGB());
            }
        }
    }

    @Test
    public void checkDeleteMetadataSet() throws KlvParseException, IOException {
        AnnotationsRenderer renderer = new AnnotationsRenderer();
        renderer.updateSet(createNewMetadataSet("bmp"));
        renderer.updateSet(createDeleteMetadataSet());
        BufferedImage image =
                new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(Color.YELLOW);
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        renderer.render(image.createGraphics());
        assertNotNull(image);
        for (int y = Y_POSITION; y < Y_POSITION + ANNOTATION_HEIGHT; y++) {
            for (int x = X_POSITION; x < X_POSITION + ANNOTATION_WIDTH; x++) {
                assertEquals(image.getRGB(x, y), Color.YELLOW.getRGB());
            }
        }
    }

    private AnnotationMetadataUniversalSet createNewMetadataSetCGM()
            throws KlvParseException, IOException {
        Map<AnnotationMetadataKey, IAnnotationMetadataValue> values = new HashMap<>();
        values.put(
                AnnotationMetadataKey.EventIndication,
                new EventIndication(EventIndicationKind.NEW));
        values.put(
                AnnotationMetadataKey.ActiveLinesPerFrame, new ActiveLinesPerFrame(IMAGE_HEIGHT));
        values.put(
                AnnotationMetadataKey.ActiveSamplesPerLine, new ActiveSamplesPerLine(IMAGE_WIDTH));
        values.put(AnnotationMetadataKey.LocallyUniqueIdentifier, new LocallyUniqueIdentifier(1));
        values.put(
                AnnotationMetadataKey.AnnotationSource,
                new AnnotationSource(AnnotationSource.AUTOMATED_FROM_USER_AOI));
        values.put(AnnotationMetadataKey.MIMEMediaType, new MIMEMediaType(MIMEMediaType.CGM));
        values.put(AnnotationMetadataKey.MIMEData, new MIMEData(getCGMAnnotation(Color.GREEN)));
        values.put(
                AnnotationMetadataKey.ModificationHistory, new ModificationHistory("Brad Hards"));
        values.put(AnnotationMetadataKey.XViewportPosition, new XViewportPosition(X_POSITION));
        values.put(AnnotationMetadataKey.YViewportPosition, new YViewportPosition(Y_POSITION));
        values.put(AnnotationMetadataKey.ZOrder, new ZOrder(Z_ORDER));
        AnnotationMetadataUniversalSet set1 = new AnnotationMetadataUniversalSet(values);
        return set1;
    }

    private AnnotationMetadataUniversalSet createNewMetadataSet(String format)
            throws KlvParseException, IOException {
        Map<AnnotationMetadataKey, IAnnotationMetadataValue> values =
                getValuesForNewMetadataSet(format);
        return new AnnotationMetadataUniversalSet(values);
    }

    private Map<AnnotationMetadataKey, IAnnotationMetadataValue> getValuesForNewMetadataSet(
            String format) throws IOException, KlvParseException {
        Map<AnnotationMetadataKey, IAnnotationMetadataValue> values = new HashMap<>();
        values.put(
                AnnotationMetadataKey.EventIndication,
                new EventIndication(EventIndicationKind.NEW));
        values.put(
                AnnotationMetadataKey.ActiveLinesPerFrame, new ActiveLinesPerFrame(IMAGE_HEIGHT));
        values.put(
                AnnotationMetadataKey.ActiveSamplesPerLine, new ActiveSamplesPerLine(IMAGE_WIDTH));
        values.put(AnnotationMetadataKey.LocallyUniqueIdentifier, new LocallyUniqueIdentifier(1));
        values.put(
                AnnotationMetadataKey.AnnotationSource,
                new AnnotationSource(AnnotationSource.AUTOMATED_FROM_USER_AOI));
        switch (format) {
            case "png":
                values.put(
                        AnnotationMetadataKey.MIMEMediaType, new MIMEMediaType(MIMEMediaType.PNG));
                break;
            case "bmp":
                values.put(
                        AnnotationMetadataKey.MIMEMediaType, new MIMEMediaType(MIMEMediaType.BMP));
                break;
            case "jpeg":
                values.put(
                        AnnotationMetadataKey.MIMEMediaType, new MIMEMediaType(MIMEMediaType.JPEG));
                break;
            default:
                break;
        }
        values.put(
                AnnotationMetadataKey.MIMEData, new MIMEData(getAnnotation(format, Color.GREEN)));
        values.put(
                AnnotationMetadataKey.ModificationHistory, new ModificationHistory("Brad Hards"));
        values.put(AnnotationMetadataKey.XViewportPosition, new XViewportPosition(X_POSITION));
        values.put(AnnotationMetadataKey.YViewportPosition, new YViewportPosition(Y_POSITION));
        values.put(AnnotationMetadataKey.ZOrder, new ZOrder(Z_ORDER));
        return values;
    }

    private AnnotationMetadataUniversalSet createNewMetadataSetNoMediaType(String format)
            throws KlvParseException, IOException {
        Map<AnnotationMetadataKey, IAnnotationMetadataValue> values =
                getValuesForNewMetadataSet(format);
        values.remove(AnnotationMetadataKey.MIMEMediaType);
        return new AnnotationMetadataUniversalSet(values);
    }

    private AnnotationMetadataUniversalSet createNewMetadataSetNoData(String format)
            throws KlvParseException, IOException {
        Map<AnnotationMetadataKey, IAnnotationMetadataValue> values =
                getValuesForNewMetadataSet(format);
        values.remove(AnnotationMetadataKey.MIMEData);
        return new AnnotationMetadataUniversalSet(values);
    }

    private AnnotationMetadataUniversalSet createNewMetadataSetNoUniqueId(String format)
            throws KlvParseException, IOException {
        Map<AnnotationMetadataKey, IAnnotationMetadataValue> values =
                getValuesForNewMetadataSet(format);
        values.remove(AnnotationMetadataKey.LocallyUniqueIdentifier);
        return new AnnotationMetadataUniversalSet(values);
    }

    private AnnotationMetadataUniversalSet createMetadataSetNoEventIndication(String format)
            throws KlvParseException, IOException {
        Map<AnnotationMetadataKey, IAnnotationMetadataValue> values =
                getValuesForNewMetadataSet(format);
        values.remove(AnnotationMetadataKey.EventIndication);
        return new AnnotationMetadataUniversalSet(values);
    }

    private AnnotationMetadataUniversalSet createMetadataSetUnknownEventIndication(String format)
            throws KlvParseException, IOException {
        Map<AnnotationMetadataKey, IAnnotationMetadataValue> values =
                getValuesForNewMetadataSet(format);
        values.remove(AnnotationMetadataKey.EventIndication);
        values.put(
                AnnotationMetadataKey.EventIndication,
                new EventIndication(EventIndicationKind.UNKNOWN));
        return new AnnotationMetadataUniversalSet(values);
    }

    @Test
    public void checkAddSVGMetadataSets() throws KlvParseException, IOException {
        AnnotationsRenderer renderer = new AnnotationsRenderer();
        renderer.updateSet(createNewMetadataSetSVG(1, 30, Color.GREEN));
        renderer.updateSet(createNewMetadataSetSVG(2, 20, Color.BLUE));
        BufferedImage image =
                new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(Color.YELLOW);
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        renderer.render(image.createGraphics());
        assertNotNull(image);
        for (int y = Y_POSITION; y < Y_POSITION + ANNOTATION_HEIGHT; y++) {
            for (int x = X_POSITION; x < X_POSITION + ANNOTATION_WIDTH; x++) {
                assertEquals(image.getRGB(x, y), Color.GREEN.getRGB());
            }
        }
    }

    @Test
    public void checkAddSVGMetadataSetNoData() throws KlvParseException, IOException {
        AnnotationsRenderer renderer = new AnnotationsRenderer();
        renderer.updateSet(createNewMetadataSetSVGNoData(1, 30, Color.GREEN));
        BufferedImage image =
                new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(Color.YELLOW);
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        renderer.render(image.createGraphics());
        assertNotNull(image);
        for (int y = Y_POSITION; y < Y_POSITION + ANNOTATION_HEIGHT; y++) {
            for (int x = X_POSITION; x < X_POSITION + ANNOTATION_WIDTH; x++) {
                assertEquals(image.getRGB(x, y), Color.YELLOW.getRGB());
            }
        }
    }

    @Test
    public void checkAddSVGMetadataSetBadData() throws KlvParseException, IOException {
        AnnotationsRenderer renderer = new AnnotationsRenderer();
        renderer.updateSet(createNewMetadataSetSVGBadData(1, 30, Color.GREEN));
        BufferedImage image =
                new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(Color.YELLOW);
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        renderer.render(image.createGraphics());
        assertNotNull(image);
        for (int y = Y_POSITION; y < Y_POSITION + ANNOTATION_HEIGHT; y++) {
            for (int x = X_POSITION; x < X_POSITION + ANNOTATION_WIDTH; x++) {
                assertEquals(image.getRGB(x, y), Color.YELLOW.getRGB());
            }
        }
        // verifySingleLoggerMessage("blah");
    }

    private AnnotationMetadataUniversalSet createNewMetadataSetSVG(int id, int zorder, Color color)
            throws KlvParseException, IOException {
        Map<AnnotationMetadataKey, IAnnotationMetadataValue> values =
                getValuesForSVG(id, color, zorder);
        return new AnnotationMetadataUniversalSet(values);
    }

    private AnnotationMetadataUniversalSet createNewMetadataSetSVGNoData(
            int id, int zorder, Color color) throws KlvParseException, IOException {
        Map<AnnotationMetadataKey, IAnnotationMetadataValue> values =
                getValuesForSVG(id, color, zorder);
        values.remove(AnnotationMetadataKey.MIMEData);
        return new AnnotationMetadataUniversalSet(values);
    }

    private AnnotationMetadataUniversalSet createNewMetadataSetSVGBadData(
            int id, int zorder, Color color) throws KlvParseException, IOException {
        Map<AnnotationMetadataKey, IAnnotationMetadataValue> values =
                getValuesForSVG(id, color, zorder);
        values.remove(AnnotationMetadataKey.MIMEData);
        values.put(
                AnnotationMetadataKey.MIMEData,
                new MIMEData("This isn't SVG+XML...".getBytes(StandardCharsets.UTF_8)));
        return new AnnotationMetadataUniversalSet(values);
    }

    private Map<AnnotationMetadataKey, IAnnotationMetadataValue> getValuesForSVG(
            int id, Color color, int zorder) throws IOException, KlvParseException {
        Map<AnnotationMetadataKey, IAnnotationMetadataValue> values = new HashMap<>();
        values.put(
                AnnotationMetadataKey.EventIndication,
                new EventIndication(EventIndicationKind.NEW));
        values.put(
                AnnotationMetadataKey.ActiveLinesPerFrame, new ActiveLinesPerFrame(IMAGE_HEIGHT));
        values.put(
                AnnotationMetadataKey.ActiveSamplesPerLine, new ActiveSamplesPerLine(IMAGE_WIDTH));
        values.put(AnnotationMetadataKey.LocallyUniqueIdentifier, new LocallyUniqueIdentifier(id));
        values.put(
                AnnotationMetadataKey.AnnotationSource,
                new AnnotationSource(AnnotationSource.AUTOMATED_FROM_USER_AOI));
        values.put(AnnotationMetadataKey.MIMEMediaType, new MIMEMediaType(MIMEMediaType.SVG));
        values.put(AnnotationMetadataKey.MIMEData, new MIMEData(getSVGAnnotation(color)));
        values.put(
                AnnotationMetadataKey.ModificationHistory, new ModificationHistory("Brad Hards"));
        values.put(AnnotationMetadataKey.XViewportPosition, new XViewportPosition(X_POSITION));
        values.put(AnnotationMetadataKey.YViewportPosition, new YViewportPosition(Y_POSITION));
        values.put(AnnotationMetadataKey.ZOrder, new ZOrder(zorder));
        return values;
    }

    private AnnotationMetadataUniversalSet createModifyMetadataSet()
            throws KlvParseException, IOException {
        Map<AnnotationMetadataKey, IAnnotationMetadataValue> values = new HashMap<>();
        values.put(
                AnnotationMetadataKey.EventIndication,
                new EventIndication(EventIndicationKind.MODIFY));
        values.put(AnnotationMetadataKey.LocallyUniqueIdentifier, new LocallyUniqueIdentifier(1));
        values.put(AnnotationMetadataKey.MIMEMediaType, new MIMEMediaType(MIMEMediaType.BMP));
        values.put(AnnotationMetadataKey.MIMEData, new MIMEData(getAnnotation("bmp", Color.BLUE)));
        values.put(
                AnnotationMetadataKey.ModificationHistory, new ModificationHistory("Brad Hards"));
        values.put(AnnotationMetadataKey.XViewportPosition, new XViewportPosition(X_POSITION));
        values.put(AnnotationMetadataKey.YViewportPosition, new YViewportPosition(Y_POSITION));
        values.put(AnnotationMetadataKey.ZOrder, new ZOrder(Z_ORDER));
        return new AnnotationMetadataUniversalSet(values);
    }

    private AnnotationMetadataUniversalSet createDeleteMetadataSet()
            throws KlvParseException, IOException {
        Map<AnnotationMetadataKey, IAnnotationMetadataValue> values = new HashMap<>();
        values.put(
                AnnotationMetadataKey.EventIndication,
                new EventIndication(EventIndicationKind.DELETE));
        values.put(AnnotationMetadataKey.LocallyUniqueIdentifier, new LocallyUniqueIdentifier(1));
        values.put(
                AnnotationMetadataKey.ModificationHistory, new ModificationHistory("Brad Hards"));
        return new AnnotationMetadataUniversalSet(values);
    }

    private byte[] getAnnotation(String format, Color color) throws IOException {
        BufferedImage bufferedImage =
                new BufferedImage(
                        ANNOTATION_WIDTH, ANNOTATION_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g.setColor(color);
        g.fillRect(0, 0, ANNOTATION_WIDTH, ANNOTATION_HEIGHT);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, format, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private byte[] getCGMAnnotation(Color color) throws IOException {
        // TODO: this is just a placeholder since we can't do real CGM yet.
        // Still useful as a test to make sure we don't crash.
        return new byte[0];
    }

    private byte[] getSVGAnnotation(Color color) throws IOException {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("svg");
            rootElement.setAttribute("version", "1.1");
            rootElement.setAttribute("baseProfile", "full");
            doc.appendChild(rootElement);
            Element description = doc.createElement("desc");
            description.setTextContent("Visual annotation for ST 0602.5");
            rootElement.appendChild(description);
            rootElement.appendChild(getSvgRectangle(doc, color));
            try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(output);
                transformer.transform(source, result);
                return output.toByteArray();
            } catch (IOException | TransformerException ex) {
                throw new IOException(ex.getMessage());
            }
        } catch (ParserConfigurationException ex) {
            // TODO: log and return
            throw new IOException(ex.getMessage());
        }
    }

    private Element getSvgRectangle(Document document, Color fillColour) {
        Element element = document.createElement("rect");
        element.setAttribute("x", "0");
        element.setAttribute("y", "0");
        element.setAttribute("width", Integer.toString(ANNOTATION_WIDTH));
        element.setAttribute("height", Integer.toString(ANNOTATION_HEIGHT));
        element.setAttribute(
                "fill",
                String.format(
                        "rgb(%d,%d,%d)",
                        fillColour.getRed(), fillColour.getGreen(), fillColour.getBlue()));
        return element;
    }

    /*
        @Test
        public void checkRenderMost() {
            OverlayRenderer renderer = new OverlayRenderer(new OverlayRendererConfiguration());
            BufferedImage image = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
            MetadataItems metadataItems = new MetadataItems();
            metadataItems.addItem(MetadataKey.AzAngle, "1.2");
            metadataItems.addItem(MetadataKey.ElAngle, "3.2");
            metadataItems.addItem(MetadataKey.ClassificationAndReleasabilityLine1, "L1");
            metadataItems.addItem(MetadataKey.HorizontalFOV, "0.1");
            metadataItems.addItem(MetadataKey.VerticalFOV, "0.2");
            metadataItems.addItem(MetadataKey.LaserPrfCode, "Laser PRF 1234");
            metadataItems.addItem(MetadataKey.LaserSensorName, "Laser Designator");
            metadataItems.addItem(MetadataKey.LaserSensorStatus, "Laser ON");
            metadataItems.addItem(MetadataKey.MainSensorName, "Sensor A");
            metadataItems.addItem(MetadataKey.MetadataTimestamp, "MT 2020-01-02T12:23:34.3Z");
            metadataItems.addItem(MetadataKey.NorthAngle, "60.0");
            metadataItems.addItem(MetadataKey.PlatformAltitude, "1425m HAE ALT");
            metadataItems.addItem(MetadataKey.PlatformLatitude, "LAT");
            metadataItems.addItem(MetadataKey.PlatformLongitude, "LON");
            metadataItems.addItem(MetadataKey.PlatformName, "MQ-99 SURE THING");
            metadataItems.addItem(MetadataKey.SlantRange, "33.34");
            metadataItems.addItem(MetadataKey.TargetElevation, "0.1m HAE");
            metadataItems.addItem(MetadataKey.TargetLatitude, "Target LAT");
            metadataItems.addItem(MetadataKey.TargetLongitude, "Target LON");
            metadataItems.addItem(MetadataKey.TargetWidth, "340.3m");
            ST0603TimeStamp miTimestamp = new ST0603TimeStamp(1444200822301748L);
            renderer.render(image, metadataItems, miTimestamp);
            assertNotNull(image);
        }
    */
}
