package com.zhihaoliang.decompile.util;

import com.zhihaoliang.decompile.Config;
import com.zhihaoliang.decompile.bean.Result;
import com.zhihaoliang.decompile.bean.Root;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by haoliang on 2017/11/9.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 *
 * 用于文件的寻找
 */
public class FileFind {

    /**
     * @param file 需要搜索的文件
     * @param content 需要搜索的内容
     * @return 返回合适的要copy文件的行数，行的内容
     */
    public final static Result readLineFile(File file, String[] content) {

        try {
            FileInputStream in = new FileInputStream(file.getAbsolutePath());
            InputStreamReader inReader = new InputStreamReader(in, "UTF-8");
            BufferedReader bufReader = new BufferedReader(inReader);
            String line ;
            int i = 0;
            OUT:
            while ((line = bufReader.readLine()) != null) {
                for (String s : content) {
                    if (!line.contains(s)) {
                        continue OUT;
                    }
                }

                System.out.println(file.getPath() + ": 第" + i + "行：" + line);

            }
            bufReader.close();
            inReader.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("读取" + file.getAbsolutePath() + "出错！");
        }
        return null;
    }


    /**
     * 根据前缀获取获取文件的路径
     * @param parentFile 父文件夹
     * @param fileNames 表示文件的名称
     * @param content 所有搜索的对象
     * @return
     */
    public static final ArrayList<String> getFileByName(File parentFile,String[] fileNames,String content){
        ArrayList<String> arrayList = new ArrayList<>();
        for (String fileName : fileNames) {
            File file = new File(parentFile,fileName);
            String[]list = file.list(new MyFileFilter(content,MyFileFilter.ST_NAME));
            for (String s : list) {
                File temp = new File(file,s);
                arrayList.add(temp.getAbsolutePath());
            }

        }
        return arrayList;
    }

    /**
     * 根据前缀返回适合的文件列表
     * @param parentPath 传入的路径 最后的的路径是res
     * @param @drawable/so  @后面 "/"前面的字段
     * @return 返回所有适合的路径
     */
    public static final String[] getPrefix(String parentPath, String arg){
        switch (arg){
            case Config.VALUES:
            case Config.DRAW:
                File file = new File(parentPath);
                return file.list(new MyFileFilter(arg,MyFileFilter.ST_PREFIX));

        }
        return null;
    }

    /**
     * 用于分割用户输入的字段
     * @param arg 用户输入的第一个参数
     * @return 返回分割后的字符串数组
     */
    public static final String[] splitPath(String arg){
        if (arg.contains("@") && arg.contains("/")) {
            arg = arg.replaceAll("@", "");
            String[] array = arg.split("/");
            return array;
        }
        return new String[]{arg};
    }

    public static final void inLine() {
        // 要验证的字符串
        String str = "<string name=\"ja\">该分类下暂无%s哦</string>";
        // 邮箱验证规则
       // String regEx = String.format("*<%s*name*=*%s*</*%s>","string","ja","string");
        String regEx ="^[*]";
        Log.println(regEx);
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.matches();
        System.out.println(rs);
    }

    /**
     * 用于此文件的测试
     * @param args 接受用户的输入
     */
    public static final void main(String[]args){
       // doDraw();
        //doString();
        inLine();
    }

    /**
     * 用于测试draw
     */
    private static final void doDraw(){
        String[] contents = splitPath("@drawable/so");
        Log.println(contents);
        Root root = XmlParser.prepareParser();
        if(root == null){
            return;
        }
        File file = new File(new File(root.getSrcPath()),Config.RES);
        String[] drawDirs = getPrefix(file.getPath(),contents[0]);
        Log.println(drawDirs);

        ArrayList<String> arrayList = getFileByName(file,drawDirs,contents[1]);
        Log.println(arrayList);
    }

    /**
     * 用于测试String
     *
     */
    public static final void doString(){
        String[] contents = splitPath("@string/avk");
        Log.println(contents);
        Root root = XmlParser.prepareParser();
        if(root == null){
            return;
        }
        File file = new File(new File(root.getSrcPath()),Config.RES);
        String[] valueDirs = getPrefix(file.getPath(),Config.VALUES);
        Log.println(valueDirs);

        ArrayList<String> arrayList = getFileByName(file,valueDirs,Config.STRING);
        Log.println(arrayList);


    }


}
