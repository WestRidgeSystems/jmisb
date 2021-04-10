// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for ${namespacedName}. */
public class ${namespacedName}Test {

    public ${namespacedName}Test() {}

    @Test
    public void fromListSingle() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new int[] {64});
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
        assertEquals(uut.getDisplayableValue(), "(64)");
        assertEquals(uut.getBytes(), new byte[] {0x40});
        assertEquals(uut.getValues(), new int[] {64});
    }

    @Test
    public void fromListMultiple() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new int[] {64, 3, 8});
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
        assertEquals(uut.getDisplayableValue(), "(64, 3, 8)");
        assertEquals(uut.getBytes(), new byte[] {0x40, 0x03, 0x08});
    }

    @Test
    public void fromBytesSingle() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new byte[] {0x40});
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
        assertEquals(uut.getDisplayableValue(), "(64)");
        assertEquals(uut.getBytes(), new byte[] {0x40});
        assertEquals(uut.getValues(), new int[] {64});
    }

    @Test
    public void fromBytesMultiple() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new byte[] {0x40, 0x03, 0x08});
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
        assertEquals(uut.getDisplayableValue(), "(64, 3, 8)");
        assertEquals(uut.getBytes(), new byte[] {0x40, 0x03, 0x08});
    }
}
