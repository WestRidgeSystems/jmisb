// Generated file - changes will be lost on rebuild
package ${packageName};

import org.jmisb.api.common.KlvParseException;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ${nameSentenceCase} */
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
        ${nameSentenceCase} uut = new ${nameSentenceCase}(new byte[] {
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
        ${nameSentenceCase} uut = new ${nameSentenceCase}(new byte[] {
                    (byte) 0x40,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00});
        assertEquals(uut.getDisplayableValue(), "2.000 ${units}");
    }

    @Test (expectedExceptions = KlvParseException.class)
    public void fromBytesBadLength() throws KlvParseException {
        ${nameSentenceCase} uut = new ${nameSentenceCase}(new byte[] {
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
}
