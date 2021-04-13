<#setting number_format="computer">
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.LoggerChecks;
import org.jmisb.api.klv.st1902.IMimdMetadataValue;
<#list entries as entry>
    <#if entry.name == "mimdId">
import org.jmisb.api.klv.st1902.MimdId;
    <#break>
    </#if>
</#list>
import org.jmisb.api.klv.st1902.MimdIdReference;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ${name} */
public class ${name}Test extends LoggerChecks {

    public static ${name} makeValue() throws KlvParseException {
        SortedMap<${name}MetadataKey, IMimdMetadataValue> values = new TreeMap<>();
<#list entries as entry>
    <#if entry.typeName == "String">
        IMimdMetadataValue ${entry.name}Value = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{0x74});
        assertTrue(${entry.name}Value instanceof ${entry.namespacedName});
        ${entry.namespacedName} ${entry.name} = (${entry.namespacedName})${entry.name}Value;
        values.put(${name}MetadataKey.${entry.name}, ${entry.name});

   <#elseif entry.name == "mimdId">
        // mimdId
        IMimdMetadataValue id = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte) 0x01});
        assertTrue(id instanceof MimdId);
        values.put(${name}MetadataKey.${entry.name}, id);

    <#elseif entry.ref && ! entry.array>
        // Ref
        IMimdMetadataValue ${entry.name}Value = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte) 0x00});
        assertTrue(${entry.name}Value instanceof MimdIdReference);
        values.put(${name}MetadataKey.${entry.name}, ${entry.name}Value);

    </#if>
</#list>
        return new ${name}(values);
    }

    public ${name}Test () {
        super(${name}.class);
    }

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

    @Test
    public void createValueUndefined() throws KlvParseException {
        verifyNoLoggerMessages();
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.Undefined, new byte[]{0x00});
        verifySingleLoggerMessage("Unknown ${name} Metadata tag: Undefined");
        assertNull(uut);
    }

    @Test
    public void getFieldUndefined() throws KlvParseException {
        verifyNoLoggerMessages();
        ${name} parentClass = new ${name}();
        IKlvValue uut = parentClass.getField(${name}MetadataKey.Undefined);
        verifySingleLoggerMessage("Unknown ${name} Metadata tag: -1");
        assertNull(uut);
    }

