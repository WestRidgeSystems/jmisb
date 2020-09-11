// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import org.jmisb.api.common.KlvParseException;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ${name}. */
public class ${name}Test {

    @Test
    public void UndefinedEnumDisplayName() {
        ${name} uut = ${name}.Undefined;
        assertEquals(uut.getDisplayName(), "${name}");
    }

    @Test
    public void UndefinedEnumDisplayableValue() {
        ${name} uut = ${name}.Undefined;
        assertEquals(uut.getDisplayableValue(), "Undefined");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void serialiseUndefined() {
        ${name} uut = ${name}.Undefined;
        uut.getBytes();
    }

    @Test
    public void UndefinedGetValue() {
        ${name} uut = ${name}.getValue(-1);
        assertEquals(uut, ${name}.Undefined);
    }

    @Test
    public void Undefined255GetValue() {
        ${name} uut = ${name}.getValue(255);
        assertEquals(uut, ${name}.Undefined);
    }

<#list entries as entry>
    @Test
    public void ${entry.name}DisplayName() {
        ${name} uut = ${name}.${entry.name};
        assertEquals(uut.getDisplayName(), "${name}");
    }

    @Test
    public void ${entry.name}DisplayableValue() {
        ${name} uut = ${name}.${entry.name};
        assertEquals(uut.getDisplayableValue(), "${entry.description}");
    }

    @Test
    public void ${entry.name}Serialise() {
        ${name} uut = ${name}.${entry.name};
        // Assumes that the enumeration encoding is always a single byte.
        assertEquals(uut.getBytes(), new byte[]{${entry.number}});
    }

    @Test
    public void ${entry.name}GetValue() {
        ${name} uut = ${name}.getValue(${entry.number});
        assertEquals(uut, ${name}.${entry.name});
    }

    @Test
    public void ${entry.name}FromBytes() throws KlvParseException {
        // Assumes that the enumeration encoding is always a single byte.
        ${name} uut = ${name}.fromBytes(new byte[]{${entry.number}});
        assertEquals(uut, ${name}.${entry.name});
    }
</#list>
}