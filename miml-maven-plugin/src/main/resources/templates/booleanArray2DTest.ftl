<#setting number_format="computer">
<#assign
    arrayDimension0=arrayDimensionSize(0)!1
    arrayDimension1=arrayDimensionSize(1)!1
>
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import org.jmisb.api.common.KlvParseException;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ${namespacedName} */
public class ${namespacedName}Test {

    @Test
    public void displayName() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(
            new boolean[][]
                {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>false<#sep>, </#list>}<#sep>, </#list>});
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
    }

    @Test
    public void getDisplayName() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new boolean[][]
                {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>false<#sep>, </#list>}<#sep>, </#list>});
        assertEquals(uut.getDisplayableValue(), "[${nameSentenceCase} Array]");
    }

    @Test
    public void getValueFalse() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new boolean[][]
                {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>false<#sep>, </#list>}<#sep>, </#list>});
        assertEquals(uut.getValue(), new boolean[][]
                {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>false<#sep>, </#list>}<#sep>, </#list>});
    }

    @Test
    public void getValueTrue() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new boolean[][]
                {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>true<#sep>, </#list>}<#sep>, </#list>});
        assertEquals(uut.getValue(), new boolean[][]
                {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>true<#sep>, </#list>}<#sep>, </#list>});
    }

    @Test
    public void fromBytesNaturalEncodedFalse() throws KlvParseException {
        ${namespacedName} uut = ${namespacedName}.fromBytes(getByteArrayForValidArrayData());
        assertEquals(uut.getValue(), new boolean[][]
                {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>false<#sep>, </#list>}<#sep>, </#list>});
    }

    static byte[] getByteArrayForValidArrayData() {
        return new byte[]
                { 0x02,
                  ${arrayDimension0},
                  ${arrayDimension1},
                  0x01,
                  0x01,
                <#list 1..arrayDimension0*arrayDimension1 as i>0x00<#sep>, </#list>};
    }

    @Test
    public void getBytesFalseEncoded() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new boolean[][]
                {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>false<#sep>, </#list>}<#sep>, </#list>});
        assertEquals(uut.getBytes(), new byte[]
                { 0x02,
                  ${arrayDimension0},
                  ${arrayDimension1},
                  0x01,
                  0x03,
                <#list 0..(arrayDimension0*arrayDimension1/8) as c>0x00<#sep>, </#list>
            });
    }

    @Test
    public void getBytesBad() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new boolean[0][0]);
        assertEquals(uut.getBytes().length, 0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void fromBytesBadEncoding() throws KlvParseException {
        ${namespacedName}.fromBytes(new byte[]
                { 0x02,
                  ${arrayDimension0},
                  ${arrayDimension1},
                  0x01,
                  0x02,
                <#list 1..arrayDimension0*arrayDimension1 as i>0x00<#sep>, </#list>
            });
    }
}
