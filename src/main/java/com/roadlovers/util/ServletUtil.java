package com.roadlovers.util;

public class ServletUtil {

    private ServletUtil() {
    }

    public static final String getURI(javax.servlet.http.HttpServletRequest req) {
        String context = req.getContextPath();
        String uri = req.getRequestURI();

        return uri.substring(context.length());
    }
}
