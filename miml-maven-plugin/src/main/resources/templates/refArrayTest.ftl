<#setting number_format="computer">
<#assign
    minVal=minValue!0
    arrayDimension0=arrayDimensionSize(0)!1
>
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import java.util.Set;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.st1902.MimdId;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ${namespacedName} */
public class ${namespacedName}Test {

    @Test
    public void displayName() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new MimdId[] {<#list 1..arrayDimension0 as x>new MimdId(${x})<#sep>, </#list>});
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
    }

    @Test
    public void getDisplayName() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new MimdId[] {<#list 1..arrayDimension0 as x>new MimdId(${x})<#sep>, </#list>});
        assertEquals(uut.getDisplayableValue(), "[${nameSentenceCase} Array]");
    }

    @Test
    public void getValue() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new MimdId[] {<#list 1..arrayDimension0 as x>new MimdId(${x})<#sep>, </#list>});
        assertEquals(uut.getValue(), new MimdId[] {<#list 1..arrayDimension0 as x>new MimdId(${x})<#sep>, </#list>});
        assertEquals(uut.getBytes(), getByteArrayForValidArrayData());
    }

    @Test
    public void bytesConstructor() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(getByteArrayForValidArrayData());
        assertEquals(uut.getValue(), new MimdId[] {<#list 1..arrayDimension0 as x>new MimdId(${x})<#sep>, </#list>});
        assertEquals(uut.getBytes(), getByteArrayForValidArrayData());
    }

    static byte[] getByteArrayForValidArrayData() {
        return new byte[] {0x01, 0x00};
    }

    @Test
    public void fromBytes() throws KlvParseException {
        ${namespacedName} uut = ${namespacedName}.fromBytes(getByteArrayForValidArrayData());
        assertEquals(uut.getValue(), new MimdId[] {<#list 1..arrayDimension0 as x>new MimdId(${x})<#sep>, </#list>});
        assertEquals(uut.getBytes(), getByteArrayForValidArrayData());
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void fromBytesBad() throws KlvParseException {
        ${namespacedName}.fromBytes(new byte[]{1});
    }

    @Test
    public void getNestedValues() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new MimdId[] {<#list 1..arrayDimension0 as x>new MimdId(${x})<#sep>, </#list>});
        Set<? extends IKlvKey> identifiers = uut.getIdentifiers();
        assertEquals(identifiers.size(), ${arrayDimension0});
        for (IKlvKey identifier: identifiers) {
            IKlvValue field = uut.getField(identifier);
            assertNotNull(field.getDisplayName());
            assertEquals(field.getDisplayableValue(), "(1, 0)");
        }
    }
<#if arrayDimensionSize(0)??>

    @Test(expectedExceptions = KlvParseException.class)
    public void BadNumberOfElementsLow() throws KlvParseException {
        new ${namespacedName}(new MimdId[] {<#list 2..arrayDimension0 as x>${minVal}<#sep>, </#list>});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void BadNumberOfElementsHigh() throws KlvParseException {
        new ${namespacedName}(new MimdId[] {<#list 0..arrayDimension0 as x>${minVal}<#sep>, </#list>});
    }
</#if>
}
