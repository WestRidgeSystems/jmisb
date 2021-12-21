package org.jmisb.st1002;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.Ber;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.st1303.MDAPDecoder;
import org.jmisb.api.klv.st1303.NaturalFormatEncoder;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Section Data Variable Length Pack.
 *
 * <p>Section data, along with its supporting information is formatted in a Variable Length Pack
 * (VLP) (see SMPTE ST 336) called the Section Data VLP. The information in each Section Data VLP
 * includes Section coordinates, Section data array, uncertainty values, and optional compression
 * parameters. The optional compression parameters are truncated from the VLP (along with their
 * lengths) as a group (in a similar fashion to MISB RP 0701 Floating Length Packs). The figure
 * below illustrates a Section Data VLP with each item in the VLP prefixed with its item length. The
 * green items indicate required values (along with their lengths in blue); the pink item can be
 * zero-length; and the yellow items can be truncated (along with their lengths).
 *
 * <p><img src="../../../../org/jmisb/st1002/doc-files/sectiondatavlp.png" alt="Section Data
 * Variable Length Pack">
 */
public class SectionData implements IKlvValue {

    private final int sectionNumberX;
    private final int sectionNumberY;
    private final double[][] arrayOfMeasuredValues;
    private final double[][] arrayOfUncertaintyValues;
    private final double planeXScaleFactor;
    private final double planeYScaleFactor;
    private final double planeConstantValue;

    /**
     * Create from values.
     *
     * <p>This constructor assumes the planar fit compression factors are not included.
     *
     * @param x the section number X value
     * @param y the section number Y value
     * @param measurements the array of measurements
     * @param uncertainties the array of uncertainties (can be null)
     */
    public SectionData(int x, int y, double[][] measurements, double[][] uncertainties) {
        this.sectionNumberX = x;
        this.sectionNumberY = y;
        this.arrayOfMeasuredValues = measurements.clone();
        if (uncertainties == null) {
            this.arrayOfUncertaintyValues = null;
        } else {
            this.arrayOfUncertaintyValues = uncertainties.clone();
        }
        this.planeXScaleFactor = 0.0;
        this.planeYScaleFactor = 0.0;
        this.planeConstantValue = 0.0;
    }

    /**
     * Create from values.
     *
     * @param x the section number X value
     * @param y the section number Y value
     * @param measurements the array of measurements
     * @param uncertainties the array of uncertainties (can be null)
     * @param planeXscale the plane X scale factor
     * @param planeYscale the plane Y scale factor
     * @param planeConstant the plane constant value
     */
    public SectionData(
            int x,
            int y,
            double[][] measurements,
            double[][] uncertainties,
            double planeXscale,
            double planeYscale,
            double planeConstant) {
        this.sectionNumberX = x;
        this.sectionNumberY = y;
        this.arrayOfMeasuredValues = measurements.clone();
        if (uncertainties == null) {
            this.arrayOfUncertaintyValues = null;
        } else {
            this.arrayOfUncertaintyValues = uncertainties.clone();
        }
        this.planeXScaleFactor = planeXscale;
        this.planeYScaleFactor = planeYscale;
        this.planeConstantValue = planeConstant;
    }

    /**
     * Create from encoded bytes.
     *
     * <p>The encoded byte array is assumed to start from the first item length ({@code L1} in the
     * diagram above, and does not include the overall VLP Key or Length field.
     *
     * @param bytes the encoded byte array
     * @throws KlvParseException if parsing fails
     */
    public SectionData(byte[] bytes) throws KlvParseException {
        MDAPDecoder mdapDecoder = new MDAPDecoder();
        int offset = 0;
        BerField l1field = BerDecoder.decode(bytes, offset, false);
        offset += l1field.getLength();
        BerField sectionXfield = BerDecoder.decode(bytes, offset, true);
        offset += sectionXfield.getLength();
        sectionNumberX = sectionXfield.getValue();
        BerField l2field = BerDecoder.decode(bytes, offset, false);
        offset += l2field.getLength();
        BerField sectionYfield = BerDecoder.decode(bytes, offset, true);
        offset += sectionYfield.getLength();
        sectionNumberY = sectionYfield.getValue();
        BerField l3field = BerDecoder.decode(bytes, offset, false);
        offset += l3field.getLength();
        this.arrayOfMeasuredValues = mdapDecoder.decodeFloatingPoint2D(bytes, offset);
        offset += l3field.getValue();
        BerField l4field = BerDecoder.decode(bytes, offset, false);
        offset += l4field.getLength();
        if (l4field.getValue() > 0) {
            this.arrayOfUncertaintyValues = mdapDecoder.decodeFloatingPoint2D(bytes, offset);
            offset += l4field.getValue();
        } else {
            this.arrayOfUncertaintyValues = null;
        }
        if (offset < bytes.length) {
            BerField l5field = BerDecoder.decode(bytes, offset, false);
            offset += l5field.getLength();
            if (l5field.getValue() == 4) {
                this.planeXScaleFactor = PrimitiveConverter.toFloat32(bytes, offset);
            } else if (l5field.getValue() == 8) {
                this.planeXScaleFactor = PrimitiveConverter.toFloat64(bytes, offset);
            } else {
                throw new KlvParseException(
                        "unsupported Plane X Scale Factor size: " + l5field.getValue());
            }
            offset += l5field.getValue();
        } else {
            this.planeXScaleFactor = 0.0;
        }
        if (offset < bytes.length) {
            BerField l6field = BerDecoder.decode(bytes, offset, false);
            offset += l6field.getLength();
            if (l6field.getValue() == 4) {
                this.planeYScaleFactor = PrimitiveConverter.toFloat32(bytes, offset);
            } else if (l6field.getValue() == 8) {
                this.planeYScaleFactor = PrimitiveConverter.toFloat64(bytes, offset);
            } else {
                throw new KlvParseException(
                        "unsupported Plane Y Scale Factor size: " + l6field.getValue());
            }
            offset += l6field.getValue();
        } else {
            this.planeYScaleFactor = 0.0;
        }

        if (offset < bytes.length) {
            BerField l7field = BerDecoder.decode(bytes, offset, false);
            offset += l7field.getLength();
            if (l7field.getValue() == 4) {
                this.planeConstantValue = PrimitiveConverter.toFloat32(bytes, offset);
            } else if (l7field.getValue() == 8) {
                this.planeConstantValue = PrimitiveConverter.toFloat64(bytes, offset);
            } else {
                throw new KlvParseException(
                        "unsupported Plane Constant size: " + l7field.getValue());
            }
            offset += l7field.getValue();
        } else {
            this.planeConstantValue = 0.0;
        }
    }