<#if topLevel>
    @Test
    public void parseFromBytesMimdId() throws KlvParseException {
        verifyNoLoggerMessages();
        ${name} uut = new ${name}(new byte[]{0x06, 0x0E, 0x2B, 0x34, 0x02, 0x05, 0x01, 0x01, 0x0E, 0x01, 0x05, 0x03,
                        0x00, 0x00, 0x00, 0x00, 0x06, 0x01, 0x02, 0x04, 0x03, (byte)0xbb, (byte)0xd4});
        verifyNoLoggerMessages();
        assertEquals(uut.getBytes(), new byte[]{0x06, 0x0E, 0x2B, 0x34, 0x02, 0x05, 0x01, 0x01, 0x0E, 0x01, 0x05, 0x03,
                        0x00, 0x00, 0x00, 0x00, 0x06, 0x01, 0x02, 0x04, 0x03, (byte)0xbb, (byte)0xd4});
        assertNotNull(uut.getIdentifiers());
        assertEquals(uut.getIdentifiers().size(), 1);
        assertNotNull(uut.getField(${name}MetadataKey.mimdId));
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void parseFromBytesBadValue() throws KlvParseException {
        new ${name}(new byte[]{0x06, 0x0E, 0x2B, 0x34, 0x02, 0x05, 0x01, 0x01, 0x0E, 0x01, 0x05, 0x03,
                        0x00, 0x00, 0x00, 0x00, 0x05, 0x01, 0x01, (byte)0x81, (byte)0xc6, (byte)0x8f});
    }

    @Test
    public void parseFromBytesBadTag() throws KlvParseException {
        verifyNoLoggerMessages();
        IMimdMetadataValue uut = new ${name}(new byte[]{0x06, 0x0E, 0x2B, 0x34, 0x02, 0x05, 0x01, 0x01, 0x0E, 0x01, 0x05, 0x03,
                        0x00, 0x00, 0x00, 0x00, 0x09, 0x7F, 0x01, 0x00, 0x01, 0x02, 0x04, 0x03, 0x59, 0x4d});
        verifySingleLoggerMessage("Unknown MIMD Metadata id: 127");
        assertEquals(uut.getBytes(), new byte[]{0x06, 0x0E, 0x2B, 0x34, 0x02, 0x05, 0x01, 0x01, 0x0E, 0x01, 0x05, 0x03,
                        0x00, 0x00, 0x00, 0x00, 0x06, 0x01, 0x02, 0x04, 0x03, (byte)0xbb, (byte)0xd4});
    }
<#else>
    @Test
    public void parseFromBytesEmpty() throws KlvParseException {
        verifyNoLoggerMessages();
        IMimdMetadataValue uut = new ${name}(new byte[]{}, 0, 0);
        verifyNoLoggerMessages();
        assertEquals(uut.getBytes().length, 0);
    }

<#list entries as entry>

    @Test
    public void checkBytesConstructor${entry.nameSentenceCase}() throws KlvParseException {
    <#if entry.array>
        // Array - ${entry.typeName}
        byte[] valueBytes = ${entry.namespacedName}Test.getByteArrayForValidArrayData();
    <#elseif entry.ref>
        // Ref - ${entry.typeName}
        byte[] valueBytes = new byte[]{(byte) 0x00};
    <#elseif entry.typeName == "String">
        // ${entry.namespacedName}, ${entry.typeName}
        byte[] valueBytes = new byte[]{0x74};
    <#elseif entry.typeName == "UInt">
        // ${entry.namespacedName}, ${entry.typeName}
        byte[] valueBytes = new byte[]{(byte)0xFF};
    <#elseif entry.typeName == "Integer">
        // ${entry.namespacedName}, ${entry.typeName}
        byte[] valueBytes = new byte[]{(byte)0xFF};
    <#elseif entry.typeName == "Real">
        // ${entry.namespacedName}, ${entry.typeName}
        <#if entry.minValue?? && entry.maxValue??>
        byte[] valueBytes = new byte[]{
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00};
        <#else>
        byte[] valueBytes = new byte[]{
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00};
        </#if>
    <#elseif entry.name == "mimdId">
        // mimdId
        byte[] valueBytes = new byte[]{(byte) 0x01};
    <#elseif entry.typeName == "Tuple">
        // ${entry.namespacedName}, ${entry.typeName}
        byte[] valueBytes = new byte[]{(byte)0x40};
    <#elseif entry.list>
        // List
        byte[] valueBytes = new byte[]{(byte) 0x00};
    <#elseif entry.typeName == "Boolean">
        // TODO - Boolean
        throw new RuntimeException("Unhandled primitive type: Boolean");
    <#else>
        // Fallback
        byte[] valueBytes = new byte[]{(byte) 0x01, (byte)0x01, (byte)0x06};
    </#if>
        ArrayBuilder builder = new ArrayBuilder();
        builder.appendAsOID(${entry.number});
        builder.appendAsBerLength(valueBytes.length);
        builder.append(valueBytes);
        byte[] parseBytes = builder.toBytes();
        ${name} parentClass = new ${name}(parseBytes);
        assertNotNull(parentClass);
        assertNotNull(parentClass.get${entry.nameSentenceCase}());
        assertEquals(parentClass.getIdentifiers().size(), 1);
        assertNotNull(parentClass.getField(${name}MetadataKey.${entry.name}));
    }
</#list>

    <#if includes??>
    @Test
    public void parseFromBytesMimdId() throws KlvParseException {
        verifyNoLoggerMessages();
        ${name} uut = new ${name}(new byte[]{0x01, 0x02, 0x04, 0x03}, 0, 4);
        verifyNoLoggerMessages();
        assertEquals(uut.getBytes(), new byte[]{0x01, 0x02, 0x04, 0x03});
        assertNotNull(uut.getIdentifiers());
        assertEquals(uut.getIdentifiers().size(), 1);
        assertNotNull(uut.getField(${name}MetadataKey.mimdId));
    }

    public void parseFromBytesMimdIdNoOffset() throws KlvParseException {
        verifyNoLoggerMessages();
        ${name} uut = ${name}.fromBytes(new byte[]{0x01, 0x02, 0x04, 0x03});
        verifyNoLoggerMessages();
        assertEquals(uut.getBytes(), new byte[]{0x01, 0x02, 0x04, 0x03});
        assertNotNull(uut.getIdentifiers());
        assertEquals(uut.getIdentifiers().size(), 1);
        assertNotNull(uut.getField(${name}MetadataKey.mimdId));
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void parseFromBytesBadValue() throws KlvParseException {
        new ${name}(new byte[]{0x01, 0x01, (byte)0x81}, 0, 3);
    }

    @Test
    public void parseFromBytesBadTag() throws KlvParseException {
        verifyNoLoggerMessages();
        IMimdMetadataValue uut = new ${name}(new byte[]{0x7F, 0x01, 0x00, 0x01, 0x02, 0x04, 0x03}, 0, 7);
        verifySingleLoggerMessage("Unknown MIMD ${name} Metadata tag: 127");
        assertEquals(uut.getBytes(), new byte[]{0x01, 0x02, 0x04, 0x03});
    }

    @Test
    public void parseFromBytesBadTagNoOffset() throws KlvParseException {
        verifyNoLoggerMessages();
        IMimdMetadataValue uut = ${name}.fromBytes(new byte[]{0x7F, 0x01, 0x00, 0x01, 0x02, 0x04, 0x03});
        verifySingleLoggerMessage("Unknown MIMD ${name} Metadata tag: 127");
        assertEquals(uut.getBytes(), new byte[]{0x01, 0x02, 0x04, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void parseFromBytesBadValueNoOffset() throws KlvParseException {
        ${name}.fromBytes(new byte[]{0x01, 0x01, (byte)0x81});
    }

    @Test
    public void parseFromBytesTimerOffset() throws KlvParseException {
        verifyNoLoggerMessages();
        ${name} uut = new ${name}(new byte[]{0x03, 0x04, 0x01, 0x02, 0x03, 0x04}, 0, 6);
        verifyNoLoggerMessages();
        assertEquals(uut.getBytes(), new byte[]{0x03, 0x04, 0x01, 0x02, 0x03, 0x04});
        assertNotNull(uut.getIdentifiers());
        assertEquals(uut.getIdentifiers().size(), 1);
        assertNotNull(uut.getField(${name}MetadataKey.timerOffset));
        assertEquals(uut.getTimerOffset().getValue(), 16909060);
    }

    @Test
    public void parseFromBytesNumericPrecision() throws KlvParseException {
        verifyNoLoggerMessages();
        ${name} uut = new ${name}(new byte[]{0x04, 0x02, 0x22, 0x00}, 0, 4);
        assertEquals(uut.getBytes(), new byte[]{0x04, 0x02, 0x22, 0x00});
        assertNotNull(uut.getIdentifiers());
        assertEquals(uut.getIdentifiers().size(), 1);
        assertNotNull(uut.getField(${name}MetadataKey.numericPrecision));
    }

    @Test
    public void parseFromValuesBaseElements() throws KlvParseException {
        SortedMap<${name}MetadataKey, IMimdMetadataValue> values = new TreeMap<>();
        values.put(${name}MetadataKey.mimdId, new MimdId(4, 1));
        values.put(${name}MetadataKey.timerOffset, new org.jmisb.api.klv.st1904.Base_TimerOffset(3210L));
        Map<org.jmisb.api.klv.st1904.NumericalPrecisionMetadataKey, IMimdMetadataValue> numericPrecisionValues = new HashMap<>();
        values.put(${name}MetadataKey.numericPrecision, new org.jmisb.api.klv.st1904.NumericalPrecision(numericPrecisionValues));
        ${name} uut = new ${name}(values);
        assertEquals(uut.getIdentifiers().size(), 3);
    }
    <#else>
    @Test
    public void parseFromBytesBadTag() throws KlvParseException {
        verifyNoLoggerMessages();
        IMimdMetadataValue uut = new ${name}(new byte[]{0x7F, 0x01, 0x00}, 0, 3);
        verifySingleLoggerMessage("Unknown MIMD ${name} Metadata tag: 127");
    }
    </#if>
</#if>
<#list entries as entry>

    @Test
    public void checkCreateValue${entry.nameSentenceCase}() throws KlvParseException {
    <#if entry.array>
        // Array - ${entry.typeName}
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name},
            ${entry.namespacedName}Test.getByteArrayForValidArrayData());
        assertTrue(uut instanceof ${entry.namespacedName});
        ${entry.namespacedName} value = (${entry.namespacedName})uut;
    <#elseif entry.ref>
        // Ref - ${entry.typeName}
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte) 0x00});
        assertTrue(uut instanceof MimdIdReference);
    <#elseif entry.typeName == "String">
        // ${entry.namespacedName}, ${entry.typeName}
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{0x74});
        assertTrue(uut instanceof ${entry.namespacedName});
        ${entry.namespacedName} value = (${entry.namespacedName})uut;
        assertEquals(value.getValue(), "t");
    <#elseif entry.typeName == "UInt">
        // ${entry.namespacedName}, ${entry.typeName}
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte)0xFF});
        assertTrue(uut instanceof ${entry.namespacedName});
        ${entry.namespacedName} value = (${entry.namespacedName})uut;
        assertEquals(value.getValue(), 255);
    <#elseif entry.typeName == "Integer">
        // ${entry.namespacedName}, ${entry.typeName}
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte)0xFF});
        assertTrue(uut instanceof ${entry.namespacedQualifiedName});
        ${entry.namespacedQualifiedName} value = (${entry.namespacedQualifiedName})uut;
        assertEquals(value.getValue(), -1);
    <#elseif entry.typeName == "Real">
        // ${entry.namespacedName}, ${entry.typeName}
        <#if entry.minValue?? && entry.maxValue??>
        IMimdMetadataValue uut = ${name}.createValue(
            ${name}MetadataKey.${entry.name},
            new byte[]{
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00});
        assertTrue(uut instanceof ${entry.namespacedName});
        ${entry.namespacedName} value = (${entry.namespacedName})uut;
        assertEquals(value.getValue(), ${entry.minValue}, 0.00001);
        <#else>
        IMimdMetadataValue uut = ${name}.createValue(
            ${name}MetadataKey.${entry.name},
            new byte[]{
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00});
        assertTrue(uut instanceof ${entry.namespacedName});
        ${entry.namespacedName} value = (${entry.namespacedName})uut;
        assertEquals(value.getValue(), 2.000);
        </#if>
    <#elseif entry.name == "mimdId">
        // mimdId
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte) 0x01});
        assertTrue(uut instanceof MimdId);
    <#elseif entry.typeName == "Tuple">
        // ${entry.namespacedName}, ${entry.typeName}
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte)0x40});
        assertTrue(uut instanceof ${entry.namespacedName});
        ${entry.namespacedName} value = (${entry.namespacedName})uut;
        assertEquals(value.getValues(), new int[] {64});
    <#elseif entry.list>
        // List
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte) 0x00});
        assertTrue(uut instanceof  ${entry.namespacedName});
    <#elseif entry.typeName == "Boolean">
        // TODO - Boolean
        throw new RuntimeException("Unhandled primitive type: Boolean");
    <#else>
        // Fallback
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte) 0x01, (byte)0x01, (byte)0x06});
        assertTrue(uut instanceof ${entry.qualifiedTypeName});
    </#if>
        SortedMap<${name}MetadataKey, IMimdMetadataValue> values = new TreeMap<>();
        values.put(${name}MetadataKey.${entry.name}, uut);
        ${name} parentClass = new ${name}(values);
        assertEquals(parentClass.getIdentifiers().size(), 1);    }
