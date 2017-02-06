package com.fractals.email;

import com.fractals.beans.Album;
import com.fractals.beans.Track;
import com.fractals.beans.User;
import jodd.mail.Email;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import jodd.mail.SmtpSslServer;

/**
 *
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
    
    public void sendEmail(Object[] items) {
        Email email = new Email();
        String message = createMessage(items);
        
        email.from(companyEmail).to(user.getEmail()).addHtml(message).subject("Fractals Invoice");

        SmtpServer<SmtpSslServer> smtpServer = SmtpSslServer
                .create(companySmtpUrl, companySmptPort)
                .authenticateWith(companyEmail, companyPass);

        SendMailSession session = smtpServer.createSession();

        session.open();
        session.sendMail(email);
        session.close();
    }
    
    private String createMessage(Object[] items) {
        String message  = "<h2>Thank you for shopping this us!</h2><br/>Your items:<ul>";
        for(Object o : items) {
            double price;
            String item;
            if(o instanceof Album) {
                Album album = (Album)o;
                price = album.getSalePrice();
                if(price == 0)
                    price = album.getListPrice();
                item = "Album: " + album.getTitle();
            }
            else {
                Track track = (Track)o;
                price = track.getSalePrice();
                if(price == 0)
                    price = track.getListPrice();
                item = "Track: " + track.getTitle();
            }
            message += "<li>"+item + " ,price: "+price+"</li>";
        }          
        message += "</ul>";
        return message;
    }
}
