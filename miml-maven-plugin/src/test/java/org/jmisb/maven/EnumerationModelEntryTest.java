package org.jmisb.maven;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for EnumerationModelEntry. */
public class EnumerationModelEntryTest {

    public EnumerationModelEntryTest() {}

    @Test
    public void number() {
        MimlToJava parser = new MimlToJava();
        EnumerationModelEntry uut =
                parser.parseEnumerationEntry(
                        " 02 = Over_Temperature {The device is over its maximum temperature};  ");
        assertEquals(uut.getNumber(), 2);
    }

    @Test
    public void name() {
        MimlToJava parser = new MimlToJava();
        EnumerationModelEntry uut =
                parser.parseEnumerationEntry(
                        " 02 = Over_Temperature {The device is over its maximum temperature};  ");
        assertEquals(uut.getName(), "Over_Temperature");
    }

    @Test
    public void description() {
        MimlToJava parser = new MimlToJava();
        EnumerationModelEntry uut =
                parser.parseEnumerationEntry(
                        " 02 = Over_Temperature {The device is over its maximum temperature};  ");
        assertEquals(uut.getDescription(), "The device is over its maximum temperature");
    }
}
