// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.LoggerChecks;
import ${listItemTypePackage}.${listItemType};
import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ${nameSentenceCase} */
public class ${nameSentenceCase}Test extends LoggerChecks {

    public ${nameSentenceCase}Test () {
        super(${listItemType}.class);
    }

    @Test
    public void displayName() {
        ${nameSentenceCase} uut = new ${nameSentenceCase}(new HashMap<>());
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
    }

    @Test
    public void displayableValue() {
        ${nameSentenceCase} uut = new ${nameSentenceCase}(new HashMap<>());
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
/*
        verifyNoLoggerMessages();
        ${nameSentenceCase} uut = new ${nameSentenceCase}(new byte[]{0x03, 0x7F, 0x01, 0x00}, 0, 4);
        verifySingleLoggerMessage("Unknown MIMD ${listItemType} Metadata tag: 127");
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
*/
    }

    @Test
    public void fromBytesUnknownValue() throws KlvParseException {
/*
        verifyNoLoggerMessages();
        ${nameSentenceCase} uut = ${nameSentenceCase}.fromBytes(new byte[]{0x03, 0x7F, 0x01, 0x00});
        verifySingleLoggerMessage("Unknown MIMD ${listItemType} Metadata tag: 127");
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
*/
    }

    @Test
    public void toBytesZLE() throws KlvParseException {
/*
        ${listItemType} item = new ${listItemType}(new HashMap<>());
        Map<${listItemType}Identifier, ${listItemType}> items = new HashMap<>();
        items.put(new ${listItemType}Identifier(1), item);
        ${nameSentenceCase} uut = new ${nameSentenceCase}(items);
        assertEquals(uut.getBytes(), new byte[]{0x00});
*/
    }

    @Test
    public void identifiersZLE() throws KlvParseException {
/*
        ${listItemType} item = new ${listItemType}(new HashMap<>());
        Map<${listItemType}Identifier, ${listItemType}> items = new HashMap<>();
        items.put(new ${listItemType}Identifier(1), item);
        ${nameSentenceCase} uut = new ${nameSentenceCase}(items);
        assertEquals(uut.getIdentifiers().size(), 1);
        assertEquals(uut.getField(new ${listItemType}Identifier(1)), item);
*/
    }
}

