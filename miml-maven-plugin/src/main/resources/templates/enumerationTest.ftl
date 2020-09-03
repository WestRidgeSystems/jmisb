// Generated file - changes will be lost on rebuild
package ${packagename};

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ${name}. */
public class ${name}Test {

    @Test
    public void UnknownEnumDisplayName() {
        ${name} uut = ${name}.Unknown;
        assertEquals(uut.getDisplayName(), "${name}");
    }

    @Test
    public void UnknownEnumDisplayableValue() {
        ${name} uut = ${name}.Unknown;
        assertEquals(uut.getDisplayableValue(), "Unknown");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void serialiseUnknown() {
        ${name} uut = ${name}.Unknown;
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