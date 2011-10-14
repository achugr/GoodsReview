package net.sf.xfresh.jetty;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * Date: Nov 24, 2010
 * Time: 10:08:49 PM
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public abstract class AbstractJettyTest extends AbstractDependencyInjectionSpringContextTests {
    protected HttpClient httpClient;
    private JettyServerInitializer serverInitializer;
    private int port;

    public AbstractJettyTest() {
        super();
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[]{
                "classpath:test-app-context.xml"
        };
    }

    @Required
    public void setServerInitializer(final JettyServerInitializer serverInitializer) {
        System.out.println("JettyServerInitializerTest.setServerInitializer");
        this.serverInitializer = serverInitializer;
    }

    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        httpClient = new DefaultHttpClient();
        port = serverInitializer.getPort();
    }

    protected HttpGet buildRequest(final String name) {
        final HttpGet httpGet = new HttpGet("http://localhost:"
                + port + "/" +
                name);
        return httpGet;
    }
}
