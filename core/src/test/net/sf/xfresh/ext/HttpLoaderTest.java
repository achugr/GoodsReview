package net.sf.xfresh.ext;

import net.sf.xfresh.jetty.AbstractJettyTest;

/**
 * Date: 12/26/10
 * Time: 22:11 PM
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public class HttpLoaderTest extends AbstractJettyTest {
    public void testTimeout() throws Throwable {
        HttpLoader httpLoader = new HttpLoader();
        final LoadedXml loadedXml = httpLoader.load("http://localhost:33333/test-timeout.xml?_ox&sleep=200", 100);
        assertTrue(loadedXml.isEmpty());
    }

    public void testNonTimeout() throws Throwable {
        HttpLoader httpLoader = new HttpLoader();
        final LoadedXml loadedXml = httpLoader.load("http://localhost:33333/test-timeout.xml?_ox&sleep=100", 300);
        assertFalse(loadedXml.isEmpty());
    }
}
