// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ${name}MetadataKey. */
public class ${name}MetadataKeyTest {

    @Test
    public void UnknownEnumDisplayName() {
        ${name}MetadataKey uut = ${name}MetadataKey.Undefined;
        assertEquals(uut.getIdentifier(), -1);
    }

    @Test
    public void lookupUnknown() {
        ${name}MetadataKey uut = ${name}MetadataKey.getKey(-1);
        assertEquals(uut, ${name}MetadataKey.Undefined);
    }

    @Test
    public void lookupUnknown9999() {
        ${name}MetadataKey uut = ${name}MetadataKey.getKey(9999);
        assertEquals(uut, ${name}MetadataKey.Undefined);
    }

<#list entries as entry>
    @Test
    public void ${entry.name}Identifier() {
        ${name}MetadataKey uut = ${name}MetadataKey.${entry.name};
        assertEquals(uut.getIdentifier(), ${entry.number});
    }

    @Test
    public void ${entry.name}GetName() {
        ${name}MetadataKey uut = ${name}MetadataKey.getKey(${entry.number});
        assertEquals(uut, ${name}MetadataKey.${entry.name});
    }

    @Test
    public void ${entry.name}Deprecated() {
        ${name}MetadataKey uut = ${name}MetadataKey.${entry.name};
        assertEquals(uut.isDeprecated(), ${entry.deprecated?c});
    }

</#list>
}
