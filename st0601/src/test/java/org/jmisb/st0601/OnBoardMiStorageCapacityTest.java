package org.jmisb.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OnBoardMiStorageCapacityTest {
    @Test
    public void testMinMax() {
        OnBoardMiStorageCapacity capacity = new OnBoardMiStorageCapacity(0);
        Assert.assertEquals(capacity.getDisplayName(), "On-Board MI Storage Capacity");
        byte[] bytes = capacity.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x00});
        Assert.assertEquals(capacity.getGigabytes(), 0);
        Assert.assertEquals(capacity.getDisplayableValue(), "0GB");

        capacity = new OnBoardMiStorageCapacity(4294967295L);
        Assert.assertEquals(capacity.getDisplayName(), "On-Board MI Storage Capacity");
        bytes = capacity.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        Assert.assertEquals(capacity.getGigabytes(), 4294967295L);
        Assert.assertEquals(capacity.getDisplayableValue(), "4294967295GB");

        bytes = new byte[] {(byte) 0x00};
        capacity = new OnBoardMiStorageCapacity(bytes);
        Assert.assertEquals(capacity.getDisplayName(), "On-Board MI Storage Capacity");
        Assert.assertEquals(capacity.getGigabytes(), 0L);
        Assert.assertEquals(capacity.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals(capacity.getDisplayableValue(), "0GB");

        bytes = new byte[] {(byte) 0xff};
        capacity = new OnBoardMiStorageCapacity(bytes);
        Assert.assertEquals(capacity.getDisplayName(), "On-Board MI Storage Capacity");
        Assert.assertEquals(capacity.getGigabytes(), 255);
        Assert.assertEquals(capacity.getBytes(), bytes);
        Assert.assertEquals(capacity.getDisplayableValue(), "255GB");

        bytes = new byte[] {(byte) 0x00, (byte) 0x00};
        capacity = new OnBoardMiStorageCapacity(bytes);
        Assert.assertEquals(capacity.getDisplayName(), "On-Board MI Storage Capacity");
        Assert.assertEquals(capacity.getGigabytes(), 0L);
        Assert.assertEquals(capacity.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals(capacity.getDisplayableValue(), "0GB");

        bytes = new byte[] {(byte) 0xff, (byte) 0xff};
        capacity = new OnBoardMiStorageCapacity(bytes);
        Assert.assertEquals(capacity.getDisplayName(), "On-Board MI Storage Capacity");
        Assert.assertEquals(capacity.getGigabytes(), 65535);
        Assert.assertEquals(capacity.getBytes(), bytes);
        Assert.assertEquals(capacity.getDisplayableValue(), "65535GB");

        bytes = new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00};
        capacity = new OnBoardMiStorageCapacity(bytes);
        Assert.assertEquals(capacity.getDisplayName(), "On-Board MI Storage Capacity");
        Assert.assertEquals(capacity.getGigabytes(), 0L);
        Assert.assertEquals(capacity.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals(capacity.getDisplayableValue(), "0GB");

        bytes = new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff};
        capacity = new OnBoardMiStorageCapacity(bytes);
        Assert.assertEquals(capacity.getDisplayName(), "On-Board MI Storage Capacity");
        Assert.assertEquals(capacity.getGigabytes(), 16777215L);
        Assert.assertEquals(
                capacity.getBytes(),
                new byte[] {(byte) 0x00, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        Assert.assertEquals(capacity.getDisplayableValue(), "16777215GB");

        bytes = new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        capacity = new OnBoardMiStorageCapacity(bytes);
        Assert.assertEquals(capacity.getDisplayName(), "On-Board MI Storage Capacity");
        Assert.assertEquals(capacity.getGigabytes(), 0L);
        Assert.assertEquals(capacity.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals(capacity.getDisplayableValue(), "0GB");

        bytes = new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        capacity = new OnBoardMiStorageCapacity(bytes);
        Assert.assertEquals(capacity.getDisplayName(), "On-Board MI Storage Capacity");
        Assert.assertEquals(capacity.getGigabytes(), 4294967295L);
        Assert.assertEquals(capacity.getBytes(), bytes);
        Assert.assertEquals(capacity.getDisplayableValue(), "4294967295GB");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testOutOfBoundsMin() {
        new OnBoardMiStorageCapacity(-1L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testOutOfBoundsMax() {
        new OnBoardMiStorageCapacity(4294967296L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBadLength() {
        byte[] fiveByteArray =
                new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        new OnBoardMiStorageCapacity(fiveByteArray);
    }

    @Test
    public void stExample() {
        long value = 10_000L;
        byte[] origBytes = new byte[] {(byte) 0x27, (byte) 0x10};

        OnBoardMiStorageCapacity capacity = new OnBoardMiStorageCapacity(origBytes);
        Assert.assertEquals(capacity.getDisplayName(), "On-Board MI Storage Capacity");
        Assert.assertEquals(capacity.getGigabytes(), value);
        Assert.assertEquals(capacity.getBytes(), origBytes);
        Assert.assertEquals(capacity.getDisplayableValue(), "10000GB");

        capacity = new OnBoardMiStorageCapacity(value);
        Assert.assertEquals(capacity.getDisplayName(), "On-Board MI Storage Capacity");
        Assert.assertEquals(capacity.getGigabytes(), value);
        Assert.assertEquals(capacity.getBytes(), origBytes);
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.OnBoardMiStorageCapacity, bytes);
        Assert.assertTrue(v instanceof OnBoardMiStorageCapacity);
        Assert.assertEquals(v.getDisplayName(), "On-Board MI Storage Capacity");
        OnBoardMiStorageCapacity capacity = (OnBoardMiStorageCapacity) v;
        Assert.assertEquals(capacity.getGigabytes(), 0L);
        Assert.assertEquals(capacity.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals(capacity.getDisplayableValue(), "0GB");

        bytes = new byte[] {(byte) 0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.OnBoardMiStorageCapacity, bytes);
        Assert.assertTrue(v instanceof OnBoardMiStorageCapacity);
        Assert.assertEquals(v.getDisplayName(), "On-Board MI Storage Capacity");
        capacity = (OnBoardMiStorageCapacity) v;
        Assert.assertEquals(capacity.getGigabytes(), 255);
        Assert.assertEquals(capacity.getBytes(), bytes);
        Assert.assertEquals(capacity.getDisplayableValue(), "255GB");

        bytes = new byte[] {(byte) 0x00, (byte) 0x00};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.OnBoardMiStorageCapacity, bytes);
        Assert.assertTrue(v instanceof OnBoardMiStorageCapacity);
        Assert.assertEquals(v.getDisplayName(), "On-Board MI Storage Capacity");
        capacity = (OnBoardMiStorageCapacity) v;
        Assert.assertEquals(capacity.getGigabytes(), 0L);
        Assert.assertEquals(capacity.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals(capacity.getDisplayableValue(), "0GB");

        bytes = new byte[] {(byte) 0xff, (byte) 0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.OnBoardMiStorageCapacity, bytes);
        Assert.assertTrue(v instanceof OnBoardMiStorageCapacity);
        Assert.assertEquals(v.getDisplayName(), "On-Board MI Storage Capacity");
        capacity = (OnBoardMiStorageCapacity) v;
        Assert.assertEquals(capacity.getGigabytes(), 65535);
        Assert.assertEquals(capacity.getBytes(), bytes);
        Assert.assertEquals(capacity.getDisplayableValue(), "65535GB");

        bytes = new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.OnBoardMiStorageCapacity, bytes);
        Assert.assertTrue(v instanceof OnBoardMiStorageCapacity);
        Assert.assertEquals(v.getDisplayName(), "On-Board MI Storage Capacity");
        capacity = (OnBoardMiStorageCapacity) v;
        Assert.assertEquals(capacity.getGigabytes(), 0L);
        Assert.assertEquals(capacity.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals(capacity.getDisplayableValue(), "0GB");

        bytes = new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.OnBoardMiStorageCapacity, bytes);
        Assert.assertTrue(v instanceof OnBoardMiStorageCapacity);
        Assert.assertEquals(v.getDisplayName(), "On-Board MI Storage Capacity");
        capacity = (OnBoardMiStorageCapacity) v;
        Assert.assertEquals(capacity.getGigabytes(), 16777215L);
        Assert.assertEquals(
                capacity.getBytes(),
                new byte[] {(byte) 0x00, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        Assert.assertEquals(capacity.getDisplayableValue(), "16777215GB");

        bytes = new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.OnBoardMiStorageCapacity, bytes);
        Assert.assertTrue(v instanceof OnBoardMiStorageCapacity);
        Assert.assertEquals(v.getDisplayName(), "On-Board MI Storage Capacity");
        capacity = (OnBoardMiStorageCapacity) v;
        Assert.assertEquals(capacity.getGigabytes(), 0L);
        Assert.assertEquals(capacity.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals(capacity.getDisplayableValue(), "0GB");

        bytes = new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.OnBoardMiStorageCapacity, bytes);
        Assert.assertTrue(v instanceof OnBoardMiStorageCapacity);
        Assert.assertEquals(v.getDisplayName(), "On-Board MI Storage Capacity");
        capacity = (OnBoardMiStorageCapacity) v;
        Assert.assertEquals(capacity.getGigabytes(), 4294967295L);
        Assert.assertEquals(capacity.getBytes(), bytes);
        Assert.assertEquals(capacity.getDisplayableValue(), "4294967295GB");
    }
}
