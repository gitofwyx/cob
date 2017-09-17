/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kelan.core.session.cache;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class EhcacheSessionCache implements SessionCache, InitializingBean {

	@SuppressWarnings("unchecked")
	public Map<String, Object> getSession(String root) {
        Element e = cache.get(root);
//        return e != null ? (HashMap<String, Object>) e.getValue() : null;
        return e != null ? (HashMap<String, Object>) e.getObjectValue() : null;
    }

    public void setSession(String root, Map<String, Object> session,
            int exp) {
        cache.put(new Element(root, session));
    }

    public Object getAttribute(String root, String name) {
        Map<String, Object> session = getSession(root);
        return session != null ? session.get(name) : null;
    }

    public void setAttribute(String root, String name, Object value,
            int exp) {
        Map<String, Object> session = getSession(root);
        if (session == null) {
            session = new HashMap<String, Object>();
        }
        session.put(name, value);
        cache.put(new Element(root, session));
    }

    public void clear(String root) {
        cache.remove(root);
    }

    public boolean exist(String root) {
        return cache.isKeyInCache(root);
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(cache);
    }

    private Ehcache cache;

    public void setCache(Ehcache cache) {
        this.cache = cache;
    }

}
