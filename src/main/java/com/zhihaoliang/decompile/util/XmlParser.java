package com.zhihaoliang.decompile.util;

import com.thoughtworks.xstream.XStream;
import com.zhihaoliang.decompile.bean.Root;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by haoliang on 2017/11/9.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 */
public class XmlParser {
    public static final Root prepareParser() {
        File file = new File("Config.xml");
        if (!file.exists()) {
            Log.println("文件Config.xml不存在");
            return null;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            XStream xstr = new XStream();
            xstr.alias("root", Root.class);
            return (Root) xstr.fromXML(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.println("文件Config.xml不存在");

        }catch (Exception e){
            Log.println("文件Config.xml格式错误");
        }
        return null;
    }
}
