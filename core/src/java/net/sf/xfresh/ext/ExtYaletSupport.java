package net.sf.xfresh.ext;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.SaxGenerator;
import net.sf.xfresh.core.impl.DefaultYaletSupport;
import org.springframework.beans.factory.annotation.Required;
import org.xml.sax.XMLFilter;

/**
 * Date: Nov 24, 2010
 * Time: 11:14:42 PM
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public class ExtYaletSupport extends DefaultYaletSupport {

    protected String resourceBase;
    protected SaxGenerator saxGenerator;

    @Required
    public void setResourceBase(final String resourceBase) {
        this.resourceBase = resourceBase;
    }

    @Required
    public void setSaxGenerator(final SaxGenerator saxGenerator) {
        this.saxGenerator = saxGenerator;
    }

    @Override
    public XMLFilter createFilter(final InternalRequest request, final InternalResponse response) {
        return new ExtYaletFilter(singleYaletProcessor, authHandler, request, response, resourceBase, saxGenerator);
    }
}
