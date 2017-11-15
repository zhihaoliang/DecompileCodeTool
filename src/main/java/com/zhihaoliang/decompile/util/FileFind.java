package com.zhihaoliang.decompile.util;

import com.zhihaoliang.decompile.Config;
import com.zhihaoliang.decompile.bean.ConfigBean;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by haoliang on 2017/11/9.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 *         <p>
 *         用于文件的寻找
 */
public class FileFind {


    /**
     * 根据名字进行搜索的文件
     *
     * @param systemIn   表示要要解析的字符
     * @param configBean 表示存放目标目录和操作的的目录
     */
    public static void fileFind(String systemIn, ConfigBean configBean) {
        File file = new File(configBean.getSrcPath(), "res");
        ArrayList<String> arrayList = findFileByName(file, systemIn);
        Log.println(arrayList);

    }

    /**
     * 根据名字进行在特定的文件搜索的文件
     *
     * @param systemIn   表示用户输入的字段
     * @param configBean 表示存放目标目录和操作的的目录
     */
    public static ArrayList<String> fileFind(String[] systemIn, ConfigBean configBean) {
        File dirRes = new File(configBean.getSrcPath(), "res");
        String[] searchDirs = dirRes.list(new MyFileFilter(systemIn[0], MyFileFilter.ST_PREFIX));
        return getFileByName(dirRes, searchDirs, systemIn[1], MyFileFilter.ST_NAME);
    }

    /**
     * @param path 项目的目录，其子目录包含res
     * @return 返回value下面所有的问题件
     */
    public static ArrayList<String> fileValueFind(String path) {
        File dirRes = new File(path, "res");
        String[] searchDirs = dirRes.list(new MyFileFilter(Config.VALUES, MyFileFilter.ST_PREFIX));
        return getFileByName(dirRes, searchDirs, null, MyFileFilter.ST_NO);
    }

    /**
     * @param file 表示要搜索的文件件
     * @param name 表示要搜索的文件的名字
     * @return 返回符合条件的所有的列表
     */
    private static ArrayList<String> findFileByName(File file, String name) {
        ArrayList<String> arrayList = new ArrayList<>();
        int index = name.indexOf(".");
        boolean isSuffix = false;
        if (index != -1) {
            isSuffix = true;
        }
        findFileByName(file, name, arrayList, isSuffix);
        return arrayList;

    }


    /**
     * @param file      表示要搜索的文件件
     * @param name      表示要搜索的文件的名字
     * @param filePaths 存储合适的文件
     * @param isSuffix  要搜索的文件的文件是否包含后缀，true表示包含后缀，false表示不包含后缀
     */
    private static void findFileByName(File file, String name, ArrayList<String> filePaths, boolean isSuffix) {
        String[] fileChirlds = file.list();
        if (fileChirlds == null) {
            return;
        }
        for (String fileChirld : fileChirlds) {
            if (fileChirld == null) continue;
            File fileTemp = new File(file, fileChirld);
            if (fileTemp.isDirectory()) findFileByName(fileTemp, name, filePaths, isSuffix);
            else {
                if (isAccordFileName(name, fileChirld, isSuffix)) filePaths.add(fileTemp.getPath());
            }
        }
    }

    /**
     * 根据前缀获取获取文件的路径
     *
     * @param parentFile 父文件夹 如果为空fileNames为存储的为全路径
     * @param fileNames  表示文件的名称
     * @param content    所有搜索的对象
     * @param filterType 过滤器类型
     * @return 获取符合条件的文件列表
     */
    private static ArrayList<String> getFileByName(File parentFile, String[] fileNames, String content, int filterType) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (String fileName : fileNames) {
            File file = parentFile == null ? new File(fileName) : new File(parentFile, fileName);
            if (file.isDirectory()) {
                String[] list = filterType == MyFileFilter.ST_NO ? file.list() : file.list(new MyFileFilter(content, filterType));
                if (list != null) {
                    for (String s : list) {
                        File temp = new File(file, s);
                        arrayList.add(temp.getAbsolutePath());
                    }
                }
            } else {
                String name = file.getName();
                if (content.equals(MyFileFilter.getFileName(name))) {
                    arrayList.add(file.getAbsolutePath());
                }
            }


        }
        return arrayList;
    }

    /**
     * @param name     要搜索文件的名字
     * @param fileName 文件的名字
     * @param isSuffix 要搜索的文件的文件是否包含后缀，true表示包含后缀，false表示不包含后缀
     * @return true 表示符合，false表示不符合
     */
    private static boolean isAccordFileName(String name, String fileName, boolean isSuffix) {
        if (isSuffix) {
            return fileName.equals(name);
        } else {
            return name.equals(getFileName(fileName));
        }
    }

    /**
     * @param fileName 去掉文件名字的后缀
     * @return 返回文件后缀的名字
     */
    private static String getFileName(String fileName) {
        int index = fileName.indexOf(".");
        if (index != -1) {
            return fileName.substring(0, index);
        }
        return fileName;
    }


}
