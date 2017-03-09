package com.fractals.controllers;

import com.fractals.backingbeans.ShoppingCart;
import com.fractals.backingbeans.UserBacking;
import com.fractals.backingbeans.exceptions.RollbackFailureException;
import com.fractals.beans.Album;
import com.fractals.beans.Order;
import com.fractals.beans.User;
import com.fractals.beanvalidators.EmailCheck;
import com.fractals.controllers.exceptions.IllegalOrphanException;
import com.fractals.converters.ProvinceConverter;
import com.fractals.email.EmailSender;
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
 * Tests OrdersController.
 * @author Aline Shulzhenko
 */
@RunWith(Arquillian.class)
public class OrdersControllerTest {
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

    @Inject
    private OrdersController ordersController;    
    @Inject
    private ShoppingCart cart;
    @Inject
    private UserJpaController usersJpa;
    @Inject
    private AlbumJpaController albumJpa;
    @Inject
    private OrderJpaController orderJpa; 
    
    private User user;

    @Resource(name = "java:app/jdbc/musicstore")
    private DataSource ds;
    
    private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger("OrdersController.class");

    @Test
     public void submitOrderTest() throws SQLException {
        int lastId = orderJpa.getOrderCount();
        Order order = ordersController.submitOrder(cart, user);
        assertThat(cart.totalItems()).isEqualTo(0);
        assertThat(lastId+1).isEqualTo(order.getId());
     }
    
    /**
     *  Creates the appropriate tables and seeds the database with test values.
     */
    @Before
    public void seedDatabase() {
        DatabaseSeedManager db = new DatabaseSeedManager(ds);
        db.seed();
        
        user = usersJpa.findUser(1);
        cart.add(albumJpa.findAlbum(1));
        cart.add(albumJpa.findAlbum(2));
    }
}

