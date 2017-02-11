package com.fractals.backingbeans;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Danieil
 */
@Named("reportsBacking")
@SessionScoped
public class ReportsBacking implements Serializable {
    
    

    // Sales
    private Date salesBegin;
    private Date salesEnd;

    // Revenue
    private Date revenueBegin;
    private Date revenueEnd;
    private String revenue;
    
    public ReportsBacking(){
        // I am not quite sure it is the best way to get the date.
        salesBegin = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        // Date objects may break if this website exists past 
        salesEnd = new Date(salesBegin.getTime());
        revenueBegin = new Date(salesBegin.getTime());
        revenueEnd = new Date(salesBegin.getTime());
    }

    /**
     * This method is called whenever a user chooses a date in the sales 
     * @param event 
     */
    public void onDateSelect(SelectEvent event) {
        System.out.println(event.getComponent().getId());
        Date date = (Date) event.getObject();
        System.out.println(salesBegin+"- -"+salesEnd);
        
        //salesBegin.compareTo(salesEnd);
    }
    
    /**
     * Calls the report controller to update the sales list.
     */
    private void updateSales(){
        
    }
    
    /**
     * Calls the ReportController to get the total income between the specified 
     * date range.
     */
    private void updateIncome(){
        
    }
    
    /**
     * Calls the ReportController to get a list of all purchases made by a client
     * between a given date range.
     * 
     * @param username username of the client to look for.
     */
    private void fetchSalesByClient(String username){
        
    }
    
    private void fetchSalesByTrack(String track){
        
    }

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
    
    
}
