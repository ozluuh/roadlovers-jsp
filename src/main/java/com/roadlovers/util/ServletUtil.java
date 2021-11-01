package com.roadlovers.util;

import java.io.IOException;

public class ServletUtil {

	private ServletUtil() {
	}

	public static final String getURI(javax.servlet.http.HttpServletRequest req) {
		String context = req.getContextPath();
		String uri = req.getRequestURI();

		return uri.substring(context.length());
	}

	public static void addSessionAttribute(
			javax.servlet.http.HttpServletRequest req,
			java.lang.String name,
			java.lang.Object value
	) {
		javax.servlet.http.HttpSession session = req.getSession(false);
		session.setAttribute(name, value);
	}

	public static void sendRedirectTo(
			javax.servlet.http.HttpServletRequest req,
			javax.servlet.http.HttpServletResponse resp,
			java.lang.String path
	) throws IOException {
		resp.sendRedirect(req.getContextPath() + path);
	}

	public static void addRequestAttribute(
			javax.servlet.http.HttpServletRequest req,
			java.lang.String name,
			java.lang.Object value
	) {
		req.setAttribute(name, value);
	}
}
