package com.zhihaoliang.decompile.bean;

/**
 * Created by haoliang on 2017/11/9.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 */
public class Result {
    /**
     * 表示要适合的行数
     */
    private int lineNum;
    /**
     * 表示合适文件的路径
     */
    private String path;
    /**
     * 表示行里的内容
     */
    private String line;

    public Result(int lineNum, String path, String line) {
        this.lineNum = lineNum;
        this.path = path;
        this.line = line;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
