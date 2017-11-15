package com.zhihaoliang.decompile.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by haoliang on 2017/11/9.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 *
 * 文件的过滤类
 */
public class MyFileFilter implements FilenameFilter {

    /**
     * 过滤的路径
     */
    private int mState;
    /**
     * 文件的前缀，文件的名称，或者文件的名称
     */
    private String mContent;

    /**
     * 根据前缀获取过滤
     */
    public static final int ST_PREFIX = 0;
    /**
     * 根据文件名字 过滤
     */
    public static final int ST_NAME = 1;
    /**
     * 表示没有过滤
     */
    public static final int ST_NO = -1;
    /**
     * 表示除了dimens，public，styles，ids，raw
     */
    public static final int ST_OTHER = 2;

    private static final String[]OTHER={
            "dimens",
            "public",
            "styles",
            "ids",
            "raw",
    };


    public MyFileFilter(String content, int state) {
        mContent = content;
        mState = state;
    }


    /**
     * @param fileName 文件的名称
     * @return 文件名称去掉"."之后的文字
     */
    public static String getFileName(String fileName) {
        int index = fileName.indexOf(".");
        if (index == -1) {
            return fileName;
        }
        return fileName.substring(0, index);

    }

    @Override
    public boolean accept(File dir, String name) {
        switch (mState) {
            case ST_PREFIX:
                return name.startsWith(mContent);
            case ST_NAME:
                return mContent.equals(getFileName(name));
            case ST_OTHER:
                return !isInOther(name);
        }
        return false;
    }

    private static final boolean isInOther(String name){
        for (String s : OTHER) {
            if(s.equals(name)){
                return true;
            }
        }
        return false;
    }

    public static final void main(String[] args) {
        Log.println(getFileName("hello.txt"));
        Log.println(getFileName("hello1.txt"));
    }
}
