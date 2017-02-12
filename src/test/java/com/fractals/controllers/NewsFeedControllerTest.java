//
//package com.fractals.controllers;
//
//import com.fractals.controllers.NewsFeedController;
//import com.fractals.beans.NewsFeed;
//import org.junit.runner.RunWith;
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.asset.EmptyAsset;
//import org.jboss.shrinkwrap.api.spec.WebArchive;
//import org.jboss.shrinkwrap.resolver.api.maven.Maven;
//import org.junit.Test;
//import javax.annotation.Resource;
//import javax.inject.Inject;
//import javax.sql.DataSource;
//import java.io.*;
//import org.junit.Ignore;
//
//@Ignore
///**
// *
// * @author lynn
// */
//@RunWith(Arquillian.class)
//public class NewsFeedControllerTest {
//    @Deployment
//    public static WebArchive deploy() {
//
//        // Use an alternative to the JUnit assert library called AssertJ
//        // Need to reference MySQL driver as it is not part of either
//        // embedded or remote TomEE
//        final File[] dependencies = Maven
//                .resolver()
//                .loadPomFromFile("pom.xml")
//                .resolve("mysql:mysql-connector-java",
//                        "org.assertj:assertj-core").withoutTransitivity()
//                .asFile();
//
//        // For testing Arquillian prefers a resources.xml file over a
//        // context.xml
//        // Actual file name is resources-mysql-ds.xml in the test/resources
//        // folder
//        // The SQL script to create the database is also in this folder
//        final WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
//                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
//                .addPackage(NewsFeedController.class.getPackage())
//                .addPackage(NewsFeed.class.getPackage())
//                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
//                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/glassfish-resources.xml"), "glassfish-resources.xml")
//                .addAsResource(new File("src/main/resources/META-INF/persistence.xml"), "META-INF/persistence.xml")
//                .addAsLibraries(dependencies);
//
//        return webArchive;
//    }
//
//    @Inject
//    private NewsFeedController newsFeed;
//
//    @Resource(name = "java:app/jdbc/musicstore")
//    private DataSource ds;
//    
//    
//    @Test
//    public void test1()
//    {
//        NewsFeed nf = newsFeed.getNewsFeed(6);
//        
//        System.out.println(nf.getLink()); 
//        
//    }
//    
//
//}