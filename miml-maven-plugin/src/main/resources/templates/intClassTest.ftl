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
        assertEquals(uut.getBytes(), new byte[]{(byte)0x00, (byte)0xFF});
    }

    @Test (expectedExceptions = KlvParseException.class)
    public void fromBytesBadLengthConstructor() throws KlvParseException {
        ${nameSentenceCase} uut = new ${nameSentenceCase}(new byte[] {});
    }

    @Test (expectedExceptions = KlvParseException.class)
    public void fromBytesBadLength() throws KlvParseException {
        ${nameSentenceCase} uut = ${nameSentenceCase}.fromBytes(new byte[] {});
    }
}
