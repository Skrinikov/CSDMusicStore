package com.fractals.controllers;

import com.fractals.backingbeans.UserBacking;
import com.fractals.beans.Album;
import com.fractals.beans.Order;
import com.fractals.beans.OrderItem;
import com.fractals.beans.Track;
import com.fractals.beans.User;
import com.fractals.controllers.exceptions.NonexistentEntityException;
import com.fractals.controllers.exceptions.RollbackFailureException;
import com.fractals.converters.ArtistConverter;
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
import java.util.logging.Logger;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;

/**
 *
 * @author Danieil Skrinikov
 */
@RunWith(Arquillian.class)
public class ReportsControllerTest {

    private static final Logger log = Logger.getLogger("ReportsControllerTest.class");
    
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
                .addPackage(NonexistentEntityException.class.getPackage())
                .addPackage(ArtistConverter.class.getPackage())
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
    
    /*
    Tests
    -------------------------------------------------------------------------
    */
    
    /**
     * Checks if the controller finds 62 zero clients in the database.
     */
    @Ignore
    @Test
    public void testGetZeroClients(){
        log.info("Test get zero clients");
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        
        List<User> zeroUsers = reports.getZeroClients(start, end);
        assertThat(zeroUsers).hasSize(62);
    }
    
    /**
     * Checks if the controller finds 62 zero clients in the database when the given
     * dates are switched.
     */
    @Ignore
    @Test
    public void testGetZeroClients_datesInversed(){
        log.info("Test get zero clients - Inversed dates");
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        
        List<User> zeroUsers = reports.getZeroClients(end, start);
        assertThat(zeroUsers).hasSize(62);
    }
    
    /**
     * Checks if the controller handles null dates.
     */
    @Ignore
    @Test
    public void testZeroClients_nullDates(){
        log.info("Test get zero clients - Null Dates");
        
        List<User> zeroUsers = reports.getZeroClients(null, null);
        assertThat(zeroUsers).isNull();
    }
    
    /**
     * Checks if the controller can find No zero users when the given date range
     * is bad.
     */
    @Ignore
    @Test
    public void testGetZerloClients_dateOutOfRange(){
        log.info("Test get zero clients - Inversed dates");
        LocalDateTime start = LocalDateTime.now().minusDays(56);
        LocalDateTime end = LocalDateTime.now().minusDays(54);
        
        List<User> zeroUsers = reports.getZeroClients(end, start);
        assertThat(zeroUsers).hasSize(100);
    }
    
    /**
     * Gets zero tracks
     */
    @Ignore
    @Test
    public void testGetZeroTracks(){
        log.info("Test get zero tracks");
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        
        List<Track> tracks = reports.getZeroTracks(start, end);
        assertThat(tracks).hasSize(63);
    }
    
    /**
     * Gets zero tracks
     */
    @Ignore
    @Test
    public void testGetZeroTracks_dateInversed(){
        log.info("Test get zero tracks");
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        
        List<Track> tracks = reports.getZeroTracks(end, start);
        assertThat(tracks).hasSize(63);
    }
    
    /**
     * Gets zero tracks
     */
    @Ignore
    @Test
    public void testGetZeroTracks_dateNull(){
        log.info("Test get zero tracks");
        
        List<Track> tracks = reports.getZeroTracks(null, null);
        assertThat(tracks).isNull();
    }
    
    /**
     * Gets zero tracks
     */
    @Ignore
    @Test
    public void testGetZeroTracks_dateOutOfRange(){
        log.info("Test get zero tracks");
        LocalDateTime start = LocalDateTime.now().minusDays(34);
        LocalDateTime end = LocalDateTime.now().minusDays(32);
        
        List<Track> tracks = reports.getZeroTracks(start, end);
        assertThat(tracks).hasSize(138);
    }
    
    /**
     * Gets zero tracks
     */
    @Ignore
    @Test
    public void testGetTopClient(){
        log.info("Test get top clients");
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        
        List<User> tracks = reports.getTopClients(start, end);
        assertThat(tracks).hasSize(38);
    }
    
