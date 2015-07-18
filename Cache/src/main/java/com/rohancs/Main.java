package com.rohancs;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.util.Properties;

public class Main {
    final static String CACHE_PROPERTIES = System.getProperty("user.dir")+"\\Cache\\src\\main\\resources\\cache.properties";
    final static int HTTP_PORT = 8008;

    static Properties  getCacheProperties(){
        Properties prop = new Properties();

        try{
            InputStream inputStream = new FileInputStream(CACHE_PROPERTIES);
            prop.load(inputStream);
        } catch (Exception e){
            e.printStackTrace();
        }

        return prop;
    }

    public static void main(String[] args) throws Exception {

        //Instantiate Cache
        CacheServer cacheServer = new CacheServer(getCacheProperties());

        //Adding Cache to MBean Server
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("com.rohancs:type=CacheServer");
        mbs.registerMBean(cacheServer, name);

        //launching Http Interface @ HTTP_PORT
        Server server = new Server(HTTP_PORT);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        ServletHolder cacheServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/Cache/*");
        cacheServlet.setInitOrder(0);
        cacheServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                httpCacheClient.class.getCanonicalName());
        server.setHandler(context);
        try {
            server.start();
            server.join();
        } finally {
            server.destroy();
        }
    }
}