</#list>
<#list entries as entry>

    @Test
    public void checkSetterGetter${entry.nameSentenceCase}() throws KlvParseException {
        ${name} parentClass = new ${name}();
    <#if entry.array>
        // Array - ${entry.typeName}
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name},
            ${entry.namespacedName}Test.getByteArrayForValidArrayData());
        ${entry.namespacedName} value = (${entry.namespacedName})uut;
    <#elseif entry.ref>
        // Ref - ${entry.typeName}
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte) 0x00});
        MimdIdReference value = (MimdIdReference)uut;
    <#elseif entry.typeName == "String">
        // ${entry.namespacedName}, ${entry.typeName}
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{0x74});
        ${entry.namespacedName} value = (${entry.namespacedName})uut;
    <#elseif entry.typeName == "UInt">
        // ${entry.namespacedName}, ${entry.typeName}
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte)0xFF});
        ${entry.namespacedName} value = (${entry.namespacedName})uut;
    <#elseif entry.typeName == "Integer">
        // ${entry.namespacedName}, ${entry.typeName}
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte)0xFF});
        ${entry.namespacedQualifiedName} value = (${entry.namespacedQualifiedName})uut;
    <#elseif entry.typeName == "Real">
        // ${entry.namespacedName}, ${entry.typeName}
        <#if entry.minValue?? && entry.maxValue??>
        IMimdMetadataValue uut = ${name}.createValue(
            ${name}MetadataKey.${entry.name},
            new byte[]{
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00});
        <#else>
        IMimdMetadataValue uut = ${name}.createValue(
            ${name}MetadataKey.${entry.name},
            new byte[]{
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00});
        </#if>
        ${entry.namespacedName} value = (${entry.namespacedName})uut;
    <#elseif entry.name == "mimdId">
        // mimdId
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte) 0x01});
        MimdId value = (MimdId)uut;
    <#elseif entry.typeName == "Tuple">
        // ${entry.namespacedName}, ${entry.typeName}
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte)0x40});
        ${entry.namespacedName} value = (${entry.namespacedName})uut;
    <#elseif entry.list>
        // List
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte) 0x00});
        ${entry.namespacedName} value = (${entry.namespacedName})uut;
    <#elseif entry.typeName == "Boolean">
        // TODO - Boolean
        throw new RuntimeException("Unhandled primitive type: Boolean");
    <#else>
        // Fallback
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte) 0x01, (byte)0x01, (byte)0x06});
        ${entry.qualifiedTypeName} value = (${entry.qualifiedTypeName})uut;
    </#if>
        parentClass.set${entry.nameSentenceCase}(value);
        assertEquals(parentClass.get${entry.nameSentenceCase}(), value);
    }
