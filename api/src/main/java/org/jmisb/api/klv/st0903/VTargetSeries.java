package org.jmisb.api.klv.st0903;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;

public class VTargetSeries implements IVmtiMetadataValue
{

    private List<VTargetPack> targetPacks = new ArrayList<>();

    public VTargetSeries(byte[] bytes) throws KlvParseException
    {
        int index = 0;
        while (index < bytes.length - 1) {
            BerField lengthField = BerDecoder.decode(bytes, index, true);
            index += lengthField.getLength();
            byte[] targetPackBytes = Arrays.copyOfRange(bytes, index, index + lengthField.getValue());
            VTargetPack targetPack = new VTargetPack(targetPackBytes);
            targetPacks.add(targetPack);
            index += lengthField.getValue();
        }
    }

    @Override
    public byte[] getBytes()
    {
        // TODO: fix
        return new byte[]{0x00};
    }

    @Override
    public String getDisplayableValue()
    {
        return "Targets";
    }

    @Override
    public String getDisplayName()
    {
        return "Target Series";
    }

}
