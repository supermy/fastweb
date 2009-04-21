package org.supermy.cxf.service;

public class Man {
	private String name;

	public Man() {
		super();
	}

	public Man(String n) {
		name=n;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