</#list>
<#list entries as entry>

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkValidation${entry.nameSentenceCase}() throws KlvParseException {
        SortedMap<${name}MetadataKey, IMimdMetadataValue> values = new TreeMap<>();
        IMimdMetadataValue badValue = new IMimdMetadataValue() {
            @Override
            public byte[] getBytes() {
                return new byte[]{0x00};
            }

            @Override
            public String getDisplayName() {
                return "";
            }

            @Override
            public String getDisplayableValue() {
                return "";
            }
        };
        values.put(${name}MetadataKey.${entry.name}, badValue);
        new ${name}(values);
    }
</#list>

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkValidationUndefined() throws KlvParseException {
        SortedMap<${name}MetadataKey, IMimdMetadataValue> values = new TreeMap<>();
        IMimdMetadataValue badValue = new IMimdMetadataValue() {
            @Override
            public byte[] getBytes() {
                return new byte[]{0x00};
            }

            @Override
            public String getDisplayName() {
                return "";
            }

            @Override
            public String getDisplayableValue() {
                return "";
            }
        };
        values.put(${name}MetadataKey.Undefined, badValue);
        new ${name}(values);
    }

    @Test
    public void testBuildRef() throws KlvParseException {
        SortedMap<${name}MetadataKey, IMimdMetadataValue> values = new TreeMap<>();
<#list entries as entry>
    <#if entry.ref && !entry.array>
        // Ref
        IMimdMetadataValue refValue${entry?index} = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte) 0x00});
        values.put(${name}MetadataKey.${entry.name}, refValue${entry?index});
    </#if>
