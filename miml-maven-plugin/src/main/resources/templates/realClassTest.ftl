<#setting number_format="computer">
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import org.jmisb.api.common.KlvParseException;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ${nameSentenceCase}. */
public class ${nameSentenceCase}Test {

    @Test
    public void displayName() {
        ${nameSentenceCase} uut = new ${nameSentenceCase}(0.0);
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
    }

    @Test
    public void displayableValue() {
        ${nameSentenceCase} uut = new ${nameSentenceCase}(0.0);
        assertEquals(uut.getDisplayableValue(), "0.000 ${units}");
    }

    @Test
    public void fromBytes8() throws KlvParseException {
        ${nameSentenceCase} uut = ${nameSentenceCase}.fromBytes(new byte[] {
                    (byte) 0x40,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00});
        assertEquals(uut.getDisplayableValue(), "2.000 ${units}");
    }

    @Test
    public void fromBytesConstructor8() throws KlvParseException {
        ${nameSentenceCase} uut = new ${nameSentenceCase}(new byte[] {
                    (byte) 0x40,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00});
        assertEquals(uut.getDisplayableValue(), "2.000 ${units}");
    }

    @Test
    public void fromBytes8Negative() throws KlvParseException {
        ${nameSentenceCase} uut = ${nameSentenceCase}.fromBytes(new byte[] {
                    (byte) 0xC0,
                    (byte) 0x10,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00});
        assertEquals(uut.getDisplayableValue(), "-4.000 ${units}");
    }

    @Test
    public void fromBytes4() throws KlvParseException {
        ${nameSentenceCase} uut = ${nameSentenceCase}.fromBytes(new byte[] {
                    (byte) 0x40,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00});
        assertEquals(uut.getDisplayableValue(), "2.000 ${units}");
    }

    @Test (expectedExceptions = KlvParseException.class)
    public void fromBytesBadLengthConstructor() throws KlvParseException {
        ${nameSentenceCase} uut = new ${nameSentenceCase}(new byte[] {
                    (byte) 0x40,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00});
    }

    @Test (expectedExceptions = KlvParseException.class)
    public void fromBytesBadLength() throws KlvParseException {
        ${nameSentenceCase} uut = ${nameSentenceCase}.fromBytes(new byte[] {
                    (byte) 0x40,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00});
    }

    @Test
    public void getBytes() {
        ${nameSentenceCase} uut = new ${nameSentenceCase}(2.0);
        assertEquals(uut.getBytes(), new byte[] {
                    (byte) 0x40,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00});
    }

<#if minValue??>
    @Test
    public void fromValueMin() {
        ${nameSentenceCase} uut = new ${nameSentenceCase}(${minValue});
        assertNotNull(uut);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void fromValueMinOutOfRange() {
        new ${nameSentenceCase}(${minValue} - 0.5);
    }
</#if>

<#if maxValue??>
    @Test
    public void fromValueMax() {
        ${nameSentenceCase} uut = new ${nameSentenceCase}(${maxValue});
        assertNotNull(uut);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void fromValueMaxOutOfRange() {
        new ${nameSentenceCase}(${maxValue} + 0.5);
    }
</#if>
}
