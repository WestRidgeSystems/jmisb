package org.jmisb.api.klv.st0602;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for ST 0602 MIME Data implementation. */
public class MIMEDataTest {

    public MIMEDataTest() {}

    @Test
    public void checkConstruction() {
        MIMEData uut = new MIMEData(new byte[] {0x01, 0x02, 0x03});
        assertEquals(uut.getDisplayName(), "MIME Data");
        assertEquals(uut.getDisplayableValue(), "byte[3]");
        assertEquals(uut.getBytes(), new byte[] {0x01, 0x02, 0x03});
    }

    @Test
    public void checkConstructionFromFactory() throws KlvParseException {
        IAnnotationMetadataValue value =
                UniversalSetFactory.createValue(
                        AnnotationMetadataKey.MIMEData, new byte[] {0x01, 0x02, 0x03});
        assertTrue(value instanceof MIMEData);
        MIMEData uut = (MIMEData) value;
        assertEquals(uut.getDisplayName(), "MIME Data");
        assertEquals(uut.getDisplayableValue(), "byte[3]");
        assertEquals(uut.getBytes(), new byte[] {0x01, 0x02, 0x03});
    }
}
