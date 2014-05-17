package com.bdy.dwr.test;

import java.util.LinkedList;
import java.util.List;

public class chat {
	private static LinkedList<Message> messages = new LinkedList<Message>();

	public List addMessage(String text) {
		if (text != null && text.trim().length() > 0) {
			messages.addFirst(new Message(text));
			while (messages.size() > 10) {
				messages.removeLast();
			}
		}

		return messages;
	}

	public List getMessages() {
		return messages;
	}
}
