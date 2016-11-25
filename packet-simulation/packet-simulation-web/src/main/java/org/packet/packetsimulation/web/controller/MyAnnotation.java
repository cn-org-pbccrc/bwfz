package org.packet.packetsimulation.web.controller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.RUNTIME) 
public @interface MyAnnotation {

	public String name();
	
}
