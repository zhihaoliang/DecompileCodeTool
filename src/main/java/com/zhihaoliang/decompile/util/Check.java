package com.zhihaoliang.decompile.util;

import com.zhihaoliang.decompile.bean.ArgBean;

import java.util.ArrayList;

/**
 * Created by haoliang on 2017/11/15.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 */
public class Check {

    public static ArgBean splitCopyAndNo(ArrayList<ArgBean> argBeans, ArgBean argBean) {
        if(argBeans == null || argBeans.size() ==0){
            return null;
        }

        for (ArgBean bean : argBeans) {
            if(bean.equals(argBean)){
                return bean;
            }
        }

        return null;

    }


}
