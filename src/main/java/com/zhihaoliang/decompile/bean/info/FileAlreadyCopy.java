package com.zhihaoliang.decompile.bean.info;

import com.zhihaoliang.decompile.bean.copy.CopyFile;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by haoliang on 2017/11/16.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 */
@Root(name = "alreadyCopy", strict = false)
public class FileAlreadyCopy {
    @Attribute(name = "describe")
    private String describe = "已经拷贝的文件列表";

    @ElementList(inline = true, entry = "alreadyCopyFile", required = false)
    private ArrayList<CopyFile> alreadyCopyFiles;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public ArrayList<CopyFile> getAlreadyCopyFiles() {
        return alreadyCopyFiles;
    }

    public void setAlreadyCopyFiles(ArrayList<CopyFile> alreadyCopyFiles) {
        this.alreadyCopyFiles = alreadyCopyFiles;
    }
}
