/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jmisb.api.klv.st0903.vtarget;


public class TargetCentroidPixelNumber extends PixelNumber {

    public TargetCentroidPixelNumber(long num) {
        super(num);
    }

    public TargetCentroidPixelNumber(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Target Centroid";
    }
    
}
