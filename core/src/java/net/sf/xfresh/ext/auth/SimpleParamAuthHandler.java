package net.sf.xfresh.ext.auth;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.xml.Xmler;
import net.sf.xfresh.ext.AuthHandler;
import org.xml.sax.ContentHandler;

/**
 * @author: Vladislav Dolbilov (darl@yandex-team.ru)
 * <p/>
 * Date: 5/1/11 6:43 PM
 */
public class SimpleParamAuthHandler implements AuthHandler {
    private static final String PARAM_USER_ID = "__user_id";

    public void processAuth(final InternalRequest req, final InternalResponse res, final ContentHandler handler) {
        final String userId = req.getParameter(PARAM_USER_ID);
        final Xmler.Tag authTag = userId == null
                ? Xmler.tag("no-auth")
                : Xmler.tag("auth", Xmler.tag("user-id", userId));
        authTag.writeTo(handler);
    }

    public Long getUserId(final InternalRequest req) {
        final String userId = req.getParameter(PARAM_USER_ID);
        return userId == null
                ? null
                : Long.valueOf(userId);
    }
}
