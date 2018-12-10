package com.miqiang.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件操作工具类
 *
 * @author miqiang
 * @date 17/2/4
 */
public class FileUtil {

    /**
     * 读取文件中的字符串数据
     * @param file
     * @return
     */
    public static String readString(File file){
        String str;
        try {
            FileInputStream in=new FileInputStream(file);
            // 一次性读完
            int size=in.available();
            byte[] buffer=new byte[size];
            in.read(buffer);
            str=new String(buffer,"UTF-8");
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return str;
    }

    /**
     * 文件中追加字符串
     * @param file      文件路径和名称
     * @param conent    要追加的字符串内容
     */
    public static void append(String file, String conent) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write("\r\n"+conent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
