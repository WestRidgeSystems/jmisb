package org.jmisb.api.klv;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit test for MisbMessageFactory. */
public class MisbMessageFactoryTest {

    @Test
    @SuppressWarnings("deprecation")
    public void checkFactoryLoad() throws KlvParseException {
        MisbMessageFactory uut = MisbMessageFactory.getInstance();
        assertNotNull(uut);
        uut.registerHandler(NothingMessage1.UNIVERSAL_LABEL, new NothingFactory1());
        uut.registerHandler(NothingMessage2.UNIVERSAL_LABEL, new NothingFactory2());
        IMisbMessage message = uut.handleMessage(NothingMessage1.UNIVERSAL_LABEL.getBytes());
        assertTrue(message instanceof NothingMessage1);
        assertEquals(message.displayHeader(), "Nothing Message 1");
        message = uut.handleMessage(NothingMessage2.UNIVERSAL_LABEL.getBytes());
        assertTrue(message instanceof NothingMessage2);
        assertEquals(message.displayHeader(), "Nothing Message 2");
    }
}
