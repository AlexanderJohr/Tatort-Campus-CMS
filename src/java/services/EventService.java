package services;

import beans.DBConnection;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;

/**
 * Stellt Dienste zum verwalten der Events bereit.
 *
 * @author Alexander Johr u26865 m18927
 */
@Path("event")
@Stateless
@Interceptors(AdminInterceptor.class)
public class EventService {

    @EJB
    private DBConnection db;

    /**
     * Entfernt das Event der gesendeten ID.
     *
     * @param id Die ID, des zu entfernenden Events.
     * @return XML Antwort zur Aktualisierung der events_box.
     * @throws IOException
     */
    @POST
    @Path(value = "/delete/{id}")
    @Produces("application/xml")
    public String deleteEvent(@PathParam(value = "id") String id) {
        // Entferner Event aus der Datenbank
        db.callStoredUpdateProcedure("{ call deleteEvent(" + id + ") }", new int[0]);
        return "<updates><update><target>events_box</target><url>faces/events.xhtml</url></update></updates>";
    }

    /**
     * Setzt ein neues Event in der Datenbank ein.
     *
     * @return XML Antwort zur Aktualisierung der events_box.
     */
    @POST
    @Path(value = "/insert")
    @Produces("application/xml")
    public String insertEvent() {
        // Trage Event in Datenbank ein
        db.callStoredUpdateProcedure("{ call insertEvent() }", new int[0]);
        return "<updates><update><target>events_box</target><url>faces/events.xhtml</url></update></updates>";
    }

    /**
     * Vertauscht in der Datenbank die Reihenfolge zweier Events.
     * @param order1 Reihenfolge des ersten zu vertauschenden Events.
     * @param order2 Reihenfolge des zweiten zu vertauschenden Events.
     * @return XML Antwort zur Aktualisierung der events_box.
     */
    @POST
    @Path(value = "/swap/{order1}/{order2}")
    @Produces("application/xml")
    public String swapEvent(@PathParam(value = "order1") int order1, @PathParam(value = "order2") int order2) {
        // Vertausche beide Elemente miteinander auf der Datenbank.
        db.callStoredUpdateProcedure("{ call swapOrder('tc_event'," + order1 + "," + order2 + ") }", new int[0]);
        return "<updates><update><target>events_box</target><url>faces/events.xhtml</url></update></updates>";
    }
}
