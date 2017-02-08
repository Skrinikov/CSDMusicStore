/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.rss;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 1430468
 */
public class RSSFeedParserTest {
    
    public RSSFeedParserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
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
        RSSFeedParser instance = new RSSFeedParser("http://rss.cbc.ca/lineup/topstories.xml");
        Feed expResult = null;
        List<FeedMessage> result = instance.readFeed();
        System.out.println(result.toString());
        fail("The test case is a prototype.");
    }
    
}
