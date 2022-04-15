package org.jmisb.api.klv;

import static org.testng.Assert.*;

import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for LdsParser. */
public class LdsParserTest {

    @Test
    public void checkSingleParse() throws KlvParseException {
        byte[] bytes = new byte[] {0x01, 0x03, 0x04, 0x03, 0x02};
        List<LdsField> fields = LdsParser.parseFields(bytes, 0, bytes.length);
        assertEquals(fields.size(), 1);
        LdsField field = fields.get(0);
        assertEquals(field.getTag(), 1);
        assertEquals(field.getData(), new byte[] {0x04, 0x03, 0x02});
    }
}
