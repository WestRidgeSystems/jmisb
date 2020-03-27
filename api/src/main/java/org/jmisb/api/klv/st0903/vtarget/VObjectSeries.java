package org.jmisb.api.klv.st0903.vtarget;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.vobject.VObjectLS;

/**
 * VObject Ontology Series (ST0903 VTarget Pack Tag 107).
 * <p>
 * From ST0903:
 * <blockquote>
 * A Series of one or more VObject LS associated with a specific target. Each LS
 * contains ontology information. When using the VObject LS within a
 * VObjectSeries, omit the VObject LS Tag 102 because all values are of the same
 * type. Just specify the Length and Value for each VObject LS represented.
 * </blockquote>
 */
public class VObjectSeries implements IVmtiMetadataValue
{
    private static final Logger LOG = Logger.getLogger(VObjectSeries.class.getName());

    private final List<VObjectLS> vobjects = new ArrayList<>();

    /**
     * Create from value.
     *
     * @param objects the VObject Local Sets to add.
     */
    public VObjectSeries(List<VObjectLS> objects)
    {
        vobjects.addAll(objects);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array comprising the VObjectSeries
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public VObjectSeries(byte[] bytes) throws KlvParseException
    {
        int index = 0;
        while (index < bytes.length - 1)
        {
            BerField lengthField = BerDecoder.decode(bytes, index, true);
            index += lengthField.getLength();
            byte[] vObjectBytes = Arrays.copyOfRange(bytes, index, index + lengthField.getValue());
            VObjectLS objectLocalSet = new VObjectLS(vObjectBytes);
            vobjects.add(objectLocalSet);
            index += lengthField.getValue();
        }
    }

    @Override
    public byte[] getBytes()
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try
        {
            for (VObjectLS vobject: getVObjects())
            {
                byte[] localSetBytes = vobject.getBytes();
                baos.write(BerEncoder.encode(localSetBytes.length));
                baos.write(localSetBytes);
            }
        }
        catch (IOException ex) 
        {
            LOG.log(Level.SEVERE, null, ex);
            return null;
        }
        return baos.toByteArray();
    }

    @Override
    public String getDisplayableValue()
    {
        return "[VObject Series]";
    }

    @Override
    public String getDisplayName()
    {
        return "Ontologies";
    }

    /**
     * Get the VObjectLS structure.
     *
     * @return the list of VObject local sets.
     */
    public List<VObjectLS> getVObjects()
    {
        return vobjects;
    }
}
