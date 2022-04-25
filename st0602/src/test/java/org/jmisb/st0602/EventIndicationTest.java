package org.jmisb.st0602;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for ST 0602 Event Indication implementation. */
public class EventIndicationTest {

    public EventIndicationTest() {}

    @Test
    public void checkNew() {
        EventIndicationKind uut = EventIndicationKind.NEW;
        assertEquals(uut.getDisplayName(), "Event Indication");
        assertEquals(uut.getDisplayableValue(), "NEW");
        assertEquals(uut.getEncodedValue(), 0x31);
    }

    @Test
    public void checkNewFromFactory() throws KlvParseException {
        IAnnotationMetadataValue value =
                UniversalSetFactory.createValue(
                        AnnotationMetadataKey.EventIndication, new byte[] {0x31});
        assertTrue(value instanceof EventIndication);
        EventIndication uut = (EventIndication) value;
        assertEquals(uut.getEventIndicationKind(), EventIndicationKind.NEW);
        assertEquals(uut.getDisplayName(), "Event Indication");
        assertEquals(uut.getDisplayableValue(), "NEW");
        assertEquals(uut.getBytes(), new byte[] {0x31});
    }

    @Test
    public void checkMove() {
        EventIndicationKind uut = EventIndicationKind.MOVE;
        assertEquals(uut.getDisplayName(), "Event Indication");
        assertEquals(uut.getDisplayableValue(), "MOVE");
        assertEquals(uut.getEncodedValue(), 0x32);
    }

    @Test
    public void checkMoveFromFactory() throws KlvParseException {
        IAnnotationMetadataValue value =
                UniversalSetFactory.createValue(
                        AnnotationMetadataKey.EventIndication, new byte[] {0x32});
        assertTrue(value instanceof EventIndication);
        EventIndication uut = (EventIndication) value;
        assertEquals(uut.getEventIndicationKind(), EventIndicationKind.MOVE);
        assertEquals(uut.getDisplayName(), "Event Indication");
        assertEquals(uut.getDisplayableValue(), "MOVE");
        assertEquals(uut.getBytes(), new byte[] {0x32});
    }

    @Test
    public void checkModify() {
        EventIndicationKind uut = EventIndicationKind.MODIFY;
        assertEquals(uut.getDisplayName(), "Event Indication");
        assertEquals(uut.getDisplayableValue(), "MODIFY");
        assertEquals(uut.getEncodedValue(), 0x33);
    }

    @Test
    public void checkModifyFromFactory() throws KlvParseException {
        IAnnotationMetadataValue value =
                UniversalSetFactory.createValue(
                        AnnotationMetadataKey.EventIndication, new byte[] {0x33});
        assertTrue(value instanceof EventIndication);
        EventIndication uut = (EventIndication) value;
        assertEquals(uut.getEventIndicationKind(), EventIndicationKind.MODIFY);
        assertEquals(uut.getDisplayName(), "Event Indication");
        assertEquals(uut.getDisplayableValue(), "MODIFY");
        assertEquals(uut.getBytes(), new byte[] {0x33});
    }

    @Test
    public void checkDelete() {
        EventIndicationKind uut = EventIndicationKind.DELETE;
        assertEquals(uut.getDisplayName(), "Event Indication");
        assertEquals(uut.getDisplayableValue(), "DELETE");
        assertEquals(uut.getEncodedValue(), 0x34);
    }

    @Test
    public void checkDeleteFromFactory() throws KlvParseException {
        IAnnotationMetadataValue value =
                UniversalSetFactory.createValue(
                        AnnotationMetadataKey.EventIndication, new byte[] {0x34});
        assertTrue(value instanceof EventIndication);
        EventIndication uut = (EventIndication) value;
        assertEquals(uut.getEventIndicationKind(), EventIndicationKind.DELETE);
        assertEquals(uut.getDisplayName(), "Event Indication");
        assertEquals(uut.getDisplayableValue(), "DELETE");
        assertEquals(uut.getBytes(), new byte[] {0x34});
    }

    @Test
    public void checkStatus() {
        EventIndicationKind uut = EventIndicationKind.STATUS;
        assertEquals(uut.getDisplayName(), "Event Indication");
        assertEquals(uut.getDisplayableValue(), "STATUS");
        assertEquals(uut.getEncodedValue(), 0x35);
    }

    @Test
    public void checkStatusFromFactory() throws KlvParseException {
        IAnnotationMetadataValue value =
                UniversalSetFactory.createValue(
                        AnnotationMetadataKey.EventIndication, new byte[] {0x35});
        assertTrue(value instanceof EventIndication);
        EventIndication uut = (EventIndication) value;
        assertEquals(uut.getEventIndicationKind(), EventIndicationKind.STATUS);
        assertEquals(uut.getDisplayName(), "Event Indication");
        assertEquals(uut.getDisplayableValue(), "STATUS");
        assertEquals(uut.getBytes(), new byte[] {0x35});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkBadLength() throws KlvParseException {
        new EventIndication(new byte[] {0x31, 0x32});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkBadLengthFactory() throws KlvParseException {
        UniversalSetFactory.createValue(
                AnnotationMetadataKey.EventIndication, new byte[] {0x34, 0x35});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkBadLengthFactoryShort() throws KlvParseException {
        UniversalSetFactory.createValue(AnnotationMetadataKey.EventIndication, new byte[] {});
    }
}
