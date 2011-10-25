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

import net.sf.xfresh.core.InternalRequest;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.TestOnly;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Date: 20.04.2007
 * Time: 18:30:02
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public class SimpleInternalRequest implements InternalRequest {
    private static final Logger log = Logger.getLogger(SimpleInternalRequest.class);

    private static final String OFF_XSLT_PARAM_NAME = "_ox";

    private final HttpServletRequest httpRequest;
    private final String realPath;
    private boolean needTransform = true;
    private Map<String, List<String>> allParams;
    private Map<String, String> cookiesMap;
    private Long userId;

    public SimpleInternalRequest(final HttpServletRequest httpRequest, final String realPath) {
        this.httpRequest = httpRequest;
        this.realPath = realPath;
        buildCookiesMap();
    }

    public String getRealPath() {
        return realPath;
    }

    @TestOnly
    public void setNeedTransform(final boolean needTransform) {
        this.needTransform = needTransform;
    }

    public boolean needTransform() {
        if (httpRequest != null) {
            needTransform = httpRequest.getParameter(OFF_XSLT_PARAM_NAME) == null;
            if (log.isDebugEnabled()) {
                log.debug("needTransform for " + realPath + " = " + needTransform);
            }
        }
        return needTransform;
    }

    public String getParameter(final String name) {
        final String value = httpRequest.getParameter(name);
        if (log.isDebugEnabled()) {
            log.debug("name = " + name);
            log.debug("value = " + value);
        }
        return value;
    }

    public String[] getParameters(final String name) {
        return httpRequest.getParameterValues(name);
    }

    public Map<String, String> getCookies() {
        return new HashMap<String, String>(cookiesMap);
    }

    public void putAttribute(final String name, final Object value) {
        httpRequest.setAttribute(name, value);
    }

    public Object getAttribute(final String name) {
        return httpRequest.getAttribute(name);
    }

    public String getCookie(final String name) {
        return cookiesMap.get(name);
    }

    private void buildCookiesMap() {
        if (httpRequest == null) {
            cookiesMap = Collections.emptyMap();
            return;
        }

        final Cookie[] cookies = httpRequest.getCookies();
        if (cookies == null) {
            cookiesMap = Collections.emptyMap();
            return;
        }

        cookiesMap = new HashMap<String, String>();
        for (final Cookie cookie : cookies) {
            cookiesMap.put(cookie.getName(), cookie.getValue());
        }
    }

    public Map<String, List<String>> getAllParameters() {
        if (allParams != null) {
            return Collections.unmodifiableMap(allParams);
        }

        allParams = new HashMap<String, List<String>>();
        final Enumeration names = httpRequest.getParameterNames();
        while (names.hasMoreElements()) {
            final String name = (String) names.nextElement();
            final List<String> values = Arrays.asList(httpRequest.getParameterValues(name));
            allParams.put(name, values);
        }
        return Collections.unmodifiableMap(allParams);
    }

    public String getRequestURL() {
        return httpRequest.getRequestURL().toString();
    }

    public String getRequestRoot() {
        final StringBuffer url = httpRequest.getRequestURL();
        url.setLength(url.length() - httpRequest.getRequestURI().length());
        url.append(httpRequest.getContextPath() == null ? "" : httpRequest.getContextPath());
        return url.toString();
    }

    public String getQueryString() {
        return httpRequest.getQueryString();
    }

    public String getHeader(final String name) {
        return httpRequest.getHeader(name);
    }

    public int getIntParameter(final String name) {
        return Integer.parseInt(getParameter(name));
    }

    public int getIntParameter(final String name, final int defaultValue) {
        try {
            return Integer.parseInt(getParameter(name));
        } catch (NumberFormatException ignored) {
            return defaultValue;
        }
    }

    public long getLongParameter(final String name) {
        return Long.parseLong(getParameter(name));
    }

    public long getLongParameter(final String name, final long defaultValue) {
        try {
            return Long.parseLong(getParameter(name));
        } catch (NumberFormatException ignored) {
            return defaultValue;
        }
    }

    public String getRemoteAddr() {
        return httpRequest.getRemoteAddr();
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
