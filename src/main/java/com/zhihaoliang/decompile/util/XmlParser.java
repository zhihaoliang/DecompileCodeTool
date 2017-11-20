package com.zhihaoliang.decompile.util;

import com.zhihaoliang.decompile.bean.ConfigBean;
import com.zhihaoliang.decompile.bean.copy.CopyBean;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.filter.SystemFilter;

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
            Log.println(String.format("文件%s解析失败,失败原因%s",filePath,e.toString()));
        }
        return null;
    }

    public static final void main(String[] args){
        ConfigBean configBean = (ConfigBean) XmlParser.getInstance().parser("Config.xml",new ConfigBean());
        String copyName = MD5.MD5Encode(configBean.getSrcPath() + configBean.getDestPath()) + ".xml";
        File file = new File(new File(configBean.getSrcPath()), copyName);
        String copyFilePath = file.getAbsolutePath();

        Serializer serializer = new Persister();
        try {
            serializer.write(copyFilePath, System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
