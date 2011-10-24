package net.sf.xfresh.jetty;

import org.apache.log4j.Logger;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.HandlerCollection;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.thread.QueuedThreadPool;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;

/**
 * Date: Oct 31, 2010
 * Time: 11:03:03 AM
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public class JettyServerInitializer implements InitializingBean {
    private static final Logger log = Logger.getLogger(JettyServerInitializer.class);
    private Server server;
    private int port;
    private int maxThreads;
    private Handler[] handlers;

    @Required
    public void setPort(final int port) {
        this.port = port;
    }

    public void setMaxThreads(final int maxThreads) {
        this.maxThreads = maxThreads;
    }

    @Required
    public void setHandlers(final Handler[] handlers) {
        this.handlers = handlers;
    }

    public void afterPropertiesSet() {
        init();
    }

    public void init() {
        final long st = System.currentTimeMillis();

        try {
            server = new Server();

            final Connector connector = new SelectChannelConnector();
            connector.setPort(port);

            final QueuedThreadPool threadPool = new QueuedThreadPool();
            threadPool.setMaxThreads(maxThreads);

            final HandlerCollection handlerCollection = new HandlerCollection();
            handlerCollection.setHandlers(handlers);

            server.setConnectors(new Connector[]{connector});
            server.setThreadPool(threadPool);
            server.setHandler(handlerCollection);

            server.start();
//            server.join();
            log.info("Server started: " + (System.currentTimeMillis() - st) + " ms");
        } catch (Exception e) {
            log.error("Could not initialize server: ", e);
        }
    }

    public int getPort() {
        return port;
    }
}
