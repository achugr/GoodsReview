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
package net.sf.xfresh.core.servlet;

import net.sf.xfresh.core.YaletProcessor;
import net.sf.xfresh.core.YaletSupport;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Date: 18.04.2007
 * Time: 20:15:05
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public class Dispatcher extends HttpServlet {
    private static final Logger log = Logger.getLogger(Dispatcher.class);

    private YaletSupport yaletSupport;
    private YaletProcessor yaletProcessor;

    @Override
    public void init(final ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        final ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(
                getServletContext());
        setApplicationContext(applicationContext);
    }

    protected void setApplicationContext(final BeanFactory applicationContext) {
        yaletSupport = (YaletSupport) applicationContext.getBean("yaletSupport");
        yaletProcessor = (YaletProcessor) applicationContext.getBean("yaletProcessor");
        yaletProcessor.setYaletSupport(yaletSupport);
    }

    @Override
    protected void service(final HttpServletRequest req,
                           final HttpServletResponse res) throws ServletException, IOException {
        if (yaletSupport == null) {
            throw new IllegalStateException("yaletSupport is null");
        }

        final String servletPath = req.getServletPath();
        yaletProcessor.process(req, res, getServletContext().getRealPath(servletPath));
    }
}
