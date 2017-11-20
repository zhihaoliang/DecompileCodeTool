package com.zhihaoliang.decompile.util;

import com.zhihaoliang.decompile.bean.ArgBean;
import com.zhihaoliang.decompile.bean.copy.CopyFile;
import com.zhihaoliang.decompile.bean.info.CopyInfoBean;
import com.zhihaoliang.decompile.bean.info.FileAlreadyCopy;
import com.zhihaoliang.decompile.bean.info.FileUnCopy;

import java.util.ArrayList;

/**
 * Created by haoliang on 2017/11/15.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 */
public class Check {
    /**
     * @param copyFilePaths 已经copy过的文件列表
     * @param filePaths     要copy的文件目录
     * @return 拷贝的信息
     */
    public static CopyInfoBean splitCopyAndNo(ArrayList<CopyFile> copyFilePaths, ArrayList<String> filePaths) {
        if (filePaths == null || filePaths.size() == 0) {
            return null;
        }

        CopyInfoBean copyInfoBean = new CopyInfoBean();
        FileUnCopy fileUnCopy = new FileUnCopy();
        copyInfoBean.setFileUnCopy(fileUnCopy);
        if (copyFilePaths == null || copyFilePaths.size() == 0) {
            fileUnCopy.setUnCopyFiles(filePaths);
            return copyInfoBean;
        }


        ArrayList<CopyFile> copys = new ArrayList<>();
        ArrayList<String> no = new ArrayList<>();

        for (String filePath : filePaths) {
            CopyFile copyFile = isAlreadyCopyFile(copyFilePaths, filePath);
            if (copyFile == null) {
                no.add(filePath);
            } else {
                copys.add(copyFile);
            }
        }

        fileUnCopy.setUnCopyFiles(no);

        FileAlreadyCopy fileAlreadyCopy = new FileAlreadyCopy();
        fileAlreadyCopy.setAlreadyCopyFiles(copys);

        return copyInfoBean;
    }

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


    /**
     * @param copyFilePaths 已经copy过的文件列表
     * @param filePath      要copy的文件目录
     * @return null 表示没有进行copy ，反之表示文件已经copy过
     */
    private static CopyFile isAlreadyCopyFile(ArrayList<CopyFile> copyFilePaths, String filePath) {
        for (CopyFile copyFile : copyFilePaths) {
            if (copyFile.getSrcPath().equals(filePath)) {
                return copyFile;
            }
        }
        return null;
    }
}
