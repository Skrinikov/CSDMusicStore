package com.fractals.controllers;

import com.fractals.controllers.SearchJPAController;
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
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import org.junit.Ignore;
@Ignore
/**
 * i did it
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
        List<Album> items = search.searchByAlbumTitle("no");
        assertThat(items).hasSize(2);
    }

    @Test
    public void searchByTrackNameTest() throws SQLException {
        List<Track> items = search.searchByTrackName("no");
        assertThat(items).hasSize(7);
    }

    @Test
    public void searchByArtistNameTest() throws SQLException {
        Object[] items = search.searchByArtistName("tu");
        assertThat((List<Album>)items[0]).hasSize(3);
        assertThat((List<Track>)items[1]).hasSize(8);
    }

    @Test
    public void searchByDateTest_Albums() throws SQLException {
        LocalDateTime from = LocalDateTime.of(2017, 2, 4, 22, 34, 31);
        LocalDateTime to = LocalDateTime.now();
        Object[] items = search.searchByDate(from, to);
        assertThat((List<Album>)items[0]).hasSize(73);
        assertThat((List<Track>)items[1]).hasSize(138);
    }
    
    @Ignore
    @Test
    public void searchByDateTest_Tracks() throws SQLException {
        LocalDateTime from = LocalDateTime.of(2017, 2, 4, 22, 34, 43);
        LocalDateTime to = LocalDateTime.now();
        Object[] items = search.searchByDate(from, to);
        assertThat((List<Album>)items[0]).hasSize(0);
        assertThat((List<Track>)items[1]).hasSize(33);
    }

    @Test(expected = DateTimeException.class)
    public void searchByDateTest_Tracks_Invalid() throws SQLException {
        LocalDateTime from = LocalDateTime.of(2017, 2, 4, 22, 34, 44);
        LocalDateTime to = LocalDateTime.of(2017, 2, 4, 22, 34, 43);
        Object[] items = search.searchByDate(from, to);
        fail();
    }
}
