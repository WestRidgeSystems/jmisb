package org.jmisb.api.klv.st1904;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Additional unit tests for SDCC. */
public class AdditionalSDCCTest {

    public AdditionalSDCCTest() {}

    @Test(expectedExceptions = KlvParseException.class)
    public void checkFromBytesConstructorBadSDCC() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    (byte) 0x21,
                    (byte) 0x07,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x01,
                    (byte) 0x40,
                    (byte) 0x49,
                    (byte) 0x16
                };
        new SDCC(bytes, 0, 9);
    }
}
