package com.niaobulashi.util;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: javademo
 * @description: 转Map工具
 * @author: hulang
 * @create: 2019-05-30 14:16
 */
public class MapUtil {

    private static final Logger logger = LoggerFactory.getLogger(MapUtil.class);

    /**
     * 将两个对象的字段合并为Map，Key:target-value  Value:src-value
     * @param map
     * @param src
     * @param target
     * @return
     */
    /**
     * @param src
     * @param target
     * @return
     * @throws Exception 将两个对象的字段值合并为Map. Key:target-value  Value:src-value
     */
    public static Map<String, String> getMainMap(Map<String, String> map, Object src, Object target) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (map == null)
            map = new HashMap<>();
        if (src != null && target != null) {
            try {
                Field[] srcFields = src.getClass().getDeclaredFields();
                Class supClass = src.getClass().getSuperclass();
                Field[] superFields = supClass.getDeclaredFields();
                Field[] fields = (Field[]) ArrayUtils.addAll(srcFields, superFields);
                for (Field sfield : fields) {
                    sfield.setAccessible(true);
                    Field[] tarfields = target.getClass().getDeclaredFields();
                    Class type = sfield.getType();
                    for (Field tfield : tarfields) {
                        tfield.setAccessible(true);
                        if (sfield.getName().equals(tfield.getName())) {
                            Object key = tfield.get(target);
                            Object value = sfield.get(src);
                            if (key != null && value != null) {
                                if (type == Timestamp.class) {
                                    map.put((String) key, format.format(value));
                                } else if (type == Date.class) {
                                    map.put((String) key, format.format(value));
                                } else {
                                    map.put((String) key, (String) value);
                                }
                            }

                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage(), e);
            }
        }
        return map;
    }


    /**
     * 获取明细表数据
     *
     * @param src
     * @param target
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> getDetailMapList(List<Object> src, Object target) throws Exception {
        List<Map<String, String>> list = new ArrayList<>();
        try {
            if (src != null && target != null && src.size() != 0) {

                for (Object obj : src) {
                    list.add(getMainMap(new HashMap<String, String>(), obj, target));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return list;
    }


    public static Map<String, String> getMainMap(Map<String, String> map, Map<String, String> src, Object target) throws Exception {
        if (map == null)
            map = new HashMap<>();
        if (src != null && target != null) {
            try {
                Set<String> srcfields = src.keySet();
                for (String sfield : srcfields) {
                    Field[] tarfields = target.getClass().getDeclaredFields();
                    for (Field tfield : tarfields) {
                        tfield.setAccessible(true);
                        String fname = tfield.getName();
                        String key = (String) tfield.get(target);
                        if (sfield.equals(key)) {
                            String value = src.get(key);
                            map.put(fname, value);
                        }
                    }
                }
                return map;
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return map;
    }

    /**
     * Object对象转Map
     * @param object
     * @return
     * @throws Exception
     */
    public static Map<String, Object> objectToMap(Object object) throws Exception {
        Map<String, Object> map = new HashMap<>();
        if(object!=null){
            try {
                Method[] methods = object.getClass().getMethods(); // 获取所有方法
                for (Method method : methods) {
                    if (method.getName().startsWith("get")) {
                        String field = method.getName(); // 拼接属性名
                        field = field.substring(field.indexOf("get") + 3);
                        field = field.toLowerCase().charAt(0) + field.substring(1);
                        Object value = method.invoke(object, (Object[]) null); // 执行方法
                        map.put(field, value);
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage(), e);
                throw e;
            }
        }
        return map;
    }

    /**
     * Map转Object对象
     * @param object
     * @param map
     * @return
     * @throws Exception
     */
    public static Object mapToObject(Object object, Map<String, Object> map) throws Exception {
        try {
            Method[] methods = object.getClass().getMethods(); // 获取所有方法
            for (Method method : methods) {
                if (method.getName().startsWith("set")) {
                    String field = method.getName(); // 截取属性名
                    field = field.substring(field.indexOf("set") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);
                    if (map.containsKey(field)) {
                        method.invoke(object, map.get(field));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw e;
        }

        return object;
    }
}

