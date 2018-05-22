package org.jmisb.api.klv.st0102.universalset;

import org.jmisb.api.klv.st0102.Classification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ClassificationUniversalTest
{
    @Test
    public void testCreateFromValue()
    {
        ClassificationUniversal classification = new ClassificationUniversal(Classification.UNCLASSIFIED);
        byte[] bytes = classification.getBytes();
        String string = new String(bytes);
        Assert.assertEquals(string, "UNCLASSIFIED//");
        Assert.assertEquals(classification.getClassification(), Classification.UNCLASSIFIED);

        classification = new ClassificationUniversal(Classification.TOP_SECRET);
        bytes = classification.getBytes();
        string = new String(bytes);
        Assert.assertEquals(string, "TOP SECRET//");
        Assert.assertEquals(classification.getClassification(), Classification.TOP_SECRET);
    }

    @Test
    public void testCreateFromEncoded()
    {
        ClassificationUniversal classification = new ClassificationUniversal("UNCLASSIFIED//".getBytes());
        Assert.assertEquals(classification.getClassification(), Classification.UNCLASSIFIED);
        byte[] bytes = classification.getBytes();
        String string = new String(bytes);
        Assert.assertEquals(string, "UNCLASSIFIED//");

        classification = new ClassificationUniversal("RESTRICTED//".getBytes());
        Assert.assertEquals(classification.getClassification(), Classification.RESTRICTED);
        bytes = classification.getBytes();
        string = new String(bytes);
        Assert.assertEquals(string, "RESTRICTED//");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalValue()
    {
        new ClassificationUniversal("UNCLASSIFIED/".getBytes());
    }
}
