package org.jmisb.api.klv.st0602;

import org.jmisb.api.klv.Ber;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;

public class ZOrder implements IAnnotationMetadataValue {

    private int value;
    /**
     * Create from value.
     *
     * @param zorder the z-order value
     */
    public ZOrder(int zorder) {
        this.value = zorder;
    }

    /**
     * Construct from byte array.
     *
     * @param bytes the BER-OID encoded integer z-order value
     */
    public ZOrder(byte[] bytes) {
        BerField field = BerDecoder.decode(bytes, 0, true);
        if (field.getLength() != bytes.length) {
            throw new IllegalArgumentException("Inconsistent length for BER-OID encoded Z-Order");
        }
        value = field.getValue();
    }

    @Override
    public byte[] getBytes() {
        return BerEncoder.encode(value, Ber.OID);
    }

    @Override
    public String getDisplayName() {
        return "Z-Order";
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d", value);
    }
}
