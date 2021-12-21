package org.jmisb.st1002;

import org.jmisb.api.klv.Ber;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;

/**
 * Number of Sections in Y (ST 1002 Range Image Local Set Tag 18).
 *
 * <p>Range Imagery Data is a rectangular array of Range Measurements. Range Imagery Data can be
 * formatted in whole or in separate parts, where each part is called a Section. Sections are
 * rectangular areas that when combined form the full image. Each Section can be compressed to
 * provide the most efficient transmission and storage. ST 1002.2 requires Sections to be in simple
 * layout. A simple Section layout divides the image into either horizontal or vertical strips. All
 * horizontal strips have the same width as the full image but can vary in height as needed as
 * illustrated below.
 *
 * <p><img src="../../../../org/jmisb/st1002/doc-files/fivehorizontalsections.png" alt="Five
 * horizontal sections">
 *
 * <p>All vertical strips have the same height as the full image but can vary in width as
 * illustrated below.
 *
 * <p><img src="../../../../org/jmisb/st1002/doc-files/threeverticalsections.png" alt="Three
 * vertical sections">
 *
 * <p>When the Range Image is formatted into separate Sections, the Range Image Local Set will
 * contain multiple Section Data Variable Length Packs. Specifically, the number of Section Data
 * Variable Length Packs will be the Number of Sections in X multiplied by the Number of Sections in
 * Y.
 *
 * @see NumberOfSectionsInX
 */
public class NumberOfSectionsInY implements IRangeImageMetadataValue {

    private final int value;

    /**
     * Create from value.
     *
     * @param numberOfSections the number of Sections, at least 1
     */
    public NumberOfSectionsInY(int numberOfSections) {
        if (numberOfSections < 1) {
            throw new IllegalArgumentException("ST 1002 Number of Sections in Y must be positive");
        }
        this.value = numberOfSections;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array containing BER-OID formatted value number
     */
    public NumberOfSectionsInY(byte[] bytes) {
        BerField berField = BerDecoder.decode(bytes, 0, true);
        this.value = berField.getValue();
    }

    /**
     * Get the number of sections.
     *
     * @return the number of sections
     */
    public int getNumberOfSections() {
        return value;
    }

    @Override
    public byte[] getBytes() {
        return BerEncoder.encode(value, Ber.OID);
    }

    @Override
    public String getDisplayName() {
        return "Number of Sections in Y";
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d", value);
    }
}
