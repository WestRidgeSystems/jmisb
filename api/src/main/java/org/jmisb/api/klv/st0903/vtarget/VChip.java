package org.jmisb.api.klv.st0903.vtarget;

import java.util.EnumSet;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ParseOptions;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.vchip.VChipLS;

/**
 * VChip Image Chip (ST0903 VTarget Pack Tag 105).
 * <p>
 * From ST0903:
 * <blockquote>
 * Allows inclusion of an image “chip” of the target. This LS will find use in
 * bandwidth constrained environments, where the operator does not have access
 * to the underlying Motion Imagery stream. In general, the image chip will
 * simply be “embedded” with the VMTI metadata. However, this specification
 * permits reference to an image using a Uniform Resource Identifier/Locator
 * (URI / URL) to support linking to a previously stored image, obviating the
 * need to include the image data itself in the stream.
 * </blockquote>
 */
public class VChip implements IVmtiMetadataValue
{
    private final VChipLS value;

    /**
     * Create from value.
     *
     * @param vchip the VChip Local Set.
     */
    public VChip(VChipLS vchip)
    {
        value = vchip;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array comprising the VChip LS
     * @param parseOptions any special parse options
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public VChip(byte[] bytes, EnumSet<ParseOptions> parseOptions) throws KlvParseException
    {
        value = new VChipLS(bytes, parseOptions);
    }

    @Override
    public byte[] getBytes()
    {
        return value.getBytes();
    }

    @Override
    public String getDisplayableValue()
    {
        return "[VChip]";
    }

    @Override
    public final String getDisplayName()
    {
        return "Image Chip";
    }

    /**
     * Get the VChipLS.
     *
     * @return the chip local set.
     */
    public VChipLS getChip()
    {
        return value;
    }
}
