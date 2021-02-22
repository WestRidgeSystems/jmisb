package org.jmisb.api.klv.st0903.shared;

import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.st0903.vtracker.AccelerationPack;
import org.jmisb.api.klv.st0903.vtracker.VelocityPack;

/**
 * Enumeration of the various fields used in {@link LocationPack}, {@link VelocityPack} and {@link
 * AccelerationPack}.
 *
 * <p>Each of these corresponds to part of the pack structure. Note that not all of them have to be
 * filled in.
 */
public enum LocVelAccPackKey implements IKlvKey {
    unknown(0),
    east(1),
    north(2),
    up(3),
    sigEast(4),
    sigNorth(5),
    sigUp(6),
    rhoEastNorth(7),
    rhoEastUp(8),
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
