package com.fractals.controllers;

import com.fractals.backingbeans.BrowseGenreBacking;
import com.fractals.beans.Genre;
import com.fractals.beans.Track;
import com.fractals.beanvalidators.EmailCheck;
import com.fractals.controllers.exceptions.IllegalOrphanException;
import com.fractals.controllers.exceptions.RollbackFailureException;
import com.fractals.converters.ProvinceConverter;
import com.fractals.jsf.util.PaginationHelper;
import com.fractals.rss.FeedMessage;
import com.fractals.utilities.DatabaseSeedManager;
import com.fractals.utilities.SecurityHelper;
import java.io.File;
import java.util.List;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;

/**
 *
 * @author Thai-Vu Nugyen
 */

@RunWith(Arquillian.class)
public class TrackJpaControllerTest {
    
    
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
                .addPackage(GenreJpaController.class.getPackage())
                .addPackage(Genre.class.getPackage())
                .addPackage(BrowseGenreBacking.class.getPackage())
                .addPackage(EmailCheck.class.getPackage())
                .addPackage(org.primefaces.model.StreamedContent.class.getPackage())
                .addPackage(RollbackFailureException.class.getPackage())
                .addPackage(IllegalOrphanException.class.getPackage())
                .addPackage(PaginationHelper.class.getPackage())
                .addPackage(FeedMessage.class.getPackage())
                .addPackage(ProvinceConverter.class.getPackage())
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
    
    @Resource(name = "java:app/jdbc/musicstore")
    private DataSource ds;
    
    @Inject
    private GenreJpaController genresControl;
    
    @Inject
    private TrackJpaController tracksControl;
    
    @Before
    public void seedDatabase() {
        DatabaseSeedManager db = new DatabaseSeedManager(ds);
        db.seed();
    }

    /**
     * Test of findTracksByGenre method, of class TrackJpaController.
     */
    @Test
    public void testFindTracksByGenre() {
        Genre genre = genresControl.findGenre(1);
        
        List<Track> tracks = tracksControl.findTracksByGenre(genre, 5, true);
        
        assertTrue (tracks.size() <= 5);
        
    }

    /**
     * Test of getMostRecentTracks method, of class TrackJpaController.
     */
    @Test
    public void testGetMostRecentTracks() {
        List<Track> tracks = tracksControl.getMostRecentTracks(5);
        
        boolean valid = true;
        
        if (tracks.size() > 5)
            valid = false;
        
        if (tracks.size() >0){
            Track temp = tracks.get(0);
            for (Track track : tracks){
                if (track.getCreatedAt().isAfter(temp.getCreatedAt()))
                        valid = false;
                temp = track;
            }
        }
        
        assertTrue(valid);
    }

    /**
     * Test of getSimilarTracks method, of class TrackJpaController.
     */
    @Test
    public void testGetSimilarTracks() {
        Track track = tracksControl.findTrack(12);
        List<Track> tracks = tracksControl.getSimilarTracks(track, 3);
        
        boolean valid = true;
        
        if (tracks.size() > 3){
            valid = false;
        }
        
        for (Track t : tracks){
            if (t.equals(track))
                valid = false;
        }
        
        assertTrue(valid);
    }


    /**
     * Test of getSpecials method, of class TrackJpaController.
     */
    @Test
    public void testGetSpecials() {
        List<Track> tracks = tracksControl.getSpecials(3);
        
        boolean valid = true;
        
        for (Track track : tracks){
            if (track.getSalePrice() == 0)
                valid = false;
        }
        assertTrue (valid);
    }

    /**
     * Test of getTotalSales method, of class TrackJpaController.
     */
    @Test
    public void testGetTotalSales() {
        double expectedTotalSales = 2.97;
        int track_id = 13;
        Track track = tracksControl.findTrack(track_id);
        Number totalSales = tracksControl.getTotalSales(track);
        
        assertEquals (expectedTotalSales, totalSales.doubleValue(), 0.01);
        
    }
    
    @Test
    public void testGetSaleNumberOfTrack(){
        int track_id = 14;
        //In db, track, with id == 14, 
        //should have 1 individual sale count and 2 album sale count
        int expectedNumberSold = 3;
        Track track = tracksControl.findTrack(track_id);
        
        Number numberSold = tracksControl.getTracksSold(track);
        
        assertEquals (expectedNumberSold, numberSold.intValue());
    }
    
    @Test
    public void testGetSaleNumberOfTrackAsPartOfAlbum(){
        int track_id = 123;
        int expectedNumberSold = 3;
        Track track = tracksControl.findTrack(track_id);
        
        Number numberSold = tracksControl.getTracksSoldAsPartOfAlbum(track);
        
        assertEquals (expectedNumberSold, numberSold.intValue());
    }
    
    @Test
    public void testSaleNumberOfTrackIndividual(){
        int track_id = 72;
        int expectedNumberSold = 3;
        Track track = tracksControl.findTrack(track_id);
        
        Number numberSold = tracksControl.getTracksSoldAsIndividualTrack(track);
        
        assertEquals (expectedNumberSold, numberSold.intValue());
    }
    
}
