package net.sf.xfresh.mock;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;

/**
 * Date: 21.04.2007
 * Time: 15:38:54
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public class RuStringYalet implements Yalet {
    public void process(final InternalRequest req, final InternalResponse res) {
        res.add("Тест");
    }
}
