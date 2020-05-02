/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jmisb.api.klv.st0601;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for FlagDataKey enumeration.
 */
public class FlagDataKeyTest {
    
    @Test
    public void checkEnumeration()
    {
        assertEquals(FlagDataKey.LaserRange.getTagCode(), 0);
        
        assertEquals(FlagDataKey.ImageInvalid.getTagCode(), 5);
        
        assertEquals(FlagDataKey.IR_Polarity.getTagCode(), 2);
    }
    
}
