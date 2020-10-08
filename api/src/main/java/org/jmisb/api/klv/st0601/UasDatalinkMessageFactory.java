package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessageFactory;

/** Factory method for UasDatalinkMessages. */
public class UasDatalinkMessageFactory implements IMisbMessageFactory {
    @Override
    public UasDatalinkMessage create(byte[] bytes) throws KlvParseException {
        return new UasDatalinkMessage(bytes);
    }
}
