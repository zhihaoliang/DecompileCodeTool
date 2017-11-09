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
     * 根据文件内容 过滤
     */
    public static final int ST_CONTENT = 2;


    public MyFileFilter(String content, int state) {
        mContent = content;
        mState = state;
    }


    /**
     * @param fileName 文件的名称
     * @return 文件名称去掉"."之后的文字
     */
    private static String getFileName(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            return fileName;
        }

        return fileName.substring(0, index);

    }

    public static final void main(String[] args) {
        Log.println(getFileName("hello.txt"));
    }

    @Override
    public boolean accept(File dir, String name) {
        switch (mState) {
            case ST_PREFIX:
                return name.startsWith(mContent);
            case ST_NAME:
                return mContent.equals(getFileName(name));
            case ST_CONTENT:
                return true;
        }
        return false;
    }
}
