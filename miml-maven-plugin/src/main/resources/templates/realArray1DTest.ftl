<#setting number_format="computer">
<#assign
    minVal=minValue!0.0
    arrayDimension0=arrayDimensionSize(0)!1
>
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import java.util.Set;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ${namespacedName} */
public class ${namespacedName}Test {

    @Test
    public void displayName() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new double[] {<#list 1..arrayDimension0 as x>${minVal}<#sep>, </#list>});
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
    }

    @Test
    public void getDisplayName() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new double[] {<#list 1..arrayDimension0 as x>${minVal}<#sep>, </#list>});
        assertEquals(uut.getDisplayableValue(), "[${nameSentenceCase} Array]");
    }

    @Test
    public void getValue() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new double[] {<#list 1..arrayDimension0 as x>${minVal}<#sep>, </#list>});
        assertEquals(uut.getValue(), new double[] {<#list 1..arrayDimension0 as x>${minVal}<#sep>, </#list>});
    }

    @Test
    public void getBytes() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new double[] {<#list 1..arrayDimension0 as x>${minVal}<#sep>, </#list>});
        assertNotNull(uut.getBytes());
    }

    @Test
    public void fromBytes() throws KlvParseException {
        ${namespacedName} uut = ${namespacedName}.fromBytes(getByteArrayForValidArrayData());
    }

    static byte[] getByteArrayForValidArrayData() {
        return new byte[]
                { 0x01, ${arrayDimension0}, 0x04, 0x01, <#list 1..arrayDimension0 as i>0x00, 0x00, 0x00, 0x00<#sep>, </#list>};
    }

    @Test
    public void getNestedValues() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new double[] {<#list 1..arrayDimension0 as x>${minVal}<#sep>, </#list>});
        Set<? extends IKlvKey> identifiers = uut.getIdentifiers();
        assertEquals(identifiers.size(), ${arrayDimension0});
        for (IKlvKey identifier: identifiers) {
            IKlvValue field = uut.getField(identifier);
            assertNotNull(field.getDisplayName());
            assertEquals(field.getDisplayableValue(), "0.000000");
        }
    }
<#if minValue??>

    @Test(expectedExceptions = KlvParseException.class)
    public void minValueBad() throws KlvParseException {
        new ${namespacedName}(new double[] {<#list 1..arrayDimension0 as x>${minValue - 0.5}<#sep>, </#list>});
    }
</#if>
<#if maxValue??>

    @Test(expectedExceptions = KlvParseException.class)
    public void maxValueBad() throws KlvParseException {
        new ${namespacedName}(new double[] {<#list 1..arrayDimension0 as x>${maxValue + 0.5}<#sep>, </#list>});
    }
</#if>
<#if arrayDimensionSize(0)??>

    @Test(expectedExceptions = KlvParseException.class)
    public void BadNumberOfElementsLow() throws KlvParseException {
        new ${namespacedName}(new double[] {<#list 2..arrayDimension0 as x>${minVal}<#sep>, </#list>});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void BadNumberOfElementsHigh() throws KlvParseException {
        new ${namespacedName}(new double[] {<#list 0..arrayDimension0 as x>${minVal}<#sep>, </#list>});
    }
<#else>

    @Test
    public void getBytesBad() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new double[0]);
        assertEquals(uut.getBytes(), new byte[0]);
    }
</#if>
}
