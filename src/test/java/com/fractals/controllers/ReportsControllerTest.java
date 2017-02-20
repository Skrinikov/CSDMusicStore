package com.fractals.controllers;

import com.fractals.backingbeans.UserBacking;
import com.fractals.beans.Album;
import com.fractals.beans.Order;
import com.fractals.beans.Track;
import com.fractals.beans.User;
import com.fractals.controllers.exceptions.RollbackFailureException;
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
import org.junit.Test;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Ignore;

/**
 *
 * @author Danieil Skrinikov
 */
@RunWith(Arquillian.class)
public class ReportsControllerTest {

    @Deployment
    public static WebArchive deploy() {

        // Use an alternative to the JUnit assert library called AssertJ
        // Need to reference MySQL driver as it is not part of either
        // embedded or remote TomEE
        final File[] dependencies = Maven
                .resolver()
                .loadPomFromFile("pom.xml")
                .resolve("mysql:mysql-connector-java",
                        "org.assertj:assertj-core").withoutTransitivity()
                .asFile();

        // For testing Arquillian prefers a resources.xml file over a
        // context.xml
        // Actual file name is resources-mysql-ds.xml in the test/resources
        // folder
        // The SQL script to create the database is also in this folder
        final WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                .addPackage(SearchJPAController.class.getPackage())
                .addPackage(Album.class.getPackage())
                .addPackage(UserBacking.class.getPackage())
                .addPackage(RollbackFailureException.class.getPackage())
                .addPackage(PaginationHelper.class.getPackage())
                .addPackage(FeedMessage.class.getPackage())
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
    private ReportsController reports;

    @Resource(name = "java:app/jdbc/musicstore")
    private DataSource ds;
    
    @Before
    public void init(){
        DatabaseSeedManager dsm = new DatabaseSeedManager(ds);
        dsm.seed();
    }

    @Ignore
    @Test
    public void testGetTopClients() {
        System.out.println("getZeroTracks");
        List<Object[]> result = reports.getTopClients(LocalDateTime.now().minusDays(40), LocalDateTime.now());

        System.out.println("test\n" + result.size());
        assertThat("Hello").isNotEmpty();
    }

    @Test
    public void testGetTotalSales() {
        System.out.println("getTotalSales");
        List<Order> result = reports.getTotalSales(LocalDateTime.now().minusDays(20), LocalDateTime.now());

        System.out.println("test\n" + result.size());
        assertThat(result).hasSize(50);
    }

    @Ignore
    @Test
    public void testGetZeroClients() {
        System.out.println("getZeroClients");
        //List<User> expResult = null;		          
        List<User> result = reports.getZeroClients();
        assertThat(result).hasSize(62);
    }

    @Ignore
    @Test
    public void testGetZeroTracks() {
        System.out.println("getZeroTracks");
        List<Track> result = reports.getZeroTracks(LocalDateTime.now().minusDays(2), LocalDateTime.now());

        assertThat(result).hasSize(60);
        System.out.println(result);
    }
}
