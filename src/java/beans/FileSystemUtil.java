/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import org.apache.commons.fileupload.FileItem;

/**
 * Verwaltet Dateioperationen relativ von einem Startpfad der Anwendung heraus
 * @author Alexander Johr u26865 m18927
 */
@Stateless
public class FileSystemUtil {

    @Context
    private ServletContext servletContext;

    /**
     * Gibt relative URL der angeforderten Adresse relativ vom Startverzeichnis 
     * @param path angeforderte Adresse
     * @return URL des Pfades relativ vom Startverzeichnis 
     */
    public String getWebContentPath(String path) {
        return getCmsFilesRoot() + path;
    }
    
    /**
     * Gibt absoluten Dateipfad des Pfades relativ vom Startverzeichnis 
     * @param path Pfad relativ vom Startverzeichnis 
     * @return absoluter Dateipfad des relativen Pfades
     */
    public String getAbsolutePath(String path) {
        return getAbsoluteRoot() + path;
    }

    /**
     * Gibt absoluten Dateipfad des Pfades relativ vom Startverzeichnis 
     * @param path Pfad relativ vom Startverzeichnis 
     * @return absoluter Dateipfad des relativen Pfades
     */
    public File getFile(String path) {
        return new File(getAbsolutePath(path));
    }

    /**
     * Erstellt Ordner gegeben durch den Pfad relativ vom Startverzeichnis 
     * @param path Pfad relativ vom Startverzeichnis 
     * @return Erstellter Ordner
     */
    public File createDir(String path) {
        File dir = getFile(path);
        dir.mkdirs();
        return dir;
    }

    /**
     * Schreibt Datei beim &uuml;bergebenen relativen Dateipfad
     * @param item Zu schreibende Datei
     * @param path Absoluter Dateipfad
     * @return  &uuml;bergebene Datei bei erfolgreichem Speichern, sonst null
     */
    public File write(FileItem item, String path) {
        return write(item, getFile(path));
    }

    /**
     * Schreibt Datei beim &uuml;bergebenen absoluten Dateipfad
     * @param item Zu schreibende Datei
     * @param path Absoluter Dateipfad
     * @return &uuml;bergebene Datei bei erfolgreichem Speichern, sonst null
     */
    public File write(FileItem item, File path) {
        File written = null;
        try {
            if (!path.getParentFile().exists()) {
                path.getParentFile().mkdirs();
            }
            item.write(path);
            written = path;
        } catch (Exception ex) {
            Logger.getLogger(FileSystemUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return written;
    }

    /**
     * Entfernt Datei des &uuml;bergebenen Pfades relativ vom Startverzeichnis
     * @param path Pfad der zu entfernenden Datei relativ vom Startverzeichnis
     */
    public void delete(String path) {
        File toDelete = getFile(path);
        toDelete.delete();
    }

    /**
     * Gibt alle Dateien aus einem Ordner zur&uuml;ck
     *
     * @param folderPath Pfad des Ordners relativ vom Startverzeichnis
     * @return Alle Dateien aus gew&uuml;nschtem Ordner
     */
    public List<String> getFilesFromFolder(String folderPath) {
        String rootPfad = getAbsoluteRoot() + folderPath;
        File ordner = new File(rootPfad);
        File[] dateien = ordner.listFiles();
        List<String> dateiNamen = new ArrayList<String>();

        if (dateien != null) {
            for (File datei : dateien) {
                dateiNamen.add(datei.getName());
            }
        }

        return dateiNamen;
    }

    /**
     * Getter f&uuml;r den absoluten Dateipfad des Startverzeichnisses
     *
     * @return "C://.../cmsFiles/"
     */
    public String getAbsoluteRoot() {
        return servletContext.getRealPath(getCmsFilesRoot());
    }

    /**
     * Getter f&uuml;r relative URL des Startverzeichnisses aller verwalteten Dateien
     *
     * @return "cmsFiles/"
     */
    public String getCmsFilesRoot() {
        return "cmsFiles/";
    }
}
