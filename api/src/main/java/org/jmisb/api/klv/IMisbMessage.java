package org.jmisb.api.klv;

import java.util.Set;

/**
 * A packet containing MISB-compliant metadata
 */
public interface IMisbMessage extends INestedKlvValue
{
    /**
     * Get the Universal Label (UL) identifying the message type
     * @return The {@link UniversalLabel}
     */
    UniversalLabel getUniversalLabel();

    /**
     * Frame the message into a byte array
     * @param isNested If true, the key and length field are omitted, and only the value will be written
     * @return Generated byte array
     */
    byte[] frameMessage(boolean isNested);
    
    /**
     * A display header for the message type (e.g. which standard it conforms to).
     * @return String containing human-readable metadata message label.
     */
    String displayHeader();
}
