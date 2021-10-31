package com.roadlovers.util;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatterUtil {

	private FormatterUtil() {
	}

	public static final String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
		return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
	}

	public static final String formatCurrency(Object value, String pattern) {
		DecimalFormat formatter = new DecimalFormat(pattern);

		return formatter.format(value);
	}
}
