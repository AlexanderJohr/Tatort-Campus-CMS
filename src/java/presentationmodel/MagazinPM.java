package presentationmodel;

import beans.DBConnection;
import beans.FileSystemUtil;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.sql.rowset.CachedRowSet;

/**
 * Pr&auml;sentationsmodell f&uuml;r die Ausgaben
 *
 * @author Alexander Johr u26865 m18927
 */
@Named
@RequestScoped
public class MagazinPM extends TabularPresentationModel implements Serializable {

    @EJB
    private FileSystemUtil files;
    // Erstes Magazin initialisieren mit -1 (nicht gesetzt)
    private int newestMagazineID = -1;
    private int magazineID;
    private int order;

    /**
     * Getter f&uuml;r die Reihenfolge
     *
     * @return Die Reihenfolge des aktuellen Magazins
     */
    public int getOrder() {
        return order;
    }

    /**
     * Getter f&uuml;r die ID
     *
     * @return Die ID des aktuellen Magazins
     */
    public int getMagazineID() {
        return magazineID;
    }

    /**
     * Getter f&uuml;r die relative URL des neusten Magazins (PDF)
     *
     * @return Die relative URL des neusten Magazins
     */
    public String getNewestPdfPath() {
        return files.getWebContentPath("magazine/" + newestMagazineID + "1.pdf");
    }

    /**
     * Getter f&uuml;r die relative URL des Bildes des neusten Magazins
     *
     * @return Die relative URL des Bildes des neusten Magazins
     */
    public String getNewestPdfImgPath() {
        return files.getWebContentPath("magazine/" + newestMagazineID + "1.jpg");
    }

    /**
     * Getter f&uuml;r die relative URL des neusten Magazins (PDF)
     *
     * @return Die relative URL des altuellen Magazins
     */
    public String getPdfPath() {
        // Apache PDF BOX fuegt jedem Bild eine 1 hinzu, da das Cover das erste Bild ist
        return files.getWebContentPath("magazine/" + magazineID + "1.pdf");
    }

    /**
     * Getter f&uuml;r die relative URL des Bildes des aktuellen Magazins
     *
     * @return Die relative URL das Bild des aktuellen Magazins
     */
    public String getPdfImgPath() {
        // Apache PDF BOX fuegt jedem Bild eine 1 hinzu, da das Cover das erste Bild ist
        return files.getWebContentPath("magazine/" + magazineID + "1.jpg");
    }

    /**
     * Initialisierung
     */
    @PostConstruct
    public void PostConstruct() {
        // Setzte Spaltenanzahl auf 1 und lese Magazine aus der Datenbank
        this.columns = 1;
        setRowSet("SELECT * FROM tc_magazine ORDER BY `ORDER` DESC;");
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
        magazineID = rowSet.getInt("ID");
        order = rowSet.getInt("ORDER");
        // Die erste Zeile ist auch die neueste Ausgabe
        if (newestMagazineID == -1) {
            newestMagazineID = magazineID;
        }
    }
}
