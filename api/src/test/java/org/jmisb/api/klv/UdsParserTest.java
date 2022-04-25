package org.jmisb.api.klv;

import static org.testng.Assert.*;

import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for UdsParser. */
public class UdsParserTest {

    @Test
    public void checkSingleParse() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x01, 0x02,
                    0x00, 0x00, 0x00, 0x03, 0x04, 0x03, 0x02
                };
        List<UdsField> fields = UdsParser.parseFields(bytes, 0, bytes.length);
        assertEquals(fields.size(), 1);
        UdsField field = fields.get(0);
        assertEquals(
                field.getKey().getBytes(),
                new byte[] {
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x01, 0x02,
                    0x00, 0x00, 0x00
                });
        assertEquals(field.getValue(), new byte[] {0x04, 0x03, 0x02});
    }
}