    /**
     * Gets zero tracks
     */
    @Ignore
    @Test
    public void testGetTopClient_dateInversed(){
        log.info("Test get top client - date inversed");
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        
        List<User> tracks = reports.getTopClients(end, start);
        assertThat(tracks).hasSize(38);
    }
    
    /**
     * Gets zero tracks
     */
    @Ignore
    @Test
    public void testGetTopClient_nullDates(){
        log.info("Test get top client - null dates");
        
        List<User> tracks = reports.getTopClients(null, null);
        assertThat(tracks).isNull();
    }
    
    /**
     * Gets zero tracks
     */
    @Ignore
    @Test
    public void testGetTopClient_dateOutOfRange(){
        log.info("Test get top client - date out of range");
        LocalDateTime start = LocalDateTime.now().minusDays(24);
        LocalDateTime end = LocalDateTime.now().minusDays(20);
        
        List<User> tracks = reports.getTopClients(start, end);
        assertThat(tracks).hasSize(0);
    }
    
    /**
     * Checks if the controller can fetch sales by user.
     * bjordan1m has 3 orders on the database
     */
    @Ignore
    @Test
    public void testGetSalesByClient(){
        log.info("Test get sales by client");
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        String identifier = "bjordan1m";
        
        List<Order> tracks = reports.getSalesByClient(identifier, start, end);
        assertThat(tracks).hasSize(3);
    }
    
    /**
     * Checks if the controller can fetch sales by user.
     * thunter2r has 1 order on the database
     */
    @Ignore
    @Test
    public void testGetSalesByClient_otherPerson(){
        log.info("Test get sales by client");
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        String identifier = "thunter2r";
        
        List<Order> tracks = reports.getSalesByClient(identifier, start, end);
        assertThat(tracks).hasSize(1);
    }
    
    /**
     * Checks if the controller can fetch sales by user.
     * bmorris2m has no orders on the database
     */
    @Ignore
    @Test
    public void testGetSalesByClient_otherPersonNoOrders(){
        log.info("Test get sales by client");
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        String identifier = "bmorris2m";
        
        List<Order> tracks = reports.getSalesByClient(identifier, start, end);
        assertThat(tracks).hasSize(0);
    }
    
    /**
     * Checks if the controller can fetch sales by user.
     * bmorris2m has no orders on the database
     */
    @Ignore
    @Test
    public void testGetSalesByClient_nullDates(){
        log.info("Test get sales by client");
        String identifier = "bmorris2m";
        
        List<Order> tracks = reports.getSalesByClient(identifier, null, null);
        assertThat(tracks).isNull();
    }
    
    /**
     * Checks if the controller can fetch sales by user.
     * bmorris2m has no orders on the database
     */
    @Ignore
    @Test
    public void testGetSalesByClient_nullIdentifier(){
        log.info("Test get sales by client");
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        
        List<Order> tracks = reports.getSalesByClient(null, start, end);
        assertThat(tracks).hasSize(0);
    }
    
    /**
     * Checks if the controller can fetch sales by user.
     */
    @Ignore
    @Test
    public void testGetSalesByClient_dateOutOfRange(){
        log.info("Test get sales by client");
        LocalDateTime start = LocalDateTime.now().minusDays(23);
        LocalDateTime end = LocalDateTime.now().minusDays(21);
        String identifier = "bjordan1m";
        
        List<Order> tracks = reports.getSalesByClient(identifier, start, end);
        assertThat(tracks).hasSize(0);
    }
    
    /**
     * Checks if the controller can fetch sales by user email.
     * thunter2r has 1 order on the database
     */
    @Ignore
    @Test
    public void testGetSalesByClient_email(){
        log.info("Test get sales by client");
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        String identifier = "thunter2r@ameblo.jp";
        
        List<Order> tracks = reports.getSalesByClient(identifier, start, end);
        assertThat(tracks).hasSize(1);
    }
    
    
    /**
     * Checks if the controller returns total dates
     */
    @Ignore
    @Test
    public void testGetTotalSales(){
        log.info("Test get total sales");
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        
        List<Order> tracks = reports.getTotalSales(start, end);
        assertThat(tracks).hasSize(50);
    }
    
