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
package net.sf.xfresh.core;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Date: 20.04.2007
 * Time: 18:30:53
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public interface InternalResponse {
    void redirectTo(final String path);

    void add(final Object object);

    <K, V> void addMap(final Map<K, V> map, SaxWriter<Map.Entry<K, V>> writer);

    void addWrapped(final String name, final Object object);

    List<Object> getData();

    String getRedir();

    void addError(ErrorInfo errorInfo);

    List<ErrorInfo> getErrors();

    void addCookie(String name, String value);

    void addCookie(String name, String value, int maxAge);

    void addCookie(String name, String value, int maxAge, String domain);

    void addCookie(String name, String value, int maxAge, String domain, String path);

    void addCookie(String name, String value, int maxAge, String domain, String path, boolean httpOnly);

    void setCookies(Map<String, String> cookies);

    void removeCookie(String name);

    void clear();

    void setHeader(String name, String value);

    void setHttpStatus(int statusCode);

    void setContentType(String contentType);

    String getContentType();

    void setProcessed(boolean b);

    boolean isProcessed();

    OutputStream getOutputStream() throws IOException;
}
