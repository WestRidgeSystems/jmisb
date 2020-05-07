package org.jmisb.api.klv.st1204;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class UUIDCombinationTest {

    public UUIDCombinationTest() {
    }

    @Test
    public void checkExample()
    {
        List<String> uuids = new ArrayList<>();
        uuids.add("f81d4fae-7dec-11d0-a765-00a0c91e6bf6");
        uuids.add("1ea5de30-e1d3-40fa-b501-2ca5cb58ca25");
        UUID result = CoreIdentifier.combineMultipleUUID(uuids);
        assertEquals(result.toString(), "cd9a0ef2-24a6-5343-aa21-3cd6be881b52");
    }

    @Test
    public void checkExampleUppercase()
    {
        List<String> uuids = new ArrayList<>();
        uuids.add("F81D4FAE-7DEC-11D0-A765-00A0C91E6BF6");
        uuids.add("1EA5DE30-E1D3-40FA-B501-2CA5CB58CA25");
        UUID result = CoreIdentifier.combineMultipleUUID(uuids);
        assertEquals(result.toString(), "cd9a0ef2-24a6-5343-aa21-3cd6be881b52");
    }

    @Test
    public void checkExampleMixedCase()
    {
        List<String> uuids = new ArrayList<>();
        uuids.add("f81D4FAE-7dEC-11D0-a765-00A0C91e6Bf6");
        uuids.add("1Ea5de30-e1d3-40FA-b501-2CA5CB58cA25");
        UUID result = CoreIdentifier.combineMultipleUUID(uuids);
        assertEquals(result.toString(), "cd9a0ef2-24a6-5343-aa21-3cd6be881b52");
    }

    @Test
    public void checkFourUUID()
    {
        List<String> uuids = new ArrayList<>();
        uuids.add("4e0f8130-7ed2-4f38-8278-21c9c3922ef6");
        uuids.add("73eb8d66-4b90-46a3-b107-93678992e240");
        uuids.add("53678ad0-f5a5-41b6-a379-8fb142794c15");
        uuids.add("bbb3a8b5-def0-467a-9292-a40fd980a0bc");
        UUID result = CoreIdentifier.combineMultipleUUID(uuids);
        assertEquals(result.toString(), "74762af1-0c7b-56ff-b4f2-06209381b93a");
    }
}
