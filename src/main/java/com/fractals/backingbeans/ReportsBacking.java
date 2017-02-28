package com.fractals.backingbeans;

import com.fractals.beans.Album;
import com.fractals.beans.Order;
import com.fractals.beans.OrderItem;
import com.fractals.beans.Track;
import com.fractals.beans.User;
import com.fractals.beans.displaybeans.CustomOrderItem;
import com.fractals.controllers.ReportsController;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Danieil
 */
@Named("reportsBacking")
@SessionScoped
public class ReportsBacking implements Serializable {

    private static final Logger log = Logger.getLogger("ReportsBacking.class");

    // Controller
    @Inject
    private ReportsController reports;

    // Sales
    private Date salesBegin;
    private Date salesEnd;

    // Top Reports
    private int userCount;
    private long salesCount;
    private double totalRevenue;

    // Secondary Reports
    private double totalCost;
    private double totalProfits;
    private long totalTracks;
    private long totalAlbums;

    // Revenue
    private Date revenueBegin;
    private Date revenueEnd;
    private String revenue;

    // Total Sales
    private List<Order> totalSalesOrders;
    // Orders from user
    private List<Order> ordersFromUser;
    // Orders From track
    private List<Order> ordersFromTrack;
    // Orders by Album
    private List<Order> ordersFromAlbum;
    // Order by Artist
    private List<Order> ordersFromArtist;
    // Top Sellers
    private List<Order> topOrders;
    // Top Clients
    private List<User> topUsers;
    // Zero Tracks
    private List<Track> zeroTracks;
    // Zero Users
    private List<User> zeroUsers;

    /**
     * Default constructor.
     */
    public void init() {
        // I am not quite sure it is the best way to get the date.
        LocalDateTime now = LocalDateTime.now();

        userCount = reports.getVisitorCount();
        salesCount = reports.getSalesCount(now.minusDays(1), now.plusDays(1));
        totalRevenue = Math.round(reports.getSalesAmount(now.minusDays(1), now.plusDays(1)) * 100) / 100.;
        totalCost = Math.round(reports.getTotalCost(now.minusDays(1), now.plusDays(1)) * 100) / 100.;
        totalProfits = Math.round(reports.getTotalProfit(now.minusDays(1), now.plusDays(1)) * 100) / 100.;
        totalTracks = reports.getTrackCount(now.minusDays(1), now.plusDays(1));
        totalAlbums = reports.getAlbumCount(now.minusDays(1), now.plusDays(1));

    }

    /**
     * This method is called whenever a user chooses a date in the sales
     *
     * @param event
     */
    public void onDateSelect(SelectEvent event) {
        System.out.println(event.getComponent().getId());
        Date date = (Date) event.getObject();
        System.out.println(salesBegin + "- -" + salesEnd);

        //salesBegin.compareTo(salesEnd);
    }

    /**
     * Calls the report controller to update the sales list.
     */
    private void updateSales() {

    }

    /**
     * Calls the ReportController to get the total income between the specified
     * date range.
     */
    private void updateIncome() {

    }

    /**
     * Calls the ReportController to get a list of all purchases made by a
     * client between a given date range.
     *
     * @param username username of the client to look for.
     */
    private void fetchSalesByClient(String username) {

    }

    private void fetchSalesByTrack(String track) {

    }

    /**
     * Is invoked whenever the total sales action button is pressed. Calls the
     * getTotalSales method from the controller and sets it into the list
     */
    public void getTotalSales() {
        totalSalesOrders = reports.getTotalSales(LocalDateTime.now().minusDays(2), LocalDateTime.now().plusDays(1));
    }

    public void getSalesByUser() {

    }

