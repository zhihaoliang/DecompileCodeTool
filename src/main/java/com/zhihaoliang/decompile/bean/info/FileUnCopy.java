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
@Root(name = "unCopy", strict = false)
public class FileUnCopy {

    @Attribute(name = "describe")
    private String describe = "未拷贝的文件列表";

    @ElementList(inline = true, entry = "unCopyFile", required = false)
    private ArrayList<String> unCopyFiles;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public ArrayList<String> getUnCopyFiles() {
        return unCopyFiles;
    }

    public void setUnCopyFiles(ArrayList<String> unCopyFiles) {
        this.unCopyFiles = unCopyFiles;
    }
}

