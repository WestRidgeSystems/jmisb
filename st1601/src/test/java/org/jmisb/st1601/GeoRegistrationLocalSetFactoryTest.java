package org.jmisb.st1601;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.MisbMessageFactory;
import org.testng.annotations.Test;

/** Unit tests for GeoRegistrationLocalSetFactory. */
public class GeoRegistrationLocalSetFactoryTest {

    public GeoRegistrationLocalSetFactoryTest() {}

    @Test
    public void checkFromBytesNonNested() throws KlvParseException {
        GeoRegistrationLocalSetFactory uut = new GeoRegistrationLocalSetFactory();
        GeoRegistrationLocalSet localSet =
                uut.create(GeoRegistrationLocalSetTwoImageTest.BYTES_NON_NESTED);
        assertEquals(
                localSet.getUniversalLabel(), GeoRegistrationLocalSet.GeoRegistrationLocalSetUl);
        GeoRegistrationLocalSetTwoImageTest.checkLocalSet(localSet);
    }

    @Test
    public void checkFromBytesNonNestedService() throws KlvParseException {
        MisbMessageFactory messageFactory = MisbMessageFactory.getInstance();
        IMisbMessage message =
                messageFactory.handleMessage(GeoRegistrationLocalSetTwoImageTest.BYTES_NON_NESTED);
        assertTrue(message instanceof GeoRegistrationLocalSet);
        GeoRegistrationLocalSet localSet = (GeoRegistrationLocalSet) message;
        assertEquals(
                localSet.getUniversalLabel(), GeoRegistrationLocalSet.GeoRegistrationLocalSetUl);
        GeoRegistrationLocalSetTwoImageTest.checkLocalSet(localSet);
    }
}
