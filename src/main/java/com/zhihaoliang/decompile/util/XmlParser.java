package com.zhihaoliang.decompile.util;

import com.zhihaoliang.decompile.bean.copy.CopyBean;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by haoliang on 2017/11/9.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 */
public class XmlParser<T> {

    private static  XmlParser instance;

    private static Logger logger = LogManager.getLogger(XmlParser.class);


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
            logger.error(String.format("文件%s解析失败,失败原因%s",filePath,e.toString()));
        }
        return null;
    }

    public final CopyBean parser(String filePath){
        File result = new File(filePath);
        File parentFile = result.getParentFile();
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }

        if(!result.exists()){
            try {
                result.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            return  new Persister().read(CopyBean.class,new File(filePath));
        } catch (Exception e) {
            logger.error(String.format("文件%s解析失败,失败原因%s",filePath,e.toString()));
        }
        return null;
    }


    public static String ob2Str(Object object) {
        if (object == null) {
            return  "object is null";
        }
        Serializer serializer = new Persister();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            serializer.write(object, baos);
            return baos.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  "object is null";
    }

}
