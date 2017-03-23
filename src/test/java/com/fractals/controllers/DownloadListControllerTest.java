
package com.fractals.controllers;

import com.fractals.backingbeans.LoginBacking;
import com.fractals.backingbeans.SurveyClientBacking;
import com.fractals.backingbeans.UserBacking;
import com.fractals.beans.Survey;
import com.fractals.beans.User;
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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;
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
 *  This class will be used to measure the integrity of the downloadListController
 *  and ensure it returns the collect number of tracks. 
 * 
 * @author Renuchan
 */
@Ignore
@RunWith(Arquillian.class)
public class DownloadListControllerTest {
    
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
                .addPackage(DownloadListController.class.getPackage())
                .addPackage(Track.class.getPackage())
                .addPackage(SurveyClientBacking.class.getPackage())
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
    
    @Inject
    private UserJpaController ujc; 
    
    @Inject 
    private DownloadListController dl; 
    
    
    @Resource(name = "java:app/jdbc/musicstore")
    private DataSource ds;

    private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger("DownloadController.class");
   
   
    
    private int[] expectedArray = {4,6,0,7};
    private int[] idArray = {2,50,90,51};
    private List<User> user; 
    
    
    @Before
    public void setup()
    {
        
        //when seeded 
        // Legend - Currently set db
        //user 2, 4 tracks bought
        //user 50, 6 tracks
        //user 90, 0 tracks
        //user 51, 1 tracks  
        
        user = new ArrayList<>(); 
        
        for(int i = 0; i < idArray.length; i++)
            this.user.add(getUser(idArray[i]));      
    }
   
    private User getUser(int id)
    {
        return ujc.findUser(id);
    }
    
    
    @Test
    public void testPurchasedTracks()
    {
        for(int i = 0; i < user.size(); i++)
        {
            User cur = user.get(i);
            
            List<Track> tracks = dl.getClientTracks(cur);
            
            int obtained = tracks.size(); 
            
            if(tracks != null)
                obtained = tracks.size(); 
            
            int expected = expectedArray[i];  
            
            log.info("DownloadListControllerTest: userId= " + cur.getId() + 
                    " numTracksObtained= " + obtained + " numTracksObtained= " + expected);
              
            Assert.assertEquals(expected, obtained);
                        
        }
    }
    
    
    @Before
    public void seedDatabase() {
        
       log.info("Seeding the database");
        
        DatabaseSeedManager db = new DatabaseSeedManager(ds);
        db.seed();
    }
    
}
