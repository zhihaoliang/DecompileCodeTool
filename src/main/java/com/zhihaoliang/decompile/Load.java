package com.zhihaoliang.decompile;

import com.zhihaoliang.decompile.bean.ConfigBean;
import com.zhihaoliang.decompile.bean.res.ResColor;
import com.zhihaoliang.decompile.bean.res.ResDimen;
import com.zhihaoliang.decompile.bean.res.ResString;
import com.zhihaoliang.decompile.bean.Resources;
import com.zhihaoliang.decompile.bean.res.base.Res;
import com.zhihaoliang.decompile.util.Arg;
import com.zhihaoliang.decompile.util.FileFind;
import com.zhihaoliang.decompile.util.Log;
import com.zhihaoliang.decompile.util.XmlParser;

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

    public static final void main(String[] args) {
        if (CONFIG_BEAN == null) {
            return;
        }

        CONFIG_BEAN.getStrings();
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
            args = Arg.getArgs(read);
            Log.println(args);
            if (args == null) {
                Log.println(USER_USE);
            } else {
                oprateSystemLine(args);
            }
        }
    }

    private static void load() {
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


    private static void oprateSystemLine(String[] systemLine) {
        String[] state = Arg.checkSystemLine(systemLine[0]);
        boolean isCopy = Boolean.parseBoolean(systemLine[1]);
        switch (state[0]) {
            case Config.FIND_FILE:
                FileFind.fileFind(systemLine[0], CONFIG_BEAN);
                break;
            case Config.DRAW:
            case Config.LAYOUT:
            case Config.MIPMAP:
                ArrayList<String> arrayList = FileFind.fileFind(state, CONFIG_BEAN);
                Log.println(arrayList);
                break;
            case Config.STRING:
            case Config.DIMEN:
                ArrayList<Res> resList = getRes(state, false);
                Log.println(resList);
                break;
            case Config.COLOR:
                ArrayList<String> files = FileFind.fileFind(state, CONFIG_BEAN);
                if (files == null || files.size() == 0) {
                    resList = getRes(state, false);
                    Log.println(resList);
                } else {
                    Log.println(files);
                }
                break;
            default:
                Log.println(USER_USE);
                break;
        }
    }

    private static ArrayList<Res> getRes(String[] state, boolean isDest) {
        ArrayList<Res> resList = new ArrayList<>();

        ArrayList<Resources> resourcesList = getResources(state[0], isDest);
        for (Resources resources : resourcesList) {
            ArrayList list = getResources(state[0], resources);
            for (Object item : list) {
                Res res = (Res) item;
                if (state[1].equals(res.getName())) {
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


}
