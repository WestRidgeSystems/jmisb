// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.LoggerChecks;
import org.jmisb.api.klv.st1902.IMimdMetadataValue;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for LIST&lt;${name}&gt;. */
public class ${namespacedName}Test extends LoggerChecks {

    public ${namespacedName}Test () {
        super(${namespacedName}.class);
    }

    @Test
    public void displayName() {
        ${namespacedName} uut = new ${namespacedName}(new ArrayList<>());
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
    }

    @Test
    public void displayableValue() {
        ${namespacedName} uut = new ${namespacedName}(new ArrayList<>());
        assertEquals(uut.getDisplayableValue(), "LIST[${typeName}]");
    }

    @Test
    public void fromBytesConstructorZeroLength() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new byte[]{}, 0, 0);
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
    }

    @Test
    public void fromBytesNoIdentifiers() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new byte[]{}, 0, 0);
        assertNotNull(uut);
        assertEquals(uut.getIdentifiers().size(), 0);
    }

    @Test
    public void fromList${typeName}() throws KlvParseException {
        List<${qualifiedTypeName}> listOfValues = new ArrayList<>();
        listOfValues.add(${qualifiedTypeName}Test.makeValue());
        ${namespacedName} uut = new ${namespacedName}(listOfValues);
        assertNotNull(uut);
        byte[] bytes = uut.getBytes();
        assertTrue(bytes.length > 0);
        ${namespacedName} uutRebuilt = ${namespacedName}.fromBytes(bytes);
        assertEquals(uut.getIdentifiers(), uutRebuilt.getIdentifiers());
        for (IKlvKey k: uut.getIdentifiers()) {
            assertNotNull(uut);
            assertTrue(uut.getField(k) instanceof IMimdMetadataValue);
        }
    }
}

