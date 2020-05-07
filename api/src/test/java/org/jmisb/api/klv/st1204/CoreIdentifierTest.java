package org.jmisb.api.klv.st1204;

import java.util.UUID;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class CoreIdentifierTest
{

    public CoreIdentifierTest()
    {
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
        UUID platformId = UUID.randomUUID();
        coreIdentifier.setPlatformUUID(IdType.Managed, platformId);
        assertEquals(coreIdentifier.getPlatformIdType(), IdType.Managed);
        assertEquals(coreIdentifier.getPlatformUUID(), platformId);
    }

    @Test
    public void windowSetter() {
        CoreIdentifier coreIdentifier = new CoreIdentifier();
        assertNull(coreIdentifier.getWindowUUID());
        assertEquals(coreIdentifier.getWindowUUID(), null);
        UUID windowId = UUID.randomUUID();
        coreIdentifier.setWindowUUID(windowId);
        assertEquals(coreIdentifier.getWindowUUID(), windowId);
        coreIdentifier.setWindowUUID(null);
        assertNull(coreIdentifier.getWindowUUID());
        assertEquals(coreIdentifier.getWindowUUID(), null);
    }

    @Test
    public void MinorSetter() {
        CoreIdentifier coreIdentifier = new CoreIdentifier();
        assertNull(coreIdentifier.getMinorUUID());
        assertEquals(coreIdentifier.getMinorUUID(), null);
        UUID minorId = UUID.randomUUID();
        coreIdentifier.setMinorUUID(minorId);
        assertEquals(coreIdentifier.getMinorUUID(), minorId);
        coreIdentifier.setMinorUUID(null);
        assertNull(coreIdentifier.getMinorUUID());
        assertEquals(coreIdentifier.getMinorUUID(), null);
    }
}
