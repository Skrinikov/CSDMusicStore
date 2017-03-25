/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.controllers;

import com.fractals.backingbeans.UserBacking;
import com.fractals.beans.Album;
import com.fractals.beans.Genre;
import com.fractals.beans.Track;
import com.fractals.beanvalidators.EmailCheck;
import com.fractals.controllers.exceptions.IllegalOrphanException;
import com.fractals.controllers.exceptions.RollbackFailureException;
import com.fractals.converters.ProvinceConverter;
import com.fractals.email.EmailSender;
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
 * @author Thai-Vu Nguyen
 */
@Ignore
@RunWith(Arquillian.class)
public class ClientTrackingControllerTest {
    
    @Deployment
    public static WebArchive deploy() {

        //Use an alternative to the JUnit assert library called AssertJ
        final File[] dependencies = Maven
                .resolver()
                .loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity()
                .asFile();
        
        final File[] assertj = Maven
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
                .addPackage(ProvinceConverter.class.getPackage())
                .addPackage(SecurityHelper.class.getPackage())
                .addPackage(DatabaseSeedManager.class.getPackage())
                .addPackage(EmailSender.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/glassfish-resources.xml"), "glassfish-resources.xml")
                .addAsResource(new File("src/main/resources/META-INF/persistence.xml"), "META-INF/persistence.xml")
                .addAsResource("schema.sql")
                .addAsResource("setup.sql")
                .addAsResource("Bundle.properties")
                .addAsLibraries(dependencies)
                .addAsLibraries(assertj);

        return webArchive;
    }
    
    @Resource(name = "java:app/jdbc/musicstore")
    private DataSource ds;
    
    @Inject
    private ClientTrackingController tracker;
    
    @Inject
    private TrackJpaController trackControl;
    
    
    /**
     *  Creates the database testing environment
     */
    @Before
    public void seedDatabase() {
        DatabaseSeedManager db = new DatabaseSeedManager(ds);
        db.seed();
    }
     
    /**
     * Test of saveGenre method, of class ClientTrackingController.
     */
    @Test
    public void testSaveGenre() {
       
        Track track = trackControl.findTrack(2);
        
        tracker.saveGenre(track.getGenre());
        
        
        assertTrue(tracker.isGenreSaved());
        
    }

    /**
     * Test of getGenre method, of class ClientTrackingController.
     */
    @Test
    public void testGetGenre() {
        
        Track track = trackControl.findTrack(3);
        tracker.saveGenre(track.getGenre());
        
        Genre savedGenre = tracker.getGenre();
        
        assertEquals(track.getGenre(), savedGenre);
        
    }

    /**
     * Test of getAlbums method, of class ClientTrackingController.
     */
    @Test
    public void testGetAlbums() {
        Track track = trackControl.findTrack(3);
        tracker.saveGenre(track.getGenre());
        
        List<Album> albums = tracker.getAlbums(3, true);
        
        assertTrue(albums.size() == 3);
        
        
    }

    /**
     * Test of getTracks method, of class ClientTrackingController.
     */
    @Test
    public void testGetTracks() {
        Track track = trackControl.findTrack(1);
        tracker.saveGenre(track.getGenre());
        
        List<Track> tracks = tracker.getTracks(3, true);
        
        assertTrue (tracks.size() == 3);
        
    }
    
}
