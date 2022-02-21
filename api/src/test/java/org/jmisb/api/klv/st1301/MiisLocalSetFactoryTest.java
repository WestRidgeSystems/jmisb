package org.jmisb.api.klv.st1301;

import static org.jmisb.api.klv.st1301.MiisLocalSetTest.bytes;
import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for MiisLocalSetFactory */
public class MiisLocalSetFactoryTest {

    public MiisLocalSetFactoryTest() {}

    @Test
    public void parseTags() throws KlvParseException {
        MiisLocalSetFactory uut = new MiisLocalSetFactory();
        MiisLocalSet localSet = (MiisLocalSet) uut.create(MiisLocalSetTest.bytes);
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST 1301 MIIS Augmentation Identifiers");
        assertEquals(localSet.getUniversalLabel(), MiisMetadataConstants.MiisLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 2);
        MiisLocalSetTest.checkVersionExample(localSet);
        MiisLocalSetTest.checkCoreIdentifierExample(localSet);
        assertEquals(localSet.frameMessage(false), bytes);
    }
}
