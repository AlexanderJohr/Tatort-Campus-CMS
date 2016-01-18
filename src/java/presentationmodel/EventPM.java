package presentationmodel;

import beans.UserSession;
import beans.DBConnection;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.sql.rowset.CachedRowSet;

/**
 * Pr&auml;sentationsmodell f&uuml;r die Events
 *
 * @author Alexander Johr u26865 m18927
 */
@Named
@RequestScoped
public class EventPM extends TabularPresentationModel implements Serializable {

    private int eventID;
    String eventTitelHtmlID;
    String eventTextHtmlID;
    private int order;

    /**
     * Getter f&uuml;r die Reihenfolge
     *
     * @return Die Reihenfolge des aktuellen Events
     */
    public int getOrder() {
        return order;
    }
    
    /**
     * Getter f&uuml;r die ID des Events
     *
     * @return Die ID des Events
     */
    public int getEventID() {
        return eventID;
    }

    /**
     * Getter f&uuml;r die HTMLID des Titels
     *
     * @return Die HTMLID des Titels
     */
    public String getEventTitelHtmlID() {
        return eventTitelHtmlID;
    }

    /**
     * Getter f&uuml;r die HTMLID des Textes
     *
     * @return Die HTMLID des Textes
     */
    public String getEventTextHtmlID() {
        return eventTextHtmlID;
    }

    /**
     * Initialisierung
     */
    @PostConstruct
    public void PostConstruct() {
        // Setzte Spaltenanzahl auf 3 und lese Events aus der Datenbank
        this.columns = 3;
        setRowSet("SELECT * FROM tc_event ORDER BY `ORDER` DESC;");
    }

    /**
     * Wird aufgerufen, wenn n&auml;chste Zeile gelesen wird, jedoch nicht, wenn alle
     * Datens&auml;tze bereits gelesen sind. Wird von Superklasse aufgerufen.
     *
     * @param rowSet Die aktuelle Zeile der gelesenen Tabelle.
     * @throws SQLException
     */
    @Override
    public void readRow(CachedRowSet rowSet) throws SQLException {
        // Speichere Zellenwerte in den Attributen ab
        eventID = rowSet.getInt("ID");
        order = rowSet.getInt("ORDER");
        eventTitelHtmlID = rowSet.getString("TITLEHTMLID");
        eventTextHtmlID = rowSet.getString("TEXTHTMLID");
    }
}
