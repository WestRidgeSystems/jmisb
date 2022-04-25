package org.jmisb.st0602;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.Ber;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;

/**
 * Rendering Z-Order.
 *
 * <p>The Z-Order defines the order in which annotations are rendered. In any given frame,
 * annotations are rendered in order from lowest to highest Z-Order.
 */
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
     * @throws KlvParseException if the field length is not consistent with BER-OID encoding, or the
     *     BER-OID value is unreasonably large.
     */
    public ZOrder(byte[] bytes) throws KlvParseException {
        BerField field = BerDecoder.decode(bytes, 0, true);
        if (field.getLength() != bytes.length) {
            throw new KlvParseException("Inconsistent length for BER-OID encoded Z-Order");
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
