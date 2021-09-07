package com.niaobulashi.collection.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @version V1.0
 * @Description: Map测试类
 * @author: hulang
 * @date: 2021/09/01
 * @Copyright: 长安国际信托股份有限公司 http://www.caitc.cn
 */
public class MapTest {
    
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("1", "1");
        map.put("2", "2");
        map.put("3", "3");
        map.put("3", "5");
        map.putIfAbsent("3", "4");
        map.replace("3", "223");
        map.put("4", "4");
        map.put("6", null);
        map.put(null, "11111");
        System.out.println(map.get(null));
        System.out.println(map.getOrDefault("3", "123123"));
        System.out.println("map.containsKey:" + map.containsKey("3"));
        System.out.println("map.containsValue:" + map.containsValue("3"));
        Set<String> set = map.keySet();
        System.out.println(map.containsKey("1"));
        set.remove("1");
        System.out.println(map.containsKey("1"));
        
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("狗剩", "单身");
        map1.put("狗娃", "单身");
        map1.put("狗蛋", "不单身");
        Collection<String> values = map1.values();
        values.remove("单身");
        for (Map.Entry<String, String> entry : map1.entrySet()) {
            System.out.println(entry.getKey() + "-->" + entry.getValue());
        }
    }
}
