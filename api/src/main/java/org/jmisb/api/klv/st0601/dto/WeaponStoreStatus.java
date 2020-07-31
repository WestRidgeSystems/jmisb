package org.jmisb.api.klv.st0601.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Weapon / Store Status code.
 *
 * <p>This is the "General Status" from ST0601 Item 140.
 */
public enum WeaponStoreStatus {
    /** Off - No power operating power is available to the Store. */
    Off((byte) 0),
    /** Initialization - Operating Power is on and the Store is initializing. */
    Initialization((byte) 1),
    /** Ready/Degraded - Store initialization completed – full capability not available. */
    ReadyDegraded((byte) 2),
    /** Ready/All Up Round - Store initialization completed – full capability is available. */
    ReadyAllUpRound((byte) 3),
    /**
     * Launch - Dedicated release processes started including activation of irreversible functions.
     */
    Launch((byte) 4),
    /** Free Flight - Store has successfully separated from the platform. */
    FreeFlight((byte) 5),
    /** Abort - Either commanded into or safety critical anomaly detected. */
    Abort((byte) 6),
    /** Miss Fire - Weapon miss-fired. */
    MissFire((byte) 7),
    /**
     * Hang Fire - Weapon which does not separate from aircraft when activated for employment or
     * jettison.
     */
    HangFire((byte) 8),
    /**
     * Jettisoned - Intentional or emergency separation of weapon from aircraft with the weapon in
     * the unarmed state (fuze-safe).
     */
    Jettisoned((byte) 9),
    /** Stepped Over - Weapon is bypassed due to failure. Weapon can still be jettisoned. */
    SteppedOver((byte) 10),
    /** No Status Available - Unknown status. */
    NoStatusAvailable((byte) 11);

    private byte code;

    private static final Map<Byte, WeaponStoreStatus> lookupTable = new HashMap<>();

    static {
        for (WeaponStoreStatus c : values()) {
            lookupTable.put(c.code, c);
        }
    }

    WeaponStoreStatus(byte c) {
        code = c;
    }

    /**
     * Get the encoded form of this enumerated value.
     *
     * @return byte corresponding to enumeration value
     */
    public byte getCode() {
        return code;
    }

    /**
     * Get the store status corresponding to this encoded value.
     *
     * @param code the encoded value
     * @return the corresponding enumeration
     * @throws IllegalArgumentException if the code is not valid
     */
    public static WeaponStoreStatus getStatus(byte code) {
        if (lookupTable.containsKey(code)) {
            return lookupTable.get(code);
        }
        throw new IllegalArgumentException("Invalid Weapon Store Status code: " + code);
    }
}
