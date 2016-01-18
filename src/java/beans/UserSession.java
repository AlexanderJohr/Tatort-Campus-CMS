package beans;

import java.io.Serializable;
import java.sql.Types;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Verwaltet die Sitzung des Nutzers.
 *
 * @author Alexander Johr u26865 m18927
 */
@Named
@Stateful
@SessionScoped
public class UserSession implements Serializable {

    private boolean authorised;
    @EJB
    private DBConnection db;

    /**
     * Gibt den Status der Sitzung zur&uuml;ck
     * @return true wenn autorisiert, false wenn nicht
     */
    public boolean isAuthorised() {        return authorised;    }

    /**
     * &uuml;berpr&uuml;ft, ob die Kombination aus Benutzername und Passwort als Redaktionsmitglied
     * gespeichert ist und setzt dementsprechend die Sitzung auf autorisiert oder nicht. 
     * @param userName Der zupr&uuml;fende Benutzernamen
     * @param password Das pr&uuml;fende Passwort
     */
    public void login(String userName, String password) {
        // Findet sich der Benutzername mit dem Passwort in der Datenbank?
        Object[] outParams = db.callStoredUpdateProcedure
                ("{ call isEditorialUser( ?, '" + userName + "','" + password + "') }"
                , new int[]{Types.BOOLEAN});

        // Wurde der OUT-Parameter mit True gesetzt, sind Name und Passwort korrekt
        if (outParams[0] != null && (Boolean) outParams[0] == true) {
            authorised = true;
        }
        // Andernfalls
        else {
            authorised = false;
        }
    }

    /**
     * Setzt die Sitzung auf nicht autorisiert.
     */
    public void logout() {
        authorised = false;
    }
}
