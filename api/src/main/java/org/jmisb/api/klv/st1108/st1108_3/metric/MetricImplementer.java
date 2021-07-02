package org.jmisb.api.klv.st1108.st1108_3.metric;

import java.nio.charset.StandardCharsets;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.st1108.*;

/**
 * Metric Implementer.
 *
 * <p>From ST 1108.3:
 *
 * <blockquote>
 *
 * <p>The Metric Implementer mandatory item identifies the organization responsible for how a metric
 * is calculated. This enables various versions of the same metric without having to maintain an
 * active registry. The string should be Name of Company/Organization[ASCII Code 30]Subgroup. ASCII
 * Code 30 is Record Separator and used here to separate two meaningful strings with a default
 * separator. For example: "Company XYZ[ASCII Code 30]Imaging Dept."
 *
 * </blockquote>
 *
 * This implementation will parse the value at the Record Separator character and internally manage
 * the two parts. The required Record Separator is automatically added on encoding.
 */
public class MetricImplementer implements IMetricLocalSetValue {
    private final String orgPart;
    private final String subgroupPart;

    /**
     * Create from values.
     *
     * @param organization the organization or company name implementing the metric
     * @param subgroup the subgroup within the organization or company
     */
    public MetricImplementer(String organization, String subgroup) {
        orgPart = organization;
        subgroupPart = subgroup;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of the metric version encoded in UTF-8.
     * @throws KlvParseException if the byte array is not correctly formatted (e.g. missing Record
     *     Separator)
     */
    public MetricImplementer(byte[] bytes) throws KlvParseException {
        String[] parts = new String(bytes, StandardCharsets.UTF_8).split("\u001e");
        if (parts.length != 2) {
            throw new KlvParseException(
                    "Did not find correctly formatted Metric Implementer - missing Record Separator");
        }
        orgPart = parts[0];
        subgroupPart = parts[1];
    }

    @Override
    public byte[] getBytes() {
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        arrayBuilder.append(orgPart.getBytes(StandardCharsets.UTF_8));
        arrayBuilder.appendByte((byte) 0x1E);
        arrayBuilder.append(subgroupPart.getBytes(StandardCharsets.UTF_8));
        return arrayBuilder.toBytes();
    }

    @Override
    public String getDisplayableValue() {
        return getOrganization() + " - " + getSubgroup();
    }

    @Override
    public final String getDisplayName() {
        return "Metric Implementer";
    }

    /**
     * Get the organization part of the metric implementer.
     *
     * @return the metric implementer organization.
     */
    public String getOrganization() {
        return orgPart;
    }

    /**
     * Get the subgroup part of the metric implementer.
     *
     * @return the metric implementer subgroup.
     */
    public String getSubgroup() {
        return subgroupPart;
    }
}
