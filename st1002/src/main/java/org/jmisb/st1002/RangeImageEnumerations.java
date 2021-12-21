package org.jmisb.st1002;

import org.jmisb.api.klv.Ber;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;

/**
 * Range Image Enumerations (ST 1002 Range Image Local Set Tag 12).
 *
 * <p>Range Image Enumerations is a set of enumerated values encoded as a BER-OID integer value.
 * Range Image Enumerations contains three separate enumerated values:
 *
 * <ul>
 *   <li>Range Image Source,
 *   <li>Range Image Data Type, and
 *   <li>Range Image Compression Method.
 * </ul>
 *
 * <p>Range Image Source declares how the Range Imagery was created, either from a Range Sensor or
 * Computationally Extracted, as described in Section 5. This enumeration has two values, so it
 * consumes one bit of the Range Image Enumerations value.
 *
 * <p>Range Imagery Data Type declares the type of Range Imagery, either Perspective Range Image or
 * Depth Range Image. To allow for further types to be defined in the future this enumeration has
 * eight values (0 through 7), where currently only two values are defined: 0=Perspective Range
 * Image and 1=Depth Range Image.
 *
 * <p>Range Imagery Compression Method declares the method of compression used for reducing the
 * number of bytes of the Range Image. One method of compression is called Planar Fit, described in
 * ST 1002.2 Section 7. To allow for further compression techniques to be defined in the future,
 * this enumeration has eight values (0 through 7), where currently only two values are defined:
 * 0=No Compression and 1=Planar Fit.
 */
public class RangeImageEnumerations implements IRangeImageMetadataValue {

    private final RangeImageCompressionMethod compressionMethod;
    private final RangeImageryDataType dataType;
    private final RangeImageSource rangeImageSource;
    private static final int COMPRESSION_METHOD_MASK = 0b00000111;
    private static final int DATA_TYPE_BIT_SHIFT = 3;
    private static final int DATA_TYPE_MASK = 0b00111000;
    private static final int SOURCE_BIT_SHIFT = 6;
    private static final int SOURCE_MASK = 0b01000000;

    /**
     * Construct from values.
     *
     * @param compressionMethod the compression method enumeration value
     * @param dataType the range imagery data type enumeration value
     * @param rangeImageSource the range image source enumeration value
     */
    public RangeImageEnumerations(
            RangeImageCompressionMethod compressionMethod,
            RangeImageryDataType dataType,
            RangeImageSource rangeImageSource) {
        this.compressionMethod = compressionMethod;
        this.dataType = dataType;
        this.rangeImageSource = rangeImageSource;
    }

    /**
     * Construct from encoded bytes.
     *
     * @param bytes byte array (currently only length 1)
     */
    public RangeImageEnumerations(byte[] bytes) {
        BerField ber = BerDecoder.decode(bytes, 0, true);
        int value = ber.getValue();
        this.compressionMethod =
                RangeImageCompressionMethod.lookup(value & COMPRESSION_METHOD_MASK);
        this.dataType =
                RangeImageryDataType.lookup((value & DATA_TYPE_MASK) >> DATA_TYPE_BIT_SHIFT);
        this.rangeImageSource = RangeImageSource.lookup((value & SOURCE_MASK) >> SOURCE_BIT_SHIFT);
    }

    @Override
    public byte[] getBytes() {
        if (compressionMethod == RangeImageCompressionMethod.UNKNOWN) {
            throw new IllegalArgumentException("Range Image Compression Method cannot be UNKNOWN");
        }
        if (dataType == RangeImageryDataType.UNKNOWN) {
            throw new IllegalArgumentException("Range Imagery Data Type cannot be UNKNOWN");
        }
        if (rangeImageSource == RangeImageSource.UNKNOWN) {
            throw new IllegalArgumentException("Range Image Source cannot be UNKNOWN");
        }
        int value = 0;
        value += compressionMethod.getEncodedValue();
        value += (dataType.getEncodedValue() << DATA_TYPE_BIT_SHIFT);
        value += (rangeImageSource.getEncodedValue() << SOURCE_BIT_SHIFT);
        return BerEncoder.encode(value, Ber.OID);
    }

    @Override
    public String getDisplayName() {
        return "Range Image Enumerations";
    }

    @Override
    public String getDisplayableValue() {
        StringBuilder sb = new StringBuilder();
        sb.append(rangeImageSource.getTextDescription());
        sb.append(" | ");
        sb.append(dataType.getTextDescription());
        sb.append(" | ");
        sb.append(compressionMethod.getTextDescription());
        return sb.toString();
    }

    /**
     * Range image compression method.
     *
     * @return the range image compression method as an enumerated value.
     */
    public RangeImageCompressionMethod getCompressionMethod() {
        return this.compressionMethod;
    }

    /**
     * Range imagery data type.
     *
     * @return the range imagery data type as an enumerated value.
     */
    public RangeImageryDataType getDataType() {
        return dataType;
    }

    /**
     * Range image source.
     *
     * @return the range image source as an enumerated value.
     */
    public RangeImageSource getRangeImageSource() {
        return rangeImageSource;
    }
}
