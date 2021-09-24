package org.jmisb.api.klv.st0903.vtarget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.jmisb.api.klv.st0903.shared.IVTrackItemMetadataValue;
import org.jmisb.api.klv.st0903.shared.LocationPack;
import org.jmisb.core.klv.ArrayUtils;

/**
 * Target Boundary Series (ST0903 VTarget Pack Item 18 and VTrackItem Pack Item 14).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * Provides detailed geo-positioning information for the boundary around an area or volume of
 * interest. An arbitrary number of vertices defines the boundary. Each vertex is an element of type
 * Location. Typical boundary are the bounding boxes defined by two or four vertices. Location type
 * captures geo-positioning data about a specific location on or near the surface of the Earth. The
 * contents of these packs fall into three groups, namely, geospatial location (Latitude, Longitude,
 * and Height), standard deviations for these values, and correlation coefficients among them.
 * Location elements are Defined-Length Truncation Packs, omitting unknown or less important data
 * from the end of the Pack. Use of TargetBoundarySeries is preferred over Target Bounding Box (Tags
 * 13 through 16) when accuracy and correlation information is available and needed. Such
 * information aids fusion with other moving object indicators, such as, radar based GMTI, to
 * support track identification and tracking.
 *
 * </blockquote>
 */
public class TargetBoundarySeries implements IVmtiMetadataValue, IVTrackItemMetadataValue {
    private final List<LocationPack> boundary = new ArrayList<>();

    /**
     * Create from value.
     *
     * @param locations the Location Packs to add.
     */
    public TargetBoundarySeries(List<LocationPack> locations) {
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
     * @param bytes Encoded byte array comprising the TargetBoundarySeries
     * @param encodingMode which encoding mode the {@code bytes} parameter uses.
     */
    public TargetBoundarySeries(byte[] bytes, EncodingMode encodingMode) {
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
        return "Target Boundary";
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
