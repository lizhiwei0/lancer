package org.lizhiwei.lancer.config;

import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lizhiwe on 7/16/2017.
 */
public class TypeHelper {

    private static final TypeHelper INSTANCE = new TypeHelper();

    private static  Map<String,Integer> types = new HashMap<String,Integer>();

    public static TypeHelper getInstance() {
        return INSTANCE;
    }

    public void loadTypes(ClassPathResourceLoader loader) {
        Yaml yaml = new Yaml();
        Map raw =  yaml.loadAs(loader.loadResource("/model_dictionary.yml"),Map.class);
        types.putAll(raw);
    }



    public static  int getIdByName(String charset) {
        return types.get(charset);
    }

    public  static void addTypes(Map<String,Integer> in) {
        types.putAll(in);
    }

    public static String getNameById(int id) {
        for (Map.Entry<String,Integer> entry : types.entrySet()) {
            if (entry.getValue().intValue() == id) {
                return entry.getKey();
            }
        }
        throw new RuntimeException("no corresponding Type for "+id);
    }
}
