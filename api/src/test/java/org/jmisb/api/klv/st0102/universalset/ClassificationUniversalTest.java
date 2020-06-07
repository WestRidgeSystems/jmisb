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
        Assert.assertEquals(classification.getDisplayName(), "Classification");
        Assert.assertEquals(classification.getDisplayableValue(), "UNCLASSIFIED//");
        byte[] bytes = classification.getBytes();
        String string = new String(bytes);
        Assert.assertEquals(string, "UNCLASSIFIED//");
        Assert.assertEquals(classification.getClassification(), Classification.UNCLASSIFIED);

        classification = new ClassificationUniversal(Classification.TOP_SECRET);
        Assert.assertEquals(classification.getDisplayName(), "Classification");
        Assert.assertEquals(classification.getDisplayableValue(), "TOP SECRET//");
        bytes = classification.getBytes();
        string = new String(bytes);
        Assert.assertEquals(string, "TOP SECRET//");
        Assert.assertEquals(classification.getClassification(), Classification.TOP_SECRET);

        classification = new ClassificationUniversal(Classification.SECRET);
        Assert.assertEquals(classification.getDisplayName(), "Classification");
        Assert.assertEquals(classification.getDisplayableValue(), "SECRET//");
        bytes = classification.getBytes();
        string = new String(bytes);
        Assert.assertEquals(string, "SECRET//");
        Assert.assertEquals(classification.getClassification(), Classification.SECRET);

        classification = new ClassificationUniversal(Classification.CONFIDENTIAL);
        Assert.assertEquals(classification.getDisplayName(), "Classification");
        Assert.assertEquals(classification.getDisplayableValue(), "CONFIDENTIAL//");
        bytes = classification.getBytes();
        string = new String(bytes);
        Assert.assertEquals(string, "CONFIDENTIAL//");
        Assert.assertEquals(classification.getClassification(), Classification.CONFIDENTIAL);

        classification = new ClassificationUniversal(Classification.RESTRICTED);
        Assert.assertEquals(classification.getDisplayName(), "Classification");
        Assert.assertEquals(classification.getDisplayableValue(), "RESTRICTED//");
        bytes = classification.getBytes();
        string = new String(bytes);
        Assert.assertEquals(string, "RESTRICTED//");
        Assert.assertEquals(classification.getClassification(), Classification.RESTRICTED);
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

        classification = new ClassificationUniversal("CONFIDENTIAL//".getBytes());
        Assert.assertEquals(classification.getClassification(), Classification.CONFIDENTIAL);
        bytes = classification.getBytes();
        string = new String(bytes);
        Assert.assertEquals(string, "CONFIDENTIAL//");

        classification = new ClassificationUniversal("SECRET//".getBytes());
        Assert.assertEquals(classification.getClassification(), Classification.SECRET);
        bytes = classification.getBytes();
        string = new String(bytes);
        Assert.assertEquals(string, "SECRET//");

        classification = new ClassificationUniversal("TOP SECRET//".getBytes());
        Assert.assertEquals(classification.getClassification(), Classification.TOP_SECRET);
        bytes = classification.getBytes();
        string = new String(bytes);
        Assert.assertEquals(string, "TOP SECRET//");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalValue()
    {
        new ClassificationUniversal("UNCLASSIFIED/".getBytes());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalValueLookup()
    {
        ClassificationUniversal classificationUniversal = new ClassificationUniversal(Classification.UNKNOWN);
        classificationUniversal.getBytes();
    }
}
