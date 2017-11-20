package com.zhihaoliang.decompile.bean.copy;

import com.zhihaoliang.decompile.bean.ArgBean;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by haoliang on 2017/11/15.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 * 用于放置COPY文件
 */
@Root(name = "copy", strict = false)
public class CopyBean {
    /**
     * <file destPath="destpath" srcPath="srcPath"/>
     * 的集合
     */
    @ElementList(inline = true, entry = "file", required = false)
    private ArrayList<ArgBean> argBeans;
    /**
     * <color name="a">#ff252525</color>
     * 的集合
     */
    @ElementList(inline = true, entry = "content", required = false)
    private ArrayList<CopyContent> copyContents;


    public ArrayList<CopyContent> getCopyContents() {
        return copyContents;
    }

    public void setCopyContents(ArrayList<CopyContent> copyContents) {
        this.copyContents = copyContents;
    }

    public ArrayList<ArgBean> getArgBeans() {
        return argBeans;
    }

    public void setArgBeans(ArrayList<ArgBean> argBeans) {
        this.argBeans = argBeans;
    }

    public void addCopyFile(ArgBean argBean){
        if(argBeans == null){
            argBeans = new ArrayList<>();
        }
        argBeans.add(argBean);
    }
}