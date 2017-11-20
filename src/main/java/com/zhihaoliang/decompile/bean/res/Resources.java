package com.zhihaoliang.decompile.bean.res;

import com.zhihaoliang.decompile.bean.res.ResColor;
import com.zhihaoliang.decompile.bean.res.ResDimen;
import com.zhihaoliang.decompile.bean.res.ResString;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by haoliang on 2017/11/13.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 * 存放 Res 中的XML资源
 */
@Root(name = "resources", strict = false)
public class Resources {

    /**
     * 对应问你件的路径
     */
    private String filePath;
    /**
     * <string name="b">QQ钱包支付失败，请重试或选择其它支付方式</string>
     * 的集合
     */
    @ElementList(inline = true, entry = "string",required = false)
    private ArrayList<ResString> strings;
    /**
     * <dimen name="r">27.0dip</dimen>
     * 的集合
     */
    @ElementList(inline = true, entry = "dimen",required = false)
    private ArrayList<ResDimen> dimens;
    /**
     * <color name="a">#ff252525</color>
     * 的集合
     */
    @ElementList(inline = true, entry = "color",required = false)
    private ArrayList<ResColor> colors;

    public ArrayList<ResString> getStrings() {
        return strings;
    }

    public ArrayList<ResDimen> getDimens() {
        return dimens;
    }

    public ArrayList<ResColor> getColors() {
        return colors;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


}
