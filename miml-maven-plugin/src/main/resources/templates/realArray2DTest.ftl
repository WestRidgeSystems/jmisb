<#setting number_format="computer">
<#assign
    minVal=minValue!0.0
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
            new double[][]
                {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
    }

    @Test
    public void getDisplayName() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new double[][]
                {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
        assertEquals(uut.getDisplayableValue(), "[${nameSentenceCase} Array]");
    }

    @Test
    public void getValue() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new double[][]
                {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
        assertEquals(uut.getValue(), new double[][]
                {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
    }

    @Test
    public void getBytes() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new double[][]
                {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
        // TODO: check values - may be messy given IMAPA.
        assertNotNull(uut.getBytes());
    }

    @Test
    public void fromBytes() throws KlvParseException {
        ${namespacedName} uut = ${namespacedName}.fromBytes(getByteArrayForValidArrayData());
    }

    static byte[] getByteArrayForValidArrayData() {
        return new byte[]
                { 0x02, ${arrayDimension0}, ${arrayDimension1}, 0x08, 0x01, <#list 1..arrayDimension0*arrayDimension1 as i>0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00<#sep>, </#list>};
    }
<#if arrayDimensionSize(0)??>

    @Test(expectedExceptions = KlvParseException.class)
    public void fromBytesBadDim0() throws KlvParseException {
        ${namespacedName} uut = ${namespacedName}.fromBytes(new byte[]
                { 0x02, ${arrayDimension0 + 1}, ${arrayDimension1}, 0x04, 0x01, <#list 1..((arrayDimension0+1)*arrayDimension1) as i>0x00, 0x00, 0x00, 0x00<#sep>, </#list>});
    }
</#if>
<#if arrayDimensionSize(1)??>

    @Test(expectedExceptions = KlvParseException.class)
    public void fromBytesBadDim1() throws KlvParseException {
        ${namespacedName} uut = ${namespacedName}.fromBytes(new byte[]
                { 0x02, ${arrayDimension0}, ${arrayDimension1+1}, 0x04, 0x01, <#list 1..(arrayDimension0*(arrayDimension1+1)) as i>0x00, 0x00, 0x00, 0x00<#sep>, </#list>});
    }
</#if>
<#if minValue??>

    @Test(expectedExceptions = KlvParseException.class)
    public void minValueBad() throws KlvParseException {
        new ${namespacedName}(new double[][]
            {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${minValue - 0.1}<#sep>, </#list>}<#sep>, </#list>});
    }
</#if>
<#if maxValue??>

    @Test(expectedExceptions = KlvParseException.class)
    public void maxValueBad() throws KlvParseException {
        new ${namespacedName}(new double[][]
            {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${maxValue + 0.1}<#sep>, </#list>}<#sep>, </#list>});
    }
</#if>
<#if arrayDimensionSize(0)??>

    @Test(expectedExceptions = KlvParseException.class)
    public void BadNumberOfElementsLowRow() throws KlvParseException {
        new ${namespacedName}(new double[][]
            {<#list 2..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void BadNumberOfElementsRowHigh() throws KlvParseException {
        new ${namespacedName}(new double[][]
            {<#list 0..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
    }
</#if>
<#if arrayDimensionSize(1)??>

    @Test(expectedExceptions = KlvParseException.class)
    public void BadNumberOfElementsLowColumns() throws KlvParseException {
        new ${namespacedName}(new double[][]
            {<#list 1..arrayDimension0 as r>{<#list 2..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void BadNumberOfElementsHighColumns() throws KlvParseException {
        new ${namespacedName}(new double[][]
            {<#list 1..arrayDimension0 as r>{<#list 0..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
    }
<#else>

    @Test
    public void getBytesBad() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new double[${arrayDimension0}][0]);
        assertEquals(uut.getBytes(), new byte[0]);
    }
</#if>

}
