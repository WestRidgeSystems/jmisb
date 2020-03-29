package org.jmisb.api.klv.st0903.shared;

import org.jmisb.api.klv.st0903.IVmtiMetadataValue;


/**
 * Shared algorithm Id implementation.
 * <p>
 * This is a tentative implementation, pending decision from MISB on range of this value.
 */
public class AlgorithmId extends VmtiV3Value implements IVmtiMetadataValue
{
    /**
     * Create from value
     *
     * @param id the algorithm identifier.
     */
    public AlgorithmId(int id)
    {
        super(id);
    }

    /**
     * Create from encoded bytes
     * @param bytes identifier, encoded as a variable length unsigned int (max 3 bytes)
     */
    public AlgorithmId(byte[] bytes)
    {
        super(bytes);
    }

    @Override
    public String getDisplayName()
    {
        return "Algorithm Id";
    }

}
