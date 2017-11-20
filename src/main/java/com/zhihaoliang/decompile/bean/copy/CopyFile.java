package com.zhihaoliang.decompile.bean.copy;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by haoliang on 2017/11/15.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 * <file destPath="destpath" srcPath="srcPath"/>
 */
@Root(name = "file", strict = false)
public class CopyFile {

    public CopyFile(String destPath, String srcPath) {
        this.destPath = destPath;
        this.srcPath = srcPath;
    }

    @Attribute(name = "destPath")
    private String destPath;
    @Attribute(name = "srcPath")
    private String srcPath;

    public String getSrcPath() {
        return srcPath;
    }
}
