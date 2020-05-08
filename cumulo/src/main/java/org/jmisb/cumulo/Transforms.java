package org.jmisb.cumulo;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class Transforms {

    private final RealMatrix[] matrices;

    public Transforms(SensorModel sensorModel)
    {
        matrices = new Array2DRowRealMatrix[5];
    }

    public RealMatrix getTransform(CoordinateFrame from, CoordinateFrame to)
    {
        return matrices[from.getCode()];
    }
}
