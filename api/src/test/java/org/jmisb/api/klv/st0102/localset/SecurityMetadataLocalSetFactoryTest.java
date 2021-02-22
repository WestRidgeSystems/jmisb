package org.jmisb.api.klv.st0102.localset;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.LoggerChecks;
import org.jmisb.api.klv.st0102.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SecurityMetadataLocalSetFactoryTest extends LoggerChecks {
    private SecurityMetadataLocalSet localSet;
    private static final byte[] ItemDesignatorIdValue =
            new byte[] {
                (byte) 0x00,
                (byte) 0x01,
                (byte) 0x02,
                (byte) 0x03,
                (byte) 0x04,
                (byte) 0x05,
                (byte) 0x06,
                (byte) 0x07,
                (byte) 0x08,
                (byte) 0x09,
                (byte) 0x0A,
                (byte) 0x0B,
                (byte) 0x0C,
                (byte) 0x0D,
                (byte) 0x0E,
                (byte) 0x0F
            };

    public SecurityMetadataLocalSetFactoryTest() {
        super(SecurityMetadataLocalSet.class);
    }

    @Test
    public void testParse() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x06, 0x0e, 0x2b, 0x34, 0x02, 0x03, 0x01, 0x01, 0x0e, 0x01, 0x03, 0x03, 0x02,
                    0x00, 0x00, 0x00, 0x12, 0x15, 0x10, 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06,
                    0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F
                };
        SecurityMetadataLocalSetFactory factory = new SecurityMetadataLocalSetFactory();
        SecurityMetadataLocalSet securityMetadataLocalSet = factory.create(bytes);
        Assert.assertEquals(securityMetadataLocalSet.displayHeader(), "ST 0102 (local)");
        Assert.assertNotNull(
                securityMetadataLocalSet.getField(SecurityMetadataKey.ItemDesignatorId));
        ISecurityMetadataValue val =
                securityMetadataLocalSet.getField(SecurityMetadataKey.ItemDesignatorId);
        Assert.assertTrue(val instanceof ItemDesignatorId);
        ItemDesignatorId id = (ItemDesignatorId) val;
        Assert.assertEquals(id.getItemDesignatorId(), ItemDesignatorIdValue);
    }
}
