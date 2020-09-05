// Generated file - changes will be lost on rebuild
package ${packageName};

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import ${listItemTypePackage}.${listItemType};
import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ${nameSentenceCase} */
public class ${nameSentenceCase}Test {

    @Test
    public void displayName() {
        ${nameSentenceCase} uut = new ${nameSentenceCase}(new ArrayList<>());
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
    }

    @Test
    public void displayableValue() {
        ${nameSentenceCase} uut = new ${nameSentenceCase}(new ArrayList<>());
        assertEquals(uut.getDisplayableValue(), "[${nameSentenceCase}]");
    }

    @Test
    public void fromBytesConstructorZeroLength() throws KlvParseException {
        ${nameSentenceCase} uut = new ${nameSentenceCase}(new byte[]{}, 0, 0);
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
    }

    @Test
    public void fromBytesConstructorUnknownValue() throws KlvParseException {
        // TODO: check logger message
        ${nameSentenceCase} uut = new ${nameSentenceCase}(new byte[]{0x03, 0x7F, 0x01, 0x00}, 0, 4);
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
    }

    @Test
    public void fromBytesUnknownValue() throws KlvParseException {
        // TODO: check logger message
        ${nameSentenceCase} uut = ${nameSentenceCase}.fromBytes(new byte[]{0x03, 0x7F, 0x01, 0x00});
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
    }

    @Test
    public void toBytesZLE() throws KlvParseException {
        ${listItemType} item = new ${listItemType}(new HashMap<>());
        List<${listItemType}> items = new ArrayList<>();
        items.add(item);
        ${nameSentenceCase} uut = new ${nameSentenceCase}(items);
        assertEquals(uut.getBytes(), new byte[]{0x00});
    }
}

