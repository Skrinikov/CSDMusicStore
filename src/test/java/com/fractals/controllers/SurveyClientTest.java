package com.fractals.controllers;
import com.fractals.backingbeans.SurveyClientBacking;
import com.fractals.beans.Survey;
import com.fractals.beans.SurveyChoice; 
import com.fractals.beans.Track;
import com.fractals.beanvalidators.EmailCheck;
import com.fractals.controllers.exceptions.IllegalOrphanException;
import com.fractals.controllers.exceptions.RollbackFailureException;
import com.fractals.converters.ProvinceConverter;
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
import org.junit.Assert;


/**
 * Ensure that the survey backing returns proper surveys
 * @author Renuchan
 */
@Ignore
@RunWith(Arquillian.class)
public class SurveyClientTest {
    
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
                .addPackage(SurveyJpaController.class.getPackage())
                .addPackage(Survey.class.getPackage())
                .addPackage(SurveyClientBacking.class.getPackage())
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
    
    
    
    private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger("surveyClient.class");

    @Inject
    private SurveyJpaController sjc; 
    
    @Inject 
    private SurveyChoiceJpaController scjc; 
    
    @Inject 
    private SurveyClientBacking scb; 
    
    
    @Test 
    public void Test2VisibleSurveys()
    {
        boolean fail = false;       
        //there are only 2 visible survey in the database
        List<Survey> surveys = sjc.getVisibleSurveys();   
        if(surveys.size() != 2)
            fail = true;
        else
        {
            // check the survey 
            for(Survey sur : surveys)
                if(!sur.getVisible())
                    fail = true; 
        } 
        
        Assert.assertFalse(fail);  
    }
    
    @Test
    public void TestBackingGivesValidSurvey()
    {
        //get all valid surveys
        List<Survey> visibles = sjc.getVisibleSurveys(); 
        
        //get a survey from the backing bean
        Survey sur = scb.getSurvey();
        
        //check if the given sur is good
        boolean valid = false;
        
        for(Survey survey : visibles)
            if(survey.getId() == sur.getId())
                valid = true;
        
        Assert.assertTrue(valid);
    }
    
    @Test
    public void TestUpVotes()
    {
        //get survey from backing bean
        SurveyChoice surC = scb.getSurvey().getSurveyChoices().get(0);
        int expected = surC.getNumVotes() + 1; 
        
        scb.selectedOption(surC);
        
        Assert.assertEquals(expected, surC.getNumVotes()); 
    }
    
    /*
    @Before
    public void seedDatabase() {
        DatabaseSeedManager db = new DatabaseSeedManager(ds);
        db.seed();
    }
    */
}
