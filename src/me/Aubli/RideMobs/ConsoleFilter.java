package me.Aubli.RideMobs;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class ConsoleFilter implements Filter {
	String[] filter = new String[] { "moved too quickly!" };

	public boolean isLoggable(LogRecord logRecord) {
		for (String s : filter) {
			if (logRecord.getMessage().contains(s)) {
				return false;
			}
		}
		return true;
	}
}