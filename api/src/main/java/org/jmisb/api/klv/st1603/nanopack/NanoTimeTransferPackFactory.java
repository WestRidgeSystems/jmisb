package org.jmisb.api.klv.st1603.nanopack;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessageFactory;
import org.jmisb.api.klv.UniversalLabel;

/** Factory method for NanoTimeTransferPack. */
public class NanoTimeTransferPackFactory implements IMisbMessageFactory {

    @Override
    public NanoTimeTransferPack create(byte[] bytes) throws KlvParseException {
        return new NanoTimeTransferPack(bytes);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return NanoTimeTransferPack.NanoTimeTransferPackUl;
    }
}
