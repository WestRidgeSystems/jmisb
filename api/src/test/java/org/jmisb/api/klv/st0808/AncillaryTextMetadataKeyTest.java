package org.jmisb.api.klv.st0808;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for AncillaryTextMetadataKey. */
public class AncillaryTextMetadataKeyTest {

    public AncillaryTextMetadataKeyTest() {}

    @Test
    public void checkOriginatorIdentifier() {
        AncillaryTextMetadataKey uut = AncillaryTextMetadataKey.Originator;
        assertEquals(uut.getIdentifier(), 1);
    }

    @Test
    public void checkOriginatorLookup() {
        AncillaryTextMetadataKey uut = AncillaryTextMetadataKey.getKey(1);
        assertEquals(uut, AncillaryTextMetadataKey.Originator);
    }

    @Test
    public void checkPrecisionTimeStampIdentifier() {
        AncillaryTextMetadataKey uut = AncillaryTextMetadataKey.PrecisionTimeStamp;
        assertEquals(uut.getIdentifier(), 2);
    }

    @Test
    public void checkMessageBodyIdentifier() {
        AncillaryTextMetadataKey uut = AncillaryTextMetadataKey.MessageBody;
        assertEquals(uut.getIdentifier(), 3);
    }

    @Test
    public void checkMessageBodyLookup() {
        AncillaryTextMetadataKey uut = AncillaryTextMetadataKey.getKey(3);
        assertEquals(uut, AncillaryTextMetadataKey.MessageBody);
    }

    @Test
    public void checkSourceIdentifier() {
        AncillaryTextMetadataKey uut = AncillaryTextMetadataKey.Source;
        assertEquals(uut.getIdentifier(), 4);
    }

    @Test
    public void checkSourceLookup() {
        AncillaryTextMetadataKey uut = AncillaryTextMetadataKey.getKey(4);
        assertEquals(uut, AncillaryTextMetadataKey.Source);
    }

    @Test
    public void checkMessageCreationTimeIdentifier() {
        AncillaryTextMetadataKey uut = AncillaryTextMetadataKey.MessageCreationTime;
        assertEquals(uut.getIdentifier(), 5);
    }

    @Test
    public void checkMessageCreationTimeLookup() {
        AncillaryTextMetadataKey uut = AncillaryTextMetadataKey.getKey(5);
        assertEquals(uut, AncillaryTextMetadataKey.MessageCreationTime);
    }

    @Test
    public void checkPrecisionTimeStampLookup() {
        AncillaryTextMetadataKey uut = AncillaryTextMetadataKey.getKey(2);
        assertEquals(uut, AncillaryTextMetadataKey.PrecisionTimeStamp);
    }

    @Test
    public void checkUndefinedIdentifier() {
        AncillaryTextMetadataKey uut = AncillaryTextMetadataKey.Undefined;
        assertEquals(uut.getIdentifier(), 0);
    }

    @Test
    public void checkUndefinedLookup() {
        AncillaryTextMetadataKey uut = AncillaryTextMetadataKey.getKey(0);
        assertEquals(uut, AncillaryTextMetadataKey.Undefined);
    }

    @Test
    public void checkUnknownLookup() {
        AncillaryTextMetadataKey uut = AncillaryTextMetadataKey.getKey(99);
        assertEquals(uut, AncillaryTextMetadataKey.Undefined);
    }
}
