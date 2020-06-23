package org.jmisb.api.klv.st1204;

/**
 * Type of Identifier.
 *
 * <p>Sensor Identifiers and Platform Identifiers can be Physical, Virtual or Managed identifiers.
 *
 * <p>From ST1204.3 Section 6.1.1:
 *
 * <blockquote>
 *
 * <b>Physical Identifier</b>: generated or stored <i>within</i> a device itself and never changes
 * over the lifetime of the device. Should the device be power cycled the exact same identifier
 * applies (i.e., persistent). Likewise, removing the device from the system and later
 * re-integrating (or moved to another system) the same identifier applies for that device (i.e.,
 * consistent). Physical Identifiers are frame accurate, meaning every frame of the Motion Imagery
 * Data associates with a Physical Identifier. No device will generate, store or use a predefined
 * Physical Device Identifier assigned to another device.
 *
 * <p><b>Virtual Identifier</b>: generated or stored <i>outside</i> of the physical device by an
 * external host device (e.g., flight computer) and managed by that host so it is persistent. If the
 * device or host is power cycled the exact same identifier applies (i.e., persistent). Similarly,
 * the host will manage the change to the Virtual Identifier when swapping or changing the device.
 * Inserting a Virtual Identifier into all copies of the Motion Imagery Data sent from, or stored
 * on, the platform maintains the ubiquitous nature of the identifier. Although a Virtual Identifier
 * does not need to be frame accurate, requirement MISB ST 1204.1-12 places limits on its update
 * rate.
 *
 * <p><b>Managed Identifier</b>: generated or stored by the control station (e.g., GCS) and managed
 * by the control station so it is persistent. Devices in a control station need to provide the
 * exact same identifier (i.e., persistent) if power cycled. The control station will manage changes
 * to the Managed Identifier when swapping or changing the device. Although a Managed Identifier
 * does not need to be frame accurate, requirement MISB ST 1204.1-16 places limits on its update
 * rate.
 *
 * <p>The difference between a Virtual Identifier and Managed Identifier is the Virtual Identifier
 * is ubiquitous while a Managed Identifier only serves users downstream from the control station;
 * this means a Virtual Identifier will be a higher identifier quality level than a Managed
 * Identifier.
 *
 * </blockquote>
 *
 * Note: The values of these identifier types are those used in the Usage part of the Core
 * Identifier, without any bit shifting. So a Physical Identifier is {@code 0b11}, rather than
 * {@code 0b011xxxx0} for Sensor Physical Identifier or {@code 0b0xx11xx0} for a Platform Physical
 * Identifier.
 */
public enum IdType {

    /**
     * Identifier is not present.
     *
     * <p>This corresponds to 0x0 (0b00).
     */
    None(0),
    /**
     * Identifier is a Managed Identifier.
     *
     * <p>This corresponds to 0x1 (0b01).
     */
    Managed(1),
    /**
     * Identifier is a Virtual Identifier.
     *
     * <p>This corresponds to 0x2 (0b10)
     */
    Virtual(2),
    /**
     * Identifier is a Physical Identifier.
     *
     * <p>This corresponds to 0x03 (0b11).
     */
    Physical(3);

    /**
     * Lookup an identifier by specified value.
     *
     * <p>< pre>{@code IdType idType = IdType.fromValue(2); // idType is IdType.Virtual }</pre>
     *
     * <p>< pre>{@code IdType idType = IdType.fromValue(0); // idType is IdType.None }</pre>
     *
     * @param i the integer value of the identifier.
     * @return the equivalent IdType.
     */
    static IdType fromValue(int i) {
        for (IdType x : IdType.values()) {
            if (x.value == i) {
                return x;
            }
        }
        return None;
    }

    private final int value;

    private IdType(int v) {
        this.value = v;
    }

    int getValue() {
        return value;
    }
}
