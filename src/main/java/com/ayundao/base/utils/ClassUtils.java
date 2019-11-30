package com.ayundao.base.utils;

import org.hibernate.proxy.HibernateProxy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @ClassName: ClassUtils
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/17 19:15
 * @Description: 类工具类
 * @Version: V1.0
 */
public class ClassUtils {

    /**
     * 不可实例化
     */
    private ClassUtils() {
    }

    private final static String[] baseNames = new String[]{"id", "version", "createdDate", "lastModifiedDate"};

    /**
     * 强制获取字段的value
     * @param obj
     * @param name
     * @return
     */
    public static String forceGetProperty(Object obj, String name) {
        return reflectObject(obj, new String[]{"id", "version", "createdDate", "lastModifiedDate"}, name);
    }

    /**
     * 强制获取字段的value
     * @param obj
     * @param name
     * @return
     */
    public static String getBrieflyProperty(Object obj, String name) {
        return reflectObject(obj, new String[]{"id"}, name);
    }
    
    private static String reflectObject(Object obj, String[] fieldNames, String name) {
        Class<?> cls = obj.getClass();
        String result = "";
        String mn = "";
        Method method = null;
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (String s : fieldNames) {
                if (name.equals(s)) {
                    fields = obj.getClass().getSuperclass().getDeclaredFields();
                    break;
                }
            }
            for (Field field : fields) {
                if (field.getName().equals(name)) {
                    field.setAccessible(true);
                    mn = "get" + toFirstUpperCase(field.getName());
                    method = cls.getMethod(mn, new Class[]{});
                    obj = method.invoke(obj, new Object[]{});
                    result = obj.toString();
                }
            }
        } catch (NullPointerException e) {
        } catch (NoSuchMethodException e) {
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
        return result;
    }

    /**
     * 获取基类和子类的所有字段属性
     * @param clazz
     * @return
     */
    public static Map<String, Field> getDeclaredFieldsWithSuper(Class<? extends Object> clazz) {
        Class superClass = clazz.getSuperclass();
        List<Method> methods = getAllMethods(clazz);
        Map<String, Field> map = new LinkedHashMap<>();
        Field[] fields = null;
        Field field = null;
        //基类
        map = getAllFields(map, methods, clazz.getSuperclass());
        map = getAllFields(map, methods, clazz);
        return map;
    }

    /**
     * 获取基类与子类的所有字段(set/get)
     * @param map
     * @param methods
     * @param cls
     * @return
     */
    public static Map<String, Field> getAllFields(Map<String, Field> map, List<Method> methods, Class<?> cls) {
        Field[] fields = cls.getDeclaredFields();
        for (Method m : methods) {
            for (Field f : fields) {
                if (f.getName().equals(toFirstLowerCase(m.getName().substring(3, m.getName().length())))) {
                    map.put(f.getName(), f);
                }
            }
        }
        return map;
    }

    /**
     * 获取基类和子类的所有方法(set/get)
     * @param cls
     * @return
     */
    public static List<Method> getAllMethods(Class cls) {
        List<Method> methods = new LinkedList<>();
        for (Method method : cls.getSuperclass().getDeclaredMethods()) {
            if (method.getName().startsWith("set")) {
                methods.add(method);
            }
        }
        for (Method method : cls.getDeclaredMethods()) {
            if (method.getName().startsWith("set")) {
                methods.add(method);
            }
        }
        return methods;
    }
    
    /**
     * 首字母大写
     * @param name
     * @return
     */
    public static String toFirstUpperCase(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
    }

    /**
     * 首字母小写
     * @param name
     * @return
     */
    public static String toFirstLowerCase(String name) {
        return name.substring(0, 1).toLowerCase() + name.substring(1, name.length());
    }

    /**
     * 解析敏感词
     * @param s
     * @return
     */
    public static Object parseEntity(Object s) {
        if (s instanceof HibernateProxy) {
            s = ((HibernateProxy)s).getHibernateLazyInitializer().getImplementation();
        }
        Method[] methods = s.getClass().getMethods();
        try {
            for (Method method : methods) {
                if (method.getName().startsWith("get") && method.getReturnType().isAssignableFrom(String.class) && !baseNames.equals(method.getName().substring(3, method.getName().length()))) {
                    Object obj = method.invoke(s, new Object[]{});
                    String mn = method.getName();
                    Field field = s.getClass().getDeclaredField(toFirstLowerCase(mn.substring(3, mn.length())));
                    String str = obj == null ? "" : obj.toString();
                    field.setAccessible(true);
                    field.set(s, str);
                }
            }
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        } catch (NoSuchFieldException e) {
        }
        return s;
    }
}
