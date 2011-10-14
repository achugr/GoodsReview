package net.sf.xfresh.jetty;

import net.sf.xfresh.core.YaletProcessor;
import org.apache.log4j.Logger;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.handler.AbstractHandler;
import org.springframework.beans.factory.annotation.Required;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Date: Nov 5, 2010
 * Time: 5:50:54 PM
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public class YaletXmlPageHandler extends AbstractHandler {
    private static final Logger log = Logger.getLogger(YaletXmlPageHandler.class);

    private YaletProcessor yaletProcessor;
    private String resourceBase;

    @Required
    public void setYaletProcessor(final YaletProcessor yaletProcessor) {
        this.yaletProcessor = yaletProcessor;
    }

    @Required
    public void setResourceBase(final String resourceBase) {
        this.resourceBase = resourceBase;
    }

    public void handle(final String target,
                       final HttpServletRequest httpServletRequest,
                       final HttpServletResponse httpServletResponse,
                       final int i) throws IOException, ServletException {
        Request baseRequest = Request.getRequest(httpServletRequest);
        if (baseRequest.isHandled()) {
            return;
        }
        if (!target.endsWith(".xml")) {
            return;
        }
        final String path = resourceBase + target;
        if (log.isDebugEnabled()) {
            log.debug("handle url => {" + path + "}");
        }
        yaletProcessor.process(httpServletRequest, httpServletResponse, path);
        baseRequest.setHandled(true);
    }
}
