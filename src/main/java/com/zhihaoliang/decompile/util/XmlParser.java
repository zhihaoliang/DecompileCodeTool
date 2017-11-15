package com.zhihaoliang.decompile.util;

import com.zhihaoliang.decompile.bean.ConfigBean;
import org.simpleframework.xml.core.Persister;

import java.io.File;

/**
 * Created by haoliang on 2017/11/9.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 */
public class XmlParser<T> {

    private static  XmlParser instance;


    public static XmlParser getInstance(){
        if(instance == null){
            synchronized (XmlParser.class){
                if(instance == null){
                    instance = new XmlParser();
                }
            }
        }
        return instance;
    }

    public final T parser(String filePath, T t){
        try {
            return (T) new Persister().read(t.getClass(),new File(filePath));
        } catch (Exception e) {
            Log.println(String.format("文件%s解析失败,失败原因%s",filePath,e.toString()));
        }
        return null;
    }

    public static final void main(String[] args){
        Log.println(getInstance().parser("Config.xml", new ConfigBean()));
    }

}
