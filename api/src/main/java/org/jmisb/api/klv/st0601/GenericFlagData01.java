/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jmisb.api.klv.st0601;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.INestedKlvValue;

/**
 *
 * @author bradh
 */
public class GenericFlagData01 implements IUasDatalinkValue, INestedKlvValue
{

    SortedMap<IKlvKey, IKlvValue> map = new TreeMap<>();
    
    public GenericFlagData01(byte[] bytes)
    {
        byte flags = bytes[bytes.length - 1];
        if ((flags & 0x01) == 0x01)
        {
            map.put(FlagDataKey.LaserRange, new UasDatalinkString("Laser Range", "Laser off"));
        }
        else
        {
            map.put(FlagDataKey.LaserRange, new UasDatalinkString("Laser Range", "Laser on"));
        }

        if ((flags & 0x02) == 0x02)
        {
            map.put(FlagDataKey.AutoTrack, new UasDatalinkString("Auto-Track", "Auto-Track off"));
        }
        else
        {
            map.put(FlagDataKey.AutoTrack, new UasDatalinkString("Auto-Track", "Auto-Track on"));
        }

        if ((flags & 0x04) == 0x04)
        {
            map.put(FlagDataKey.IR_Polarity, new UasDatalinkString("IR Polarity", "White Hot"));
        }
        else
        {
            map.put(FlagDataKey.IR_Polarity, new UasDatalinkString("IR Polarity", "Black Hot"));
        }

        if ((flags & 0x08) == 0x08)
        {
            map.put(FlagDataKey.IcingStatus, new UasDatalinkString("Icing Status", "No Icing Detected"));
        }
        else
        {
            map.put(FlagDataKey.IcingStatus, new UasDatalinkString("Icing Status", "Icing Detected"));
        }

        if ((flags & 0x10) == 0x10)
        {
            map.put(FlagDataKey.SlantRange, new UasDatalinkString("Slant Range", "Calculated"));
        }
        else
        {
            map.put(FlagDataKey.SlantRange, new UasDatalinkString("Slant Range", "Measured"));
        }

        if ((flags & 0x20) == 0x20)
        {
            map.put(FlagDataKey.ImageInvalid, new UasDatalinkString("Image Invalid", "Image Valid"));
        }
        else
        {
            map.put(FlagDataKey.ImageInvalid, new UasDatalinkString("Image Invalid", "Image Invalid"));
        }
    }

    @Override
    public byte[] getBytes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDisplayName()
    {
        return "Generic Flag Data 01";
    }

    @Override
    public String getDisplayableValue()
    {
        return "[Flag data]";
    }

    @Override
    public IKlvValue getField(IKlvKey tag)
    {
        return map.get(tag);
    }

    @Override
    public Set<? extends IKlvKey> getTags()
    {
        return map.keySet();
    }
    
}
