package org.lizhiwei.lancer.config;

import org.yaml.snakeyaml.Yaml;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lizhiwe on 7/16/2017.
 */
public class CharsetHelper {

    private static  Map<String,Integer> charsets = new HashMap<String,Integer>();

    static {
        charsets.put("UTF-8",1);
        charsets.put("GBK",2);
        charsets.put("GB2312",3);
    }

    private static final CharsetHelper INSTANCE = new CharsetHelper();

    public static CharsetHelper getInstance() {
        return INSTANCE;
    }

    public void load(ClassPathResourceLoader loader) {
        Yaml yaml = new Yaml();
        Map raw =  yaml.loadAs(loader.loadResource("/charset_dictionary.yml"),Map.class);
        charsets.putAll(raw);
    }


    public static  int getIdByName(String charset) {
        return charsets.get(charset);
    }

    public  static void addCharsets(Map<String,Integer> in) {
        charsets.putAll(in);
    }

    public static String getNameById(int id) {
        for (Map.Entry<String,Integer> entry : charsets.entrySet()) {
            if (entry.getValue().intValue() == id) {
                return entry.getKey();
            }
        }
        throw new RuntimeException("no corresponding Charset for "+id);
    }
}
