package org.jmisb.api.klv.st0903.shared;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Represents a URI string value in ST 0903.
 */
public class VmtiUri extends VmtiTextString
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
