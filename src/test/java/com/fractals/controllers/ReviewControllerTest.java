package com.fractals.controllers;

import com.fractals.backingbeans.BrowseGenreBacking;
import com.fractals.beans.Review;
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
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;
import org.eclipse.persistence.jpa.jpql.Assert;
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

/**
 * Test class for ReviewController
 * @author MOUFFOK Sarah
 */
@Ignore
@RunWith(Arquillian.class)
public class ReviewControllerTest {
       private static final Logger log = Logger.getLogger("ReviewControllerTest.class");
    
    @Deployment
    public static WebArchive deploy() {
        
        final File[] dependencies = Maven
                .resolver()
                .loadPomFromFile("pom.xml")
                .resolve("mysql:mysql-connector-java",
                        "org.assertj:assertj-core").withoutTransitivity()
                .asFile();

        final WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                          .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                .addPackage(ReviewJpaController.class.getPackage())
                .addPackage(Review.class.getPackage())
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

    @Inject
    private ReviewController reviewController;
    
    @Resource(name = "java:app/jdbc/musicstore")
    private DataSource ds;
    
    @Before
    public void init(){
        DatabaseSeedManager dsm = new DatabaseSeedManager(ds);
        dsm.seed();
    }
    
    @Test
    public void getPendingReviewsTest() {
        log.info("Test get pending reviews");
        List<Review> pending = reviewController.getPendingReviews();
        for (Review r : pending) {
            Assert.isFalse(r.isPending(), null);
            Assert.isEqual("PENDING", r.status(), null);
        }
    }
    
     @Test
    public void getApprovedReviewsTest() {
        log.info("Test get approved reviews");
        List<Review> approved = reviewController.getApprovedReviews();
        for (Review r : approved) {
            Assert.isTrue(r.isPending(), null);
            Assert.isTrue(r.getApproved(), null);
            Assert.isEqual("APPROVED", r.status(), null);
        }
    }
    
    @Test
    public void getDisapprovedReviewsTest() {
        log.info("Test get disapproved reviews");
        List<Review> disapproved = reviewController.getDisapprovedReviews();
        for (Review r : disapproved) {
            Assert.isTrue(r.isPending(), null);
            Assert.isFalse(r.getApproved(), null);
            Assert.isEqual("DISAPPROVED", r.status(), null);
        }
    }

    
    
}
