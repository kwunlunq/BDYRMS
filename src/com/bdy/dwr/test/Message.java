package com.bdy.dwr.test;

public class Message {
	private long id = System.currentTimeMillis();
	private String text;

	public Message(String newtext) {
		text = newtext;
		if (text.length() > 256) {
			text = text.substring(0, 256);
		}
		text = text.replace('<', '[');
		text = text.replace('&', '_');
	}

	public long getId() {
		return id;
	}

	public String getText() {
		return text;
	}
}