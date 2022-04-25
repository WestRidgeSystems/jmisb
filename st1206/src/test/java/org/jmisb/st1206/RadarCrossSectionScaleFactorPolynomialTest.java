package org.jmisb.st1206;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for SARMI Radar Cross Section Scale Factor Polynomial Test (ST1206 Tag 22) */
public class RadarCrossSectionScaleFactorPolynomialTest {

    private final byte[] bytes =
            new byte[] {
                (byte) 0x02,
                (byte) 0x01,
                (byte) 0x02,
                (byte) 0x03,
                (byte) 0x02,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x41,
                (byte) 0x2e,
                (byte) 0x84,
                (byte) 0x80,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0xf0,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x50
            };

    @Test
    public void testConstructFromValue() {
        RadarCrossSectionScaleFactorPolynomial uut =
                new RadarCrossSectionScaleFactorPolynomial(new double[][] {{30.0, 10.0}});
        assertEquals(uut.getBytes(), bytes);
        assertEquals(uut.getDisplayName(), "Radar Cross Section Scale Factor Polynomial");
        assertEquals(uut.getDisplayableValue(), "[RCS Polynomial]");
        assertEquals(uut.getPolynomialValues(), new double[][] {{30.0, 10.0}});
    }

    @Test
    public void testConstructFromEncodedBytes() throws KlvParseException {
        RadarCrossSectionScaleFactorPolynomial uut =
                new RadarCrossSectionScaleFactorPolynomial(bytes);
        assertEquals(uut.getBytes(), bytes);
        assertEquals(uut.getDisplayName(), "Radar Cross Section Scale Factor Polynomial");
        assertEquals(uut.getDisplayableValue(), "[RCS Polynomial]");
        assertEquals(uut.getPolynomialValues(), new double[][] {{30.0, 10.0}});
    }

    @Test
    public void testFactory() throws KlvParseException {
        ISARMIMetadataValue value =
                SARMILocalSet.createValue(
                        SARMIMetadataKey.RadarCrossSectionScaleFactorPolynomial, bytes);
        assertTrue(value instanceof RadarCrossSectionScaleFactorPolynomial);
        RadarCrossSectionScaleFactorPolynomial uut = (RadarCrossSectionScaleFactorPolynomial) value;
        assertEquals(uut.getBytes(), bytes);
        assertEquals(uut.getDisplayName(), "Radar Cross Section Scale Factor Polynomial");
        assertEquals(uut.getDisplayableValue(), "[RCS Polynomial]");
        assertEquals(uut.getPolynomialValues(), new double[][] {{30.0, 10.0}});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void badArrayLength() throws KlvParseException {
        new RadarCrossSectionScaleFactorPolynomial(new byte[] {0x01, 0x02, 0x03});
    }
}
