package com.fractals.controllers;

import com.fractals.backingbeans.BrowseGenreBacking;
import com.fractals.beans.Genre;
import com.fractals.beans.Track;
import java.util.List; 
import java.util.ArrayList;
import com.fractals.beanvalidators.EmailCheck;
import com.fractals.controllers.exceptions.IllegalOrphanException;
import com.fractals.controllers.exceptions.RollbackFailureException;
import com.fractals.converters.ProvinceConverter;
import com.fractals.jsf.util.PaginationHelper;
import com.fractals.rss.FeedMessage;
import com.fractals.utilities.DatabaseSeedManager;
import com.fractals.utilities.SecurityHelper;
import java.io.File;
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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
/**
 *  This class will be used to measure the integrity of the BrowseGenreBacking Bean
 *  and ensure it returns the collect number of elements for specific actions. 
 * 
 * @author Renuchan
 */
@Ignore
@RunWith(Arquillian.class)
public class BrowseGenreTest {
    
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

    private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger("BrowseGenreBacking.class");
 
    @Inject 
    private BrowseGenreBacking bgb; 
    
    @Inject 
    private GenreJpaController gjc; 
    
    @Test
    public void testNumGenre()
    {
        boolean valid = true; 
        
        //grab all the genres in the database 
        List<Genre> dbGenres = gjc.findGenreEntities();
        
        //grab the genres from the backing bean 
        List<Genre> bbGenres = bgb.getAllGenre(); 
        
        if(dbGenres.size() != bbGenres.size())
        {
            valid = false;
            log.info("List size for backing bean and database aren't matching.");
        }
        else
            //check that all the genres given are in the database
            for(Genre ele : bbGenres)
                if(!dbGenres.contains(ele))
                {
                    valid = false;
                    log.info("List contains genre that isn't in the database");
                }
        
        Assert.assertTrue(valid); 
        
    }

    @Test
    public void CorrectTracksForGenre()
    {
        boolean valid = true; 
        
        //grab all the genres 
        List<Genre> dbGenres = gjc.findGenreEntities();

        //now check that all tracks given by the backing bean for a specific 
        //genre are in that genre. Ensure that the backing bean is not mistakingly
        //give tracks in other genres. 
        
        for(Genre ele : dbGenres)
        {
            List<Track> tracks = bgb.getTracksByGenre(ele);
            
            for(Track ele2 : tracks)
                if(!ele2.getGenre().getId().equals(ele.getId()))
                {
                    valid = false;
                    log.info("List size for backing bean and database aren't matching.");
                }
            
        }
        
        Assert.assertTrue(valid);
        
    }
    /*
     @Before
    public void seedDatabase() {
        DatabaseSeedManager db = new DatabaseSeedManager(ds);
        db.seed();
    }
    
    */
}
