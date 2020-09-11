package org.jmisb.maven;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ClassEntryModel and associated parsing. */
public class ClassEntryModelTest {

    public ClassEntryModelTest() {}

    @Test
    public void minValue() {
        MimlToJava parser = new MimlToJava();
        ClassModelEntry entry = parser.parseClassEntry("  38_syncPulseFreq : Real (0.0) { Hz};  ");
        assertEquals(entry.getMinValue(), 0.0);
        assertNull(entry.getMaxValue());
    }

    @Test
    public void number() {
        MimlToJava parser = new MimlToJava();
        ClassModelEntry entry = parser.parseClassEntry("  38_syncPulseFreq : Real (0.0) { Hz};  ");
        assertEquals(entry.getNumber(), 38);
    }

    @Test
    public void name() {
        MimlToJava parser = new MimlToJava();
        ClassModelEntry entry = parser.parseClassEntry("  38_syncPulseFreq : Real (0.0) { Hz};  ");
        assertEquals(entry.getName(), "syncPulseFreq");
    }

    @Test
    public void nameSentenceCase() {
        MimlToJava parser = new MimlToJava();
        ClassModelEntry entry = parser.parseClassEntry("  38_syncPulseFreq : Real (0.0) { Hz};  ");
        assertEquals(entry.getNameSentenceCase(), "SyncPulseFreq");
    }

    @Test
    public void type() {
        MimlToJava parser = new MimlToJava();
        ClassModelEntry entry = parser.parseClassEntry("  38_syncPulseFreq : Real (0.0) { Hz};  ");
        assertEquals(entry.getTypeName(), "Real");
    }

    @Test
    public void units() {
        MimlToJava parser = new MimlToJava();
        ClassModelEntry entry = parser.parseClassEntry("  38_syncPulseFreq : Real (0.0) { Hz};  ");
        assertEquals(entry.getUnits(), "Hz");
    }

    @Test
    public void maxValue() {
        MimlToJava parser = new MimlToJava();
        ClassModelEntry entry =
                parser.parseClassEntry(
                        "  36_differentialPressure : Real (0, 5000)          {hPa};");
        assertEquals(entry.getMinValue(), 0.0);
        assertEquals(entry.getMaxValue(), 5000.0);
    }

    @Test
    public void maxValue2PI() {
        MimlToJava parser = new MimlToJava();
        ClassModelEntry entry =
                parser.parseClassEntry(
                        "  38_windDirection        : Real (0, TWO_PI)        {rad};");
        assertEquals(entry.getMinValue(), 0.0);
        assertEquals(entry.getMaxValue(), 6.283185307179586, 0.000000000000001);
    }

    @Test
    public void maxValuePI() {
        MimlToJava parser = new MimlToJava();
        ClassModelEntry entry =
                parser.parseClassEntry("  38_windDirection        : Real (0, PI)        {rad};");
        assertEquals(entry.getMinValue(), 0.0);
        assertEquals(entry.getMaxValue(), 3.141592653589793, 0.000000000000001);
    }

    @Test
    public void maxValueHALF_PI() {
        MimlToJava parser = new MimlToJava();
        ClassModelEntry entry =
                parser.parseClassEntry(
                        "  38_windDirection        : Real (0, HALF_PI)        {rad};");
        assertEquals(entry.getMinValue(), 0.0);
        assertEquals(entry.getMaxValue(), 1.5707963267948965, 0.000000000000001);
    }
}
