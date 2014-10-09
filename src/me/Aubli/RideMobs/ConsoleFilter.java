package de.MiniDigger.RideThaMob;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class ConsoleFilter implements Filter {
	String[] filter = new String[] { "moved too quickly!" };

	public ConsoleFilter() {
	}

	public boolean isLoggable(LogRecord logRecord) {
		for (String s : filter) {
			if (logRecord.getMessage().contains(s)) {
				return false;
			}
		}
		return true;
	}
}