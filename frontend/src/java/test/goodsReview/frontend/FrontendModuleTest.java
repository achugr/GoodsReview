package test.goodsReview.frontend;

import net.sf.xfresh.core.ErrorInfo;
import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.SaxWriter;
import net.sf.xfresh.jetty.JettyServerInitializer;
import org.apache.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import ru.goodsReview.frontend.yalet.GetReviewOnProductYalet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Artemij
 * Date: 18.10.11
 * Time: 3:00
 * To change this template use File | Settings | File Templates.
 */
public class FrontendModuleTest {
    private static final Logger log = Logger.getLogger(JettyServerInitializer.class);

    public static void main(String [] args){
        InternalRequest req=new InternalRequest() {
            public String getRealPath() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public boolean needTransform() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getParameter(String s) {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public String[] getParameters(String s) {
                return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
            }

            public void putAttribute(String s, Object o) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public Object getAttribute(String s) {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public Map<String, String> getCookies() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public Map<String, List<String>> getAllParameters() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getRequestURL() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getRequestRoot() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getQueryString() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public int getIntParameter(String s) {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public int getIntParameter(String s, int i) {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public long getLongParameter(String s) {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public long getLongParameter(String s, long l) {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getRemoteAddr() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getHeader(String s) {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getCookie(String s) {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public Long getUserId() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        };
        InternalResponse resp = new InternalResponse() {
            public void redirectTo(String s) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void add(Object o) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public <K, V> void addMap(Map<K, V> kvMap, SaxWriter<Map.Entry<K, V>> entrySaxWriter) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void addWrapped(String s, Object o) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public List<Object> getData() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getRedir() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public void addError(ErrorInfo errorInfo) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public List<ErrorInfo> getErrors() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public void addCookie(String s, String s1) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void addCookie(String s, String s1, int i) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void addCookie(String s, String s1, int i, String s2) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void addCookie(String s, String s1, int i, String s2, String s3) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void addCookie(String s, String s1, int i, String s2, String s3, boolean b) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void setCookies(Map<String, String> stringStringMap) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void removeCookie(String s) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void clear() {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void setHeader(String s, String s1) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void setHttpStatus(int i) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void setContentType(String s) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getContentType() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public void setProcessed(boolean b) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public boolean isProcessed() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public OutputStream getOutputStream() throws IOException {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        };
        final FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("beans.xml");
        log.info("Server started: " + (System.currentTimeMillis() ) + " ms");

    }

}
