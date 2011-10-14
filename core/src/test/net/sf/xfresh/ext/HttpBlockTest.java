package net.sf.xfresh.ext;

import net.sf.xfresh.jetty.AbstractJettyTest;
import net.sf.xfresh.jetty.JettyServerInitializer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import java.io.InputStreamReader;

import static org.springframework.util.FileCopyUtils.copyToString;

/**
 * Date: Oct 31, 2010
 * Time: 11:18:11 AM
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public class HttpBlockTest extends AbstractJettyTest {

    public HttpBlockTest() {
        super();
    }

    public void testHttp() throws Throwable {
        final HttpResponse response = httpClient.execute(buildRequest("test-http.xml"));
        assertEquals(200, response.getStatusLine().getStatusCode());
        final String content = copyToString(new InputStreamReader(response.getEntity().getContent()));
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<page xmlns:x=\"http://xfresh.sf.net/ext\">\n" +
                        "    <page><a>тест</a><data id=\"addTestInfo\"/></page>\n" +
                        "</page>",
                content);
    }
}

