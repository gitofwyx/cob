/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kelan.core.session.cache;

import java.util.Map;

/**
 *
 * @author Administrator
 */
public interface SessionCache {

    public Object getAttribute(String root, String name);

    public void setAttribute(String root, String name, Object value,
                             int exp);

    public void clear(String root);

    public boolean exist(String root);

    public Map<String, Object> getSession(String root);

    public void setSession(String root, Map<String, Object> session,
                           int exp);
}
