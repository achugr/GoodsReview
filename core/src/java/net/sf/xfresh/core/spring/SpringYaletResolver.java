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
package net.sf.xfresh.core.spring;

import net.sf.xfresh.core.Yalet;
import net.sf.xfresh.core.YaletResolver;
import net.sf.xfresh.core.YaletResolvingException;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Date: 21.04.2007
 * Time: 13:31:55
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public class SpringYaletResolver implements YaletResolver, ApplicationContextAware {
    private static final Logger log = Logger.getLogger(SpringYaletResolver.class);
    private static final String YALET_SUFFIX = "Yalet";

    private ApplicationContext applicationContext;

    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public Yalet findYalet(final String id) throws YaletResolvingException {
        final Object bean = applicationContext.getBean(id + YALET_SUFFIX);
        if (bean == null) {
            log.error("Can't find yalet by id: " + id);
            throw new YaletResolvingException(id);
        }
        if (!(bean instanceof Yalet)) {
            log.error("Illegal type (" + bean.getClass() + ") of bean with id: " + id);
            throw new YaletResolvingException(id);
        }
        return (Yalet) bean;
    }
}
