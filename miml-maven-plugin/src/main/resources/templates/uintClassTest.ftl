<#setting number_format="computer">
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import org.jmisb.api.common.KlvParseException;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ${nameSentenceCase} */
public class ${nameSentenceCase}Test {

    @Test
    public void displayName() {
        ${nameSentenceCase} uut = new ${nameSentenceCase}(0);
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
    }

    @Test
    public void displayableValue() {
        ${nameSentenceCase} uut = new ${nameSentenceCase}(0);
        assertEquals(uut.getDisplayableValue(), "0 ${units}");
    }

    @Test
    public void fromBytes1() throws KlvParseException {
        ${nameSentenceCase} uut = ${nameSentenceCase}.fromBytes(new byte[] {0x01});
        assertEquals(uut.getDisplayableValue(), "1 ${units}");
    }

    @Test
    public void fromBytesConstructor1() throws KlvParseException {
        ${nameSentenceCase} uut = new ${nameSentenceCase}(new byte[] {0x01});
        assertEquals(uut.getDisplayableValue(), "1 ${units}");
    }

    @Test
    public void getBytes1() {
        ${nameSentenceCase} uut = new ${nameSentenceCase}(1);
        assertEquals(uut.getBytes(), new byte[]{0x01});
    }

    @Test
    public void getBytes255() {
        ${nameSentenceCase} uut = new ${nameSentenceCase}(255);
        assertEquals(uut.getBytes(), new byte[]{(byte)0xFF});
    }

    @Test (expectedExceptions = KlvParseException.class)
    public void fromBytesBadLengthConstructor() throws KlvParseException {
        ${nameSentenceCase} uut = new ${nameSentenceCase}(new byte[] {});
    }

    @Test (expectedExceptions = KlvParseException.class)
    public void fromBytesBadLength() throws KlvParseException {
        ${nameSentenceCase} uut = ${nameSentenceCase}.fromBytes(new byte[] {});
    }

    @Test (expectedExceptions = KlvParseException.class)
    public void fromBytesBadLengthTooLong() throws KlvParseException {
        ${nameSentenceCase} uut = ${nameSentenceCase}.fromBytes(new byte[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
    }

<#if minValue??>
    @Test
    public void minValue() throws KlvParseException {
        ${nameSentenceCase} uut = new ${nameSentenceCase}(${minValue});
        assertEquals(uut.getValue(), ${minValue});
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void minValueTooSmall() throws KlvParseException {
        new ${nameSentenceCase}(${minValue} - 1);
    }
<#else>
    @Test (expectedExceptions = IllegalArgumentException.class)
    public void tooSmall() throws KlvParseException {
        new ${nameSentenceCase}(-1);
    }
</#if>

<#if maxValue??>
    @Test
    public void maxValue() throws KlvParseException {
        ${nameSentenceCase} uut = new ${nameSentenceCase}(${maxValue});
        assertEquals(uut.getValue(), ${maxValue});
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void maxValueTooLarge() throws KlvParseException {
        new ${nameSentenceCase}(${maxValue} + 1);
    }
</#if>
}
