package net.sf.xfresh.base;

import net.sf.xfresh.core.InternalRequest;

import java.util.List;
import java.util.Map;

/**
 * Date: 29.08.2007
 * Time: 12:48:24
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public class Request {
    private final InternalRequest internalRequest;

    public Request(final InternalRequest internalRequest) {
        this.internalRequest = internalRequest;
    }

    public Map<String, List<String>> getAllParameters() {
     return internalRequest.getAllParameters();
    }
}
