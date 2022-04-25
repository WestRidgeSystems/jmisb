package org.jmisb.api.klv;

import java.util.Set;

public class NothingMessage2 implements IMisbMessage {

    // This isn't real - only used for unit testing.
    public static final UniversalLabel UNIVERSAL_LABEL =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                        0x00, 0x00, 0x00, 0x02
                    });

    @Override
    public UniversalLabel getUniversalLabel() {
        return UNIVERSAL_LABEL;
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String displayHeader() {
        return "Nothing Message 2";
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
