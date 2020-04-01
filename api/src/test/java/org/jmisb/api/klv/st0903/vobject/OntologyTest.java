package org.jmisb.api.klv.st0903.vobject;

import java.net.URI;
import java.net.URISyntaxException;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.VmtiUri;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OntologyTest
{
    final byte[] bytes = new byte[]{0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F, 0x72, 0x61, 0x77, 0x2E, 0x67, 0x69, 0x74, 0x68, 0x75, 0x62, 0x75, 0x73, 0x65, 0x72, 0x63, 0x6F, 0x6E, 0x74, 0x65, 0x6E, 0x74, 0x2E, 0x63, 0x6F, 0x6D, 0x2F, 0x6F, 0x77, 0x6C, 0x63, 0x73, 0x2F, 0x70, 0x69, 0x7A, 0x7A, 0x61, 0x2D, 0x6F, 0x6E, 0x74, 0x6F, 0x6C, 0x6F, 0x67, 0x79, 0x2F, 0x6D, 0x61, 0x73, 0x74, 0x65, 0x72, 0x2F, 0x70, 0x69, 0x7A, 0x7A, 0x61, 0x2E, 0x6F, 0x77, 0x6C};

    @Test
    public void testOntology()
    {
        // There is no example in the ST document.
        final String stringVal = "https://raw.githubusercontent.com/owlcs/pizza-ontology/master/pizza.owl";

        VmtiUri string = new VmtiUri(VmtiUri.ONTOLOGY, stringVal);
        Assert.assertEquals(string.getDisplayName(), VmtiUri.ONTOLOGY);
        VmtiUri stringFromBytes = new VmtiUri(VmtiUri.ONTOLOGY, bytes);
        Assert.assertEquals(stringFromBytes.getDisplayName(), VmtiUri.ONTOLOGY);

        Assert.assertEquals(string.getBytes(), bytes);
        Assert.assertEquals(string.getDisplayableValue(), "https://raw.githubusercontent.com/owlcs/pizza-ontology/master/pizza.owl");
        Assert.assertEquals(stringFromBytes.getValue(), stringVal);
        Assert.assertEquals(stringFromBytes.getDisplayableValue(), "https://raw.githubusercontent.com/owlcs/pizza-ontology/master/pizza.owl");
    }

    @Test
    public void testOntologyFromURI() throws URISyntaxException
    {
        // There is no example in the ST document.
        final URI uriSource = new URI("https://raw.githubusercontent.com/owlcs/pizza-ontology/master/pizza.owl");

        VmtiUri string = new VmtiUri(VmtiUri.IMAGE_URI, uriSource);
        Assert.assertEquals(string.getDisplayName(), VmtiUri.IMAGE_URI);
        VmtiUri stringFromBytes = new VmtiUri(VmtiUri.IMAGE_URI, bytes);
        Assert.assertEquals(stringFromBytes.getDisplayName(), VmtiUri.IMAGE_URI);

        Assert.assertEquals(string.getBytes(), bytes);
        Assert.assertEquals(string.getDisplayableValue(), "https://raw.githubusercontent.com/owlcs/pizza-ontology/master/pizza.owl");
        Assert.assertEquals(stringFromBytes.getValue(), "https://raw.githubusercontent.com/owlcs/pizza-ontology/master/pizza.owl");
        Assert.assertEquals(stringFromBytes.getDisplayableValue(), "https://raw.githubusercontent.com/owlcs/pizza-ontology/master/pizza.owl");
    }

    @Test
    public void testFactoryOntology() throws KlvParseException, URISyntaxException
    {
        IVmtiMetadataValue v = VObjectLS.createValue(VObjectMetadataKey.ontology, bytes);
        Assert.assertTrue(v instanceof VmtiUri);
        VmtiUri uri = (VmtiUri)v;
        Assert.assertEquals(uri.getDisplayName(), VmtiUri.ONTOLOGY);
        Assert.assertEquals(uri.getBytes(), bytes);
        Assert.assertEquals(uri.getDisplayableValue(), "https://raw.githubusercontent.com/owlcs/pizza-ontology/master/pizza.owl");
        Assert.assertEquals(uri.getUri(), new URI("https://raw.githubusercontent.com/owlcs/pizza-ontology/master/pizza.owl"));
    }
}
