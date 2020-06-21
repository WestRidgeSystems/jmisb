package org.jmisb.api.klv.eg0104;

import org.jmisb.api.klv.UniversalLabel;

/** Constants used by EG 0104 */
public class PredatorMetadataConstants {
    private PredatorMetadataConstants() {}

    public static final UniversalLabel FRAME_CENTRE_LATITUDE =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03,
                                (byte) 0x02, (byte) 0x00, (byte) 0x00
                    });

    public static final UniversalLabel FRAME_CENTRE_LONGITUDE =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03,
                                (byte) 0x04, (byte) 0x00, (byte) 0x00
                    });

    public static final UniversalLabel FRAME_CENTRE_ELEVATION =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x0A,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03,
                                (byte) 0x16, (byte) 0x00, (byte) 0x00
                    });

    public static final UniversalLabel IMAGE_COORDINATE_SYSTEM =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x07, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x00,
                                (byte) 0x00, (byte) 0x00, (byte) 0x00
                    });

    public static final UniversalLabel TARGET_WIDTH =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x07, (byte) 0x01, (byte) 0x09, (byte) 0x02, (byte) 0x01,
                                (byte) 0x00, (byte) 0x00, (byte) 0x00
                    });

    public static final UniversalLabel START_DATE_TIME_UTC =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x07, (byte) 0x02, (byte) 0x01, (byte) 0x02, (byte) 0x01,
                                (byte) 0x01, (byte) 0x00, (byte) 0x00
                    });

    public static final UniversalLabel EVENT_START_DATE_TIME_UTC =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x07, (byte) 0x02, (byte) 0x01, (byte) 0x02, (byte) 0x07,
                                (byte) 0x01, (byte) 0x00, (byte) 0x00
                    });

    public static final UniversalLabel USER_DEFINED_TIME_STAMP =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x03,
                        (byte) 0x07, (byte) 0x02, (byte) 0x01, (byte) 0x01, (byte) 0x01,
                                (byte) 0x05, (byte) 0x00, (byte) 0x00
                    });

    public static final UniversalLabel CORNER_LATITUDE_POINT_1 =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x03,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03,
                                (byte) 0x07, (byte) 0x01, (byte) 0x00
                    });

    public static final UniversalLabel CORNER_LATITUDE_POINT_2 =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x03,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03,
                                (byte) 0x08, (byte) 0x01, (byte) 0x00
                    });

    public static final UniversalLabel CORNER_LATITUDE_POINT_3 =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x03,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03,
                                (byte) 0x09, (byte) 0x01, (byte) 0x00
                    });

    public static final UniversalLabel CORNER_LATITUDE_POINT_4 =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x03,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03,
                                (byte) 0x0A, (byte) 0x01, (byte) 0x00
                    });

    public static final UniversalLabel CORNER_LONGITUDE_POINT_1 =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x03,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03,
                                (byte) 0x0B, (byte) 0x01, (byte) 0x00
                    });

    public static final UniversalLabel CORNER_LONGITUDE_POINT_2 =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x03,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03,
                                (byte) 0x0C, (byte) 0x01, (byte) 0x00
                    });

    public static final UniversalLabel CORNER_LONGITUDE_POINT_3 =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x03,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03,
                                (byte) 0x0D, (byte) 0x01, (byte) 0x00
                    });

    public static final UniversalLabel CORNER_LONGITUDE_POINT_4 =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x03,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03,
                                (byte) 0x0E, (byte) 0x01, (byte) 0x00
                    });

    public static final UniversalLabel SLANT_RANGE =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x07, (byte) 0x01, (byte) 0x08, (byte) 0x01, (byte) 0x01,
                                (byte) 0x00, (byte) 0x00, (byte) 0x00
                    });

    public static final UniversalLabel SENSOR_ROLL_ANGLE =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x07, (byte) 0x01, (byte) 0x10, (byte) 0x01, (byte) 0x01,
                                (byte) 0x00, (byte) 0x00, (byte) 0x00
                    });

    public static final UniversalLabel ANGLE_TO_NORTH =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x07, (byte) 0x01, (byte) 0x10, (byte) 0x01, (byte) 0x02,
                                (byte) 0x00, (byte) 0x00, (byte) 0x00
                    });

    public static final UniversalLabel OBLIQUITY_ANGLE =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x07, (byte) 0x01, (byte) 0x10, (byte) 0x01, (byte) 0x03,
                                (byte) 0x00, (byte) 0x00, (byte) 0x00
                    });

    public static final UniversalLabel PLATFORM_ROLL_ANGLE =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x07,
                        (byte) 0x07, (byte) 0x01, (byte) 0x10, (byte) 0x01, (byte) 0x04,
                                (byte) 0x00, (byte) 0x00, (byte) 0x00
                    });

    public static final UniversalLabel PLATFORM_PITCH_ANGLE =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x07,
                        (byte) 0x07, (byte) 0x01, (byte) 0x10, (byte) 0x01, (byte) 0x05,
                                (byte) 0x00, (byte) 0x00, (byte) 0x00
                    });

    public static final UniversalLabel PLATFORM_HEADING_ANGLE =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x07,
                        (byte) 0x07, (byte) 0x01, (byte) 0x10, (byte) 0x01, (byte) 0x06,
                                (byte) 0x00, (byte) 0x00, (byte) 0x00
                    });

    public static final UniversalLabel FIELD_OF_VIEW_HORIZONTAL =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x02,
                        (byte) 0x04, (byte) 0x20, (byte) 0x02, (byte) 0x01, (byte) 0x01,
                                (byte) 0x08, (byte) 0x00, (byte) 0x00
                    });

    public static final UniversalLabel FIELD_OF_VIEW_VERTICAL =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x07,
                        (byte) 0x04, (byte) 0x20, (byte) 0x02, (byte) 0x01, (byte) 0x01,
                                (byte) 0x0A, (byte) 0x01, (byte) 0x00
                    });

    public static final UniversalLabel DEVICE_ALTITUDE =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x02,
                                (byte) 0x02, (byte) 0x00, (byte) 0x00
                    });

    public static final UniversalLabel DEVICE_LATITUDE =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x03,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x02,
                                (byte) 0x04, (byte) 0x02, (byte) 0x00
                    });

    public static final UniversalLabel DEVICE_LONGITUDE =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x03,
                        (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x02,
                                (byte) 0x06, (byte) 0x02, (byte) 0x00
                    });

    public static final UniversalLabel IMAGE_SOURCE_DEVICE =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x04, (byte) 0x20, (byte) 0x01, (byte) 0x02, (byte) 0x01,
                                (byte) 0x01, (byte) 0x00, (byte) 0x00
                    });

    public static final UniversalLabel EPISODE_NUMBER =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x01, (byte) 0x05, (byte) 0x05, (byte) 0x00, (byte) 0x00,
                                (byte) 0x00, (byte) 0x00, (byte) 0x00
                    });

    public static final UniversalLabel DEVICE_DESIGATION =
            new UniversalLabel(
                    new byte[] {
                        (byte) 0x06, (byte) 0x0E, (byte) 0x2B, (byte) 0x34, (byte) 0x01,
                                (byte) 0x01, (byte) 0x01, (byte) 0x01,
                        (byte) 0x01, (byte) 0x01, (byte) 0x20, (byte) 0x01, (byte) 0x00,
                                (byte) 0x00, (byte) 0x00, (byte) 0x00
                    });
}
