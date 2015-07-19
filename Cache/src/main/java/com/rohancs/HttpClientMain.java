package com.rohancs;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class HttpClientMain {
    final static int HTTP_PORT = 8008;

    public static void main(String[] args) throws Exception{
        //launching Http Interface @ HTTP_PORT
        Server server = new Server(HTTP_PORT);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        ServletHolder cacheServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/Cache/*");
        cacheServlet.setInitOrder(0);
        cacheServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                HttpCacheClient.class.getCanonicalName());
        server.setHandler(context);
        try {
            server.start();
            server.join();
        } finally {
            server.destroy();
        }
    }
}
