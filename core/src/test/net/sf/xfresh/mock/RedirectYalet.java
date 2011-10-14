package net.sf.xfresh.mock;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;

/**
 * Date: Nov 27, 2010
 * Time: 1:13:01 PM
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public class RedirectYalet implements Yalet {
    public void process(final InternalRequest req, final InternalResponse res) {
//        res.redirectTo("http://localhost:33333/test-ru.xml");
        res.redirectTo("test-ru.xml");
    }
}
