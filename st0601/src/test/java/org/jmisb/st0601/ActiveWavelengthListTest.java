package org.jmisb.st0601;

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ActiveWavelengthListTest {
    private final byte[] ST_EXAMPLE_BYTES = new byte[] {(byte) 0x01, (byte) 0x03};

    @Test
    public void testConstructFromValue() {
        List<Integer> activeWavelengths = new ArrayList<>();
        activeWavelengths.add(1);
        activeWavelengths.add(3);
        ActiveWavelengthList activeWavelengthList = new ActiveWavelengthList(activeWavelengths);
        checkValuesForExample(activeWavelengthList);
    }

    @Test
    public void testConstructFromEncoded() {
        ActiveWavelengthList activeWavelengthList = new ActiveWavelengthList(ST_EXAMPLE_BYTES);
        checkValuesForExample(activeWavelengthList);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(
                        UasDatalinkTag.ActiveWavelengthList, ST_EXAMPLE_BYTES);
        Assert.assertTrue(v instanceof ActiveWavelengthList);
        ActiveWavelengthList activeWavelengthList = (ActiveWavelengthList) v;
        checkValuesForExample(activeWavelengthList);
    }

    private void checkValuesForExample(ActiveWavelengthList activeWavelengthList) {
        Assert.assertEquals(activeWavelengthList.getWavelengthIdentifiers().size(), 2);
        int identifier0 = activeWavelengthList.getWavelengthIdentifiers().get(0);
        Assert.assertEquals(identifier0, 1);
        int identifier1 = activeWavelengthList.getWavelengthIdentifiers().get(1);
        Assert.assertEquals(identifier1, 3);
        Assert.assertEquals(activeWavelengthList.getBytes(), ST_EXAMPLE_BYTES);
        Assert.assertEquals(activeWavelengthList.getDisplayableValue(), "1,3");
        Assert.assertEquals(activeWavelengthList.getDisplayName(), "Active Wavelength List");
    }
}
