// Generated file - changes will be lost on rebuild
package ${packageName};

import java.util.TreeMap;
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
}
