package org.jmisb.api.klv.st0601;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jmisb.core.klv.ArrayUtils;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Image Horizon Pixel Pack (ST 0601 tag 81).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Location of earth-sky horizon in the Imagery.
 *
 * <p>The Image Horizon Pixel Pack allows a user to separate sky and ground portions of an image by
 * defining a line representing the horizon. The method for detecting where the horizon is within
 * the image is left to the system implementer.
 *
 * <p>The line representing the horizon which transects the image is defined by a vector with start
 * and end points which must lie on the extents of the image. This is called the Horizon Vector. The
 * horizontal (x) and vertical (y) coordinates are represented in a relative scale (from 0 to 100%)
 * with (x, y) equal to (0%,0%) being the top left corner of the image. Once start and end
 * coordinates are defined, the pixels to the right of this Horizon Vector designates the ground
 * region, while pixels to the left represent sky.
 *
 * </blockquote>
 */
public class ImageHorizonPixelPack implements IUasDatalinkValue {

    private final int x0;
    private final int y0;
    private final int x1;
    private final int y1;

    private double latitude0 = Double.NaN;
    private double longitude0 = Double.NaN;
    private double latitude1 = Double.NaN;
    private double longitude1 = Double.NaN;

    private static final byte[] ERROR_BYTES =
            new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00};
    private static final double LAT_FLOAT_RANGE = 90.0;
    private static final double LON_FLOAT_RANGE = 180.0;
    private static final double MAX_INT = 2147483647.0;

    /**
     * Create from value.
     *
     * <p>The valid range for x and y coordinates is [0, 100].
     *
     * @param x0 The X coordinate (in percent) of an X-Y pair representing the start point of a
     *     vector crossing an image.
     * @param y0 The Y coordinate (in percent) of an X-Y pair representing the start point of a
     *     vector crossing an image.
     * @param x1 The X coordinate (in percent) of an X-Y pair representing the end point of a vector
     *     crossing an image.
     * @param y1 The Y coordinate (in percent) of an X-Y pair representing the end point of a vector
     *     crossing an image.
     */
    public ImageHorizonPixelPack(int x0, int y0, int x1, int y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }

    /**
     * Create from encoded bytes.
     *
     * <p>The encoding is a floating length pack of either 4 bytes or 20 bytes. The 20 byte case
     * includes the optional latitude and longitude pairs for the start and end points.
     *
     * @param bytes The byte array that specifies the encoded value.
     */
    public ImageHorizonPixelPack(byte[] bytes) {
        if (!((bytes.length == 4) || (bytes.length == 20))) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding must be either 4 or 20 bytes");
        }
        this.x0 = bytes[0];
        this.y0 = bytes[1];
        this.x1 = bytes[2];
        this.y1 = bytes[3];
        if (bytes.length == 20) {
            byte[] lat0Bytes = new byte[] {bytes[4], bytes[5], bytes[6], bytes[7]};
            latitude0 = parseLatitudeFromBytes(lat0Bytes);
            byte[] lon0Bytes = new byte[] {bytes[8], bytes[9], bytes[10], bytes[11]};
            longitude0 = parseLongitudeFromBytes(lon0Bytes);
            byte[] lat1Bytes = new byte[] {bytes[12], bytes[13], bytes[14], bytes[15]};
            latitude1 = parseLatitudeFromBytes(lat1Bytes);
            byte[] lon1Bytes = new byte[] {bytes[16], bytes[17], bytes[18], bytes[19]};
            longitude1 = parseLongitudeFromBytes(lon1Bytes);
        }
    }

    private Double parseLatitudeFromBytes(byte[] latBytes) {
        if (Arrays.equals(latBytes, ERROR_BYTES)) {
            return Double.NaN;
        } else {
            int intVal = PrimitiveConverter.toInt32(latBytes);
            return (intVal / MAX_INT) * LAT_FLOAT_RANGE;
        }
    }

    private Double parseLongitudeFromBytes(byte[] lonBytes) {
        if (Arrays.equals(lonBytes, ERROR_BYTES)) {
            return Double.NaN;
        } else {
            int intVal = PrimitiveConverter.toInt32(lonBytes);
            return (intVal / MAX_INT) * LON_FLOAT_RANGE;
        }
    }

    @Override
    public byte[] getBytes() {

        if (Double.isFinite(this.latitude0)
                || Double.isFinite(this.longitude0)
                || Double.isFinite(this.latitude1)
                || Double.isFinite(this.longitude1)) {
            List<byte[]> chunks = new ArrayList<>();
            chunks.add(getMandatoryBytes());
            chunks.add(getLatitudeBytes(latitude0));
            chunks.add(getLongitudeBytes(longitude0));
            chunks.add(getLatitudeBytes(latitude1));
            chunks.add(getLongitudeBytes(longitude1));
            return ArrayUtils.arrayFromChunks(chunks, 20);
        } else {
            return getMandatoryBytes();
        }
    }

    private byte[] getMandatoryBytes() {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) this.x0;
        bytes[1] = (byte) this.y0;
        bytes[2] = (byte) this.x1;
        bytes[3] = (byte) this.y1;
        return bytes;
    }

    private byte[] getLatitudeBytes(double latitude) {
        if (!Double.isFinite(latitude)) {
            return ERROR_BYTES.clone();
        }
        int intVal = (int) Math.round((latitude / LAT_FLOAT_RANGE) * MAX_INT);
        return PrimitiveConverter.int32ToBytes(intVal);
    }

    private byte[] getLongitudeBytes(double longitude) {
        if (!Double.isFinite(longitude)) {
            return ERROR_BYTES.clone();
        }
        int intVal = (int) Math.round((longitude / LON_FLOAT_RANGE) * MAX_INT);
        return PrimitiveConverter.int32ToBytes(intVal);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("(%d%%, %d%%),(%d%%, %d%%)", x0, y0, x1, y1);
    }

    @Override
    public final String getDisplayName() {
        return "Image Horizon";
    }

    /**
     * Get the X coordinate of an X-Y pair representing the start point of a vector crossing an
     * image.
     *
     * @return X0 coordinate as a percentage of the full width.
     */
    public double getX0() {
        return x0;
    }

    /**
     * Get the Y coordinate of an X-Y pair representing the start point of a vector crossing an
     * image.
     *
     * @return Y0 coordinate as a percentage of the full height.
     */
    public double getY0() {
        return y0;
    }

    /**
     * Get the X coordinate of an X-Y pair representing the end point of a vector crossing an image.
     *
     * @return X1 coordinate as a percentage of the full width.
     */
    public double getX1() {
        return x1;
    }

    /**
     * Get the Y coordinate of an X-Y pair representing the end point of a vector crossing an image.
     *
     * @return Y1 coordinate as a percentage of the full height.
     */
    public double getY1() {
        return y1;
    }

    /**
     * The Latitude of the Start point (x0,y0) on the image border.
     *
     * <p>Based on WGS84 ellipsoid.
     *
     * @return the latitude of the start point, in degrees, or Double.NaN to represent an error
     *     condition or not provided.
     */
    public double getLatitude0() {
        return latitude0;
    }

    /**
     * Set the latitude of the Start point (x0,y0) on the image border.
     *
     * <p>Based on WGS84 ellipsoid.
     *
     * @param latitude0 the latitude of the start point, in degrees, or Double.NaN to represent an
     *     error condition.
     */
    public void setLatitude0(double latitude0) {
        this.latitude0 = latitude0;
    }

    /**
     * The Longitude of the Start point (x0,y0) on the image border.
     *
     * <p>Based on WGS84 ellipsoid.
     *
     * @return longitude of the start point, in degrees, or Double.NaN to represent an error
     *     condition or not provided.
     */
    public double getLongitude0() {
        return longitude0;
    }

    /**
     * Set the longitude of the Start point (x0,y0) on the image border.
     *
     * <p>Based on WGS84 ellipsoid.
     *
     * @param longitude0 longitude of the start point, in degrees, or Double.NaN to represent an
     *     error condition.
     */
    public void setLongitude0(double longitude0) {
        this.longitude0 = longitude0;
    }

    /**
     * The Latitude of the End point (x1,y1) on the image border.
     *
     * <p>Based on WGS84 ellipsoid.
     *
     * @return latitude of the end point, in degrees, or Double.NaN to represent an error condition
     *     or not provided.
     */
    public double getLatitude1() {
        return latitude1;
    }

    /**
     * Set the Latitude of the End point (x1,y1) on the image border.
     *
     * <p>Based on WGS84 ellipsoid.
     *
     * @param latitude1 latitude of the end point, in degrees, or Double.NaN to represent an error
     *     condition.
     */
    public void setLatitude1(double latitude1) {
        this.latitude1 = latitude1;
    }

    /**
     * The Longitude of the End point (x1,y1) on the image border.
     *
     * <p>Based on WGS84 ellipsoid.
     *
     * @return longitude of the end point, in degrees, or Double.NaN to represent an error condition
     *     or not provided.
     */
    public double getLongitude1() {
        return longitude1;
    }

    /**
     * Set the Longitude of the End point (x1,y1) on the image border.
     *
     * <p>Based on WGS84 ellipsoid.
     *
     * @param longitude1 longitude of the end point, in degrees, or Double.NaN to represent an error
     *     condition.
     */
    public void setLongitude1(double longitude1) {
        this.longitude1 = longitude1;
    }
}
