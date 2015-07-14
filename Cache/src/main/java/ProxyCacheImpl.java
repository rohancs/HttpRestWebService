import java.util.Properties;
import com.rohancs.tools.Cache;

public class ProxyCacheImpl extends Cache<String,Integer> {

    public ProxyCacheImpl(Properties properties) {
        super(properties);
    }

    @Override
    public Integer load(String key) {
        if(key == null) return -1;
        return key.length();
    }
}
