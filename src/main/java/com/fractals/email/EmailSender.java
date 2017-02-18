package com.fractals.email;

import com.fractals.beans.Album;
import com.fractals.beans.Track;
import com.fractals.beans.User;
import com.fractals.backingbeans.ShoppingCart;
import java.util.List;
import jodd.mail.Email;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import jodd.mail.SmtpSslServer;

/**
 * EmailSender class is responsible for sending emails to the client.
 *
 * @author Alena Shulzhenko
 * @version 18/02/2017
 * @since 1.8
 */
public class EmailSender {
    private String companyEmail = "fractals@gmail.com";
    private String companyPass = "fractals";
    private String companySmtpUrl = "smtp.gmail.com";
    private int companySmptPort = 465;
    private User user;
    
    /**
     * Instantiates the object.
     * @param user The User to whom the email should be sent.
     */
    public EmailSender(User user){
        this.user = user;
    }
    
    /**
     * Sends the email informing the customer of his/her purchases.
     * @param cart All items bought by the customer.
     */
    public void sendEmail(ShoppingCart cart) {
        List<Album> albums = cart.getAllAlbums();
        List<Track> tracks = cart.getAllTracks();
        Email email = new Email();
        String message = createMessage(albums, tracks);
        
        email.from(companyEmail).to(user.getEmail()).addHtml(message).subject("Fractals Invoice");

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
     * @param albums All albums bought by the customer.
     * @param tracks All tracks bought by the customer.
     * @return a message to the user inside the email.
     */
    private String createMessage(List<Album> albums, List<Track> tracks) {
        String message  = "<h2>Thank you for shopping this us!</h2><br/>Your items:<ul>";
        double price;
         
        for(Album album : albums) {         
            price = album.getSalePrice();
            if(price == 0)
                price = album.getListPrice();
            message += "<li>"+ "Album: " + album.getTitle() + " ,price: "+price+"</li>";
        }
        
        for(Track track : tracks) {
            price = track.getSalePrice();
            if(price == 0)
                price = track.getListPrice();
            message += "<li>"+ "Track: " + track.getTitle() + " ,price: "+price+"</li>";
        }         
        
        message += "</ul>";
        return message;
    }
}
