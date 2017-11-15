package com.zhihaoliang.decompile.util;

import com.zhihaoliang.decompile.bean.ConfigBean;

import java.io.*;

/**
 * Created by haoliang on 2017/11/9.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 *         文件的写入
 */
public class FileWrite {

    /**
     * 用文件的拷贝
     *
     * @param sourcePath 文件的原目录
     * @param destPath   文件要拷贝到的目录
     * @return true表示拷贝成功，false拷贝成功
     */
    private static final boolean copyFile(String sourcePath, String destPath) {
        File destFile = new File(destPath);
        if (destFile.exists()) {
            Log.println(String.format("文件拷贝失败，失败原因：%s文件已经存在", destPath));
            return false;
        }

        File parentFile = destFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        try {
            destFile.createNewFile();
        } catch (IOException e) {
            Log.println(String.format("文件%s创建失败，失败原因%s", destPath, e.toString()));
            e.printStackTrace();
            return false;
        }

        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(sourcePath);
            output = new FileOutputStream(destPath);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } catch (Exception e) {
            Log.println(String.format("文件%s写入失败,失败原因", destPath, e.toString()));
            return false;
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (Exception e) {

            }

        }
        Log.println(String.format("文件%s拷贝到%s成功", sourcePath, destPath));
        return true;
    }

    /**
     * 用文件的拷贝
     *
     * @param sourcePath 表示要操作的原目录
     * @param root       表示存放Config.xml的目录
     * @return true表示拷贝成功，false拷贝成功
     */
    private static final boolean copyFile(String sourcePath, ConfigBean root) {
        String destPath = sourcePath.replace(root.getSrcPath(), root.getDestPath());
        return copyFile(sourcePath, destPath);
    }

}