    /**
     * Copy Constructor.
     *
     * @param other the section data instance to copy values from
     */
    public SectionData(SectionData other) {
        this.sectionNumberX = other.getSectionNumberX();
        this.sectionNumberY = other.getSectionNumberY();
        this.arrayOfMeasuredValues = other.getArrayOfMeasuredValues().clone();
        if (other.getArrayOfUncertaintyValues() == null) {
            this.arrayOfUncertaintyValues = null;
        } else {
            this.arrayOfUncertaintyValues = other.getArrayOfUncertaintyValues().clone();
        }
        this.planeXScaleFactor = other.getPlaneXScaleFactor();
        this.planeYScaleFactor = other.getPlaneYScaleFactor();
        this.planeConstantValue = other.getPlaneConstantValue();
    }

    /**
     * Serialise the Section Data to encoded byte array.
     *
     * @return the byte array
     * @throws KlvParseException if serialisation fails.
     */
    public byte[] getBytes() throws KlvParseException {
        ArrayBuilder builder = new ArrayBuilder();
        byte[] sectionNumberXbytes = BerEncoder.encode(sectionNumberX, Ber.OID);
        builder.appendAsBerLength(sectionNumberXbytes.length);
        builder.append(sectionNumberXbytes);
        byte[] sectionNumberYbytes = BerEncoder.encode(sectionNumberY, Ber.OID);
        builder.appendAsBerLength(sectionNumberYbytes.length);
        builder.append(sectionNumberYbytes);
        NaturalFormatEncoder mdapEncoder = new NaturalFormatEncoder();
        byte[] arrayOfMeasuredValuesBytes = mdapEncoder.encode(arrayOfMeasuredValues);
        builder.appendAsBerLength(arrayOfMeasuredValuesBytes.length);
        builder.append(arrayOfMeasuredValuesBytes);
        if (arrayOfUncertaintyValues == null) {
            builder.appendAsBerLength(0);
        } else {
            byte[] arrayOfUncertaintyValuesBytes = mdapEncoder.encode(arrayOfUncertaintyValues);
            builder.appendAsBerLength(arrayOfUncertaintyValuesBytes.length);
            builder.append(arrayOfUncertaintyValuesBytes);
        }
        if ((this.planeXScaleFactor == 0.0)
                && (this.planeYScaleFactor == 0.0)
                && (this.planeConstantValue == 0.0)) {
            // just omit the rest.
        } else {
            builder.appendAsBerLength(Double.BYTES);
            builder.appendAsFloat64Primitive(this.planeXScaleFactor);
            builder.appendAsBerLength(Double.BYTES);
            builder.appendAsFloat64Primitive(this.planeYScaleFactor);
            builder.appendAsBerLength(Double.BYTES);
            builder.appendAsFloat64Primitive(this.planeConstantValue);
        }
        return builder.toBytes();
    }

    /**
     * Section Number in X direction.
     *
     * @return section number as an integer
     */
    public int getSectionNumberX() {
        return sectionNumberX;
    }

    /**
     * Section Number in Y direction.
     *
     * @return section number as an integer.
     */
    public int getSectionNumberY() {
        return sectionNumberY;
    }

    /**
     * Array of measured values.
     *
     * @return the measured values as a 2-dimensional array
     */
    public double[][] getArrayOfMeasuredValues() {
        return arrayOfMeasuredValues.clone();
    }
    /**
     * Array of uncertainty values.
     *
     * @return the uncertainty values as a 2-dimensional array, or null if not provided.
     */
    public double[][] getArrayOfUncertaintyValues() {
        if (arrayOfUncertaintyValues == null) {
            return null;
        }
        return arrayOfUncertaintyValues.clone();
    }

    /**
     * Plane X scale factor.
     *
     * <p>This is used for planar fit compression. See ST 1002.2 Section 7.
     *
     * @return the scale factor as a double.
     */
    public double getPlaneXScaleFactor() {
        return planeXScaleFactor;
    }

    /**
     * Plane Y scale factor.
     *
     * <p>This is used for planar fit compression. See ST 1002.2 Section 7.
     *
     * @return the scale factor as a double.
     */
    public double getPlaneYScaleFactor() {
        return planeYScaleFactor;
    }

    /**
     * Plane Constant value.
     *
     * <p>This is used for planar fit compression. See ST 1002.2 Section 7.
     *
     * @return the scale factor as a double.
     */
    public double getPlaneConstantValue() {
        return planeConstantValue;
    }

    @Override
    public String getDisplayName() {
        return "Section Data";
    }

    @Override
    public String getDisplayableValue() {
        return String.format("Section [%d,%d]", this.sectionNumberX, this.sectionNumberY);
    }
}
