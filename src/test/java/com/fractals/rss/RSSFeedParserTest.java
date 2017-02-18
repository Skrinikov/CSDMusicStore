package com.fractals.rss;

import java.util.List;
import javax.xml.stream.XMLStreamException;
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
    public void testReadFeed() throws XMLStreamException {
        System.out.println("readFeed");
        RSSFeedParser instance = new RSSFeedParser();
        
        List<FeedMessage> result = instance.readFeed(new String[]{"http://apps.shareholder.com/rss/rss.aspx?channels=632&companyid=YHOO&sh_auth=3913894844%2E0%2E0%2E42785%2Eb0bacaa66e68a37fe8bf3bcb9066fdfd"});
        System.out.println(result);
        assertEquals(1,1);
    }
    
}
