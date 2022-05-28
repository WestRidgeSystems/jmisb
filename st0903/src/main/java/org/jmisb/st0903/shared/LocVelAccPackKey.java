package org.jmisb.st0903.shared;

import org.jmisb.api.klv.IKlvKey;
import org.jmisb.st0903.vtracker.AccelerationPack;
import org.jmisb.st0903.vtracker.VelocityPack;

/**
 * Enumeration of the various fields used in {@link LocationPack}, {@link VelocityPack} and {@link
 * AccelerationPack}.
 *
 * <p>Each of these corresponds to part of the pack structure. Note that not all of them have to be
 * filled in.
 */
public enum LocVelAccPackKey implements IKlvKey {
    /**
     * Unknown key.
     *
     * <p>This should not be created.
     */
    unknown(0),
    /** East component. */
    east(1),
    /** North component. */
    north(2),
    /** Up component. */
    up(3),
    /** Standard deviation of East component. */
    sigEast(4),
    /** Standard deviation of North component. */
    sigNorth(5),
    /** Standard deviation of Up component. */
    sigUp(6),
    /** Cross correlation of east and north components. */
    rhoEastNorth(7),
    /** Cross correlation of east and up components. */
    rhoEastUp(8),
    /** Cross correlation of north and up components. */
    rhoNorthUp(9);

    private LocVelAccPackKey(int key) {
        this.tag = key;
    }

    private final int tag;

    @Override
    public int getIdentifier() {
        return tag;
    }
}
