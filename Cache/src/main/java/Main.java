import java.util.Properties;

/**
 * Created by ksainani on 7/13/2015.
 */
public class Main {
    public static void main(String[] args){
        //Launch Cache Here
        Properties p = new Properties();
        p.setProperty("cacheDuration","10000");
        p.setProperty("cacheSizeBytes","150000");
        p.setProperty("cacheSizeElements","10");

        ProxyCacheImpl pc = new ProxyCacheImpl(p);

        pc.put("a",5);

        System.out.println(pc.get("b"));
        System.out.println(pc.get("c"));
        System.out.println(pc.get("d"));
        System.out.println(pc.get("e"));
        System.out.println(pc.get("f"));
        System.out.println(pc.get("g"));
        System.out.println(pc.get("h"));
        System.out.println(pc.get("i"));
        System.out.println(pc.get("j"));
        System.out.println(pc.get("k"));
        System.out.println(pc.get("l"));
        System.out.println(pc.get("m"));
        System.out.println(pc.get("n"));

        System.out.println(pc.get("h"));
    }
}
