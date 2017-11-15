package com.zhihaoliang.decompile;

/**
 * Created by haoliang on 2017/11/9.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 */
public interface Config {
    /**
     * 分割符
     */
     String MAKER = ">>>";
    /**
     * res 路径中的values
     */
     String VALUES = "values";
    /**
     * Android 工程中的res路径
     */
     String RES = "res";
    /**
     * Android 工程中draw的路径
     */
    String DRAW = "drawable";
    /**
     * Android 工程中draw的路径
     */
    String MIPMAP = "mipmap";
    /**
     * Android 工程中layout的路径
     */
    String LAYOUT = "layout";
    /**
     * 文件中的resources
     */
    String RESOURCES = "resources";
    /**
     * 表示颜色的路径
     */
     String COLOR = "color";
    /**
     * 表示存储字符串的路径
     */
    String STRINGS = "strings";
    /**
     * 表示存储字符串的路径
     */
    String STRING = "string";

    String ERROR = "erro";

    String FIND_FILE = "findFile";

    String DIMEN = "dimen";

}
