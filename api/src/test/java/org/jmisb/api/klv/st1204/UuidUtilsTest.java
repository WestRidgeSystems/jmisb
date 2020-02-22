package org.jmisb.api.klv.st1204;

import java.util.UUID;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class UuidUtilsTest {
    
    public UuidUtilsTest() {
    }
    
    @Test
    public void checkConstructor() {
        UuidUtils uuidUtils = new UuidUtils();
        assertNotNull(uuidUtils);
    }

    @Test
    public void checkFormatting() {
        assertEquals(UuidUtils.formatUUID(UUID.fromString("C2A7D724-96F7-47DB-A23D-A29730075876")), "C2A7-D724-96F7-47DB-A23D-A297-3007-5876");
    }
}
