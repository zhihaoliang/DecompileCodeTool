package com.zhihaoliang.decompile.bean.res;


import com.zhihaoliang.decompile.bean.res.base.Res;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;



/**
 * Created by haoliang on 2017/11/13.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 * <string name="b">QQ钱包支付失败，请重试或选择其它支付方式</string>
 */
@Root(name = "string", strict = false)
public class ResString extends Res{

    @Attribute(name="name")
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
