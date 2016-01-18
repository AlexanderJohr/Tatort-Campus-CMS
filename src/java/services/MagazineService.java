package services;

import beans.DBConnection;
import beans.FileSystemUtil;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Types;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
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
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFImageWriter;

/**
 * Stellt Dienste zum verwalten der Magazine bereit
 *
 * @author Alexander Johr u26865 m18927
 */
@Path("magazine")
@Stateless
@Interceptors(AdminInterceptor.class)
public class MagazineService {

    @EJB
    private DBConnection db;
    @EJB
    private FileSystemUtil files;

    /**
     * Entfernt die Ausgabe der gesendeten ID.
     *
     * @param id Die ID, der zu entfernenden Ausgabe.
     * @return XML Antwort zur Aktualisierung der welcome_box und magazines_box.
     * @throws IOException
     */
    @POST
    @Path(value = "/delete/{id}")
    @Produces("application/xml")
    public String deleteMagazine(@PathParam(value = "id") String id) throws IOException {
        // Entferne PDF und Vorschaubild der zu entfernenden Ausgabe
        files.delete("/magazine/" + id + "1.jpg");
        files.delete("/magazine/" + id + "1.pdf");
        // Entferne Ausgabe aus der Datenbank
        db.callStoredUpdateProcedure("{ call deleteMagazine(" + id + ") }", new int[0]);
        return "<updates><update><target>welcome_box</target><url>faces/welcome.xhtml</url></update>"
                + "<update><target>magazines_box</target><url>faces/magazines.xhtml</url></update></updates>";
    }

    /**
     * Setzt eine neue Ausgabe in der Datenbank ein.
     *
     * @param request Die HTTP-Anfrage, mit der PDF.
     *
     * @return XML Antwort zur Aktualisierung der welcome_box und magazines_box.
     */
    @POST
    @Path(value = "/upload")
    @Produces("application/xml")
    public String uploadMagazine(@Context HttpServletRequest request) {
        try {

            if (ServletFileUpload.isMultipartContent(request)) {
                // Liest Dateien und Felder aus.                
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> items = upload.parseRequest(request);


                for (FileItem item : items) {
                    if (!item.isFormField() && item.getSize() > 0) {
                        // Speichere Gallerie in der Datenbank und erhalte den generierten Prim&auml;rschl&uuml;ssel
                        Object[] outParams = db.callStoredUpdateProcedure("{ call insertMagazine( ? ) }", new int[]{Types.INTEGER});
                        int id = (Integer) outParams[0];
                        // Verwende generierten Prim&auml;rschl&uuml;ssel zum eindeutigen Speichern von den Bildern
                        File writtenPDF = files.write(item, "/magazine/" + id + "1.pdf");

                        // Verwende Apache PDFBox zum Speichern der ersten Seite der PDF als Bild
                        PDDocument document = PDDocument.load(writtenPDF);
                        PDFImageWriter imageWriter = new PDFImageWriter();
                        imageWriter.writeImage(document, "jpg", "", 1, 1, files.getAbsolutePath("/magazine/" + id), BufferedImage.TYPE_INT_RGB, 100);
                        document.close();
                    }
                }                
            }
        } catch (FileUploadException ex) {
            Logger.getLogger(MagazineService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MagazineService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "<updates><update><target>welcome_box</target><url>faces/welcome.xhtml</url></update>"
                + "<update><target>magazines_box</target><url>faces/magazines.xhtml</url></update></updates>";
    }

    /**
     * Vertauscht in der Datenbank die Reihenfolge zweier Ausgaben.
     *
     * @param order1 Reihenfolge der ersten zu vertauschenden Ausgabe.
     * @param order2 Reihenfolge der zweiten zu vertauschenden Ausgabe.
     * @return XML Antwort zur Aktualisierung der welcome_box und magazines_box.
     */
    @POST
    @Path(value = "/swap/{order1}/{order2}")
    @Produces("application/xml")
    public String swapMagazine(@PathParam(value = "order1") int order1, @PathParam(value = "order2") int order2) {
        // Vertausche beide Elemente miteinander auf der Datenbank.
        db.callStoredUpdateProcedure("{ call swapOrder('tc_magazine'," + order1 + "," + order2 + ") }", new int[0]);
        return "<updates><update><target>welcome_box</target><url>faces/welcome.xhtml</url></update>"
                + "<update><target>magazines_box</target><url>faces/magazines.xhtml</url></update></updates>";
    }
}
