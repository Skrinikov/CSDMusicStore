/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.jpacontrollers;

import com.fractals.beans.Album;
import com.fractals.beans.Track;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Ignore;
@Ignore
/**
 *
 * @author lynn
 */
@RunWith(Arquillian.class)
public class SearchJPAControllerTest {
    @Deployment
    public static WebArchive deploy() {

        // Use an alternative to the JUnit assert library called AssertJ
        // Need to reference MySQL driver as it is not part of either
        // embedded or remote TomEE
        final File[] dependencies = Maven
                .resolver()
                .loadPomFromFile("pom.xml")
                .resolve("mysql:mysql-connector-java",
                        "org.assertj:assertj-core").withoutTransitivity()
                .asFile();

        // For testing Arquillian prefers a resources.xml file over a
        // context.xml
        // Actual file name is resources-mysql-ds.xml in the test/resources
        // folder
        // The SQL script to create the database is also in this folder
        final WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                .addPackage(SearchJPAController.class.getPackage())
                .addPackage(Album.class.getPackage())
                .addPackage(Track.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/glassfish-resources.xml"), "glassfish-resources.xml")
                .addAsResource(new File("src/main/resources/META-INF/persistence.xml"), "META-INF/persistence.xml")
                .addAsLibraries(dependencies);

        return webArchive;
    }

    @Inject
    private SearchJPAController search;

    @Resource(name = "java:app/jdbc/musicstore")
    private DataSource ds;
    
    @Test
    public void searchByAlbumTitleTest() throws SQLException {
        //try{
        long t = System.nanoTime();
        List<Album> items = search.searchByAlbumTitle("no");
        assertThat(items).hasSize(2);
        double seconds = (double) (System.nanoTime() - t) / 1000000000.0;
        System.out.println("search : " + seconds + " seconds.");
        //}catch()
    }
    
    @Test
    public void searchByTrackNameTest() throws SQLException {
        //try{
        long t = System.nanoTime();
        List<Track> items = search.searchByTrackName("no");
        assertThat(items).hasSize(7);
        double seconds = (double) (System.nanoTime() - t) / 1000000000.0;
        System.out.println("search : " + seconds + " seconds.");
        //}catch()
    }
}
