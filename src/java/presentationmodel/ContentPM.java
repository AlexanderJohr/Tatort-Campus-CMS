package presentationmodel;

import beans.UserSession;
import beans.DBConnection;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.rowset.CachedRowSet;

/**
 * Pr&auml;sentationsmodell f&uuml;r die Seiteninhalte
 *
 * @author Alexander Johr u26865 m18927
 */
@Named
@RequestScoped
public class ContentPM implements Serializable {

    @Inject
    UserSession session;
    @EJB
    DBConnection db;
    
    HashMap<String, String> contentMap = new HashMap<String, String>();

    /**
     * Getter f&uuml;r Schreibrecht
     * @return true, wenn Nutzer autorisiert ist, sonst false
     */
    public boolean editable() {
        return session.isAuthorised();
    }
    
    /**
     * Getter f&uuml;r den Seiteninhalt der &uuml;bergebenen HTMLID
     * @param key HTMLID des gew&uuml;nschten Seiteninhaltes
     * @return Seiteninhalt der &uuml;bergebenen HTMLID
     */
    public String get(String key){
        return contentMap.get(key);
    }

    /**
     * Initialisierung
     */
    @PostConstruct
    private void PostConstruct() {
        // Lese alle Seiten Inhalte aus der Datenbank
        CachedRowSet contents = db.select("SELECT * FROM tatortcampusdb.tc_content;");

        try {
            while (contents.next()) {
                // F&uuml;ge jeden Seiteninhalt &uuml;ber die HTML ID als Schl&uuml;ssel in eine Map ein
                contentMap.put(contents.getString("HTMLID"), contents.getString("CONTENT"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContentPM.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
