package com.zhihaoliang.decompile;

import com.thoughtworks.xstream.XStream;
import com.zhihaoliang.decompile.bean.Root;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by haoliang on 2017/10/16.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 */
public class CodeTool {

    /**
     * res 路径中的values
     */
    private static final String VALUES = "values";
    /**
     * Android 工程中的res路径
     */
    private static final String RES = "res";
    /**
     * Android 工程中draw的路径
     */
    private static final String DRAW = "draw";
    /**
     * Android 工程中layout的路径
     */
    private static final String LAYOUT = "layout";
    /**
     * 文件中的resources
     */
    private static final String RESOURCES = "resources";
    /**
     * 存放设置的Config的路径
     */
    private Root mRoot;

    private final static ArrayList<String> EMPTY_FILE = new ArrayList<>();


    static {
        EMPTY_FILE.add("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        EMPTY_FILE.add("<resources>");
        EMPTY_FILE.add("</resources>");
    }

    public static final void main(String[] args) {
        CodeTool codeTool = new CodeTool();
        codeTool.prepareParser();
        if (args == null || args.length == 0) {
            codeTool.getFilePath("@dimen/aok");
        } else if (args.length == 1) {
            codeTool.getFilePath(args[0], null);
        } else {
            codeTool.getFilePath(args[0], args[1]);
        }
    }


    private void prepareParser() {
        File file = new File("Config.xml");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            XStream xstr = new XStream();
            xstr.alias("root", Root.class);
            mRoot = (Root) xstr.fromXML(fileInputStream);
            System.out.println(mRoot.getSrcPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void getFilePath(String content) {
        getFilePath(content, null);
    }

    private void getFilePath(String content, String replaceName) {
        String[] array = getContent(content);
        File file = new File(mRoot.getSrcPath());
        File resFile = new File(file, RES);
        String[] resNames = resFile.list();
        for (String resName : resNames) {
            printlnLine(new File(resFile, resName), array, replaceName);
        }
    }

    private void printlnLine(File file, String[] content, String replaceName) {
        if (file.isDirectory()) {
            String[] fileChildNames = file.list();
            for (String fileChildName : fileChildNames) {
                File fileChild = new File(file, fileChildName);
                printlnLine(fileChild, content, replaceName);
            }
            return;
        }

        readLineFile(file, content, replaceName);
    }

    private void readLineFile(File file, String[] content, String replaceName) {

        if (file.getName().equals("public.xml")) {
            return;
        }

        try {
            FileInputStream in = new FileInputStream(file.getAbsolutePath());
            InputStreamReader inReader = new InputStreamReader(in, "UTF-8");
            BufferedReader bufReader = new BufferedReader(inReader);
            String line = null;
            int i = 1;
            OUT:
            while ((line = bufReader.readLine()) != null) {
                for (String s : content) {
                    if (!line.contains(s)) {
                        continue OUT;
                    }
                }
                wirteFile(file, line, replaceName, content[content.length - 1]);
                System.out.println(file.getPath() + ": 第" + i + "行：" + line);
                i++;
            }
            bufReader.close();
            inReader.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("读取" + file.getAbsolutePath() + "出错！");
        }
    }

    private String[] getContent(String content) {
        if (content.contains("@") && content.contains("/")) {
            content = content.replaceAll("@", "");
            String[] array = content.split("/");
            return array;
        }
        return new String[]{content};
    }


    private ArrayList<String> readFileContent(File file) {
        ArrayList<String> arrayList = new ArrayList();
        try {
            FileInputStream in = new FileInputStream(file.getAbsolutePath());
            InputStreamReader inReader = new InputStreamReader(in, "UTF-8");
            BufferedReader bufReader = new BufferedReader(inReader);
            String line = null;
            OUT:
            while ((line = bufReader.readLine()) != null) {
                arrayList.add(line);
            }
            bufReader.close();
            inReader.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("读取" + file.getAbsolutePath() + "出错！");
        }
        return arrayList;
    }

    private void wirteFile(File file, String line, String replaceName, String oldName) throws IOException {

        File srcPaFile = file.getParentFile();

        if (srcPaFile.getName().contains("layout")) {
            return;
        }

        File destFileDir = new File(new File(mRoot.getDestPath()), RES);

        File destPaFile = new File(destFileDir, srcPaFile.getName());
        if (!destPaFile.exists()) {
            destPaFile.mkdir();
        }

        File destFile = new File(destPaFile, file.getName());
        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        if (replaceName != null) {
            line = line.replace(oldName, replaceName);
        }

        ArrayList<String> arrayList = readFileContent(destFile);
        if (arrayList == null || arrayList.size() == 0) {
            arrayList.addAll(EMPTY_FILE);
        } else {
            for (String s : arrayList) {
                if (s.contains(line)) {
                    return;
                }
            }
        }


        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).contains(RESOURCES)) {
                arrayList.add(i + 1, line);
                writeLineFile(destFile, arrayList);
                return;
            }
        }
    }

    private void writeFileEnd(String fileName, String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeLineFile(File file, ArrayList<String> arrayList) {
        try {
            System.out.println(file.getName() + arrayList.size());
            FileOutputStream out = new FileOutputStream(file.getAbsolutePath());
            OutputStreamWriter outWriter = new OutputStreamWriter(out, "UTF-8");
            BufferedWriter bufWrite = new BufferedWriter(outWriter);
            for (String s : arrayList) {
                bufWrite.write(s + "\r\n");
            }
            bufWrite.close();
            outWriter.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("写入" + file.getPath() + "出错！");
        }
    }

}
