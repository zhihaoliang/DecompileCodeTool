package com.zhihaoliang.decompile.bean;


import java.util.ArrayList;

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

    public Result(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public static ArrayList<Result> setResult(String[] contents){
        ArrayList<Result> results = new ArrayList<>();
        if(contents == null || contents.length ==0){
            return results;
        }

        for (String content : contents) {
            results.add(new Result(content));
        }
        return results;
    }

    public static ArrayList<Result> setResult(ArrayList<String>contents ){
        ArrayList<Result> results = new ArrayList<>();
        if(contents == null || contents.size() ==0){
            return results;
        }

        for (String content : contents) {
            results.add(new Result(content));
        }
        return results;
    }

}
