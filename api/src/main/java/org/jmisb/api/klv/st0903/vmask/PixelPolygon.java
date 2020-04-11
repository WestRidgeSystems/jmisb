package org.jmisb.api.klv.st0903.vmask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.core.klv.ArrayUtils;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Pixel Polygon (ST0903 VMask Local Set Tag 1).
 * <p>
 * From ST0903:
 * <blockquote>
 * Three or more points that represent the vertices of a polygon within a Motion
 * Imagery Frame. List the points in clockwise order. Close the polygon by
 * connecting the last point to the first point. Each point is a pixel number
 * with numbering commencing with 1, at the top left pixel, proceeding from left
 * to right, top to bottom, then encoded using the Length-Value construct of a
 * Variable-Length Pack.
 * <p>
 * Note: in the UML of the VMTI LS the type of polygon is indicated as an array
 * i.e., [].
 * <p>
 * The calculation of the pixel number is pixel number = Column + ((Row-1) x
 * frame width)). The top left pixel of the frame equates to (Column, Row) = (1,
 * 1) and pixel number=1. For example, for frame width = 1920, pixel location
 * (1, 1) pixel number=1; pixel location (2, 1) pixel number=2; pixel location
 * (1, 2) pixel number=1921.
 * </blockquote>
 */
public class PixelPolygon implements IVmtiMetadataValue
{
    private final List<Long> polygon = new ArrayList<>();

    /**
     * Create from value.
     *
     * @param points pixel number of the boundary polygon (at least three points are required).
     */
    public PixelPolygon(List<Long> points)
    {
        if (points.size() < 3)
        {
            throw new IllegalArgumentException("Pixel polygon requires at least three points");
        }
        polygon.addAll(points);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array comprising the polygon
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public PixelPolygon(byte[] bytes) throws KlvParseException
    {
        int index = 0;
        while (index < bytes.length - 1)
        {
            BerField lengthField = BerDecoder.decode(bytes, index, true);
            index += lengthField.getLength();
            byte[] polygonPointBytes = Arrays.copyOfRange(bytes, index, index + lengthField.getValue());
            Long location = parseV6(polygonPointBytes);
            polygon.add(location);
            index += lengthField.getValue();
        }
    }

    @Override
    public byte[] getBytes()
    {
        int len = 0;
        List<byte[]> chunks = new ArrayList<>();
        for (Long point: getPolygon())
        {
            byte[] pointBytes = PrimitiveConverter.uintToVariableBytesV6(point);
            byte[] lengthBytes = BerEncoder.encode(pointBytes.length);
            chunks.add(lengthBytes);
            len += lengthBytes.length;
            chunks.add(pointBytes);
            len += pointBytes.length;
        }
        return ArrayUtils.arrayFromChunks(chunks, len);
    }

    @Override
    public String getDisplayableValue()
    {
        return "[Pixel Numbers]";
    }

    @Override
    public String getDisplayName()
    {
        return "Polygon";
    }

    /**
     * Get the polygon points.
     *
     * @return the list of pixel numbers.
     */
    public List<Long> getPolygon()
    {
        return polygon;
    }

    private long parseV6(byte[] bytes)
    {
        if (bytes.length > 6)
        {
            throw new IllegalArgumentException("Pixel number encoding is up to 6 bytes");
        }
        long pixelNumber = 0;
        for (int i = 0; i < bytes.length; ++i)
        {
            pixelNumber = pixelNumber << 8;
            pixelNumber += ((int) bytes[i] & 0xFF);
        }
        return pixelNumber;
    }
}
