package com.zhihaoliang.decompile;

import com.zhihaoliang.decompile.bean.ArgBean;
import com.zhihaoliang.decompile.bean.ConfigBean;
import com.zhihaoliang.decompile.bean.copy.CopyBean;
import com.zhihaoliang.decompile.bean.res.ResColor;
import com.zhihaoliang.decompile.bean.res.ResDimen;
import com.zhihaoliang.decompile.bean.res.ResString;
import com.zhihaoliang.decompile.bean.res.Resources;
import com.zhihaoliang.decompile.bean.res.base.Res;
import com.zhihaoliang.decompile.util.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by haoliang on 2017/11/13.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 *         <p>
 *         工程的主类用于加载文件，接受用户的输入，并输出结果
 */
public class Load {
    /**
     * 表示要输入的提示的用户输入的
     */
    private static final String USER_USE =
            ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>搜索>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> \n" +
                    "例如输入\"@string/name\"表示要搜索string为name的字段\n" +
                    "例如输入\"@layout/name\"表示layout的文件件名字为name的所有文件\n" +
                    "例如输入文件的名字\"fileName\"或\"fileName.xml\"或\"fileName.png\"搜素res中名字相同的文件\n" +
                    "例如输入\"@layout/name ture\"表示将搜索的内容的拷贝到队形文件中\n" +
                    "例如输入\"@layout/name ture newName\"表示将搜索的内容的拷贝到队形文件中，并重命名\n" +
                    ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>搜索>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> \n";

    /**
     * 用于解析XML的工具类
     */
    private static final XmlParser XML_PARSER = XmlParser.getInstance();
    /**
     * 用于存储目标目录和操作目录的地址
     */
    private static final ConfigBean CONFIG_BEAN = (ConfigBean) XML_PARSER.parser("Config.xml", new ConfigBean());

    /**
     * 保存所有的string字段 原目录
     */
    private static final ArrayList<Resources> SRC_STR = new ArrayList<>();
    /**
     * 保存所有的string字段 目标目录
     */
    private static final ArrayList<Resources> DEST_STR = new ArrayList<>();
    /**
     * 保存所有的dimen字段 原目录
     */
    private static final ArrayList<Resources> SRC_DIMEN = new ArrayList<>();
    /**
     * 保存所有的dimen字段 目标目录
     */
    private static final ArrayList<Resources> DEST_DIMEN = new ArrayList<>();
    /**
     * 保存所有的color字段 原目录
     */
    private static final ArrayList<Resources> SRC_COLOR = new ArrayList<>();
    /**
     * 保存所有的color字段 原目录
     */
    private static final ArrayList<Resources> DEST_COLOR = new ArrayList<>();

    private static CopyBean sCopyBean;


    private static String sCopyFilePath;

    public static final void main(String[] args) {
        if (CONFIG_BEAN == null) {
            return;
        }

        Log.println("加载中...");
        load();
        Log.println("加载完成");
        Log.println(USER_USE);

        while (true) {
            Scanner scan = new Scanner(System.in);
            String read = scan.nextLine();
            if ("exit".equalsIgnoreCase(read)) {
                Log.println("程序解析完成");
                return;
            }
            oprateSystemLine(read);
        }
    }

    private static void load() {
        String copyName = MD5.MD5Encode(CONFIG_BEAN.getSrcPath() + CONFIG_BEAN.getDestPath()) + ".xml";
        File file = new File(new File(CONFIG_BEAN.getSrcPath()), copyName);
        sCopyFilePath = file.getAbsolutePath();
        sCopyBean = XmlParser.getInstance().parser(sCopyFilePath);
        if (sCopyBean == null) {
            sCopyBean = new CopyBean();
        }
        ArrayList<String> arrayList = FileFind.fileValueFind(CONFIG_BEAN.getSrcPath());
        for (String s : arrayList) {
            Resources res = (Resources) XmlParser.getInstance().parser(s, new Resources());
            if (res == null) {
                continue;
            }
            res.setFilePath(s);

            ArrayList<ResString> strings = res.getStrings();
            if (strings != null && strings.size() > 0) {
                SRC_STR.add(res);
            }

            ArrayList<ResDimen> dimens = res.getDimens();
            if (dimens != null && dimens.size() > 0) {
                SRC_DIMEN.add(res);
            }

            ArrayList<ResColor> colors = res.getColors();
            if (colors != null && colors.size() > 0) {
                SRC_COLOR.add(res);
            }

            Log.println(String.format("原文件%s加载完成", s));
        }

        arrayList = FileFind.fileValueFind(CONFIG_BEAN.getDestPath());
        for (String s : arrayList) {
            Resources res = (Resources) XmlParser.getInstance().parser(s, new Resources());
            if (res == null) {
                continue;
            }
            res.setFilePath(s);

            ArrayList<ResString> strings = res.getStrings();
            if (strings != null && strings.size() > 0) {
                DEST_STR.add(res);
            }

            ArrayList<ResDimen> dimens = res.getDimens();
            if (dimens != null && dimens.size() > 0) {
                DEST_DIMEN.add(res);
            }

            ArrayList<ResColor> colors = res.getColors();
            if (colors != null && colors.size() > 0) {
                DEST_COLOR.add(res);
            }

            Log.println(String.format("目标文件%s加载完成", s));
        }
    }


