package com.bdy.dwr.test;

import java.util.ResourceBundle;

public class book {
	private ResourceBundle resource;

	public book() {
		resource = ResourceBundle.getBundle("book");
	}

	public String getDescription(String key) {
		return resource.getString(key);
	}
}
