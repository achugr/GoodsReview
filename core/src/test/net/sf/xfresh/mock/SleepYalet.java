package net.sf.xfresh.mock;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;
import org.apache.log4j.Logger;

/**
 * Date: Nov 28, 2010
 * Time: 1:45:05 AM
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public class SleepYalet implements Yalet {
    private static final Logger log = Logger.getLogger(SleepYalet.class);

    public void process(final InternalRequest req, final InternalResponse res) {
        final int sleepTime = req.getIntParameter("sleep", 300);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            log.error("ERROR", e); //ignored
        }
    }
}
