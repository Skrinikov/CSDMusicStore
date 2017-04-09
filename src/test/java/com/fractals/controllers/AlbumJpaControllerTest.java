/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.controllers;

import com.fractals.backingbeans.BrowseGenreBacking;
import com.fractals.beans.Album;
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
 * @author Thai-Vu Nguyen
 */
@Ignore
@RunWith(Arquillian.class)
public class AlbumJpaControllerTest {
    
    public AlbumJpaControllerTest() {
    }
    
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
    private AlbumJpaController albumControl;
    
    
    @Inject
    private GenreJpaController genreControl;
    
    @Before
    public void seedDatabase() {
        DatabaseSeedManager db = new DatabaseSeedManager(ds);
        db.seed();
    }
   
    /**
     * Test of findAlbumsByGenre method, of class AlbumJpaController.
     */
    @Test
    public void testFindAlbumsByGenre() {
        Genre genre = genreControl.findGenre(3);
        
        List<Album> albums = albumControl.findAlbumsByGenre(genre, 3, true);
        
        boolean valid = true;
        
        if(albums.size() > 3){
           valid = false; 
        }
        
        for(Album album : albums){
            if (containsGenre(album, genre) == false){
                valid = false;
            }
        }
        
        assertTrue(valid);
        
    }


    /**
     * Test of getSimilarAlbums method, of class AlbumJpaController.
     */
    @Test
    public void testGetSimilarAlbums() {
        Album album = albumControl.findAlbum(4);
        
        List<Album> albums = albumControl.getSimilarAlbums(album, 3);
        
        boolean valid = true;
        
        if (albums.size() > 3){
            valid = false;
        }
        
        assertTrue(valid);
        
        
    }

    /**
     * Test of getTotalSales method, of class AlbumJpaController.
     */
    @Test
    public void testGetTotalSales() {
        double expectedTotalSales = 29.97;
        int albumid=66;
        
        Album album = albumControl.findAlbum(albumid);
        Number totalSales = albumControl.getTotalSales(album);
        
        assertEquals (expectedTotalSales, totalSales.doubleValue(), 0.01);
       
    }

    /**
     * Test of getAlbumsSold method, of class AlbumJpaController.
     */
    @Test
    public void testGetAlbumsSold() {
        int expectedSaleNumber = 3;
        int albumid = 66;
        
        Album album = albumControl.findAlbum(albumid);
        
        Number saleNumber = albumControl.getAlbumsSold(album);
        
        assertEquals(saleNumber.intValue(), expectedSaleNumber);
    }
    
    
    /**
     * Because Album does not have a genre field in the database,
     * we have to find a Track in an Album that contains the genre
     * @param album
     * @param genre
     * @return True if the Album contains a Track with the genre
     */
    private boolean containsGenre(Album album, Genre genre){
        
        for(Track track : album.getTracks()){
            if (track.getGenre().equals(genre))
                return true;
        }
        return false;
    }
}
