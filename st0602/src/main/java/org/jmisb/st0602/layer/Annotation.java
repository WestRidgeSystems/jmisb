package org.jmisb.st0602.layer;

import java.util.Arrays;
import org.jmisb.st0602.AnnotationSource;
import org.jmisb.st0602.EventIndicationKind;
import org.jmisb.st0602.MIMEMediaType;

/** An annotation on the {@link AnnotationLayer}. */
public class Annotation {

    private long source = AnnotationSource.MANUALLY_ANNOTATED;
    private short xPosition;
    private short yPosition;
    private String mediaType = MIMEMediaType.PNG;
    private byte[] mediaData = null;

    private EventIndicationKind state = EventIndicationKind.NEW;

    /**
     * Constructor.
     *
     * <p>This requires specifying the origin of the annotation. This corresponds to the the X and Y
     * Viewport Position in Pixels values from ST 0602, and is the location of the media reference
     * point, typically the image origin, but is defined independently for each data type. For PNG,
     * JPEG and SVG, this is the top left corner. For BMP, this is the lower left corner. For CGM,
     * this is the VDC origin. The X and Y position is referenced based upon a (0, 0) origin in the
     * upper-left corner of the original essence data image.
     *
     * @param xPosition the x position
     * @param yPosition the y position
     */
    public Annotation(int xPosition, int yPosition) {
        this.xPosition = (short) xPosition;
        this.yPosition = (short) yPosition;
    }

    /**
     * Create a delete annotation.
     *
     * <p>This is used to flag a value for removal.
     *
     * @return a delete annotation.
     */
    static Annotation getDeleteAnnotation() {
        Annotation annotation = new Annotation(-1, -1);
        annotation.state = EventIndicationKind.DELETE;
        return annotation;
    }

    /**
     * Get the source(s) of the annotation.
     *
     * <p>The Annotation Source describes the source method of the annotation.
     *
     * <p>This is a bit mask value. See {@link AnnotationSource} and ST 0602.5 Section 6.3.7 for the
     * meaning of the values.
     *
     * @return a bit mask of annotation sources
     */
    public long getSource() {
        return source;
    }

    /**
     * Set the source(s) of the annotation.
     *
     * <p>The Annotation Source describes the source method of the annotation.
     *
     * <p>This is a bit mask value. See {@link AnnotationSource} and ST 0602.5 Section 6.3.7 for the
     * meaning of the values.
     *
     * <p>If not specified, this defaults to {@link AnnotationSource#MANUALLY_ANNOTATED }. If
     * generating from an algorithm, consider using {@link
     * AnnotationSource#AUTOMATED_FROM_PIXEL_INTELLIGENCE}.
     *
     * @param source a bit mask of annotation sources
     */
    public void setSource(long source) {
        this.source = source;
    }

    /**
     * Get the X position for the annotation.
     *
     * <p>This corresponds to the the X Viewport Position in Pixels values from ST 0602, and is the
     * location of the media reference point, typically the image origin, but is defined
     * independently for each data type. For PNG, JPEG and SVG, this is the top left corner. For
     * BMP, this is the lower left corner. For CGM, this is the VDC origin. The X position is
     * referenced based upon a (0, 0) origin in the upper-left corner of the original essence data
     * image.
     *
     * @return the X position in pixels.
     */
    public short getXPosition() {
        return xPosition;
    }

    /**
     * Get the Y position for the annotation.
     *
     * <p>This corresponds to the the Y Viewport Position in Pixels values from ST 0602, and is the
     * location of the media reference point, typically the image origin, but is defined
     * independently for each data type. For PNG, JPEG and SVG, this is the top left corner. For
     * BMP, this is the lower left corner. For CGM, this is the VDC origin. The Y position is
     * referenced based upon a (0, 0) origin in the upper-left corner of the original essence data
     * image.
     *
     * @return the Y position in pixels.
     */
    public short getYPosition() {
        return yPosition;
    }

    /**
     * Get the media type for the annotation data.
     *
     * <p>This is the format for the media data, corresponding to ST 0602 MIME Media Type.
     *
     * <p>As an example, PNG would be represented as "image/png".
     *
     * @return the media type
     */
    public String getMediaType() {
        return mediaType;
    }

    /**
     * Set the media type for the annotation data.
     *
     * <p>This is the format for the media data, corresponding to ST 0602 MIME Media Type.
     *
     * <p>As an example, PNG would be represented as "image/png". If not specified, it defaults to
     * {@link MIMEMediaType#PNG}.
     *
     * @param mediaType the media type as a string.
     */
    public void setMediaType(String mediaType) {
        if (!this.mediaType.equals(mediaType)) {
            if (this.state != EventIndicationKind.NEW) {
                this.state = EventIndicationKind.MODIFY;
            }
            this.mediaType = mediaType;
        }
    }

    /**
     * Get the media data.
     *
     * <p>This is the "payload" for the annotation. For example, if the media type is "image/png",
     * this is a byte array encoding of the PNG image to be displayed at the specified annotation
     * position.
     *
     * @return a copy of the media data as a byte array.
     */
    public byte[] getMediaData() {
        return mediaData.clone();
    }

    /**
     * Set the media data.
     *
     * <p>This is the "payload" for the annotation. For example, if the media type is "image/png",
     * this is a byte array encoding of the PNG image to be displayed at the specified annotation
     * position.
     *
     * @param mediaData the media data as a byte array.
     */
    public void setMediaData(byte[] mediaData) {
        if (!Arrays.equals(this.mediaData, mediaData)) {
            if (this.state != EventIndicationKind.NEW) {
                this.state = EventIndicationKind.MODIFY;
            }
            this.mediaData = mediaData;
        }
    }

    /**
     * Get the state of this annotation.
     *
     * @return the state
     */
    public EventIndicationKind getState() {
        return state;
    }

    /**
     * Set the state of this annotation.
     *
     * <p>This should only be done by the owning (parent) AnnotationLayer instance.
     *
     * @param newState the state to set
     */
    void setState(EventIndicationKind newState) {
        this.state = newState;
    }

    void moveTo(int x, int y) {
        this.xPosition = (short) x;
        this.yPosition = (short) y;
        if (this.state == EventIndicationKind.STATUS) {
            // we don't want to use MOVE if we're already in NEW or MODIFY.
            this.state = EventIndicationKind.MOVE;
        }
    }
}
