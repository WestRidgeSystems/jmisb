package org.jmisb.api.klv.st0805;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for CotPoint class. */
public class CotPointTest {

    public CotPointTest() {}

    @Test
    public void serialise() {
        CotPoint uut = new CotPoint();
        uut.setLat(10.0);
        uut.setLon(20.0);
        uut.setHae(30.0);
        uut.setCe(100.0);
        uut.setLe(50.0);
        StringBuilder sb = new StringBuilder();
        uut.writeAsXML(sb);
        assertEquals(
                sb.toString(), "<point lat='10.0' lon='20.0' hae='30.0' ce='100.0' le='50.0'/>");
    }

    @Test
    public void serialiseEmpty() {
        CotPoint uut = new CotPoint();
        StringBuilder sb = new StringBuilder();
        uut.writeAsXML(sb);
        assertEquals(sb.toString(), "");
    }

    @Test
    public void serialiseIncompleteNoLat() {
        CotPoint uut = new CotPoint();
        uut.setLon(20.0);
        uut.setHae(30.0);
        uut.setCe(100.0);
        uut.setLe(50.0);
        StringBuilder sb = new StringBuilder();
        uut.writeAsXML(sb);
        assertEquals(sb.toString(), "");
    }

    @Test
    public void serialiseIncompleteNoLon() {
        CotPoint uut = new CotPoint();
        uut.setLat(10.0);
        uut.setHae(30.0);
        uut.setCe(100.0);
        uut.setLe(50.0);
        StringBuilder sb = new StringBuilder();
        uut.writeAsXML(sb);
        assertEquals(sb.toString(), "");
    }

    @Test
    public void serialiseIncompleteNoHae() {
        CotPoint uut = new CotPoint();
        uut.setLat(10.0);
        uut.setLon(20.0);
        uut.setCe(100.0);
        uut.setLe(50.0);
        StringBuilder sb = new StringBuilder();
        uut.writeAsXML(sb);
        assertEquals(sb.toString(), "");
    }

    @Test
    public void serialiseNoErrors() {
        CotPoint uut = new CotPoint();
        uut.setLat(10.0);
        uut.setLon(20.0);
        uut.setHae(30.0);
        StringBuilder sb = new StringBuilder();
        uut.writeAsXML(sb);
        assertEquals(
                sb.toString(),
                "<point lat='10.0' lon='20.0' hae='30.0' ce='9999999.0' le='9999999.0'/>");
    }
}
