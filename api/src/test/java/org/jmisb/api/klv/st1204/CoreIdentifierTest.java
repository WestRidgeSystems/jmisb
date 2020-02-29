package org.jmisb.api.klv.st1204;

import java.util.UUID;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class CoreIdentifierTest {

    public CoreIdentifierTest() {
    }

    @Test
    public void sensorSetter() {
        CoreIdentifier coreIdentifier = new CoreIdentifier();
        assertNull(coreIdentifier.getSensorUUID());
        assertEquals(coreIdentifier.getSensorIdType(), IdType.None);
        UUID sensorid = UUID.randomUUID();
        coreIdentifier.setSensorUUID(IdType.Virtual, sensorid);
        assertEquals(coreIdentifier.getSensorIdType(), IdType.Virtual);
        assertEquals(coreIdentifier.getSensorUUID(), sensorid);
    }

    @Test
    public void platformSetter() {
        CoreIdentifier coreIdentifier = new CoreIdentifier();
        assertNull(coreIdentifier.getPlatformUUID());
        assertEquals(coreIdentifier.getPlatformIdType(), IdType.None);
        UUID sensorid = UUID.randomUUID();
        coreIdentifier.setPlatformUUID(IdType.Managed, sensorid);
        assertEquals(coreIdentifier.getPlatformIdType(), IdType.Managed);
        assertEquals(coreIdentifier.getPlatformUUID(), sensorid);
    }
}
