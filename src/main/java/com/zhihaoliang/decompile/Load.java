package com.zhihaoliang.decompile;

import com.google.gson.Gson;
import com.zhihaoliang.decompile.bean.ArgBean;
import com.zhihaoliang.decompile.bean.ConfigBean;
import com.zhihaoliang.decompile.bean.copy.CopyBean;
import com.zhihaoliang.decompile.bean.res.Resources;
import com.zhihaoliang.decompile.bean.res.Res;
import com.zhihaoliang.decompile.util.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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

    private static final Logger LOG = LogManager.getLogger(Load.class);
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
        LOG.info("加载中...");
        load();
        LOG.info("加载完成");
        LOG.info(USER_USE);

        while (true) {
            Scanner scan = new Scanner(System.in);
            String read = scan.nextLine();
            if ("exit".equalsIgnoreCase(read)) {
                LOG.fatal("程序解析完成");
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

            ArrayList<Res> strings = res.getStrings();
            if (strings != null && strings.size() > 0) {
                SRC_STR.add(res);
            }

            ArrayList<Res> dimens = res.getDimens();
            if (dimens != null && dimens.size() > 0) {
                SRC_DIMEN.add(res);
            }

            ArrayList<Res> colors = res.getColors();
            if (colors != null && colors.size() > 0) {
                SRC_COLOR.add(res);
            }

            LOG.info(String.format("原文件%s加载完成", s));
        }
        arrayList = FileFind.fileValueFind(CONFIG_BEAN.getDestPath());
        for (String s : arrayList) {
            Resources res = (Resources) XmlParser.getInstance().parser(s, new Resources());
            if (res == null) {
                continue;
            }
            res.setFilePath(s);

            ArrayList<Res> strings = res.getStrings();
            if (strings != null && strings.size() > 0) {
                DEST_STR.add(res);
            }

            ArrayList<Res> dimens = res.getDimens();
            if (dimens != null && dimens.size() > 0) {
                DEST_DIMEN.add(res);
            }

            ArrayList<Res> colors = res.getColors();
            if (colors != null && colors.size() > 0) {
                DEST_COLOR.add(res);
            }

            LOG.info(String.format("目标文件%s加载完成", s));
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
                copyContent(resList, argBean);
                break;
            case Config.COLOR:
                ArrayList<String> files = FileFind.fileFind(argBean, CONFIG_BEAN);
                if (files == null || files.size() == 0) {
                    resList = getRes(argBean, false);
                    copyContent(resList, argBean);
                } else {
                    copyFile(files, argBean);
                }
                break;
            default:
                LOG.info(USER_USE);
                break;
        }
    }

    private static ArrayList<Res> getRes(ArgBean argbean, boolean isDest) {
        ArrayList<Res> resList = new ArrayList<>();
        String name = isDest ? argbean.getNewName() : argbean.getOldName();
        ArrayList<Resources> resourcesList = getResources(argbean.getState(), isDest);
        for (Resources resources : resourcesList) {
            ArrayList<Res> list = getResources(argbean.getState(), resources);
            for (Res res : list) {
                if (name.equals(res.getName())) {
                    res.setFilePath(resources.getFilePath());
                    resList.add(res);
                }
            }
        }

        return resList;
    }

    private static ArrayList<Res> getResources(String state, Resources resources) {
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

    private static void copyContent(ArrayList<Res> resList, ArgBean argBean) {
        if (!argBean.isCopy()) {
            LOG.fatal(new Gson().toJson(resList));
            return;
        }

        if (resList == null || resList.size() == 0) {
            LOG.fatal("没有找到要拷贝的文件");
            return;
        }

        ArgBean argBeanResult = Check.splitCopyAndNo(sCopyBean.getArgBeans(), argBean);

        if(isOprate(argBeanResult)){
            return;
        }

        ArrayList<Res> desList = getRes(argBean, true);
        if (desList != null && desList.size() > 0) {
            LOG.fatal(String.format("名字\"%s\"已经存在当前文件中：",argBean.getNewName()));
            LOG.fatal(XmlParser.ob2Str(argBean));
            return;
        }


        for (Res res : resList) {
            String destFilePath = res.getFilePath().replace(CONFIG_BEAN.getSrcPath(), CONFIG_BEAN.getDestPath());
            Resources resource = (Resources) XmlParser.getInstance().parser(destFilePath, new Resources());
            if (resource == null) {
                resource = new Resources();
            }
            Res add = new Res();
            add.setName(argBean.getNewName());
            add.setValue(res.getValue());
            resource.addRes(add, argBean.getState());
            FileWrite.writeFile(destFilePath, resource);
        }

        sCopyBean.addCopyFile(argBean);
        FileWrite.writeFile(sCopyFilePath, sCopyBean);
        LOG.fatal("内容copy完成");

    }

    private static void copyFile(ArrayList<String> arrayList, ArgBean argBean) {
        if (!argBean.isCopy()) {
            LOG.fatal(new Gson().toJson(arrayList));
            return;
        }

        if (arrayList == null || arrayList.size() == 0) {
            LOG.fatal("没有找到要拷贝的文件");
            return;
        }

        ArgBean argBeanResult = Check.splitCopyAndNo(sCopyBean.getArgBeans(), argBean);
        if(isOprate(argBeanResult)){
            return;
        }


        String[] destFiles = new String[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            destFiles[i] = arrayList.get(i).replace(CONFIG_BEAN.getSrcPath(), CONFIG_BEAN.getDestPath());
            File file = new File(destFiles[i]);
            String name = file.getName();
            String destName = name.replace(argBean.getOldName(), argBean.getNewName());
            File fileDest = new File(file.getParentFile(), destName);
            if (fileDest.exists()) {
                LOG.fatal(String.format("文件%s已存在，停止拷贝操作", fileDest.getAbsolutePath()));
                return;
            }
            destFiles[i] = fileDest.getAbsolutePath();
        }


        boolean isCopyFaile = false;
        for (int i = 0; i < destFiles.length; i++) {
            if (!FileWrite.copyFile(arrayList.get(i), destFiles[i])) {
                isCopyFaile = true;
            }
        }

        //如果有文件Copy失败，删除所有已经Copy的文件
        if (isCopyFaile) {
            for (String destFile : destFiles) {
                File file = new File(destFile);
                if (file.exists()) {
                    file.delete();
                }
            }
        } else {
            sCopyBean.addCopyFile(argBean);
            FileWrite.writeFile(sCopyFilePath, sCopyBean);
        }


    }

    /**
     * @param argBeanResult 返回当前的文件
     * 文件的已经进行copy
     */
    private static boolean isOprate(ArgBean argBeanResult){
        if (argBeanResult != null) {
            LOG.fatal("数据已经存在,上一次的拷贝信息为：");
            LOG.fatal(XmlParser.ob2Str(argBeanResult));
            return true;
        }
        return false;
    }


}
