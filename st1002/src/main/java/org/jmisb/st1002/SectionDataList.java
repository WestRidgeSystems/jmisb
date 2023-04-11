package org.jmisb.st1002;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.INestedKlvValue;

/**
 * Section Data list (ST 1002 Range Image Local Set Tag 20).
 *
 * <p>Section data, along with its supporting information is formatted in a Variable Length Pack
 * (VLP) (see SMPTE ST 336) called the Section Data VLP.
 *
 * <p>Section data is repeatable within the Range Image Local Set. {@code SectionDataList}
 * represents zero or more Section Data VLPs.
 *
 * @see NumberOfSectionsInX
 * @see NumberOfSectionsInY
 */
public class SectionDataList implements IRangeImageMetadataValue, INestedKlvValue {

    private final List<SectionData> vlps = new ArrayList<>();

    @Override
    public byte[] getBytes() {
        throw new UnsupportedOperationException("Should not be called");
    }

    @Override
    public String getDisplayName() {
        return "Section Data";
    }

    @Override
    public String getDisplayableValue() {
        return "[VLPs]";
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        SectionDataIdentifierKey key = (SectionDataIdentifierKey) tag;
        for (SectionData vlp : vlps) {
            if ((vlp.getSectionNumberX() == key.getSectionX())
                    && (vlp.getSectionNumberY() == key.getSectionY())) {
                return vlp;
            }
        }
        return null;
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        SortedSet<SectionDataIdentifierKey> identifiers = new TreeSet<>();
        vlps.forEach(
                (SectionData vlp) -> {
                    identifiers.add(
                            new SectionDataIdentifierKey(
                                    vlp.getSectionNumberX(), vlp.getSectionNumberY()));
                });
        return identifiers;
    }

    /**
     * Add a Section Data VLP to the list.
     *
     * @param sectionData the data to add
     */
    public void add(SectionData sectionData) {
        this.vlps.add(new SectionData(sectionData));
    }

    /**
     * Get the Section Data Variable Length Packs.
     *
     * @return copy of the packs as a list.
     */
    public List<SectionData> getPacks() {
        return new ArrayList<>(vlps);
    }
}
