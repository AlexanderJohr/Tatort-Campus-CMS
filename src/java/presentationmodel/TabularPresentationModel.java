/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentationmodel;

import beans.DBConnection;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.sql.rowset.CachedRowSet;

/**
 * Kapsel die Funktionalit&auml;t zum tabellenartigen Ausgeben von Containerelement.
 *
 * @author Alexander Johr u26865 m18927
 */
@Named
@RequestScoped
public abstract class TabularPresentationModel implements Serializable {

    @EJB
    private DBConnection db;
    protected int columns;
    protected int count;
    protected int rows;
    protected boolean allProcessed = false;
    private CachedRowSet rowSet;

    /**
     * Initialisiert das Modell
     *
     * @param sql Aufruf an die Datenbank zum Lesen der anzuzeigenden Tabelle
     */
    public void setRowSet(String sql) {
        // Speichert Tabelle von der Datenbank in eine Arbeitsspeicher-Tabelle.
        rowSet = db.select(sql);
        // Setzt die Anzahl der Zeilen.
        this.count = rowSet.size();
        // Anzahl durch Spalten ergibt aufgerundet die Anzahl der Zeilen
        this.rows = (int) Math.ceil((double) count / columns);
        // Erste Zeile lesen
        next();
    }
    
    /**
     * Getter ff&uuml; die Anzahl der gelesenen Datens&auml;tze
     *
     * @return Anzahl der gelesenen Datens&auml;tze
     */
    public int getCount() {
        return count;
    }

    /**
     * Getter ff&uuml; die Anzahl der zu f&uuml;llenden Spalten
     *
     * @return Anzahl der zu f&uuml;llenden Spalten.
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Getter ff&uuml; die Anzahl der zu f&uuml;llenden Zeilen
     *
     * @return Anzahl der zu f&uuml;llenden Zeilen.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Getter ff&uuml; Flag, ob alles gelesen wurde.
     *
     * @return true, wenn alle Zeilen gelesen wurde, sonst false
     */
    public boolean isAllProcessed() {
        return allProcessed;
    }

    /**
     * Setzt die aktuelle Zeile in der Datenbank auf die folgende Zeile.
     */
    public void next() {
        try {
            if (!allProcessed && rowSet.next()) {
                readRow(rowSet);
            } else {
                // Wenn alle Zeilen gelesen wurden, also next() false liefert, wird der entsprechende Flag gesetzt.
                allProcessed = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventPM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Wird aufgerufen, wenn next() aufgerufen wird, jedoch nicht, wenn alle
     * Datens&auml;tze bereits gelesen sind. Wird von Subklassen jeweils anders
     * implementiert.
     *
     * @param rowSet Die aktuelle Zeile der gelesenen Tabelle.
     * @throws SQLException
     */
    public abstract void readRow(CachedRowSet rowSet) throws SQLException;
}
