package org.jmisb.st1002;

import org.jmisb.api.klv.IKlvKey;

/** Pseudo-key item for Section Data identifier. */
public class SectionDataIdentifierKey implements IKlvKey, Comparable<SectionDataIdentifierKey> {

    private final int sectionX;
    private final int sectionY;

    /**
     * Constructor.
     *
     * <p>Section numbers in the simple layout (mandated by ST 1002.2) only vary in one direction (X
     * for horizontal composition, Y for vertical composition).
     *
     * @param x the section number X identifier.
     * @param y the section number Y identifier.
     */
    public SectionDataIdentifierKey(final int x, final int y) {
        this.sectionX = x;
        this.sectionY = y;
    }

    @Override
    public int getIdentifier() {
        return sectionX + sectionY - 1;
    }

    /**
     * Section Number X value.
     *
     * @return the section number as an integer
     */
    public int getSectionX() {
        return sectionX;
    }

    /**
     * Section Number Y value.
     *
     * @return the section number as an integer
     */
    public int getSectionY() {
        return sectionY;
    }

    @Override
    public int compareTo(SectionDataIdentifierKey other) {
        return Integer.compare(getIdentifier(), other.getIdentifier());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.sectionX;
        hash = 29 * hash + this.sectionY;
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
        final SectionDataIdentifierKey other = (SectionDataIdentifierKey) obj;
        if (this.sectionX != other.sectionX) {
            return false;
        }
        return this.sectionY == other.sectionY;
    }
}
