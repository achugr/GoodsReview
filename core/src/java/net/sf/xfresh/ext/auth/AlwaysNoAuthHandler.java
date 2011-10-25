package net.sf.xfresh.ext.auth;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.xml.Xmler;
import net.sf.xfresh.ext.AuthHandler;
import org.xml.sax.ContentHandler;

/**
 * Author: Olga Bolshakova (obolshakova@yandex-team.ru)
 * Date: 02.01.11 22:04
 */
public final class AlwaysNoAuthHandler implements AuthHandler {

    public void processAuth(final InternalRequest req, final InternalResponse res, final ContentHandler handler) {
        Xmler.tag("no-auth").writeTo(handler);
    }

    public Long getUserId(final InternalRequest req) {
        return null;
    }
}
