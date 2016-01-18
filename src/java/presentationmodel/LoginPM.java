package presentationmodel;

import beans.UserSession;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.inject.Inject;

/**
 * Pr&auml;sentationsmodell der Anmeldemaske
 *
 * @author Alexander Johr u26865 m18927
 */
@Named
@SessionScoped
public class LoginPM implements Serializable {

    @Inject
    UserSession session;
    
    private String userName;
    private String password;

    /**
     * Getter f&uuml;r den Inhalt des Benutzernamen-Eingabefeldes
     *
     * @return Inhalt des Benutzernamen-Eingabefeldes
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter f&uuml;r den Inhalt des Benutzernamen-Eingabefeldes
     * @param userName Inhalt des Benutzernamen-Eingabefeldes
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    
    public String getPassword() {
        return password;
    }

    /**
     * Setter f&uuml;r den Inhalt des Passwort-Eingabefeldes
     * @param password Inhalt des Passwort-Eingabefeldes
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
