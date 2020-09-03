// Generated file - changes will be lost on rebuild
package ${packagename};

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
        // Assumes that the UInt encoding is always a single byte.
        assertEquals(uut.getBytes(), new byte[]{${entry.number}});
    }

</#list>
}