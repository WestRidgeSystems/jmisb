package org.jmisb.api.klv.st0903.vtracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.jmisb.api.klv.st0903.shared.IVTrackMetadataValue;
import org.jmisb.api.klv.st0903.shared.LocationPack;
import org.jmisb.api.klv.st0903.vtarget.TargetLocation;
import org.jmisb.core.klv.ArrayUtils;

/**
 * Boundary Series (ST0903 VTracker Local Set Tag 5).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * BoundarySeries is of type BoundarySeries that specifies a bounding area or volume, which encloses
 * the full extent of VMTI detections for the entity. For a simple, planar bounding box, the area
 * will generally lie on the surface of the Earth (although not necessarily, depending upon the
 * Height values provided) defining the “footprint” of the track. By specifying additional vertices,
 * enables describing complex, multifaceted volumes.
 *
 * </blockquote>
 */
public class BoundarySeries implements IVmtiMetadataValue, IVTrackMetadataValue {
    private final List<LocationPack> boundary = new ArrayList<>();

    /**
     * Create from value.
     *
     * @param locations the Location Packs to add.
     */
    public BoundarySeries(List<LocationPack> locations) {
        boundary.addAll(locations);
    }

    /**
     * Create from encoded bytes.
     *
     * <p>ST0903 changed the encoding for the Location to 2-byte IMAPB (4-byte IMAPB for Latitude /
     * Longitude) in ST0903.4. Earlier versions used a set of unsigned integer encoding that was
     * then mapped into the same ranges that the IMAPB encoding uses. Which formatting applies can
     * only be determined from the ST0903 version in the parent {@link
     * org.jmisb.api.klv.st0903.VmtiLocalSet}. The {@code encodingMode} parameter determines
     * whether to parse using the legacy encoding or current encoding.
     *
     * <p>Note that this only affects parsing. Output encoding is IMAPB (ST0903.4 or later).
     *
     * @param bytes Encoded byte array comprising the BoundarySeries
     * @param encodingMode which encoding mode the {@code bytes} parameter uses.
     */
    public BoundarySeries(byte[] bytes, EncodingMode encodingMode) {
        int index = 0;
        while (index < bytes.length - 1) {
            BerField lengthField = BerDecoder.decode(bytes, index, false);
            index += lengthField.getLength();
            byte[] packBytes = Arrays.copyOfRange(bytes, index, index + lengthField.getValue());
            LocationPack location =
                    TargetLocation.targetLocationPackFromBytes(packBytes, encodingMode);
            boundary.add(location);
            index += lengthField.getValue();
        }
    }

    @Override
    public byte[] getBytes() {
        int len = 0;
        List<byte[]> chunks = new ArrayList<>();
        for (LocationPack location : getLocations()) {
            byte[] localSetBytes = TargetLocation.serialiseLocationPack(location);
            byte[] lengthBytes = BerEncoder.encode(localSetBytes.length);
            chunks.add(lengthBytes);
            len += lengthBytes.length;
            chunks.add(localSetBytes);
            len += localSetBytes.length;
        }
        return ArrayUtils.arrayFromChunks(chunks, len);
    }

    @Override
    public String getDisplayableValue() {
        return "[Location Series]";
    }

    @Override
    public String getDisplayName() {
        return "Boundary";
    }

    /**
     * Get the TargetLocation structure.
     *
     * @return the list of Target Locations.
     */
    public List<LocationPack> getLocations() {
        return boundary;
    }
}
