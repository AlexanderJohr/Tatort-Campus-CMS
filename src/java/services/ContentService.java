package services;

import beans.DBConnection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Stellt Dienst zum Ver&auml;ndern der Seiteninhalte bereit.
 *
 * @author Alexander Johr u26865 m18927
 */
@Path("content")
@Stateless
@Interceptors(AdminInterceptor.class)
public class ContentService {

    @EJB
    private DBConnection db;

    /**
     * /F0120/ Ver&auml;ndert Seiteninhalte, die durch einen Multipart-Request
     * gesendet wurden.
     *
     * @param request Der Multipart-Request welcher die zu aktualisierenden
     * Seiteninhalte enth&auml;lt.
     * @return Leere XML Antwort.
     */
    @POST
    @Path(value = "/update")
    @Produces("application/xml")
    public String updateContent(@Context HttpServletRequest request) {
        try {
            if (ServletFileUpload.isMultipartContent(request)) {
                // Felder des Multipart-Request auslesen.
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> items = upload.parseRequest(request);

                // Jeden Seiteninhalt in der Datenbank aktualisieren.
                for (FileItem item : items) {
                    if (item.isFormField()) {
                        String htmlID = item.getFieldName();
                        String content = item.getString("UTF-8");
                        db.callStoredUpdateProcedure("{ call insertOrUpdateContent('" + htmlID + "', '" + content + "') }", new int[0]);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MagazineService.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Die gesamte Seite muss neu geladen werden. Daher wird kein Seitenbereich angegeben.
        return "<updates><update><target></target><url></url></update></updates>";

    }
}
