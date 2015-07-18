package com.rohancs;

import javax.annotation.ManagedBean;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.lang.management.ManagementFactory;

@ManagedBean
@Path("/")
public  class httpCacheClient {
    MBeanServer mbs = null;
    ObjectName name = null;
    public httpCacheClient(){
        try {
            mbs = ManagementFactory.getPlatformMBeanServer();
            name = new ObjectName("com.rohancs:type=CacheServer");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @GET
    @Path("get/{key}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getValue(@PathParam("key") String key) {
        String value = null;
        try{
            value = (String) mbs.invoke(name,"get",new Object[] {key},new String[]{"java.lang.String"});
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if(value == null) value = "null";
        return value;
    }

    @GET
    @Path("set/{key}/{value}")
    @Produces(MediaType.TEXT_PLAIN)
    public String setValue(@PathParam("key") String key,@PathParam("value") String value) {
        try{
            mbs.invoke(name,"set",new Object[] {key,value},new String[]{"java.lang.String","java.lang.String"});
        }catch (Exception e){
            e.printStackTrace();
            return "false";
        }

        return "true";
    }

    @GET
    @Path("size")
    @Produces(MediaType.TEXT_PLAIN)
    public int getCacheSize() {
        int size = -1;
        try{
            size = (Integer) mbs.invoke(name,"size",null,null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return size;
    }
}
