package com.cn.yc.utils;

import java.io.*;

/**
 * Created by DT167 on 2018/3/15.
 */
public class FileReadUtils {
    public static String readFileReturnString(String filePath) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        String pathname = filePath;
        File filename = new File(pathname);
        /*InputStreamReader reader = new InputStreamReader(
                new FileInputStream(filename)); // 建立一个输入流对象reader
        BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
        if ((line = br.readLine()) != null) {
            stringBuilder.append(line);
        }*/
        InputStreamReader reader = new InputStreamReader(
                new FileInputStream(filename));
        int tempchar;
        while ((tempchar = reader.read()) != -1) {
            // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
            // 但如果这两个字符分开显示时，会换两次行。
            // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
            if (((char) tempchar) != '\r') {
                stringBuilder.append((char) tempchar);
            }
        }
        reader.close();
        return stringBuilder.toString();
    }

    public static void appendFile(String filePath, String text) {
        FileWriter fw = null;
        try {//如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f = new File(filePath);
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(text);
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(String filePath, String text) {
        FileWriter fw = null;
        try {//如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f = new File(filePath);
            fw = new FileWriter(f,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(text);
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
