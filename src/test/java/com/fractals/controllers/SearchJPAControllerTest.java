package com.fractals.controllers;

import com.fractals.backingbeans.UserBacking;
import com.fractals.backingbeans.exceptions.RollbackFailureException;
import com.fractals.beans.Album;
import com.fractals.beans.Track;
import com.fractals.beanvalidators.EmailCheck;
import com.fractals.jsf.util.PaginationHelper;
import com.fractals.rss.FeedMessage;
import com.fractals.utilities.DatabaseSeedManager;
import com.fractals.utilities.SecurityHelper;
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
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import org.junit.Ignore;

@Ignore
/**
 * Tests SearchJPAController.
 * @author Aline Shulzhenko
 */
@RunWith(Arquillian.class)
public class SearchJPAControllerTest {
    @Deployment
    public static WebArchive deploy() {

        //Use an alternative to the JUnit assert library called AssertJ
        final File[] dependencies = Maven
                .resolver()
                .loadPomFromFile("pom.xml")
                .resolve("org.assertj:assertj-core").withoutTransitivity()
                .asFile();

        // For testing Arquillian prefers a resources.xml file over a context.xml
        // Actual file name is resources-mysql-ds.xml in the test/resources folder
        final WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                .addPackage(SearchJPAController.class.getPackage())
                .addPackage(Album.class.getPackage())
                .addPackage(UserBacking.class.getPackage())
                .addPackage(EmailCheck.class.getPackage())
                .addPackage(RollbackFailureException.class.getPackage())
                .addPackage(PaginationHelper.class.getPackage())
                .addPackage(FeedMessage.class.getPackage())
                .addPackage(SecurityHelper.class.getPackage())
                .addPackage(DatabaseSeedManager.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/glassfish-resources.xml"), "glassfish-resources.xml")
                .addAsResource(new File("src/main/resources/META-INF/persistence.xml"), "META-INF/persistence.xml")
                .addAsResource("schema.sql")
                .addAsResource("setup.sql")
                .addAsLibraries(dependencies);

        return webArchive;
    }

    @Inject
    private SearchJPAController search;

    @Resource(name = "java:app/jdbc/musicstore")
    private DataSource ds;
    
    private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger("SearchJPAController.class");

    @Test
    public void searchByAlbumTitleTest() throws SQLException {
        log.info("searchByAlbumTitleTest");
        List<Album> items = search.searchByAlbumTitle("no");
        assertThat(items).hasSize(2);
    }

    @Test
    public void searchByTrackNameTest() throws SQLException {
        log.info("searchByTrackNameTest");
        List<Track> items = search.searchByTrackName("no");
        assertThat(items).hasSize(7);
    }

    @Test
    public void searchByArtistNameAlbumsTest() throws SQLException {
        log.info("searchByArtistNameAlbumsTest");
        List<Album> items = search.searchByArtistNameAlbums("tu");
        assertThat(items).hasSize(3);
    }
 
    @Test
    public void searchByArtistNameTracksTest() throws SQLException {
        log.info("searchByArtistNameTracksTest");
        List<Track> items = search.searchByArtistNameTracks("tu");
        assertThat(items).hasSize(8);
    }

    @Test
    public void searchByDateAlbumsTest() throws SQLException {
        log.info("searchByDateAlbumsTest");      
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusDays(1);
        List<Album> items = search.searchByDateAlbums(from, to);
        assertThat(items).hasSize(73);
    }
    
    @Test
    public void searchByDateTracksTest() throws SQLException {
        log.info("searchByDateTracksTest");      
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusDays(1);
        List<Track> items = search.searchByDateTracks(from, to);
        assertThat(items).hasSize(138);
    }

    @Test(expected = DateTimeException.class)
    public void searchByDateTest_Albums_Invalid() throws SQLException {
        log.info("searchByDateTest_Albums_Invalid");
        LocalDateTime from = LocalDateTime.of(2017, 2, 4, 22, 34, 44);
        LocalDateTime to = LocalDateTime.of(2017, 2, 4, 22, 34, 43);
        search.searchByDateAlbums(from, to);
        fail();
    }
    
    /**
     *  Creates the appropriate tables and seeds the database with test values.
     */
    @Before
    public void seedDatabase() {
        DatabaseSeedManager db = new DatabaseSeedManager(ds);
        db.seed();
    }
}
