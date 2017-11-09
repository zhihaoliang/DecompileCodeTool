package com.zhihaoliang.decompile.util;

import com.google.gson.Gson;

/**
 * Created by haoliang on 2017/11/9.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 *
 * 用于文件的打印
 */
public class Log {

    public static final void println(Object object){
        if(object == null){
            System.out.println("object is null");
            return;
        }
        Gson gson = new Gson();
        System.out.println(gson.toJson(object));
    }

}
