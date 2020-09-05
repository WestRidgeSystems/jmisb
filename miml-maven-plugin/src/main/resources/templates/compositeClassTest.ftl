// Generated file - changes will be lost on rebuild
package ${packageName};

import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st190x.IMimdMetadataValue;
import org.jmisb.api.klv.st190x.MimdIdReference;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ${name} */
public class ${name}Test {

    @Test
    public void displayName() {
        ${name} uut = new ${name}(new TreeMap<>());
        assertEquals(uut.getDisplayName(), "${name}");
    }

    @Test
    public void displayableValue() {
        ${name} uut = new ${name}(new TreeMap<>());
        assertEquals(uut.getDisplayableValue(), "[${name}]");
    }

    @Test
    public void createValueUndefined() throws KlvParseException {
        // TODO: logger checks
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.Undefined, new byte[]{0x00});
        assertNull(uut);
    }

<#list entries as entry>
    @Test
    public void createValue${entry.nameSentenceCase}() throws KlvParseException {
<#if entry.typeName == "String">
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{0x74});
        assertTrue(uut instanceof ${entry.nameSentenceCase});
        ${entry.nameSentenceCase} value = (${entry.nameSentenceCase})uut;
        assertEquals(value.getValue(), "t");
<#elseif entry.typeName == "UInt">
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte)0xFF});
        assertTrue(uut instanceof ${entry.nameSentenceCase});
        ${entry.nameSentenceCase} value = (${entry.nameSentenceCase})uut;
        assertEquals(value.getValue(), 255);
<#elseif entry.typeName == "Integer">
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte)0xFF});
        assertTrue(uut instanceof ${entry.nameSentenceCase});
        ${entry.nameSentenceCase} value = (${entry.nameSentenceCase})uut;
        assertEquals(value.getValue(), -1);
<#elseif entry.typeName == "Real">
        IMimdMetadataValue uut = ${name}.createValue(
            ${name}MetadataKey.${entry.name},
            new byte[]{
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00});
        assertTrue(uut instanceof ${entry.nameSentenceCase});
        ${entry.nameSentenceCase} value = (${entry.nameSentenceCase})uut;
        assertEquals(value.getValue(), 2.000);
<#elseif entry.typeName?starts_with("REF\l")>
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte) 0x00});
        assertTrue(uut instanceof MimdIdReference);
<#else>
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte) 0x00});
        assertTrue(uut instanceof ${entry.nameSentenceCase});
</#if>
    }
</#list>
}
