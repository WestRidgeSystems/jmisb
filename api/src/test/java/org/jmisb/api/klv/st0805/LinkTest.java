package org.jmisb.api.klv.st0805;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit test for CoT Link class. */
public class LinkTest {

    public LinkTest() {}

    @Test
    public void basicXML() {
        Link link = new Link();
        link.setLinkRelation("p-p");
        link.setLinkType("a-f-A-M-F");
        link.setLinkUid("SupaFly.3");
        assertEquals(link.getLinkRelation(), "p-p");
        assertEquals(link.getLinkType(), "a-f-A-M-F");
        assertEquals(link.getLinkUid(), "SupaFly.3");
        StringBuilder sb = new StringBuilder();
        link.writeAsXML(sb);
        assertEquals(sb.toString(), "<link relation='p-p' type='a-f-A-M-F' uid='SupaFly.3'/>");
    }

    @Test
    public void xmlNoRelation() {
        Link link = new Link();
        link.setLinkType("a-f-A-M-F");
        link.setLinkUid("SupaFly.3");
        StringBuilder sb = new StringBuilder();
        link.writeAsXML(sb);
        assertEquals(sb.toString(), "");
    }

    @Test
    public void xmlNoUid() {
        Link link = new Link();
        link.setLinkRelation("p-p");
        link.setLinkType("a-f-A-M-F");
        StringBuilder sb = new StringBuilder();
        link.writeAsXML(sb);
        assertEquals(sb.toString(), "");
    }

    @Test
    public void xmlNoType() {
        Link link = new Link();
        link.setLinkRelation("p-p");
        link.setLinkUid("SupaFly.3");
        StringBuilder sb = new StringBuilder();
        link.writeAsXML(sb);
        assertEquals(sb.toString(), "");
    }
}
