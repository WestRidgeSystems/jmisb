package org.jmisb.api.klv.st0102.localset;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.testng.Assert;
import org.testng.annotations.Test;

import org.jmisb.api.common.KlvParseException;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;

public class UnknownTagTest {

    @Mock
    AppenderSkeleton appender;
    @Captor
    ArgumentCaptor<LoggingEvent> logCaptor;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
        Logger.getRootLogger().addAppender(appender);
    }

    @Test
    public void testUnknownSecurityTag() throws KlvParseException {
        byte[] bytes = new byte[]{88, 1, 1};
        SecurityMetadataLocalSet securityMetadataLocalSet = new SecurityMetadataLocalSet(bytes, false);
        Assert.assertEquals(securityMetadataLocalSet.displayHeader(), "ST 0102 (local)");
        Assert.assertEquals(0, securityMetadataLocalSet.getKeys().size());
        Mockito.verify(appender).doAppend(logCaptor.capture());
        Assert.assertEquals("Unknown Security Metadata tag: 88", logCaptor.getValue().getRenderedMessage());
    }

    @Test
    public void testMixedKnownAndUnknownSecurityTags() throws KlvParseException {
        byte[] bytes = new byte[]{1, 1, 1, 2, 1, 1, 88, 1, 1, 3, 4, 47, 47, 67, 65, 4, 0, 5, 0, 6, 2, 67, 65, 22, 2, 0, 5};
        SecurityMetadataLocalSet securityMetadataLocalSet = new SecurityMetadataLocalSet(bytes, false);
        Assert.assertEquals(securityMetadataLocalSet.displayHeader(), "ST 0102 (local)");
        Assert.assertEquals(7, securityMetadataLocalSet.getKeys().size());
        Mockito.verify(appender).doAppend(logCaptor.capture());
        Assert.assertEquals("Unknown Security Metadata tag: 88", logCaptor.getValue().getRenderedMessage());

    }
}
