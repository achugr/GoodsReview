package net.sf.xfresh.util;

import org.apache.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Date: Oct 23, 2010
 * Time: 3:17:17 PM
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public class Starter {
    private static final Logger log = Logger.getLogger(Starter.class);

    public static void main(final String[] args) {
        log.info("App started");
        final FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(new String[]{args[0]});
    }
}
