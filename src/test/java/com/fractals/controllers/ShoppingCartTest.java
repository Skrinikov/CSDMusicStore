package com.fractals.controllers;

import com.fractals.backingbeans.ShoppingCart;
import com.fractals.backingbeans.UserBacking;
import com.fractals.beans.Album;
import com.fractals.beans.Artist;
import com.fractals.beans.Track;
import com.fractals.beanvalidators.EmailCheck;
import com.fractals.controllers.exceptions.IllegalOrphanException;
import com.fractals.controllers.exceptions.RollbackFailureException;
import com.fractals.converters.LocalDateAttributeConverter;
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

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Ignore;

@Ignore
/**
 * Tests ShoppingCart backing bean.
 * @author Aline Shulzhenko
 */
@RunWith(Arquillian.class)
public class ShoppingCartTest {
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
                .addPackage(IllegalOrphanException.class.getPackage())
                .addPackage(PaginationHelper.class.getPackage())
                .addPackage(FeedMessage.class.getPackage())
                .addPackage(LocalDateAttributeConverter.class.getPackage())
                .addPackage(SecurityHelper.class.getPackage())
                .addPackage(DatabaseSeedManager.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/glassfish-resources.xml"), "glassfish-resources.xml")
                .addAsResource(new File("src/main/resources/META-INF/persistence.xml"), "META-INF/persistence.xml")
                .addAsResource("schema.sql")
                .addAsResource("setup.sql")
                .addAsResource("Bundle.properties")
                .addAsLibraries(dependencies);

        return webArchive;
    }

    @Inject
    private ShoppingCart cart;
    @Inject
    private AlbumJpaController albumJpa;
    @Inject
    private TrackJpaController trackJpa;
    @Inject
    private ArtistJpaController artistJpa;

    @Resource(name = "java:app/jdbc/musicstore")
    private DataSource ds;
    
    private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger("SearchJPAController.class");

    @Test
    public void getAllTracksTest() throws SQLException {
        cart.add(trackJpa.findTrack(1));
        cart.add(trackJpa.findTrack(2));
        cart.add(trackJpa.findTrack(3));
        assertThat(cart.getAllTracks()).hasSize(3);
    }
   
    @Test
    public void getAllAlbumsTest() throws SQLException {
        cart.add(albumJpa.findAlbum(1));
        cart.add(albumJpa.findAlbum(2));
        assertThat(cart.getAllAlbums()).hasSize(2);
    }
  
    @Test
    public void totalItemsTest() throws SQLException {
        cart.add(albumJpa.findAlbum(1));
        cart.add(trackJpa.findTrack(2));
        cart.add(trackJpa.findTrack(3));
        assertThat(cart.totalItems()).isEqualTo(3);
    }
   
    @Test
    public void getArtistTest() throws SQLException {
        Artist artist = artistJpa.findArtist(1);
        Album album = albumJpa.findAlbum(1);
        cart.add(album);
        cart.add(albumJpa.findAlbum(2));
        assertThat(cart.getArtist(album)).isEqualTo(artist);
    }
  
    @Test
    public void getArtistNullTest() throws SQLException {
        cart.add(albumJpa.findAlbum(1));
        cart.add(albumJpa.findAlbum(2));
        assertThat(cart.getArtist(null)).isNull();
    }

    @Test
    public void getCoverTest() throws SQLException {
        Album album = albumJpa.findAlbum(11);
        cart.add(album);
        cart.add(albumJpa.findAlbum(2));
        cart.add(trackJpa.findTrack(3));
        assertThat(cart.getCover(album)).isEqualTo("led_zeppelin_iii.jpg");
    }
 
    @Test
    public void getTypeTest() throws SQLException {
        Album album = albumJpa.findAlbum(11);
        cart.add(album);
        cart.add(albumJpa.findAlbum(2));
        cart.add(trackJpa.findTrack(3));
        assertThat(cart.getType(album)).isEqualTo("Album");
    }
  
    @Test
    public void getDetailsLinkTest() throws SQLException {
        Album album = albumJpa.findAlbum(11);
        cart.add(album);
        cart.add(albumJpa.findAlbum(2));
        cart.add(trackJpa.findTrack(3));
        assertThat(cart.getDetailsLink(album)).isEqualTo("Album.xhtml");
    }
   
    @Test
    public void emptyTest() throws SQLException {
        cart.add(albumJpa.findAlbum(11));
        cart.add(albumJpa.findAlbum(2));
        cart.add(trackJpa.findTrack(3));
        cart.add(trackJpa.findTrack(1));
        cart.add(trackJpa.findTrack(2));
        cart.add(trackJpa.findTrack(27));
        cart.empty();
        assertThat(cart.totalItems()).isEqualTo(0);
    }

    @Test
    public void addTest() throws SQLException {
        Album album = albumJpa.findAlbum(2);
        Track track = trackJpa.findTrack(3);
        cart.add(albumJpa.findAlbum(11));
        cart.add(album);
        cart.add(track);
        cart.add(track);
        cart.add(track);
        cart.add(album);
        assertThat(cart.totalItems()).isEqualTo(3);
    }
    
    @Test
    public void addAlbumWithTracksTest() throws SQLException {
        //add two tracks that form one album will add this album to the cart
        cart.add(trackJpa.findTrack(33));
        cart.add(trackJpa.findTrack(8));
        assertThat(cart.getAllAlbums()).hasSize(1);
        assertThat(cart.getAllTracks()).hasSize(0);
    }
    
    @Test
    public void addTrackFirstThenSameAlbumTest() throws SQLException {
        //track should be deleted since the whole album is added to the cart
        cart.add(trackJpa.findTrack(1));
        cart.add(albumJpa.findAlbum(1));
        assertThat(cart.getAllAlbums()).hasSize(1);
        assertThat(cart.getAllTracks()).hasSize(0);
    }   
 
    @Test
    public void removeTest() throws SQLException {
        Album album = albumJpa.findAlbum(2);
        Track track = trackJpa.findTrack(3);
        cart.add(albumJpa.findAlbum(11));
        cart.add(album);
        cart.add(track);
        cart.remove(album);
        cart.remove(track);
        assertThat(cart.totalItems()).isEqualTo(1);
    }

    @Test
    public void isEmptyTest() throws SQLException {
        Album album = albumJpa.findAlbum(2);
        Track track = trackJpa.findTrack(3);
        cart.add(album);
        cart.add(track);
        cart.remove(album);
        cart.remove(track);
        assertThat(cart.isEmpty()).isTrue();
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
