// Generated file - changes will be lost on rebuild
package ${packageName};

import static org.testng.Assert.*;

import java.util.Collections;
import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for ${nameSentenceCase} */
public class ${nameSentenceCase}Test {

    @Test
    public void displayName() {
        ${nameSentenceCase} uut = new ${nameSentenceCase}("x");
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
    }

    @Test
    public void displayableValue() {
        ${nameSentenceCase} uut = new ${nameSentenceCase}("x");
        assertEquals(uut.getDisplayableValue(), "x");
    }

    @Test
    public void fromBytes() {
        ${nameSentenceCase} uut = new ${nameSentenceCase}(new byte[] {0x48, 0x45, 0x6c, 0x6c, 0x6f});
        assertEquals(uut.getDisplayableValue(), "HEllo");
    }

    @Test
    public void fromValueMaxLength() {
        String s = String.join("", Collections.nCopies(${maxLength}, String.valueOf('z')));
        ${nameSentenceCase} uut = new ${nameSentenceCase}(s);
        assertEquals(uut.getDisplayableValue().length(), ${maxLength});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void fromValueTooLong() {
        String s = String.join("", Collections.nCopies(${maxLength + 1}, String.valueOf('z')));
        new ${nameSentenceCase}(s);
    }

    @Test
    public void getBytes1() {
        ${nameSentenceCase} uut = new ${nameSentenceCase}("x");
        assertEquals(uut.getBytes(), new byte[]{0x78});
    }

    @Test
    public void getBytes5() {
        ${nameSentenceCase} uut = new ${nameSentenceCase}("HEllo");
        assertEquals(uut.getBytes(), new byte[]{0x48, 0x45, 0x6c, 0x6c, 0x6f});
    }
}
