package presentationmodel;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.mail.Message;
import javax.mail.Message.RecipientType;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Pr&auml;sentationsmodell der Kontakt-Maske
 *
 * @author Alexander Johr u26865 m18927
 */
@Named
@RequestScoped
public class EmailPM implements Serializable {

    @Resource(name = "mail/tcSession")
    private Session mailSession;
    private String message = "Nachricht";
    private String mail = "E-Mail";
    private String name = "Name";

    /**
     * Getter f&uuml;r den Inhalt des Name-Eingabefeldes
     *
     * @return Inhalt des Name-Eingabefeldes
     */
    public String getName() {
        return name;
    }

    /**
     * Setter f&uuml;r den Inhalt des Name-Eingabefeldes
     * @param message Inhalt des Name-Eingabefeldes
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter f&uuml;r den Inhalt des Mail-Eingabefeldes
     *
     * @return Inhalt des Mail-Eingabefeldes
     */
    public String getMail() {
        return mail;
    }

    /**
     * Setter f&uuml;r den Inhalt des Mail-Eingabefeldes
     * @param message Inhalt des Mail-Eingabefeldes
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Getter f&uuml;r den Inhalt des Nachricht-Eingabefeldes
     *
     * @return Inhalt des Nachricht-Eingabefeldes
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter f&uuml;r den Inhalt des Nachricht-Eingabefeldes
     * @param message Inhalt des Nachricht-Eingabefeldes
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Sende die Email an eine vordefinierte EMail-Adresse. Mail konnte nicht
     * getestet werden. Vom Wohnheimnetz aus ist der Zugriff auf den Mailserver
     * gesperrt. Der Rechenzentrum Server "Buntfalke" war zum Zeitpunkt der
     * Tests offline.
     */
    public void send() {
        try {

            Message msg = new MimeMessage(mailSession);

            // Setze Attribute
            msg.setSubject("Mail von der Tatort Campus Seite");
            msg.setRecipient(RecipientType.TO, new InternetAddress("tatort.campus.mailserver@gmail.com", "Tatort Campus Mail"));
            msg.setFrom(new InternetAddress(mail, name));
            msg.setText(message);

            // Senden
            Transport.send(msg);

        } catch (MessagingException ex) {
            Logger.getLogger(EmailPM.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EmailPM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
