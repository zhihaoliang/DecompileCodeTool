package com.zhihaoliang.decompile.bean.res.base;

/**
 * Created by haoliang on 2017/11/15.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 */
public abstract class Res {
    /**
     * 对应问你件的路径
     */
    private String filePath;

    public abstract String getName();

    public abstract String getValue();

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
