package com.zhihaoliang.decompile.bean.res;

import com.zhihaoliang.decompile.bean.res.base.Res;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Text;

/**
 * Created by haoliang on 2017/11/14.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 * <color name="a">#ff252525</color>
 */
public class ResColor extends Res{
    @Attribute(name = "name")
    private String name;
    @Text(required = false)
    private String value;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }
}
