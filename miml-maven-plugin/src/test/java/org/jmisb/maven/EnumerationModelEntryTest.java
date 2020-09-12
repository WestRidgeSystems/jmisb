package org.jmisb.maven;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for EnumerationModelEntry. */
public class EnumerationModelEntryTest {

    public EnumerationModelEntryTest() {}

    @Test
    public void number() {
        EnumerationModelEntry uut =
                MimlToJava.parseEnumerationEntry(
                        " 02 = Over_Temperature {The device is over its maximum temperature};  ");
        assertEquals(uut.getNumber(), 2);
    }

    @Test
    public void name() {
        EnumerationModelEntry uut =
                MimlToJava.parseEnumerationEntry(
                        " 02 = Over_Temperature {The device is over its maximum temperature};  ");
        assertEquals(uut.getName(), "Over_Temperature");
    }

    @Test
    public void description() {
        EnumerationModelEntry uut =
                MimlToJava.parseEnumerationEntry(
                        " 02 = Over_Temperature {The device is over its maximum temperature};  ");
        assertEquals(uut.getDescription(), "The device is over its maximum temperature");
    }
}
