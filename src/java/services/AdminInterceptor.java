package services;

import beans.UserSession;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * &uuml;berpr&uuml;ft die Sitzung des Nutzers. F&uuml;hrt angeforderten Methoden nur aus, wenn
 * Nutzer autorisiert ist.
 *
 * Wird von ContentService, EventService, GalleryService, LogoService und
 * MagazineService verwendet, um nur autorisierten Nutzern den Zugriff auf diese
 * Dienste zu gew&auml;hrleisten, da sie nur f&uuml;r die Redaktionsmitglieder verf&uuml;gbar
 * sein sollen.
 *
 * @author Alexander Johr u26865 m18927
 */
public class AdminInterceptor {

    @Inject
    UserSession session;

    /**
     * Wird aufgerufen, wenn eine Methode der Klassen aufgerufen wird, die
     * diesen Interceptor verwenden
     *
     * @param ic Kontext der aufgerufenen Methode.
     * @return R&uuml;ckgabe der Aufrufmethode, null, wenn Nutzer nicht
     * autorisiert.
     * @throws Exception
     */
    @AroundInvoke
    public Object proceedWhenAdmin(InvocationContext ic) throws Exception {
        // Ist der Benutzer autorisiert? Dann Methode ausf&uuml;hren.
        if (session.isAuthorised()) {
            return ic.proceed();
        } else {
            return null;
        }
    }
}
