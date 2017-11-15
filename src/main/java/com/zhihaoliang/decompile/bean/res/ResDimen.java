package com.zhihaoliang.decompile.bean.res;

import com.zhihaoliang.decompile.bean.res.base.Res;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

/**
 * Created by haoliang on 2017/11/14.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 * <dimen name="r">27.0dip</dimen>
 */
@Root(name = "dimen", strict = false)
public class ResDimen extends Res{
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
