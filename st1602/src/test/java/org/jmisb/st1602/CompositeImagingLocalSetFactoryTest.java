package org.jmisb.st1602;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.MisbMessageFactory;
import org.testng.annotations.Test;

/** Unit tests for CompositeImagingLocalSetFactory. */
public class CompositeImagingLocalSetFactoryTest {

    public CompositeImagingLocalSetFactoryTest() {}

    @Test
    public void checkFromBytesNonNested() throws KlvParseException {
        CompositeImagingLocalSetFactory uut = new CompositeImagingLocalSetFactory();
        CompositeImagingLocalSet localSet =
                uut.create(CompositeImagingLocalSetFullTest.LOCAL_SET_BYTES);
        assertEquals(
                localSet.getUniversalLabel(), CompositeImagingLocalSet.CompositeImagingLocalSetUl);
        CompositeImagingLocalSetFullTest.checkLocalSetValues(localSet);
    }

    @Test
    public void checkFromBytesNonNestedService() throws KlvParseException {
        MisbMessageFactory messageFactory = MisbMessageFactory.getInstance();
        IMisbMessage message =
                messageFactory.handleMessage(CompositeImagingLocalSetFullTest.LOCAL_SET_BYTES);
        assertTrue(message instanceof CompositeImagingLocalSet);
        CompositeImagingLocalSet localSet = (CompositeImagingLocalSet) message;
        assertEquals(
                localSet.getUniversalLabel(), CompositeImagingLocalSet.CompositeImagingLocalSetUl);
        CompositeImagingLocalSetFullTest.checkLocalSetValues(localSet);
    }
}
