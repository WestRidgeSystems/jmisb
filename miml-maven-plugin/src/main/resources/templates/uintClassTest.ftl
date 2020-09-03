// Generated file - changes will be lost on rebuild
package ${packageName};

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
    public void fromBytes1() {
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
}
