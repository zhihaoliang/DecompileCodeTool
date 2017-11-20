package com.zhihaoliang.decompile.bean.info;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 * Created by haoliang on 2017/11/16.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 *
 * 表示Copy之前COPY的信息
 */
@Root(name = "copyInfo", strict = false)
public class CopyInfoBean {

    @Element(name ="alreadyCopy",required = false)
    private FileAlreadyCopy fileAlreadyCopy;

    @Element(name ="unCopy",required = false)
    private FileUnCopy fileUnCopy;

    public FileAlreadyCopy getFileAlreadyCopy() {
        return fileAlreadyCopy;
    }

    public void setFileAlreadyCopy(FileAlreadyCopy fileAlreadyCopy) {
        this.fileAlreadyCopy = fileAlreadyCopy;
    }

    public FileUnCopy getFileUnCopy() {
        return fileUnCopy;
    }

    public void setFileUnCopy(FileUnCopy fileUnCopy) {
        this.fileUnCopy = fileUnCopy;
    }
}
