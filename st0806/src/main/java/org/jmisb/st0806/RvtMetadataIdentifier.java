package org.jmisb.st0806;

import java.util.Comparator;
import java.util.Objects;
import org.jmisb.api.klv.IKlvKey;

/**
 * Identifier that can handle standard metadata, and AOI, POI and User Defined entries.
 *
 * <p>These are repeating entries, so we have special case code.
 */
public class RvtMetadataIdentifier implements IKlvKey, Comparable<RvtMetadataIdentifier> {

    private static final int AOI_FLAG_VALUE = 0x010000;
    private static final int POI_FLAG_VALUE = 0x020000;
    private static final int USER_DEFINED_FLAG_VALUE = 0x040000;

    private final Integer identifier;
    private final RvtMetadataKind kind;

    /**
     * Constructor.
     *
     * @param kind the kind of identifier
     * @param identifier a unique integer for this identifier object
     */
    public RvtMetadataIdentifier(final RvtMetadataKind kind, final int identifier) {
        this.kind = kind;
        this.identifier = identifier;
    }

    @Override
    public int getIdentifier() {
        switch (kind) {
            case AOI:
                return AOI_FLAG_VALUE + this.identifier;
            case POI:
                return POI_FLAG_VALUE + this.identifier;
            case USER_DEFINED:
                return USER_DEFINED_FLAG_VALUE + this.identifier;
            default:
                return identifier;
        }
    }

    /**
     * Get the kind of metadata that this identifier represents.
     *
     * @return kind of metadata as an enumeration value
     */
    public RvtMetadataKind getKind() {
        return kind;
    }

    /**
     * Get the kind-specific identification value.
     *
     * <p>This is different to the usual {@link #getIdentifier()} in that it is not unique across an
     * RVT Local Set. It is only unique within a kind of metadata. That is, there can be four "3"
     * values (for example) - one for an AOI, one for a POI, one for a user-defined value, and one
     * representing the TAS. Note that {@link #getIdentifier()} works around this problem by adding
     * in large values (unique range per kind).
     *
     * @return the kind-specific identifier as an unsigned integer value.
     */
    public int getKindId() {
        return identifier;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.identifier);
        hash = 43 * hash + Objects.hashCode(this.kind);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RvtMetadataIdentifier other = (RvtMetadataIdentifier) obj;
        if (!Objects.equals(this.identifier, other.identifier)) {
            return false;
        }
        return this.kind == other.kind;
    }

    @Override
    public String toString() {
        return "RvtMetadataIdentifier{" + "identifier=" + identifier + ", kind=" + kind + '}';
    }

    private static Comparator<RvtMetadataIdentifier> getComparator() {
        return Comparator.comparing(RvtMetadataIdentifier::getKind)
                .thenComparing(RvtMetadataIdentifier::getKindId);
    }

    @Override
    public int compareTo(RvtMetadataIdentifier o) {
        return getComparator().compare(this, o);
    }
}
