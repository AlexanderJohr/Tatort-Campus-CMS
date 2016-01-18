package presentationmodel;

import beans.FileSystemUtil;
import java.io.Serializable;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.sql.rowset.CachedRowSet;

/**
 * Pr&auml;sentationsmodell f&uuml;r die Logos
 *
 * @author Alexander Johr u26865 m18927
 */
@Named
@RequestScoped
public class LogoPM extends TabularPresentationModel implements Serializable {

    @EJB
    private FileSystemUtil files;
    private int logoID;
    private String linkHtmlID;
    private int order;

    /**
     * Getter f&uuml;r die Reihenfolge
     *
     * @return Die Reihenfolge des aktuellen Logos
     */
    public int getOrder() {
        return order;
    }

    /**
     * Getter f&uuml;r die ID
     *
     * @return Die ID des aktuellen Logos
     */
    public int getLogoID() {
        return logoID;
    }

    /**
     * Getter f&uuml;r die HTMLID des Links
     *
     * @return Die HTMLID des Links des aktuellen Logos
     */
    public String getLinkHtmlId() {
        return linkHtmlID;
    }

    /**
     * Getter f&uuml;r die relative URL des aktuellen Logos
     *
     * @return Die relative URL des aktuellen Logos
     */
    public String getImgPath() {
        return files.getWebContentPath("logos/" + logoID + ".jpeg");
    }

    /**
     * Initialisierung
     */
    @PostConstruct
    public void PostConstruct() {
        // Setzte Spaltenanzahl auf 6 und lese Logos aus der Datenbank
        this.columns = 6;
        setRowSet("SELECT * FROM tc_logo ORDER BY `ORDER` DESC;");
    }

    /**
     * Wird aufgerufen, wenn n&auml;chste Zeile gelesen wird, jedoch nicht, wenn
     * alle Datens&auml;tze bereits gelesen sind. Wird von Superklasse
     * aufgerufen.
     *
     * @param rowSet Die aktuelle Zeile der gelesenen Tabelle.
     * @throws SQLException
     */
    @Override
    public void readRow(CachedRowSet rowSet) throws SQLException {
        // Speichere Zellenwerte in den Attributen ab
        logoID = rowSet.getInt("ID");
        order = rowSet.getInt("ORDER");
        linkHtmlID = rowSet.getString("HTMLID");
    }
}