</#list>
        ${name} uut = new ${name}(values);
        assertEquals(uut.getIdentifiers().size(), values.keySet().size());
        if (uut.getIdentifiers().size() > 0) {
            uut.getIdentifiers().stream().forEach(id -> {
                assertNotNull(uut.getField(id));
                assertTrue(uut.getField(id) instanceof MimdIdReference);
            });
            byte[] bytes = uut.getBytes();
            assertTrue(bytes.length >= 3);
            ${name} parseResult = new ${name}(bytes);
            assertEquals(parseResult.getIdentifiers().size(), values.keySet().size());
        }
    }

    @Test
    public void testBuildReal() throws KlvParseException {
        SortedMap<${name}MetadataKey, IMimdMetadataValue> values = new TreeMap<>();
<#list entries as entry>
    <#if (entry.typeName == "Real") && (!entry.array)>
        IMimdMetadataValue refValue${entry?index} = ${name}.createValue(
            ${name}MetadataKey.${entry.name},
            new byte[]{
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x01,
                (byte) 0x00});
        values.put(${name}MetadataKey.${entry.name}, refValue${entry?index});
    </#if>
</#list>
        ${name} uut = new ${name}(values);
        assertEquals(uut.getIdentifiers().size(), values.keySet().size());
        if (uut.getIdentifiers().size() > 0) {
            uut.getIdentifiers().stream().forEach(id -> {
                assertNotNull(uut.getField(id));
            });
            byte[] bytes = uut.getBytes();
            assertTrue(bytes.length >= 3);
            ${name} parseResult = new ${name}(bytes);
            assertEquals(parseResult.getIdentifiers().size(), values.keySet().size());
        }
    }    

    @Test
    public void testBuildRealArray() throws KlvParseException {
        SortedMap<${name}MetadataKey, IMimdMetadataValue> values = new TreeMap<>();
<#list entries as entry>
    <#if (entry.typeName == "Real") && entry.array>
        IMimdMetadataValue refValue${entry?index} = ${name}.createValue(
            ${name}MetadataKey.${entry.name},
            ${entry.namespacedName}Test.getByteArrayForValidArrayData());
        values.put(${name}MetadataKey.${entry.name}, refValue${entry?index});
    </#if>
</#list>
        ${name} uut = new ${name}(values);
        assertEquals(uut.getIdentifiers().size(), values.keySet().size());
        if (uut.getIdentifiers().size() > 0) {
            uut.getIdentifiers().stream().forEach(id -> {
                assertNotNull(uut.getField(id));
            });
        }
        byte[] bytes = uut.getBytes();
        assertTrue(bytes.length >= 0);
        ${name} regenerated = new ${name}(bytes);
        assertEquals(uut.getIdentifiers().size(), regenerated.getIdentifiers().size());
    }

    @Test
    public void testBuildUintArray() throws KlvParseException {
        SortedMap<${name}MetadataKey, IMimdMetadataValue> values = new TreeMap<>();
<#list entries as entry>
    <#if (entry.typeName == "UInt") && entry.array>
        IMimdMetadataValue refValue${entry?index} = ${name}.createValue(
            ${name}MetadataKey.${entry.name},
            ${entry.namespacedName}Test.getByteArrayForValidArrayData());
        values.put(${name}MetadataKey.${entry.name}, refValue${entry?index});
    </#if>
</#list>
        ${name} uut = new ${name}(values);
        assertEquals(uut.getIdentifiers().size(), values.keySet().size());
        if (uut.getIdentifiers().size() > 0) {
            uut.getIdentifiers().stream().forEach(id -> {
                assertNotNull(uut.getField(id));
            });
        }
        byte[] bytes = uut.getBytes();
        assertTrue(bytes.length >= 0);
        ${name} regenerated = new ${name}(bytes);
        assertEquals(uut.getIdentifiers().size(), regenerated.getIdentifiers().size());
    }
}
