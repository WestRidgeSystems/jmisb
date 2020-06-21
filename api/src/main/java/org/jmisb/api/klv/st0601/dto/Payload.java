package org.jmisb.api.klv.st0601.dto;

import java.util.Objects;

/**
 * Data transfer object for Payload.
 *
 * <p>This is used by Payload List (ST0601 Tag 138).
 */
public class Payload {
    private int identifier;
    private int type;
    private String name;

    /**
     * Constructor.
     *
     * @param identifier the payload identifier, zero base index.
     * @param type the payload type - see getType() or setType() for valid values
     * @param name the payload name
     */
    public Payload(int identifier, int type, String name) {
        this.identifier = identifier;
        this.type = type;
        this.name = name;
    }

    /**
     * The identifier for this payload.
     *
     * <p>The Payload Identifier is a unique BER-OID integer sequentially assigned starting with the
     * number zero (0). The Active Payload (Tag 139) uses the Payload Identifier to specify which
     * payloads are active.
     *
     * @return the identifier.
     */
    public int getIdentifier() {
        return identifier;
    }

    /**
     * Set the identifier for this payload.
     *
     * <p>The Payload Identifier is a unique BER-OID integer sequentially assigned starting with the
     * number zero (0). The Active Payload (Tag 139) uses the Payload Identifier to specify which
     * payloads are active.
     *
     * @param identifier the zero index payload identifier.
     */
    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    /**
     * The type of this payload.
     *
     * <p>The Payload Type is a BER-OID enumeration. Valid values are 0 for Electro Optical MI
     * Sensor; 1 for LIDAR; 2 for RADAR; 3 for SIGINT; 4 for SAR.
     *
     * @return the payload type as an integer enumeration value.
     */
    public int getType() {
        return type;
    }

    /**
     * Set the type of this payload.
     *
     * <p>The Payload Type is a BER-OID enumeration. Valid values are 0 for Electro Optical MI
     * Sensor; 1 for LIDAR; 2 for RADAR; 3 for SIGINT; 4 for SAR.
     *
     * @param type the payload type as an integer enumeration value.
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * The human readable name for this payload.
     *
     * @return the name of the payload.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the human readable name for this payload.
     *
     * <p>Examples of this are "VIS Nose Camera", "ACME VIS Model 123" or "ACME IR Model 456"
     *
     * @param name the name of the payload.
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.identifier;
        hash = 97 * hash + this.type;
        hash = 97 * hash + Objects.hashCode(this.name);
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
        final Payload other = (Payload) obj;
        if (this.identifier != other.identifier) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
}
