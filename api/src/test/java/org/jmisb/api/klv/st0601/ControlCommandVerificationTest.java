package org.jmisb.api.klv.st0601;

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

public class ControlCommandVerificationTest
{
    private final byte[] ST_EXAMPLE_BYTES = new byte[]{(byte)0x03, (byte)0x07};

    @Test
    public void testConstructFromValue()
    {
        // From ST:
        List<Integer> ids = new ArrayList<>();
        ids.add(3);
        ids.add(7);
        ControlCommandVerification controlCommandVerification = new ControlCommandVerification(ids);
        checkValuesForExample(controlCommandVerification);
    }

    @Test
    public void testConstructFromEncoded()
    {
        ControlCommandVerification controlCommandVerification = new ControlCommandVerification(ST_EXAMPLE_BYTES);
        checkValuesForExample(controlCommandVerification);
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.ControlCommandVerification, ST_EXAMPLE_BYTES);
        assertTrue(v instanceof ControlCommandVerification);
        ControlCommandVerification controlCommandVerification = (ControlCommandVerification)v;
        checkValuesForExample(controlCommandVerification);
    }

    private void checkValuesForExample(ControlCommandVerification controlCommandVerification)
    {
        assertEquals(controlCommandVerification.getCommandIds().size(), 2);
        assertEquals(controlCommandVerification.getCommandIds().get(0).intValue(), 3);
        assertEquals(controlCommandVerification.getCommandIds().get(1).intValue(), 7);
        assertEquals(controlCommandVerification.getBytes(), ST_EXAMPLE_BYTES);
        assertEquals(controlCommandVerification.getDisplayableValue(), "3,7");
        assertEquals(controlCommandVerification.getDisplayName(), "Control Command Verification");
    }
}
