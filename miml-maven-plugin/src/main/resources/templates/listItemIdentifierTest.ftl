// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ${listItemType}Identifier. */
public class ${listItemType}IdentifierTest {

    @Test
    public void constructFromInteger() {
        ${listItemType}Identifier uut = new ${listItemType}Identifier(1);
        assertEquals(uut.getIdentifier(), 1);
        assertEquals(uut.toString(), "${listItemType} 1");
    }

    @Test
    public void equals() {
        ${listItemType}Identifier uut1 = new ${listItemType}Identifier(1);
        ${listItemType}Identifier uut2 = new ${listItemType}Identifier(2);
        assertFalse(uut1.equals(null));
        assertTrue(uut1.equals(uut1));
        assertFalse(uut1.equals("1"));
        assertFalse(uut1.equals(uut2));
    }

    @Test
    public void compareTo() {
        ${listItemType}Identifier uut1 = new ${listItemType}Identifier(1);
        ${listItemType}Identifier uut2 = new ${listItemType}Identifier(2);
        assertEquals(uut1.compareTo(uut1), 0);
        assertTrue(uut1.compareTo(uut2) < 0);
        assertTrue(uut2.compareTo(uut1) > 0);
    }
}
