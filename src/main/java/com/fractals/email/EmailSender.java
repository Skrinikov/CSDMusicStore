package com.fractals.email;

import com.fractals.beans.Album;
import com.fractals.beans.Track;
import com.fractals.beans.User;
import com.fractals.controllers.ShoppingCart;
import java.util.List;
import jodd.mail.Email;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import jodd.mail.SmtpSslServer;

/**
 * i did it
 * @author lynn
 */
public class EmailSender {
    private String companyEmail = "fractals@gmail.com";
    private String companyPass = "fractals";
    private String companySmtpUrl = "smtp.gmail.com";
    private int companySmptPort = 465;
    private User user;
    
    public EmailSender(User user){
        this.user = user;
    }
    
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
