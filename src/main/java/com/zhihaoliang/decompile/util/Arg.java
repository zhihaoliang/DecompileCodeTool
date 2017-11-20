package com.zhihaoliang.decompile.util;

import com.zhihaoliang.decompile.Config;
import com.zhihaoliang.decompile.bean.ArgBean;

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
    private static String[] getArgs(String systemLine) {

        if (systemLine == null || systemLine.length() == 0) {
            return null;
        }

        systemLine = systemLine.trim();

        String[] args = systemLine.split(" ");
        ArrayList<String> list = new ArrayList<>();
        for (String arg : args) {
            if (arg.length() > 0) {
                list.add(arg);
            }
        }

        if (list.size() == 1) {
            list.add("false");
        }

        if (!("false".equals(list.get(1)) || "true".equals(list.get(1)))) {
            return null;
        }

        if (list.size() > 3) {
            return null;
        }

        String[] result = new String[list.size()];
        return list.toArray(result);
    }

    /**
     *
     * @param systemLine 用户的输入
     * @return 返回 解析后输入
     */
    public static ArgBean initArgs(String systemLine) {
        ArgBean argBean = new ArgBean();
        String[] args = getArgs(systemLine);
        if (args == null || args[0].endsWith("/")) {
            argBean.setState(Config.ERROR);
            return argBean;
        }

        int indexAt = args[0].indexOf("@");
        int index = args[0].indexOf("/");
        if (indexAt == index) {
            argBean.setState(Config.FIND_FILE);
            argBean.setOldName(args[0]);
            argBean.setCopy(Boolean.parseBoolean(args[1]));
            if (args.length == 3) {
                argBean.setNewName(args[2]);
            } else {
                argBean.setNewName(args[0]);
            }
            return argBean;
        }

        if (args[0].endsWith("/")) {
            argBean.setState(Config.ERROR);
            return argBean;
        }

        if (indexAt == 0 && index > 0) {
            args[0] = args[0].replaceAll("@", "");
            String[] argsOne = args[0].split("/");
            if (argsOne.length != 2) {
                argBean.setState(Config.ERROR);
                return argBean;
            } else {
                argBean.setState(argsOne[0]);
                argBean.setOldName(argsOne[1]);
                argBean.setCopy(Boolean.parseBoolean(args[1]));
                if (args.length == 3) {
                    argBean.setNewName(args[2]);
                } else {
                    argBean.setNewName(argsOne[1]);
                }
            }
            return argBean;
        } else {
            argBean.setState(Config.ERROR);
            return argBean;
        }
    }

}
