package com.zhihaoliang.decompile;

import java.util.ArrayList;

/**
 * Created by haoliang on 2017/10/16.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 */
public class CodeTool {

    private static final ArrayList<String> EMPTY_FILE = new ArrayList();
    static  {
        EMPTY_FILE.add("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        EMPTY_FILE.add("<resources>");
        EMPTY_FILE.add("</resources>");
    }

/*
    private String mCopyName;ƒƒ



    public static final void main(String[] args) {
        CodeTool codeTool = new CodeTool();
        codeTool.prepareParser();

        if (args == null || args.length == 0) {
            codeTool.getFilePath("@color/jz", null, false);
            //codeTool.getFilePath("@drawable/so", null, false);
        } else if (args.length == 1) {
            codeTool.getFilePath(args[0], null, false);
        } else if (args.length == 2) {
            codeTool.getFilePath(args[0], args[1], false);
        } else {
            codeTool.getFilePath(args[0], args[1], Boolean.parseBoolean(args[2]));
        }
    }




    private String isAlwayCopy(String[] array, ArrayList<String> copyNames) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : array) {
            stringBuffer.append(s);
            stringBuffer.append(MAKER);
        }

        String nameToPlace = stringBuffer.toString();

        for (String mCopyName : copyNames) {
            if (mCopyName.startsWith(nameToPlace)) {
                return "已经复制了 ： " + mCopyName;
            }
        }
        return null;
    }

    private void getFilePath(String content, String replaceName, boolean isWrite) {
        String[] array = getContent(content);
        if (replaceName == null || replaceName.length() == 0) {
            replaceName = array[array.length - 1];
        }

        if (isWrite) {
            mCopyName = MD5Encode(mRoot.getDestPath() + mRoot.getSrcPath()) + ".txt";
            ArrayList<String> copyNames = copyFileRead(mRoot.getSrcPath(), mCopyName);
            String copyName = isAlwayCopy(array, copyNames);
            if (copyName != null) {
                System.out.println(copyName);
                return;
            }
        }

        File file = new File(mRoot.getSrcPath());
        File resFile = new File(file, RES);
        String[] resNames = resFile.list();

        if (DRAW.equals(array[0])) {
            try {
                copyFile(resNames, resFile, array, replaceName, isWrite,DRAW);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        if(COLOR.equals(array[0])){
            try {
                copyFile(resNames, resFile, array, replaceName, isWrite,VALUES);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        for (String resName : resNames) {
            printlnLine(new File(resFile, resName), array, replaceName, isWrite);
        }
    }

    private void copyFile(String[] resNames, File resFile, String[] array, String replaceName, boolean isWrite,String... paths) throws IOException {
        for (String resName : resNames) {
            if (resName.startsWith(DRAW)) {
                File file = new File(resFile, resName);
                String[] listFile = file.list();
                for (String name : listFile) {
                    File xmlFile = new File(file, name);
                    if (xmlFile.getName().equals(array[array.length - 1] + ".xml")) {
                        System.out.println(xmlFile.getPath());
                        if (isWrite) {
                            copyToDestFile(xmlFile, replaceName);
                            readLineCopyFile(array, replaceName);
                        }
                        return;
                    }
                }


            }
        }

    }

    private void copyToDestFile(File srcPaFile, String replaceName) throws IOException {
        ArrayList<String> fileContent = readFileContent(srcPaFile);
        File destFileDir = new File(new File(mRoot.getDestPath()), RES);

        File destPaFile = new File(destFileDir, new File(srcPaFile.getParent()).getName());
        if (!destPaFile.exists()) {
            destPaFile.mkdir();
        }

        File destFile = new File(destPaFile, replaceName + ".xml");
        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        writeLineFile(destFile, fileContent);

    }


    private void printlnLine(File file, String[] content, String replaceName, boolean isWrite) {
        if (file.isDirectory()) {
            String[] fileChildNames = file.list();
            for (String fileChildName : fileChildNames) {
                File fileChild = new File(file, fileChildName);
                printlnLine(fileChild, content, replaceName, isWrite);
            }
            return;
        }

        readLineFile(file, content, replaceName, isWrite);
    }

    private void readLineFile(File file, String[] content, String replaceName, boolean isWrite) {

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
                if (isWrite) {
                    wirteFile(file, line, replaceName, content);
                }
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


    private ArrayList<String> copyFileRead(String path, String fileName) {
        File file = new File(new File(path), fileName);
        if (file.exists()) {
            return readFileContent(file);
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
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

    private void wirteFile(File file, String line, String replaceName, String[] contents) throws IOException {

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
            line = line.replace(contents[contents.length - 1], replaceName);
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
                readLineCopyFile(contents, replaceName);
                return;
            }
        }
    }

    private void readLineCopyFile(String[] contents, String replaceName) {
        if (mCopyName != null) {
            StringBuffer stringBuffer = new StringBuffer();
            for (String s : contents) {
                stringBuffer.append(s);
                stringBuffer.append(MAKER);
            }

            stringBuffer.append(replaceName);
            stringBuffer.append("\n\r");
            writeFileEnd(new File(mRoot.getSrcPath(), mCopyName).getAbsolutePath(), stringBuffer.toString());
            mCopyName = null;
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
    }*/

}
