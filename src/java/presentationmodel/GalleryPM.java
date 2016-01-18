package presentationmodel;

import beans.UserSession;
import beans.DBConnection;
import beans.FileSystemUtil;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.sql.rowset.CachedRowSet;

/**
 * Pr&auml;sentationsmodell f&uuml;r die Gallerien
 *
 * @author Alexander Johr u26865 m18927
 */
@Named
@RequestScoped
public class GalleryPM extends TabularPresentationModel implements Serializable {

    @EJB
    private FileSystemUtil files;
    private int galleryID;
    private String titleHtmlID;
    private int order;
    private Random random = new Random();
    private List<String> detailImgs;

    /**
     * Getter f&uuml;r die Reihenfolge
     *
     * @return Die Reihenfolge der aktuellen Kategorie
     */
    public int getOrder() {
        return order;
    }

    /**
     * Getter f&uuml;r eine zuf&auml;llige leichte Rotation
     *
     * @return Zuf&auml;llige leichte Rotation
     */
    public double getRotation() {
        return (random.nextDouble() - 0.5) * 4;
    }

    /**
     * Getter f&uuml;r die ID der Kategorie
     *
     * @return Die ID der Kategorie
     */
    public int getGalleryID() {
        return galleryID;
    }

    /**
     * Getter f&uuml;r die HTMLID des Kategorie-Titels
     *
     * @return Die HTMLID des Kategorie-Titels
     */
    public String getTitleHtmlID() {
        return titleHtmlID;
    }

    /**
     * Getter f&uuml;r die relative URL des Kategorie-Bildes
     *
     * @return Die relative URL des Kategorie-Bildes
     */
    public String getCategoryImg() {
        return files.getWebContentPath("gallery/category" + galleryID + "/categoryImage/categoryImage.jpeg");
    }

    /**
     * Getter f&uuml;r die relative URL des Ordners der Detailbilder
     *
     * @return Die relative URL des Ordners der Detailbilder
     */
    public String getDetailImgPath() {
        return files.getWebContentPath("gallery/category" + galleryID + "/detail/");
    }

    /**
     * Getter f&uuml;r die relative URL der Detailbilder
     *
     * @return Die relative URL der Detailbilder
     */
    public List<String> getDetailImgs() {
        return detailImgs;
    }

    /**
     * Initialisierung
     */
    @PostConstruct
    public void PostConstruct() {
        // Setzte Spaltenanzahl auf 3 und lese Gallerien aus der Datenbank
        this.columns = 3;
        setRowSet("SELECT * FROM tc_gallery ORDER BY `ORDER` DESC;");
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
        galleryID = rowSet.getInt("ID");
        order = rowSet.getInt("ORDER");
        titleHtmlID = rowSet.getString("HTMLID");
        detailImgs = files.getFilesFromFolder("/gallery/category" + galleryID + "/detail/");
    }
}
