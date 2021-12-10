package org.jmisb.viewer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.imageio.ImageIO;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.st0602.AnnotationMetadataKey;
import org.jmisb.api.klv.st0602.AnnotationMetadataUniversalSet;
import org.jmisb.api.klv.st0602.EventIndication;
import org.jmisb.api.klv.st0602.EventIndicationKind;
import org.jmisb.api.klv.st0602.LocallyUniqueIdentifier;
import org.jmisb.api.klv.st0602.MIMEMediaType;
import org.jmisb.api.klv.st0602.XViewportPosition;
import org.jmisb.api.klv.st0602.YViewportPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Annotations {

    private static Logger LOG = LoggerFactory.getLogger(Annotations.class);

    private final Map<Long, AnnotationMetadataUniversalSet> sets = new ConcurrentHashMap<>();

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
            EventIndicationKind eventKind = eventIndication.getEventIndicationKind();
            switch (eventKind) {
                case NEW:
                    sets.put(id.getIdentifier(), set);
                    break;
                case DELETE:
                    sets.remove(id.getIdentifier());
                    break;
                case MOVE:
                case MODIFY:
                case STATUS:
                    if (sets.containsKey(id.getIdentifier())) {
                        sets.get(id.getIdentifier()).mergeAndUpdate(set);
                    } else {
                        sets.put(id.getIdentifier(), set);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void render(Graphics2D g2) {
        for (AnnotationMetadataUniversalSet message : sets.values()) {
            try {
                IKlvValue mimeMediaType = message.getField(AnnotationMetadataKey.MIMEMediaType);
                if (mimeMediaType != null && (mimeMediaType instanceof MIMEMediaType)) {
                    MIMEMediaType mimeType = (MIMEMediaType) mimeMediaType;
                    if (mimeType.isPNG() || mimeType.isJPEG() || mimeType.isBMP()) {
                        byte[] imageBytes =
                                message.getField(AnnotationMetadataKey.MIMEData).getBytes();
                        BufferedImage annotationImage =
                                ImageIO.read(new ByteArrayInputStream(imageBytes));
                        XViewportPosition xValue =
                                (XViewportPosition)
                                        message.getField(AnnotationMetadataKey.XViewportPosition);
                        YViewportPosition yValue =
                                (YViewportPosition)
                                        message.getField(AnnotationMetadataKey.YViewportPosition);
                        g2.drawImage(
                                annotationImage, xValue.getPosition(), yValue.getPosition(), null);
                    }
                } else {
                    throw new IllegalArgumentException("Bad annotation set");
                }
            } catch (IOException ex) {
                LOG.error("Failed to render annotation: " + ex.toString());
            }
        }
    }

    public void clear() {
        sets.clear();
    }
}
