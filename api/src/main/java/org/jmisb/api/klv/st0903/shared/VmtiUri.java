package org.jmisb.api.klv.st0903.shared;

import java.net.URI;
import java.net.URISyntaxException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;

/**
 * Represents a URI string value in ST 0903.
 */
public class VmtiUri extends VmtiUtf8 implements IVmtiMetadataValue
{
    /**
     * VChip LS Tag 2 - Image URI.
     * <p>
     * A Uniform Resource Identifier (usually, a Uniform Resource Locator) that
     * refers to an image of the type specified by VChip LS Image Type Tag 1,
     * stored on a network or a file system. In some situations, probably
     * downstream from the collection source, such a reference could be used in
     * lieu of embedding the image chip in the stream.
     * <p>
     * Valid Values: A string of UTF-8 characters that comply with the rules for
     * building a valid URI.
     */
    public final static String IMAGE_URI = "Image URI";

    /**
     * VObject LS Tag 1 - Ontology.
     * <p>
     * The Ontology Tag 1 item is a Uniform Resource Identifier (URI), which
     * refers to a VObject ontology. Refer to the OWL Web Ontology Language for
     * the ontology used. The Jet Propulsion Laboratory Semantic Web for Earth
     * and Environmental Terminology (SWEET)
     * (https://bioportal.bioontology.org/ontologies/SWEET) provides a
     * collection of ontologies, written in the OWL ontology language which
     * serves as examples and starting points for the development of additional
     * domain-specific extended ontologies.
     * <p>
     * The Ontology Tag 1 needs to precede the ontologyClass Tag 2 item. For
     * bandwidth efficiency, Ontology can appear periodically and referenced by
     * subsequent VTarget Packs which use the specified ontology.
     */
    public final static String ONTOLOGY = "Ontology";

    /**
     * Create from string value.
     *
     * @param name The display name for the URI
     * @param value The URI value as a string
     */
    public VmtiUri(String name, String value)
    {
        super(name, value);
    }

    /**
     * Create from URI value.
     *
     * @param name The display name for the URI
     * @param uri The URI value as a URI
     */
    public VmtiUri(String name, URI uri)
    {
        super(name, uri.toString());
    }

    /**
     * Create from encoded bytes.
     *
     * @param name The display name for the URI
     * @param bytes Encoded byte array
     */
    public VmtiUri(String name, byte[] bytes)
    {
        super(name, bytes);
    }

    /**
     * Get the text value as a URI.
     * @return URI corresponding to the value.
     * @throws URISyntaxException if the value is not valid.
     */
    public URI getUri() throws URISyntaxException
    {
        return new URI(this.getValue());
    }
}
