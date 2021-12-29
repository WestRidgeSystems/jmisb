package org.jmisb.api.klv.st1108;

import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.IKlvValue;

public interface IInterpretabilityQualityMetadataValue extends IKlvValue {
    /**
     * Append the encoded bytes for this item to the byte array builder.
     *
     * <p>This includes the tag value, and field length.
     *
     * @param arrayBuilder the builder to append to.
     */
    void appendBytesToBuilder(ArrayBuilder arrayBuilder);
}
