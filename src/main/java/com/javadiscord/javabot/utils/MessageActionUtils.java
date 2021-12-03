package com.javadiscord.javabot.utils;

import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Component;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.util.ArrayList;
import java.util.List;

public class MessageActionUtils {
	public static MessageAction addComponents(MessageAction action, List<? extends Component> components) {
		if (components.size() > 25) throw new IllegalArgumentException("Cannot add more than 25 components to a message action.");
		List<ActionRow> rows = new ArrayList<>(5);
		List<Component> rowComponents = new ArrayList<>(5);
		while (!components.isEmpty()) {
			rowComponents.add(components.remove(0));
			if (rowComponents.size() == 5) {
				rows.add(ActionRow.of(rowComponents));
				rowComponents.clear();
			}
		}
		if (!rowComponents.isEmpty()) {
			rows.add(ActionRow.of(rowComponents));
		}
		return action.setActionRows(rows);
	}
}