    private static void oprateSystemLine(String systemLine) {
        ArgBean argBean = Arg.initArgs(systemLine);
        switch (argBean.getState()) {
            case Config.FIND_FILE:
                ArrayList<String> arrayList = FileFind.fileFind(argBean.getOldName(), CONFIG_BEAN);
                copyFile(arrayList, argBean);
                break;
            case Config.DRAW:
            case Config.LAYOUT:
            case Config.MIPMAP:
                arrayList = FileFind.fileFind(argBean, CONFIG_BEAN);
                copyFile(arrayList, argBean);
                break;
            case Config.STRING:
            case Config.DIMEN:
                ArrayList<Res> resList = getRes(argBean, false);
                Log.println(resList);
                break;
            case Config.COLOR:
                ArrayList<String> files = FileFind.fileFind(argBean, CONFIG_BEAN);
                if (files == null || files.size() == 0) {
                    resList = getRes(argBean, false);
                    Log.println(resList);
                } else {
                    copyFile(files, argBean);
                }
                break;
            default:
                Log.println(USER_USE);
                break;
        }
    }

    private static ArrayList<Res> getRes(ArgBean argbean, boolean isDest) {
        ArrayList<Res> resList = new ArrayList<>();

        ArrayList<Resources> resourcesList = getResources(argbean.getState(), isDest);
        for (Resources resources : resourcesList) {
            ArrayList list = getResources(argbean.getState(), resources);
            for (Object item : list) {
                Res res = (Res) item;
                if (argbean.getOldName().equals(res.getName())) {
                    res.setFilePath(resources.getFilePath());
                    resList.add(res);
                }
            }
        }

        return resList;
    }

    private static ArrayList getResources(String state, Resources resources) {
        switch (state) {
            case Config.COLOR:
                return resources.getColors();
            case Config.DIMEN:
                return resources.getDimens();
            case Config.STRING:
                return resources.getStrings();
            default:
                return null;
        }
    }


    private static ArrayList<Resources> getResources(String state, boolean isDest) {
        switch (state) {
            case Config.STRING:
                if (isDest) {
                    return DEST_STR;
                } else {
                    return SRC_STR;
                }
            case Config.DIMEN:
                if (isDest) {
                    return DEST_DIMEN;
                } else {
                    return SRC_DIMEN;
                }
            case Config.COLOR:
                if (isDest) {
                    return DEST_COLOR;
                } else {
                    return SRC_COLOR;
                }

        }
        return null;
    }


    private static void copyFile(ArrayList<String> arrayList, ArgBean argBean) {
        if (!argBean.isCopy()) {
            Log.println(arrayList);
            return;
        }

        if (arrayList == null || arrayList.size() == 0) {
            Log.println("没有找到要拷贝的文件");
            return;
        }

        ArgBean argBeanResult = Check.splitCopyAndNo(sCopyBean.getArgBeans(), argBean);



        if (argBeanResult != null) {
            Log.printlnXML(argBeanResult);
            //用于换行
            Log.println("");
            return;
        }


        String[] destFiles = new String[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            destFiles[i] = arrayList.get(i).replace(CONFIG_BEAN.getSrcPath(), CONFIG_BEAN.getDestPath());
            File file = new File(destFiles[i]);
            String name = file.getName();
            String destName = name.replace(argBean.getOldName(), argBean.getNewName());
            File fileDest = new File(file.getParentFile(),destName);
            Log.println(fileDest.getAbsoluteFile());
            if(fileDest.exists()){
                Log.println(String.format("文件%s已存在，停止拷贝操作",fileDest.getAbsolutePath()));
                return;
            }
            destFiles[i] = fileDest.getAbsolutePath();
        }



        boolean isCopyFaile = false;
        for (int i = 0; i < destFiles.length; i++) {
            if(!FileWrite.copyFile(arrayList.get(i),destFiles[i])){
                isCopyFaile = true;
            }
        }

        //如果有文件Copy失败，删除所有已经Copy的文件
        if(isCopyFaile){
            for (String destFile : destFiles) {
                File file = new File(destFile);
                if(file.exists()){
                    file.delete();
                }
            }
        }else{
            sCopyBean.addCopyFile(argBean);
            FileWrite.writeFile(sCopyFilePath,sCopyBean);
        }



    }


}
