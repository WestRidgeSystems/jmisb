package org.jmisb.maven;

import static org.testng.Assert.*;

import java.io.File;
import org.testng.annotations.Test;

/** Unit tests for MIML CodeGenerator */
public class CodeGeneratorTest {

    public CodeGeneratorTest() {}

    @Test
    public void constructor() {
        CodeGenerator uut =
                new CodeGenerator(new File("target/src"), new File("target/test"), new Models());
        assertNotNull(uut);
    }
}
