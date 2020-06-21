package org.jmisb.api.klv.st0102;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.UniversalLabel;

/** ST 0102 key */
public enum SecurityMetadataKey implements IKlvKey {
    Undefined(
            0,
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                        0x00, 0x00, 0x00, 0x00
                    })),
    SecurityClassification(1, SecurityMetadataConstants.securityClassificationUl),
    CcCodingMethod(2, SecurityMetadataConstants.ccCodingMethodUl),
    ClassifyingCountry(3, SecurityMetadataConstants.classifyingCountryUl),
    SciShiInfo(4, SecurityMetadataConstants.sciShiInfoUl),
    Caveats(5, SecurityMetadataConstants.caveatsUl),
    ReleasingInstructions(6, SecurityMetadataConstants.releasingInstructionsUl),
    ClassifiedBy(7, SecurityMetadataConstants.classifiedByUl),
    DerivedFrom(8, SecurityMetadataConstants.derivedFromUl),
    ClassificationReason(9, SecurityMetadataConstants.classificationReasonUl),
    DeclassificationDate(10, SecurityMetadataConstants.declassificationDateUl),
    MarkingSystem(11, SecurityMetadataConstants.markingSystemUl),
    OcCodingMethod(12, SecurityMetadataConstants.ocCodingMethodUl),
    ObjectCountryCodes(13, SecurityMetadataConstants.objectCountryCodesUl),
    ClassificationComments(14, SecurityMetadataConstants.classificationCommentsUl),
    // TODO: 15 - 18, UMID - this is a difficult case for UL variation.
    StreamId(19, SecurityMetadataConstants.streamIdUl),
    TransportStreamId(20, SecurityMetadataConstants.transportStreamIdUl),
    ItemDesignatorId(21, SecurityMetadataConstants.itemDesignatorIdUl),
    Version(22, SecurityMetadataConstants.versionUl),
    CcCodingMethodVersionDate(23, SecurityMetadataConstants.ccCodingMethodVersionDateUl),
    OcCodingMethodVersionDate(24, SecurityMetadataConstants.ocCodingMethodVersionDateUl);

    private int tag;
    private UniversalLabel ul;

    private static final Map<Integer, SecurityMetadataKey> tagTable = new HashMap<>();
    private static final Map<UniversalLabel, SecurityMetadataKey> ulTable = new HashMap<>();

    static {
        for (SecurityMetadataKey key : values()) {
            tagTable.put(key.tag, key);
            ulTable.put(key.ul, key);
        }
    }

    SecurityMetadataKey(int tag, UniversalLabel ul) {
        this.tag = tag;
        this.ul = ul;
    }

    @Override
    public int getTag() {
        return tag;
    }

    public UniversalLabel getUl() {
        return ul;
    }

    public static SecurityMetadataKey getKey(int tag) {
        return tagTable.containsKey(tag) ? tagTable.get(tag) : Undefined;
    }

    public static SecurityMetadataKey getKey(UniversalLabel ul) {
        return ulTable.containsKey(ul) ? ulTable.get(ul) : Undefined;
    }
}