    /**
     * Checks if the controller returns total dates
     */
    @Ignore
    @Test
    public void testGetTotalSales_nullDates(){
        log.info("Test get total sales");
        
        List<Order> tracks = reports.getTotalSales(null, null);
        assertThat(tracks).isNull();
    }
    
    /**
     * Checks if the controller returns total dates
     */
    @Ignore
    @Test
    public void testGetTotalSales_OutOfRangeDates(){
        log.info("Test get total sales");
        LocalDateTime start = LocalDateTime.now().minusDays(23);
        LocalDateTime end = LocalDateTime.now().minusDays(21);
        
        List<Order> tracks = reports.getTotalSales(start, end);
        assertThat(tracks).hasSize(0);
    }
    
    /**
     * Checks if the controller returns total dates
     */
    @Ignore
    @Test
    public void testGetSalesByTrack(){
        log.info("Test get total sales");
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        int id = 127;
        
        List<OrderItem> tracks = reports.getSalesByTrack(id, start, end);
        assertThat(tracks).hasSize(2);
    }
    
     /**
     * Checks if the controller returns total dates
     */
    @Ignore
    @Test
    public void testGetSalesByTrack_differentTrack(){
        log.info("Test get total sales");
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        int id = 138;
        
        List<OrderItem> tracks = reports.getSalesByTrack(id, start, end);
        assertThat(tracks).hasSize(1);
    }
    
    /**
     * Checks if the controller returns total dates
     */
    @Ignore
    @Test
    public void testGetSalesByTrack_noSalesTrack(){
        log.info("Test get total sales");
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        int id = 137;
        
        List<OrderItem> tracks = reports.getSalesByTrack(id, start, end);
        assertThat(tracks).hasSize(0);
    }
    
    /**
     * Checks if the controller returns total dates
     */
    @Ignore
    @Test
    public void testGetSalesByAlbum(){
        log.info("Test get total sales");
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        int id = 66;
        
        List<OrderItem> tracks = reports.getSalesByAlbum(id, start, end);
        assertThat(tracks).hasSize(3);
    }
    
     /**
     * Checks if the controller returns total dates
     */
    @Ignore
    @Test
    public void testGetSalesByAlbum_differentAlbum(){
        log.info("Test get total sales");
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        int id = 57;
        
        List<OrderItem> tracks = reports.getSalesByAlbum(id, start, end);
        assertThat(tracks).hasSize(1);
    }
    
    /**
     * Checks if the controller returns total dates
     */
    @Ignore
    @Test
    public void testGetSalesByAlbum_noSalesAlbum(){
        log.info("Test get total sales");
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        int id = 1;
        
        List<OrderItem> tracks = reports.getSalesByTrack(id, start, end);
        assertThat(tracks).hasSize(0);
    }
    
    /**
     * Checks if the controller returns total dates
     */
    @Ignore
    @Test
    public void testGetSalesByArtist(){
        log.info("Test get total sales");
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        int id = 13;
        
        List<OrderItem> tracks = reports.getSalesByAlbum(id, start, end);
        assertThat(tracks).hasSize(2);
    }
    
     /**
     * Checks if the controller returns total dates
     */
    @Ignore
    @Test
    public void testGetSalesByArtist_differentArtist(){
        log.info("Test get total sales");
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        int id = 31;
        
        List<OrderItem> tracks = reports.getSalesByAlbum(id, start, end);
        assertThat(tracks).hasSize(1);
    }
    
    /**
     * Checks if the controller returns total dates
     */
    @Ignore
    @Test
    public void testGetSalesByArtist_noSalesArtist(){
        log.info("Test get total sales");
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        int id = 1;
        
        List<OrderItem> tracks = reports.getSalesByTrack(id, start, end);
        assertThat(tracks).hasSize(0);
    }
}
