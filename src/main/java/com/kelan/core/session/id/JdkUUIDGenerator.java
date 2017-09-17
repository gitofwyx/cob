/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kelan.core.session.id;

import org.apache.commons.lang.StringUtils;

import java.util.UUID;

/**
 *
 * @author Administrator
 */
public class JdkUUIDGenerator implements SessionIdGenerator {
	public String get() {
		return StringUtils.remove(UUID.randomUUID().toString(), '-');
	}

//	public static void main(String[] args) {
//		UUID.randomUUID();
//		long time = System.currentTimeMillis();
//		for (int i = 0; i < 100000; i++) {
//			UUID.randomUUID();
//		}
//		time = System.currentTimeMillis() - time;
//	}
    
}
