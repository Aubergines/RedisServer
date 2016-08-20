package com.zsq.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Aubergine on 2016/8/3.
 */
public class AnnotationUtils {
    public AnnotationUtils() {
    }

    public static String getClassAnnotation(Class clasz) {
        String returnS = "";
        Annotation[] classAnnotation = clasz.getAnnotations();
        Annotation[] arr$ = classAnnotation;
        int len$ = classAnnotation.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Annotation cAnnotation = arr$[i$];
            String temp = cAnnotation.toString();
            if(temp.indexOf("@javax.ws.rs.Path") == 0) {
                Class annotationType = cAnnotation.annotationType();
                System.out.println("类上的注释0有: " + annotationType);
                System.out.println("类上的注释1=有: " + temp);
                String[] s = temp.split("value=");
                System.out.println("类上的注释2有: " + s[1].substring(0, s[1].indexOf(")")));
                returnS = s[1].substring(0, s[1].indexOf(")"));
            }
        }

        return returnS;
    }



    public static String getPojo(String columnParam) {
        StringBuffer returnString = new StringBuffer("");

        try {
            Field[] ex = Class.forName(columnParam).getDeclaredFields();
            Field[] arr$ = ex;
            int len$ = ex.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Field f = arr$[i$];
                String filedName = f.getName();
                System.out.println("======属性名称:【" + filedName + "】");
                returnString.append("\t\t" + filedName + "\n\n");
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return returnString.toString();
    }

    public static List<Map> getMethodByClass(Class clazz) {
        ArrayList list = new ArrayList();
        System.out.println("********方法的Annotation*************");
        Method[] methods = clazz.getDeclaredMethods();

        for(int i = 0; i < methods.length; ++i) {
            HashMap map = new HashMap();
            Method method = methods[i];
            map.put("name", method.getName());
            System.out.println("name=" + method.getName());
            map.put("returnType", method.getParameterTypes()[0].toString());
            System.out.println("MethodMap=" + map);
            list.add(map);
        }

        return list;
    }

    public static String getFieldValue(String className, String fieldName) {
        String returnString = "";

        try {
            Field[] ex = Class.forName(className).getDeclaredFields();
            Field[] arr$ = ex;
            int len$ = ex.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Field f = arr$[i$];
                if(fieldName.equalsIgnoreCase(f.getName())) {
                    returnString = (String)f.get(f.getName());
                }
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return returnString;
    }

    public static String[] getField(Class columnParam) {
        String[] a = null;

        try {
            Field[] ex = columnParam.getDeclaredFields();
            System.out.println("fields fields=" + ex.length);
            if(ex != null && ex.length > 1) {
                a = new String[ex.length];

                for(int i = 0; i < ex.length; ++i) {
                    System.out.println("fields i=" + i);
                    Field f = ex[i];
                    String filedName = f.getName();
                    System.out.println("fields name=" + filedName);
                    a[i] = filedName;
                }
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return a;
    }

    public static void main(String[] args) {
    }
}
