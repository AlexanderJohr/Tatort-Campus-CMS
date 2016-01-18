package services;

import beans.DBConnection;
import beans.FileSystemUtil;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Context;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

/**
 * Stellt Dienste zum verwalten der Gallerien bereit.
 *
 * @author Alexander Johr u26865 m18927
 */
@Path("gallery")
@Stateless
@Interceptors(AdminInterceptor.class)
public class GalleryService {

    @EJB
    private DBConnection db;
    @EJB
    private FileSystemUtil files;

    /**
     * Entfernt die Galerie-Kategorie der gesendeten ID.
     *
     * @param id Die ID, der zu entfernenden Kategorie.
     * @return XML Antwort zur Aktualisierung der galleries_box.
     * @throws IOException
     */
    @POST
    @Path(value = "/delete/{id}")
    @Produces("application/xml")
    public String deleteGallery(@PathParam(value = "id") String id)
            throws IOException {
        // Entferne Kategorie-Ordner von der Festplatte
        File ordner = new File(files.getAbsoluteRoot() + "/gallery/category" + id + "/");
        FileUtils.deleteDirectory(ordner);
        // Entferne Kategorie aus der Datenbank
        db.callStoredUpdateProcedure("{ call deleteGallery(" + id + ") }", new int[0]);
        return "<updates><update><target>galleries_box</target>"
                + "<url>faces/gallery.xhtml</url></update></updates>";
    }

    /**
     * Setzt eine neue Galerie-Kategorie in der Datenbank ein.
     *
     * @param request Die HTTP-Anfrage, mit den Bildern und dem Kategorie-Titel.
     *
     * @return XML Antwort zur Aktualisierung der galleries_box.
     */
    @POST
    @Path(value = "/upload")
    @Produces("application/xml")
    public String uploaGallery(@Context HttpServletRequest request) {
        try {
            if (ServletFileUpload.isMultipartContent(request)) {
                // Liest Dateien und Felder aus.
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> items = upload.parseRequest(request);
                // Speichert Feld-Inhalt unter Feld-Name ab
                Map<String, String> fields = new HashMap<String, String>();
                // Speichert Detailbilder und Vorschaubilder
                ArrayList<FileItem> detailImgs = new ArrayList<FileItem>();
                ArrayList<FileItem> thumbImgs = new ArrayList<FileItem>();
                FileItem categoryImg = null;

                
                for (FileItem item : items) {
                    if (item.isFormField()) {
                        // Lese alle Formfelder
                        fields.put(item.getFieldName(), item.getString("UTF-8"));
                    } else {
                        // Lese alle Bilder und speichere sie in der entsprechenden Liste
                        if (item.getFieldName().contains("detail")) {
                            detailImgs.add(item);
                        } else if (item.getFieldName().contains("thumbnail")) {
                            thumbImgs.add(item);
                        } else if (item.getFieldName().contains("categoryImage")) {
                            categoryImg = item;
                        }
                    }
                }

                // Hohle den Kategorie Titel
                String categoryTitle = fields.get("categoryTitle");
                // Speichere Gallerie in der Datenbank und erhalte den generierten Prim&auml;rschl&uuml;ssel
                Object[] outParams = db.callStoredUpdateProcedure("{ call insertGallery( ?, '" + categoryTitle + "' ) }", new int[]{Types.INTEGER});
                int id = (Integer) outParams[0];

                // Verwende generierten Prim&auml;rschl&uuml;ssel zum eindeutigen Speichern von den Bildern
                for (int i = 0; i < detailImgs.size(); i++) {
                    files.write(detailImgs.get(i), "/gallery/category" + id + "/detail/" + i + ".jpeg");
                }
                for (int i = 0; i < thumbImgs.size(); i++) {
                    files.write(thumbImgs.get(i), "/gallery/category" + id + "/thumbnail/" + i + ".jpeg");
                }                
                files.write(categoryImg, "/gallery/category" + id + "/categoryImage/categoryImage.jpeg");
            }
        } catch (FileUploadException ex) {
            Logger.getLogger(GalleryService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GalleryService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "<updates><update><target>galleries_box</target><url>faces/gallery.xhtml</url></update></updates>";
    }

    /**
     * Vertauscht in der Datenbank die Reihenfolge zweier Kategorien.
     *
     * @param order1 Reihenfolge der ersten zu vertauschenden Kategorie.
     * @param order2 Reihenfolge der zweiten zu vertauschenden Kategorie.
     * @return XML Antwort zur Aktualisierung der galleries_box.
     */
    @POST
    @Path(value = "/swap/{order1}/{order2}")
    @Produces("application/xml")
    public String swapGallery(@PathParam(value = "order1") int order1, @PathParam(value = "order2") int order2) {
        // Vertausche beide Elemente miteinander auf der Datenbank.
        db.callStoredUpdateProcedure("{ call swapOrder('tc_gallery'," + order1 + "," + order2 + ") }", new int[0]);
        return "<updates><update><target>galleries_box</target><url>faces/gallery.xhtml</url></update></updates>";
    }
}
