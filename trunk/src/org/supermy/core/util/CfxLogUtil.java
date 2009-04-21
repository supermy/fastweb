package org.supermy.core.util;

import javax.annotation.PostConstruct;

import org.slf4j.bridge.SLF4JBridgeHandler;
public class CfxLogUtil {

	@PostConstruct
	public void init() {
		SLF4JBridgeHandler.install();
	}
}
