package org.jmisb.api.klv.st0601;

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0601.dto.Wavelengths;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WavelengthsListTest {
    private final byte[] ST_EXAMPLE_BYTES =
            new byte[] {
                (byte) 0x0D,
                (byte) 0x15,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x07,
                (byte) 0xD0,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x0F,
                (byte) 0xA0,
                (byte) 0x4E,
                (byte) 0x4E,
                (byte) 0x49,
                (byte) 0x52
            };
    private final byte[] TWO_ENTRY_BYTES =
            new byte[] {
                (byte) 0x0D, (byte) 0x15, (byte) 0x00, (byte) 0x00, (byte) 0x07, (byte) 0xD0,
                        (byte) 0x00, (byte) 0x00, (byte) 0x0F, (byte) 0xA0, (byte) 0x4E,
                        (byte) 0x4E, (byte) 0x49, (byte) 0x52,
                (byte) 0x0D, (byte) 0x16, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0xF8,
                        (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0xe8, (byte) 0x42,
                        (byte) 0x4C, (byte) 0x55, (byte) 0x45,
            };
    private final int ST_EXAMPLE_ID = 21;
    private final double ST_EXAMPLE_MIN_WAVELENGTH = 1000;
    private final double ST_EXAMPLE_MAX_WAVELENGTH = 2000;
    private final String ST_EXAMPLE_NAME = "NNIR";

    @Test
    public void testConstructFromValue() {
        // From ST:
        List<Wavelengths> wavelengthsArrayList = new ArrayList<>();
        Wavelengths wavelengths = new Wavelengths();
        wavelengths.setId(ST_EXAMPLE_ID);
        wavelengths.setMin(ST_EXAMPLE_MIN_WAVELENGTH);
        wavelengths.setMax(ST_EXAMPLE_MAX_WAVELENGTH);
        wavelengths.setName(ST_EXAMPLE_NAME);
        wavelengthsArrayList.add(wavelengths);
        WavelengthsList wavelengthsList = new WavelengthsList(wavelengthsArrayList);
        checkValuesForExample(wavelengthsList);
    }

    @Test
    public void testConstructFromEncoded() throws KlvParseException {
        WavelengthsList wavelengthsList = new WavelengthsList(ST_EXAMPLE_BYTES);
        checkValuesForExample(wavelengthsList);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.WavelengthsList, ST_EXAMPLE_BYTES);
        Assert.assertTrue(v instanceof WavelengthsList);
        WavelengthsList wavelengthsList = (WavelengthsList) v;
        checkValuesForExample(wavelengthsList);
    }

    private void checkValuesForExample(WavelengthsList wavelengthsList) {
        Assert.assertEquals(wavelengthsList.getWavelengthsList().size(), 1);
        Wavelengths wavelengths = wavelengthsList.getWavelengthsList().get(0);
        Assert.assertNotNull(wavelengths);
        Assert.assertEquals(wavelengths.getId(), ST_EXAMPLE_ID);
        Assert.assertEquals(wavelengths.getMin(), ST_EXAMPLE_MIN_WAVELENGTH);
        Assert.assertEquals(wavelengths.getMax(), ST_EXAMPLE_MAX_WAVELENGTH);
        Assert.assertEquals(wavelengths.getName(), ST_EXAMPLE_NAME);
        Assert.assertEquals(wavelengthsList.getBytes(), ST_EXAMPLE_BYTES);
        Assert.assertEquals(wavelengthsList.getDisplayableValue(), "[Wavelengths]");
        Assert.assertEquals(wavelengthsList.getDisplayName(), "Wavelengths List");
    }

    @Test
    public void testConstructFromEncodedTwoEntries() throws KlvParseException {
        WavelengthsList wavelengthsList = new WavelengthsList(TWO_ENTRY_BYTES);
        Assert.assertEquals(wavelengthsList.getWavelengthsList().size(), 2);
        Wavelengths wavelengths0 = wavelengthsList.getWavelengthsList().get(0);
        Assert.assertNotNull(wavelengths0);
        Assert.assertEquals(wavelengths0.getId(), ST_EXAMPLE_ID);
        Assert.assertEquals(wavelengths0.getMin(), ST_EXAMPLE_MIN_WAVELENGTH);
        Assert.assertEquals(wavelengths0.getMax(), ST_EXAMPLE_MAX_WAVELENGTH);
        Assert.assertEquals(wavelengths0.getName(), ST_EXAMPLE_NAME);
        Wavelengths wavelengths1 = wavelengthsList.getWavelengthsList().get(1);
        Assert.assertNotNull(wavelengths1);
        Assert.assertEquals(wavelengths1.getId(), 22);
        Assert.assertEquals(wavelengths1.getMin(), 380);
        Assert.assertEquals(wavelengths1.getMax(), 500);
        Assert.assertEquals(wavelengths1.getName(), "BLUE");
        Assert.assertEquals(wavelengthsList.getBytes(), TWO_ENTRY_BYTES);
        Assert.assertEquals(wavelengthsList.getDisplayableValue(), "[Wavelengths]");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void fuzz1() throws KlvParseException {
        WavelengthsList wavelengthsList =
                new WavelengthsList(
                        new byte[] {0x06, (byte) 0x97, (byte) 0x81, (byte) 0xf1, 0x29, 0x7b, 0x42});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void fuzz2() throws KlvParseException {
        new WavelengthsList(
                new byte[] {
                    0x18,
                    (byte) 0x89,
                    (byte) 0x91,
                    0x04,
                    (byte) 0x9e,
                    (byte) 0x98,
                    0x5f,
                    (byte) 0x8b,
                    (byte) 0xb9,
                    0x35,
                    (byte) 0x9e,
                    0x6c,
                    (byte) 0xe6,
                    (byte) 0xac,
                    0x50
                });
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void fuzz3() throws KlvParseException {
        new WavelengthsList(new byte[] {0x56, (byte) 0x83, 0x42, (byte) 0xce});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void fuzz4() throws KlvParseException {
        new WavelengthsList(
                new byte[] {
                    0x00,
                    0x76,
                    (byte) 0xdb,
                    0x22,
                    (byte) 0xeb,
                    0x19,
                    0x37,
                    0x15,
                    (byte) 0xc1,
                    (byte) 0xb4
                });
    }
}
