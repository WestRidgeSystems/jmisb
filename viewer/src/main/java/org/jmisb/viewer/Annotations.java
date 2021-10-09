package org.jmisb.viewer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import org.jmisb.api.klv.st0602.AnnotationMetadataKey;
import org.jmisb.api.klv.st0602.AnnotationMetadataUniversalSet;
import org.jmisb.api.klv.st0602.EventIndication;
import org.jmisb.api.klv.st0602.EventIndicationKind;
import org.jmisb.api.klv.st0602.LocallyUniqueIdentifier;
import org.jmisb.api.klv.st0602.XViewportPosition;
import org.jmisb.api.klv.st0602.YViewportPosition;

public class Annotations {

    private final Map<Long, AnnotationMetadataUniversalSet> sets = new HashMap<>();

    public Map<Long, AnnotationMetadataUniversalSet> getSets() {
        return new HashMap<>(sets);
    }

    public void updateSet(AnnotationMetadataUniversalSet set) {
        if (set.getIdentifiers().contains(AnnotationMetadataKey.EventIndication)
                && set.getIdentifiers().contains(AnnotationMetadataKey.LocallyUniqueIdentifier)) {
            LocallyUniqueIdentifier id =
                    (LocallyUniqueIdentifier)
                            set.getField(AnnotationMetadataKey.LocallyUniqueIdentifier);
            EventIndication eventIndication =
                    (EventIndication) set.getField(AnnotationMetadataKey.EventIndication);
            if (eventIndication.getEventIndicationKind().equals(EventIndicationKind.NEW)) {
                sets.put(id.getIdentifier(), set);
            } else if (eventIndication
                    .getEventIndicationKind()
                    .equals(EventIndicationKind.DELETE)) {
                sets.remove(id.getIdentifier());
            }
        }
    }

    public void render(Graphics2D g2) {
        for (AnnotationMetadataUniversalSet message : sets.values()) {
            if (message.getField(AnnotationMetadataKey.EventIndication) != null) {
                EventIndication eventIndication =
                        (EventIndication) message.getField(AnnotationMetadataKey.EventIndication);
                try {
                    if (eventIndication.getEventIndicationKind().equals(EventIndicationKind.NEW)) {
                        if (message.getField(AnnotationMetadataKey.MIMEMediaType)
                                .getDisplayableValue()
                                .equals("image/png")) {
                            byte[] pngBytes =
                                    message.getField(AnnotationMetadataKey.MIMEData).getBytes();
                            BufferedImage annotationImage =
                                    ImageIO.read(new ByteArrayInputStream(pngBytes));
                            XViewportPosition xValue =
                                    (XViewportPosition)
                                            message.getField(
                                                    AnnotationMetadataKey.XViewportPosition);
                            YViewportPosition yValue =
                                    (YViewportPosition)
                                            message.getField(
                                                    AnnotationMetadataKey.YViewportPosition);
                            g2.drawImage(
                                    annotationImage,
                                    xValue.getPosition(),
                                    yValue.getPosition(),
                                    null);
                        }
                    }
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(VideoPanel.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
