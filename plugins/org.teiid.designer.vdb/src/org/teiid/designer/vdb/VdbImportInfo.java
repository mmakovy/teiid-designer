/*
 * JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.teiid.designer.vdb;


/**
 * @author blafond
 *
 */
public class VdbImportInfo {

	String name;
	String version;
	
	/**
	 * @param name
	 * @param version
	 */
	public VdbImportInfo(String name, String version) {
		super();
		this.name = name;
		this.version = version;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public String getVersion() {
		return version;
	}

	
}
