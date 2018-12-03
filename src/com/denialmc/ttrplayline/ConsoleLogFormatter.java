package com.denialmc.ttrplayline;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class ConsoleLogFormatter extends Formatter {

	@Override
	public String format(LogRecord logRecord) {
		return Utils.formatDate(logRecord.getMillis(), logRecord.getLevel().getName(), logRecord.getMessage());
	}
}