package com.zhihaoliang.decompile.util;

import com.google.gson.Gson;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * Created by haoliang on 2017/11/9.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 *         用于文件的打印
 */
public class Log {

    public static void println(Object object) {
        if (object == null) {
            System.out.println("object is null");
            return;
        }
        Gson gson = new Gson();
        System.out.println(gson.toJson(object));
    }

    public static void printlnXML(Object object) {
        if (object == null) {
            System.out.println("object is null");
            return;
        }
        Serializer serializer = new Persister();
        try {
            serializer.write(object, System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void println(String str) {
        System.out.println(str);
    }


}
