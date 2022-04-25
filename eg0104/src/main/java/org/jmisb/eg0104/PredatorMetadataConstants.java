package org.jmisb.eg0104;

import org.jmisb.api.klv.UniversalLabel;

/** Constants used by EG 0104. */
public class PredatorMetadataConstants {
    private PredatorMetadataConstants() {}

    /** Frame centre latitude. */
    public static final UniversalLabel FRAME_CENTRE_LATITUDE =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03,
                                (byte) 0x02, (byte) 0x00, (byte) 0x00
                    });

    /** Frame centre longitude. */
    public static final UniversalLabel FRAME_CENTRE_LONGITUDE =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03,
                                (byte) 0x04, (byte) 0x00, (byte) 0x00
                    });

    /** Frame centre elevation. */
    public static final UniversalLabel FRAME_CENTRE_ELEVATION =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x0A,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03,
                                (byte) 0x16, (byte) 0x00, (byte) 0x00
                    });

    /** Image Coordinate System. */
    public static final UniversalLabel IMAGE_COORDINATE_SYSTEM =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x07, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x00,
                                (byte) 0x00, (byte) 0x00, (byte) 0x00
                    });

    /** Target Width. */
    public static final UniversalLabel TARGET_WIDTH =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x07, (byte) 0x01, (byte) 0x09, (byte) 0x02, (byte) 0x01,
                                (byte) 0x00, (byte) 0x00, (byte) 0x00
                    });

    /** Start date-time in UTC. */
    public static final UniversalLabel START_DATE_TIME_UTC =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x07, (byte) 0x02, (byte) 0x01, (byte) 0x02, (byte) 0x01,
                                (byte) 0x01, (byte) 0x00, (byte) 0x00
                    });

    /** Event start date-time in UTC. */
    public static final UniversalLabel EVENT_START_DATE_TIME_UTC =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x07, (byte) 0x02, (byte) 0x01, (byte) 0x02, (byte) 0x07,
                                (byte) 0x01, (byte) 0x00, (byte) 0x00
                    });

    /** User defined time stamp. */
    public static final UniversalLabel USER_DEFINED_TIME_STAMP =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x03,
                        (byte) 0x07, (byte) 0x02, (byte) 0x01, (byte) 0x01, (byte) 0x01,
                                (byte) 0x05, (byte) 0x00, (byte) 0x00
                    });

    /** Latitude of Corner Point 1. */
    public static final UniversalLabel CORNER_LATITUDE_POINT_1 =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x03,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03,
                                (byte) 0x07, (byte) 0x01, (byte) 0x00
                    });

    /** Latitude of Corner Point 2. */
    public static final UniversalLabel CORNER_LATITUDE_POINT_2 =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x03,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03,
                                (byte) 0x08, (byte) 0x01, (byte) 0x00
                    });

    /** Latitude of Corner Point 3. */
    public static final UniversalLabel CORNER_LATITUDE_POINT_3 =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x03,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03,
                                (byte) 0x09, (byte) 0x01, (byte) 0x00
                    });

    /** Latitude of Corner Point 4. */
    public static final UniversalLabel CORNER_LATITUDE_POINT_4 =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x03,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03,
                                (byte) 0x0A, (byte) 0x01, (byte) 0x00
                    });

    /** Longitude of Corner Point 1. */
    public static final UniversalLabel CORNER_LONGITUDE_POINT_1 =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x03,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03,
                                (byte) 0x0B, (byte) 0x01, (byte) 0x00
                    });

    /** Longitude of Corner Point 2. */
    public static final UniversalLabel CORNER_LONGITUDE_POINT_2 =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x03,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03,
                                (byte) 0x0C, (byte) 0x01, (byte) 0x00
                    });

    /** Longitude of Corner Point 3. */
    public static final UniversalLabel CORNER_LONGITUDE_POINT_3 =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x03,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03,
                                (byte) 0x0D, (byte) 0x01, (byte) 0x00
                    });

    /** Longitude of Corner Point 4. */
    public static final UniversalLabel CORNER_LONGITUDE_POINT_4 =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x03,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03,
                                (byte) 0x0E, (byte) 0x01, (byte) 0x00
                    });

    /** Slant Range. */
    public static final UniversalLabel SLANT_RANGE =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x07, (byte) 0x01, (byte) 0x08, (byte) 0x01, (byte) 0x01,
                                (byte) 0x00, (byte) 0x00, (byte) 0x00
                    });

    /** Sensor Roll Angle. */
    public static final UniversalLabel SENSOR_ROLL_ANGLE =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x07, (byte) 0x01, (byte) 0x10, (byte) 0x01, (byte) 0x01,
                                (byte) 0x00, (byte) 0x00, (byte) 0x00
                    });

    /** Angle to North. */
    public static final UniversalLabel ANGLE_TO_NORTH =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x07, (byte) 0x01, (byte) 0x10, (byte) 0x01, (byte) 0x02,
                                (byte) 0x00, (byte) 0x00, (byte) 0x00
                    });

    /** Obliquity Angle. */
    public static final UniversalLabel OBLIQUITY_ANGLE =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x07, (byte) 0x01, (byte) 0x10, (byte) 0x01, (byte) 0x03,
                                (byte) 0x00, (byte) 0x00, (byte) 0x00
                    });

    /** Platform Roll Angle. */
    public static final UniversalLabel PLATFORM_ROLL_ANGLE =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x07,
                        (byte) 0x07, (byte) 0x01, (byte) 0x10, (byte) 0x01, (byte) 0x04,
                                (byte) 0x00, (byte) 0x00, (byte) 0x00
                    });

    /** Platform Pitch Angle. */
    public static final UniversalLabel PLATFORM_PITCH_ANGLE =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x07,
                        (byte) 0x07, (byte) 0x01, (byte) 0x10, (byte) 0x01, (byte) 0x05,
                                (byte) 0x00, (byte) 0x00, (byte) 0x00
                    });

    /** Platform Heading Angle. */
    public static final UniversalLabel PLATFORM_HEADING_ANGLE =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x07,
                        (byte) 0x07, (byte) 0x01, (byte) 0x10, (byte) 0x01, (byte) 0x06,
                                (byte) 0x00, (byte) 0x00, (byte) 0x00
                    });

    /** Field of View - Horizontal. */
    public static final UniversalLabel FIELD_OF_VIEW_HORIZONTAL =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x02,
                        (byte) 0x04, (byte) 0x20, (byte) 0x02, (byte) 0x01, (byte) 0x01,
                                (byte) 0x08, (byte) 0x00, (byte) 0x00
                    });

    /** Field of View - Vertical. */
    public static final UniversalLabel FIELD_OF_VIEW_VERTICAL =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x07,
                        (byte) 0x04, (byte) 0x20, (byte) 0x02, (byte) 0x01, (byte) 0x01,
                                (byte) 0x0A, (byte) 0x01, (byte) 0x00
                    });

    /** Device altitude. */
    public static final UniversalLabel DEVICE_ALTITUDE =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x02,
                                (byte) 0x02, (byte) 0x00, (byte) 0x00
                    });

    /** Device latitude. */
    public static final UniversalLabel DEVICE_LATITUDE =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x03,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x02,
                                (byte) 0x04, (byte) 0x02, (byte) 0x00
                    });

    /** Device longitude. */
    public static final UniversalLabel DEVICE_LONGITUDE =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x03,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x02,
                                (byte) 0x06, (byte) 0x02, (byte) 0x00
                    });

    /** Image Source Device. */
    public static final UniversalLabel IMAGE_SOURCE_DEVICE =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x04, (byte) 0x20, (byte) 0x01, (byte) 0x02, (byte) 0x01,
                                (byte) 0x01, (byte) 0x00, (byte) 0x00
                    });

    /** Episode Number. */
    public static final UniversalLabel EPISODE_NUMBER =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x01, (byte) 0x05, (byte) 0x05, (byte) 0x00, (byte) 0x00,
                                (byte) 0x00, (byte) 0x00, (byte) 0x00
                    });

    /** Device Designation. */
    public static final UniversalLabel DEVICE_DESIGATION =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x01, (byte) 0x01, (byte) 0x20, (byte) 0x01, (byte) 0x00,
                                (byte) 0x00, (byte) 0x00, (byte) 0x00
                    });
}
