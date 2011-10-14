/*
* Copyright (c) 2007, Xfresh Project
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*     * Redistributions of source code must retain the above copyright
*       notice, this list of conditions and the following disclaimer.
*     * Redistributions in binary form must reproduce the above copyright
*       notice, this list of conditions and the following disclaimer in the
*       documentation and/or other materials provided with the distribution.
*     * Neither the name of the Xfresh Project nor the
*       names of its contributors may be used to endorse or promote products
*       derived from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED `AS IS'' AND ANY
* EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL Xfresh Project BE LIABLE FOR ANY
* DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package net.sf.xfresh.core.impl;

import net.sf.xfresh.core.*;
import net.sf.xfresh.core.sax.MapSelfSaxWriter;
import org.jetbrains.annotations.TestOnly;
import org.mortbay.jetty.HttpOnlyCookie;
import org.xml.sax.SAXException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Date: 20.04.2007
 * Time: 18:31:03
 * <p/>
 * Reused from different yalets.
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public class SimpleInternalResponse implements InternalResponse {
    private final HttpServletResponse httpResponse;
    private String redir;
    private final List<Object> data = new ArrayList<Object>();
    private final List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
    private OutputStream outputStream;
    private boolean processed = false;

    public SimpleInternalResponse(final HttpServletResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    public void redirectTo(final String path) {
        if (redir != null) {
            throw new IllegalStateException("Already redirected");
        }
        redir = path;
    }

    public void addWrapped(final String name, final Object object) {
        data.add(new SelfSaxWriter() {
            public void writeTo(final SaxHandler saxHandler) throws SAXException {
                saxHandler.writeAny(name, object);
            }
        });
    }

    public void add(final Object object) {
        data.add(object);
    }

    public <K, V> void addMap(final Map<K, V> map, final SaxWriter<Map.Entry<K, V>> writer) {
        data.add(new MapSelfSaxWriter<K, V>(map, writer));
    }

    public List<Object> getData() {
        return Collections.unmodifiableList(data);
    }

    public List<ErrorInfo> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public String getRedir() {
        return redir;
    }

    @TestOnly
    public void setOutputStream(final OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setHeader(final String name, final String value) {
        httpResponse.setHeader(name, value);
    }

    public OutputStream getOutputStream() throws IOException {
        if (outputStream == null && httpResponse != null) {
            outputStream = httpResponse.getOutputStream();
        }
        return outputStream;
    }

    public void addError(final ErrorInfo errorInfo) {
        errors.add(errorInfo);
    }

    public void addCookie(final String name, final String value) {
        addCookie(name, value, -1);
    }

    public void addCookie(final String name, final String value, final int maxAge) {
        addCookie(name, value, maxAge, "");
    }

    public void addCookie(final String name, final String value, final int maxAge, final String domain) {
        addCookie(name, value, maxAge, domain, "");
    }

    public void addCookie(final String name, final String value, final int maxAge, final String domain, final String path) {
        addCookie(name, value, maxAge, domain, path, false);
    }

    public void addCookie(final String name, final String value, final int maxAge, final String domain,
                          final String path, final boolean httpOnly) {
        final Cookie cookie = httpOnly
                ? new HttpOnlyCookie(name, value) //hard dependency on jetty 
                : new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setDomain(domain);
        cookie.setPath(path);
        httpResponse.addCookie(cookie);
    }

    public void setCookies(final Map<String, String> cookies) {
        for (final Map.Entry<String, String> cookie : cookies.entrySet()) {
            httpResponse.addCookie(new Cookie(cookie.getKey(), cookie.getValue()));
        }
    }

    public void removeCookie(final String name) {
        final Cookie cookie = new Cookie(name, "deleted");
        cookie.setMaxAge(0);
        httpResponse.addCookie(cookie);
    }

    public void clear() {
        data.clear();
        errors.clear();
    }

    public void setHttpStatus(final int statusCode) {
        httpResponse.setStatus(statusCode);
    }

    public void setContentType(final String contentType) {
        if (httpResponse != null) {
            httpResponse.setContentType(contentType);
        }
    }

    public String getContentType() {
        return httpResponse == null ? null : httpResponse.getContentType();
    }

    public void setProcessed(boolean b) {
        this.processed = b;
    }

    public boolean isProcessed() {
        return processed;
    }
}
