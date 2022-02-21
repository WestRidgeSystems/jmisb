package org.jmisb.api.klv.st1301;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.IMisbMessageFactory;
import org.jmisb.api.klv.UniversalLabel;

/**
 * Factory method for {@link MiisLocalSet}.
 *
 * <p>This is used to link the ST 1301 Motion Imagery Identification System (MIIS) Local Set
 * handling into the wider jmisb implementation, and is not usually required directly.
 */
public class MiisLocalSetFactory implements IMisbMessageFactory {

    @Override
    public IMisbMessage create(byte[] bytes) throws KlvParseException {
        return new MiisLocalSet(bytes);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return MiisMetadataConstants.MiisLocalSetUl;
    }
}
