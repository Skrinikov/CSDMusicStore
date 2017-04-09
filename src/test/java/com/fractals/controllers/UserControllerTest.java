package com.fractals.controllers;

import com.fractals.backingbeans.BrowseGenreBacking;
import com.fractals.beans.Order;
import com.fractals.beans.User;
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
import java.util.Random;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test class for UserController
 * @author MOUFFOK Sarah
 */
@Ignore
public class UserControllerTest {
       private static final Logger log = Logger.getLogger("UserControllerTest.class");
    
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
                .addPackage(UserJpaController.class.getPackage())
                .addPackage(User.class.getPackage())
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
    private UserController userController;
    @Inject
    private UserJpaController userJpaController;
    
    @Resource(name = "java:app/jdbc/musicstore")
    private DataSource ds;
    
    @Before
    public void init(){
        DatabaseSeedManager dsm = new DatabaseSeedManager(ds);
        dsm.seed();
    }
    
    @Test
    public void getTotalSalesByUserTest() {
        log.info("Test get Total Sales By User");
        User u = userJpaController.findUser(
                new Random().nextInt(userJpaController.getUserCount()));
        List<Order> orders = u.getOrders();
        double expected = 0.0;
        for(Order o : orders)
            expected+=o.getNetCost();
        
        double result = userController.getTotalSalesByUser(u).doubleValue();
        org.junit.Assert.assertEquals(null, expected, result, 0.01);
    }
}
