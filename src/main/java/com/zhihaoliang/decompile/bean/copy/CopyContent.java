package com.zhihaoliang.decompile.bean.copy;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;


/**
 * Created by haoliang on 2017/11/15.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 */
@Root(name = "content", strict = false)
public class CopyContent {
    @Attribute(name = "destPath")
    private String destPath;
    @Attribute(name = "srcPath")
    private String srcPath;
    @Attribute(name = "srcName")
    private String srcName;
    @Attribute(name = "destName")
    private String destName;
    @Text(required = false)
    private String value;

    public CopyContent(String destPath, String srcPath, String srcName, String destName, String value) {
        this.destPath = destPath;
        this.srcPath = srcPath;
        this.srcName = srcName;
        this.destName = destName;
        this.value = value;
    }
}
