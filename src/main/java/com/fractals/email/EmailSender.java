package com.fractals.email;

import com.fractals.beans.User;
import com.fractals.beans.Order;
import com.fractals.beans.OrderItem;
import com.fractals.beans.Province;
import com.fractals.utilities.BundleLocaleResolution;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import jodd.mail.Email;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import jodd.mail.SmtpSslServer;

/**
 * EmailSender class is responsible for sending emails to the client.
 *
 * @author Aline Shulzhenko
 * @version 01/03/2017
 * @since 1.8
 */
public class EmailSender {
    private String companyEmail = "fractals.inc@gmail.com";
    private String companyPass = "longlivemusic";
    private String companySmtpUrl = "smtp.gmail.com";
    private int companySmptPort = 465;
    private User user;
    private ResourceBundle bundle;
    
    /**
     * Instantiates the object.
     * @param user The User to whom the email should be sent.
     */
    public EmailSender(User user){
        this.user = user;
        this.bundle = new BundleLocaleResolution().returnBundleWithCurrentLocale();
    }
    
    /**
     * Sends the email informing the customer of his/her purchases.
     * @param order The newly created order for this customer.
     */
    public void sendEmail(Order order) {
        Email email = new Email();
        String message = createMessage(order);
        
        email.from(companyEmail).to(user.getEmail()).bcc(companyEmail).addHtml(message).subject("Fractals "+bundle.getString("invoice"));

        SmtpServer<SmtpSslServer> smtpServer = SmtpSslServer
                .create(companySmtpUrl, companySmptPort)
                .authenticateWith(companyEmail, companyPass);

        SendMailSession session = smtpServer.createSession();

        session.open();
        session.sendMail(email);
        session.close();
    }
    
    /**
     * Creates a message to the user inside the email.
     * @param order The newly created order for this customer.
     * @return a message to the user inside the email.
     */
    private String createMessage(Order order) {
        String message  = "<h2><i>Fractals "+bundle.getString("invoice")+"</i></h2><h4>"+bundle.getString("ty_email")+"</h4>"
                + "<b>"+bundle.getString("sale_number")+": </b>"+order.getId()+"<br/>"
                + "<b>"+bundle.getString("sale_date")+": </b>"
                + order.getOrderDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss a", Locale.CANADA))+"<br/>"
                + "<b>"+bundle.getString("total_net")+": </b>$"+Math.round(order.getNetCost()*100)/100.0+"<br/>";
        Province province = user.getProvince();
        if(province.getGst() != 0)
            message +=  "<b>"+bundle.getString("gst")+": </b>"+(Math.round(province.getGst()*100000)/1000.0)+"%<br/>";
        if(province.getPst() != 0)
            message +=  "<b>"+bundle.getString("pst")+": </b>"+(Math.round(province.getPst()*100000)/1000.0)+"%<br/>";
        if(province.getHst() != 0)
            message +=  "<b>"+bundle.getString("hst")+": </b>"+(Math.round(province.getHst()*100000)/1000.0)+"%<br/>";
        
        message += "<b>"+bundle.getString("total_gross")+": </b>$"+Math.round(order.getGrossCost()*100)/100.0+"<br/>"
                + "<h4>"+bundle.getString("items_email")+"</h4>";

        List<OrderItem> items = order.getOrderItems();
        
        for(OrderItem item : items) {
            message += "<b>"+bundle.getString("sale_item_number")+": </b>"+item.getId()+"<br/>";
            if(item.getAlbum() != null) {
                message += "<b>"+bundle.getString("item_name")+": </b>"+item.getAlbum().getTitle()+"<br/>"
                         + "<b>"+bundle.getString("inventory_number")+": </b>"+item.getAlbum().getId()+"<br/>";
            }
            else if(item.getTrack() != null) {
                message += "<b>"+bundle.getString("item_name")+": </b>"+item.getTrack().getTitle()+"<br/>"
                         + "<b>"+bundle.getString("inventory_number")+": </b>"+item.getTrack().getId()+"<br/>";
            }           
            message += "<b>"+bundle.getString("price")+": </b>$"+Math.round(item.getCost()*100)/100.0+"<br/><hr/>";
        }         
        return message;
    }
}
