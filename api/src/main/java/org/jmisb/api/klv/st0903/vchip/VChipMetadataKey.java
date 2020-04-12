package org.jmisb.api.klv.st0903.vchip;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;

/**
 * Metadata tag numbers for ST0903 VChip local set.
 */
public enum VChipMetadataKey implements IKlvKey
{
    /**
     * Unknown key. This should not be created.
     */
    Undefined(0),
    /**
     * Internet Assigned Numbers Authority (IANA) image media subtype specifying
     * the VChip image type (limited to “jpeg”, and “png”).
     */
    imageType(1),
    /**
     * Uniform Resource Identifier (or Uniform Resource Locator) that refers to
     * an image stored on a server.
     */
    imageUri(2),
    /**
     * An image “chip” of the image type specified by Tag 1.
     */
    embeddedImage(3);

    private int tag;

    private static final Map<Integer, VChipMetadataKey> tagTable = new HashMap<>();

    static
    {
        for (VChipMetadataKey key : values())
        {
            tagTable.put(key.tag, key);
        }
    }

    VChipMetadataKey(int tag)
    {
        this.tag = tag;
    }

    /**
     * Get the tag number associated with this VChip tag.
     *
     * @return the tag associated with this enumerated value.
     */
    @Override
    public int getTagCode()
    {
        return tag;
    }

    /**
     * Look up a VChip tag by enum value.
     *
     * @param tag the tag number.
     * @return the corresponding VChip tag.
     */
    public static VChipMetadataKey getKey(int tag)
    {
        return tagTable.containsKey(tag) ? tagTable.get(tag) : Undefined;
    }

}
