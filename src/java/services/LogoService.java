package services;

import beans.DBConnection;
import beans.FileSystemUtil;
import java.io.IOException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
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

/**
 * Stellt Dienste zum verwalten der Sponsorenlogos bereit
 *
 * @author Alexander Johr u26865 m18927
 */
@Path("logo")
@Stateless
@Interceptors(AdminInterceptor.class)
public class LogoService {

    @EJB
    private DBConnection db;
    @EJB
    private FileSystemUtil files;

    /**
     * Entfernt das Logo der gesendeten ID.
     *
     * @param id Die ID, des zu entfernenden Logos.
     * @return XML Antwort zur Aktualisierung der logos_box.
     * @throws IOException
     */
    @POST
    @Path(value = "/delete/{id}")
    @Produces("application/xml")
    public String deleteLogo(@PathParam(value = "id") String id) throws IOException {
        files.delete("/logos/" + id + ".jpeg");
        db.callStoredUpdateProcedure("{ call deleteLogo(" + id + ") }", new int[0]);
        return "<updates><update><target>logos_box</target>"
                + "<url>faces/logos.xhtml</url></update></updates>";
    }

    /**
     * Setzt ein neues Logo in der Datenbank ein.
     *
     * @param request Die HTTP-Anfrage, mit den Logos und Links.
     *
     * @return XML Antwort zur Aktualisierung der logos_box.
     */
    @POST
    @Path(value = "/upload")
    @Produces("application/xml")
    public String insertLogo(@Context HttpServletRequest request) throws IOException {

        files.createDir("/logos/");

        try {
            if (ServletFileUpload.isMultipartContent(request)) {
                // Liest Dateien und Felder aus.
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> items = upload.parseRequest(request);
                // Speichert Feld-Inhalt unter Feld-Name ab
                Map<String, String> fields = new HashMap<String, String>();
                // Speichert Logo unter Feld-Name ab
                Map<String, FileItem> logoImgs = new HashMap<String, FileItem>();

                
                for (FileItem item : items) {
                    if (item.isFormField()) {
                        // Lese alle Formfelder
                        if (item.getFieldName().contains("link")) {
                            fields.put(item.getFieldName(), item.getString("UTF-8"));
                        }
                    } else {
                        // Lese alle Bilder
                        if (item.getFieldName().contains("logo")) {
                            logoImgs.put(item.getFieldName(), item);
                        }
                    }
                }

                
                for (int i = 0; i < logoImgs.size(); i++) {
                    // Hole Logo mit entsprechendem Link
                    String link = fields.get("link" + i);
                    FileItem logo = logoImgs.get("logo" + i);

                    // Speichere Logo der Datenbank und erhalte den generierten Prim&auml;rschl&uuml;ssel
                    Object[] outParams = db.callStoredUpdateProcedure("{ call insertLogo( ?, '" + link + "' ) }", new int[]{Types.INTEGER});
                    int id = (Integer) outParams[0];

                    // Verwende generierten Prim&auml;rschl&uuml;ssel zum eindeutigen Speichern des Logos
                    files.write(logo, "/logos/" + id + ".jpeg");
                }
            }
        } catch (FileUploadException ex) {
            Logger.getLogger(GalleryService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "<updates><update><target>logos_box</target><url>faces/logos.xhtml</url></update></updates>";
    }

    /**
     * Vertauscht in der Datenbank die Reihenfolge zweier Logos.
     *
     * @param order1 Reihenfolge des ersten zu vertauschenden Logos.
     * @param order2 Reihenfolge des zweiten zu vertauschenden Logos.
     * @return XML Antwort zur Aktualisierung der logos_box.
     */
    @POST
    @Path(value = "/swap/{order1}/{order2}")
    @Produces("application/xml")
    public String swapLogo(@PathParam(value = "order1") int order1, @PathParam(value = "order2") int order2) {
        // Vertausche beide Elemente miteinander auf der Datenbank.
        db.callStoredUpdateProcedure("{ call swapOrder('tc_logo'," + order1 + "," + order2 + ") }", new int[0]);
        return "<updates><update><target>logos_box</target><url>faces/logos.xhtml</url></update></updates>";
    }
}
