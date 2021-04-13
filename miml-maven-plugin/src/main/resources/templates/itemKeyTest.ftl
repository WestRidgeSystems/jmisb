// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for {@link ${namespacedName}ItemKey}. */
public class ${namespacedName}ItemKeyTest {
    @Test
    public void constructorCheck() {
        ${namespacedName}ItemKey uut = new ${namespacedName}ItemKey(2);
        assertNotNull(uut);
    }

    @Test
    public void getIdentifier() {
        ${namespacedName}ItemKey uut = new ${namespacedName}ItemKey(2);
        assertEquals(uut.getIdentifier(), 2);
    }

    @Test
    public void equals() {
        ${namespacedName}ItemKey uut1 = new ${namespacedName}ItemKey(2);
        ${namespacedName}ItemKey uut2 = new ${namespacedName}ItemKey(2);
        assertEquals(uut1, uut2);
        assertTrue(uut2.compareTo(uut1) == 0);
    }

    @Test
    public void notEquals() {
        ${namespacedName}ItemKey uut1 = new ${namespacedName}ItemKey(1);
        ${namespacedName}ItemKey uut2 = new ${namespacedName}ItemKey(2);
        assertNotEquals(uut1, uut2);
    }

    @Test
    public void equalsSelf() {
        ${namespacedName}ItemKey uut1 = new ${namespacedName}ItemKey(1);
        assertEquals(uut1, uut1);
    }

    @Test
    public void notEqualsDifferentType() {
        ${namespacedName}ItemKey uut1 = new ${namespacedName}ItemKey(1);
        assertFalse(uut1.equals(new Integer(1)));
    }

    @Test
    public void notEqualNull() {
        ${namespacedName}ItemKey uut1 = new ${namespacedName}ItemKey(1);
        assertFalse(uut1.equals(null));
    }

    @Test
    public void hashDifferent() {
        ${namespacedName}ItemKey uut1 = new ${namespacedName}ItemKey(1);
        ${namespacedName}ItemKey uut2 = new ${namespacedName}ItemKey(2);
        assertNotEquals(uut1.hashCode(), uut2.hashCode());
    }

    @Test
    public void compareLessThan() {
        ${namespacedName}ItemKey uut1 = new ${namespacedName}ItemKey(1);
        ${namespacedName}ItemKey uut2 = new ${namespacedName}ItemKey(2);
        assertTrue(uut1.compareTo(uut2) < 0);
    }

    @Test
    public void compareGreaterThan() {
        ${namespacedName}ItemKey uut1 = new ${namespacedName}ItemKey(1);
        ${namespacedName}ItemKey uut2 = new ${namespacedName}ItemKey(2);
        assertTrue(uut2.compareTo(uut1) > 0);
    }
}
