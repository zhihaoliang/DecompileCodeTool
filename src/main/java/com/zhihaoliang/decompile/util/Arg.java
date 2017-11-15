package com.zhihaoliang.decompile.util;

import com.zhihaoliang.decompile.Config;

import java.util.ArrayList;

/**
 * Created by haoliang on 2017/11/9.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 *         <p>
 *         用于解析用户输入
 */
public class Arg {
    /**
     * @param systemLine 用户的输入
     * @return 返回解析后的数据格式为 {search ，false}或{search ，true}
     * 其中false表示只是打印
     * 其中true除了打印之外，还要进行拷贝
     */
    public static  String[] getArgs(String systemLine) {
        String[] args = systemLine.split(" ");
        ArrayList<String> list = new ArrayList<>();
        for (String arg : args) {
            if (arg.length() > 0) {
                list.add(arg);
            }
        }

        if (list.size() == 1) {
            return new String[]{list.get(0), "false"};
        } else if (list.size() == 2) {
            args = new String[list.size() + 1];
            if ("false".equals(list.get(1)) || "true".equals(list.get(1))) {
                args[0] = list.get(0);
                args[1] = list.get(1);
                args[2] = list.get(0);
                return args;
            }
            return null;
        } else if (list.size() == 3) {
            args = new String[list.size()];
            if ("false".equals(list.get(1)) || "true".equals(list.get(1))) {
                return list.toArray(args);
            }
            return null;
        } else {
            return null;
        }

    }

    /**
     * @param systemLine 用户的输入的第一个字段
     * @return String[]{Config.ERROR}表示用户输入错误，
     * String[]{Config.FIND_FILE}表示文件搜索，
     * String[]{string ,name}表示文件内容的搜索
     */
    public static  String[] checkSystemLine(String systemLine) {
        if (systemLine == null) {
            return new String[]{Config.ERROR};
        }

        if (systemLine.replaceAll(" ", "").length() == 0) {
            return new String[]{Config.ERROR};
        }

        if(systemLine.endsWith("/")){
            return new String[]{Config.ERROR};
        }

        int indexAt = systemLine.indexOf("@");
        int index = systemLine.indexOf("/");

        if (indexAt == index) {
            return new String[]{Config.FIND_FILE};
        }

        if (indexAt == 0) if (index > 0) {
            systemLine = systemLine.replaceAll("@", "");
            return systemLine.split("/");
        } else {
            return new String[]{Config.ERROR};
        }

        return new String[]{Config.FIND_FILE};
    }

}
