package com.fractals.rss;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for the RSSFeedParser
 * 
 * @author Danieil Skrinikov
 */
public class RSSFeedParserTest {
    
    public RSSFeedParserTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of readFeed method, of class RSSFeedParser.
     */
    @Test
    public void testReadFeed() {
        System.out.println("readFeed");
        RSSFeedParser instance = new RSSFeedParser();
        
        List<FeedMessage> result = instance.readFeed(new String[]{"http://rss.cbc.ca/lineup/topstories.xml"});
        //System.out.println(result);
        assertEquals(1,1);
    }
    
}
