package beans;

import com.sun.rowset.CachedRowSetImpl;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;

/**
 * Stellt Verbindung mit der Datenbank her und f&uuml;hrt Anfragen und Stored
 * Procedures aus
 *
 * @author Alexander Johr u26865 m18927
 */
@Stateless
public class DBConnection {

    @Resource(name = "DBConnection")
    private DataSource dBConnection;

    /**
     * Stellt eine Anfrage an die Datenbankliefert die Antwort als gecachte Tabelle
     * @param sql Anfrage-Query
     * @return gecachte Ergebnis-Tabelle
     */
    public CachedRowSet select(String sql) {
        Connection c = null;
        ResultSet rs = null;
        Statement stmt = null;
        CachedRowSetImpl crs = null;

        try {
            // Verbinde
            c = dBConnection.getConnection();
            stmt = c.createStatement();
            // F&uuml;hre Anfrage aus
            rs = stmt.executeQuery(sql);
            crs = new CachedRowSetImpl();
            // Erstelle gecachte Tabelle aus dem ResultSet
            crs.populate(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Schlie&szlig;e alles
            closeEverything(rs, stmt, c);
        }
        return crs;
    }

    /**
     * Schlie&szlig;t Das &uuml;bergebene ResultSet, Statement und die Verbindung
     *
     * @param rs Das zu schlie&szlig;ende ResultSet
     * @param stmt Das zu schlie&szlig;ende Statement
     * @param con Die zu schlie&szlig;ende Verbindung
     */
    public void closeEverything(ResultSet rs, Statement stmt,
            Connection con) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * F&uuml;hrt eine Stored Procedure auf der Datenbank aus und liefert die zur&uuml;ckgegebenen OUT-Parameter als Objekt-Array zur&uuml;ck
     * @param procedure // Die auszuf&uuml;hrende Stored Procedure
     * @param outparamTypes // Die Typen der verlangten OUT-Parameter
     * @return Objekt-Array mit den angeforderten OUT-Parametern
     */
    public Object[] callStoredUpdateProcedure(String procedure, int[] outparamTypes) {
        Connection c = null;
        CallableStatement cs = null;
        Object[] outParameters = new Object[outparamTypes.length];

        try {
            // Verbindende
            c = dBConnection.getConnection();
            cs = c.prepareCall(procedure);

            // Registriere OUT-Parameter mit &uuml;bergebenen Typen
            for (int i = 0; i < outparamTypes.length; i++) {
                cs.registerOutParameter(i + 1, outparamTypes[i]);
            }
            // F&uuml;hre Anfrage aus
            cs.execute();

            // Lies OUT-Parameter aus
            for (int i = 0; i < outparamTypes.length; i++) {
                outParameters[i] = cs.getObject(i + 1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // schlie&szlig;e alles
            closeEverything(null, cs, c);
        }
        // Liefere OUT-Parameter zur&uuml;ck
        return outParameters;
    }
}
