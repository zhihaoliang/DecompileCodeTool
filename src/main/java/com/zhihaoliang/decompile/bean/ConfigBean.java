package com.zhihaoliang.decompile.bean;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by haoliang on 2017/11/7.
 * 解析Config.xml
 */
@Root(name = "root", strict = false)
public class ConfigBean {
    /**
     * 原工程路径
     */
    @Element(name = "srcPath")
    private String srcPath;
    /**
     * 要修改工程的路径
     */
    @Element(name = "destPath")
    private String destPath;

    public String getSrcPath() {
        return srcPath;
    }

    public String getDestPath() {
        return destPath;
    }

}