    /**
     * Fetches all the order items from a given order and creates a custom order
     * item list in a way that can be easily displayed.
     *
     * @param o
     * @return
     */
    public List<CustomOrderItem> getCustomOrderItems(Order o) {
        List<CustomOrderItem> items = new ArrayList<>();
        if (o == null) {
            log.info("null");
            return items;
        }
        List<OrderItem> raw = o.getOrderItems();

        CustomOrderItem temp;

        for (OrderItem i : raw) {
            if (i.getAlbum() != null) {
                Album album = i.getAlbum();
                temp = new CustomOrderItem();

                temp.setTitle(album.getTitle());
                temp.setId(album.getId());
                temp.setCost(album.getCostPrice());
                temp.setRemoved(album.getRemovedAt());
                temp.setType("Album");

                if (album.getSalePrice() <= 0) {
                    temp.setPrice(album.getListPrice());
                } else {
                    temp.setPrice(album.getSalePrice());
                }
                items.add(temp);
            } else if (i.getTrack() != null) {
                Track track = i.getTrack();
                temp = new CustomOrderItem();

                temp.setTitle(track.getTitle());
                temp.setId(track.getId());
                temp.setCost(track.getCostPrice());
                temp.setRemoved(track.getRemovedAt());
                temp.setType("Track");

                if (track.getSalePrice() <= 0) {
                    temp.setPrice(track.getListPrice());
                } else {
                    temp.setPrice(track.getSalePrice());
                }
                items.add(temp);
            }
        }

        return items;
    }

    /* Getters and setters -------------------------------------------------------------------------------------------------------*/
    public Date getSalesBegin() {
        return salesBegin;
    }

    public void setSalesBegin(Date salesBegin) {
        this.salesBegin = salesBegin;
    }

    public Date getSalesEnd() {
        return salesEnd;
    }

    public void setSalesEnd(Date salesEnd) {
        this.salesEnd = salesEnd;
    }

    public Date getRevenueBegin() {
        return revenueBegin;
    }

    public void setRevenueBegin(Date revenueBegin) {
        this.revenueBegin = revenueBegin;
    }

    public Date getRevenueEnd() {
        return revenueEnd;
    }

    public void setRevenueEnd(Date revenueEnd) {
        this.revenueEnd = revenueEnd;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public List<Order> getTotalSalesOrders() {
        return totalSalesOrders;
    }

    public void setTotalSalesOrders(List<Order> totalSalesOrders) {
        this.totalSalesOrders = totalSalesOrders;
    }

    public List<Order> getOrdersFromUser() {
        return ordersFromUser;
    }

    public void setOrdersFromUser(List<Order> ordersFromUser) {
        this.ordersFromUser = ordersFromUser;
    }

    public List<Order> getOrdersFromTrack() {
        return ordersFromTrack;
    }

    public void setOrdersFromTrack(List<Order> ordersFromTrack) {
        this.ordersFromTrack = ordersFromTrack;
    }

    public List<Order> getOrdersFromAlbum() {
        return ordersFromAlbum;
    }

    public void setOrdersFromAlbum(List<Order> ordersFromAlbum) {
        this.ordersFromAlbum = ordersFromAlbum;
    }

    public List<Order> getOrdersFromArtist() {
        return ordersFromArtist;
    }

    public void setOrdersFromArtist(List<Order> ordersFromArtist) {
        this.ordersFromArtist = ordersFromArtist;
    }

    public List<Order> getTopOrders() {
        return topOrders;
    }

    public void setTopOrders(List<Order> topOrders) {
        this.topOrders = topOrders;
    }

    public List<User> getTopUsers() {
        return topUsers;
    }

    public void setTopUsers(List<User> topUsers) {
        this.topUsers = topUsers;
    }

    public List<Track> getZeroTracks() {
        return zeroTracks;
    }

    public void setZeroTracks(List<Track> zeroTracks) {
        this.zeroTracks = zeroTracks;
    }

    public List<User> getZeroUsers() {
        return zeroUsers;
    }

    public void setZeroUsers(List<User> zeroUsers) {
        this.zeroUsers = zeroUsers;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public long getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(long salesCount) {
        this.salesCount = salesCount;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getTotalProfits() {
        return totalProfits;
    }

    public void setTotalProfits(double totalProfits) {
        this.totalProfits = totalProfits;
    }

    public long getTotalTracks() {
        return totalTracks;
    }

    public void setTotalTracks(long totalTracks) {
        this.totalTracks = totalTracks;
    }

    public long getTotalAlbums() {
        return totalAlbums;
    }

    public void setTotalAlbums(long totalAlbums) {
        this.totalAlbums = totalAlbums;
    }

}
