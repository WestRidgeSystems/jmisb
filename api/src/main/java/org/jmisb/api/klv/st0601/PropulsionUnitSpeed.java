package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Propulsion Unit Speed (ST 0601 tag 111).
 * <p>
 * From ST:
 * <blockquote>
 * The speed the engine (or electric motor) is rotating at.
 * <p>
 * KLV format: uint, Min: 0, Max: (2^32)-1.
 * <p>
 * Length: variable, Max Length: 4, Required Length: N/A.
 * <p>
 * Resolution: 1 revolution/minute.
 * <p>
 * RPMs can apply to combustion engine or electric motor propelling the aircraft.
 * <p>
 * With multi-rotor aircraft, use an average or other representative value.
 * </blockquote>
 */
public class PropulsionUnitSpeed implements IUasDatalinkValue
{
    private long speed;
    private static long MIN_VAL = 0;
    private static long MAX_VAL = 4294967295L; // 2^32-1
    private static int MAX_BYTES = 4;

    /**
     * Create from value
     * @param speed speed in revolutions per minute (RPM). Legal values are in [0,2^31-1].
     */
    public PropulsionUnitSpeed(long speed)
    {
        if (speed > MAX_VAL || speed < MIN_VAL)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " must be in range [0,2^32-1]");
        }
        this.speed = speed;
    }

    /**
     * Create from encoded bytes
     * @param bytes Byte array, maximum four bytes
     */
    public PropulsionUnitSpeed(byte[] bytes)
    {
        if (bytes.length > MAX_BYTES)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " field length must be 1 - 4 bytes");
        }
        this.speed = PrimitiveConverter.variableBytesToUint32(bytes);
    }

    /**
     * Get the propulsion unit speed.
     * @return The speed in revolutions per minute (RPM).
     */
    public long getSpeed()
    {
        return speed;
    }

    @Override
    public byte[] getBytes()
    {
        return PrimitiveConverter.uint32ToVariableBytes(this.speed);
    }

    @Override
    public String getDisplayableValue()
    {
        return speed + "rpm";
    }

    @Override
    public final String getDisplayName()
    {
        return "Propulsion Unit Speed";
    }
}
