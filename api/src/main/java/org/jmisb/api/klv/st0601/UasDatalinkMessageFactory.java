package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessageFactory;
import org.jmisb.api.klv.UniversalLabel;

/** Factory method for UasDatalinkMessages. */
public class UasDatalinkMessageFactory implements IMisbMessageFactory {

    @Override
    public UniversalLabel getUniversalLabel() {
        return UasDatalinkMessage.UasDatalinkLocalUl;
    }

    @Override
    public UasDatalinkMessage create(byte[] bytes) throws KlvParseException {
        return new UasDatalinkMessage(bytes);
    }
}
