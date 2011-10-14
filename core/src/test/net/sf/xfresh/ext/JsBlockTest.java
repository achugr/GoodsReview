package net.sf.xfresh.ext;

import net.sf.xfresh.jetty.AbstractJettyTest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.InputStreamReader;

import static org.springframework.util.FileCopyUtils.copyToString;

/**
 * Date: Oct 31, 2010
 * Time: 11:18:11 AM
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public class JsBlockTest extends AbstractJettyTest {
    private static final String TEST_JS_0_RESULT = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<page xmlns:x=\"http://xfresh.sf.net/ext\">\n" +
            "    <out-example>\n" +
            "        1\n" +
            "    </out-example>\n" +
            "    \n" +
            "    <out-example>\n" +
            "        1001.0\n" +
            "    </out-example>\n" +
            "    <page><a>тест</a><data id=\"addTestInfo\"/></page>\n" +
            "</page>";
    private static final String TEST_JS_3_RESULT = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<page xmlns:x=\"http://xfresh.sf.net/ext\">\n" +
            "    \n" +
            "    1\n" +
            "\n" +
            "это кусок ноды:\n" +
            "<out-example>\n" +
            "        1001.0\n" +
            "    </out-example>\n" +
            "\n" +
            "это загруженный документ:\n" +
            "<page xmlns:x=\"http://xfresh.sf.net/ext\">\n" +
            "    <out-example>\n" +
            "        1\n" +
            "    </out-example>\n" +
            "    \n" +
            "    <out-example>\n" +
            "        1001.0\n" +
            "    </out-example>\n" +
            "    <page><a>тест</a><data id=\"addTestInfo\"/></page>\n" +
            "</page>\n" +
            "&lt;a&gt;а так не работает :) -- теги эскейпятся&lt;/a&gt;\n" +
            "</page>";
    private static final String TEST_JS_3_XSLT_RESULT = "<?xml version=\"1.0\" encoding=\"utf-8\"?><root>" +
            "<?xml-stylesheet type=\"text/xsl\" href=\"test-js-3.xsl\"?><page xmlns:x=\"http://xfresh.sf.net/ext\">\n" +
            "    <!--<x:js src=\"common-functions.js\"/>-->\n" +
            "    1\n" +
            "\n" +
            "это кусок ноды:\n" +
            "<out-example>\n" +
            "        1001.0\n" +
            "    </out-example>\n" +
            "\n" +
            "это загруженный документ:\n" +
            "<page>\n" +
            "    <out-example>\n" +
            "        1\n" +
            "    </out-example>\n" +
            "    \n" +
            "    <out-example>\n" +
            "        1001.0\n" +
            "    </out-example>\n" +
            "    <page><a>тест</a><data id=\"addTestInfo\"/></page>\n" +
            "</page>\n" +
            "&lt;a&gt;а так не работает :) -- теги эскейпятся&lt;/a&gt;\n" +
            "</page><test>8</test></root>";

    public JsBlockTest() {
        super();
    }

    public void testJs0() throws Throwable {
        final HttpResponse response = httpClient.execute(buildRequest("test-js-0.xml"));
        assertEquals(200, response.getStatusLine().getStatusCode());
        final String content = copyToString(new InputStreamReader(response.getEntity().getContent()));
        assertEquals(
                TEST_JS_0_RESULT,
                content);
    }

    public void testJsoWithParam() throws Throwable {
        final HttpResponse response = httpClient.execute(buildRequest("test-js-0.xml?param_1=1234567890"));
        assertEquals(200, response.getStatusLine().getStatusCode());
        final String content = copyToString(new InputStreamReader(response.getEntity().getContent()));
        assertTrue(content.contains("1234567890"));
    }

    public void testPerf() throws Throwable {
        if (!"net.sf.saxon.TransformerFactoryImpl".equals(System.getProperty("javax.xml.transform.TransformerFactory"))) {
            return;
        }
        final long st = System.currentTimeMillis();
        final int limit = 10;
//        final int limit = 1000;
        for (int i = 0; i < limit; i++) {
            final HttpResponse response = httpClient.execute(buildRequest("test-js-1.xml"));
            assertEquals(200, response.getStatusLine().getStatusCode());
            final String content = copyToString(new InputStreamReader(response.getEntity().getContent()));
            assertEquals("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><title>Тест</title></head><body><h1>Проверка211</h1></body></html>", content);
        }
        System.out.println("Processing time is: " + (System.currentTimeMillis() - st));
    }

    public void testJsSrcAndWriter() throws Throwable {
        final HttpResponse response = httpClient.execute(buildRequest("test-js-3.xml?_ox"));
        assertEquals(200, response.getStatusLine().getStatusCode());
        final String content = copyToString(new InputStreamReader(response.getEntity().getContent()));
        assertEquals(
                TEST_JS_3_RESULT,
                content);
    }

    public void testJsSrcAndWriterAndXslt() throws Throwable {
        final HttpResponse response = httpClient.execute(buildRequest("test-js-3.xml"));
        assertEquals(200, response.getStatusLine().getStatusCode());
        final String content = copyToString(new InputStreamReader(response.getEntity().getContent()));
        assertEquals(
                TEST_JS_3_XSLT_RESULT,
                content);
    }

    public void testHeaders() throws Throwable {
        final HttpGet httpGet = buildRequest("test-header.xml?_ox");
        httpGet.setHeader("test", "111");
        final HttpResponse response = httpClient.execute(httpGet);
        assertEquals(200, response.getStatusLine().getStatusCode());
        final String content = copyToString(new InputStreamReader(response.getEntity().getContent()));
        assertTrue(content.contains("111"));
        assertEquals("-1", response.getHeaders("test")[0].getValue());
    }

    public void testCookie() throws Throwable {
        final HttpGet httpGet = buildRequest("test-cookie.xml?_ox");
        httpGet.setHeader("Cookie", "test=111");
        final HttpResponse response = httpClient.execute(httpGet);
        assertEquals(200, response.getStatusLine().getStatusCode());
        final String content = copyToString(new InputStreamReader(response.getEntity().getContent()));
        assertTrue(content.contains("111"));
        assertEquals("test=-1", response.getHeaders("Set-Cookie")[0].getValue());
    }
}

