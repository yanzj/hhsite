package com.vilio.bps.util;

import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * Created by xingwei on 2017/2/21.
 */
public class CompressUtil {
    private Logger logger = Logger.getLogger(CompressUtil.class);
    static final int BUFFER = 8192;
    private File zipFile;

    /**
     * 压缩文件构造函数
     * @param pathName 压缩的文件存放目录
     */
    public CompressUtil(String pathName) {
        zipFile = new File(pathName);
    }

    /**
     * 执行压缩操作
     * @param srcPathName 被压缩的文件/文件夹
     */
    public void compressExe(String srcPathName) {
        File file = new File(srcPathName);
        if (!file.exists()){
            throw new RuntimeException(srcPathName + "不存在！");
        }
        try {
            ZipArchiveOutputStream out = new ZipArchiveOutputStream(zipFile);
            String basedir = "";
            compressByType(file, out, basedir);
            out.finish();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行压缩操作时发生异常:"+e);
            throw new RuntimeException(e);
        }
    }

    public void compressExe(String srcPathName,ZipArchiveOutputStream out,String fileName) {
        File file = new File(srcPathName);
        if (!file.exists()){
            throw new RuntimeException(srcPathName + "不存在！");
        }
        try {
            compressFiles2Zip(file, out, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行压缩操作时发生异常:"+e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 执行压缩操作 仅针对输入流
     *
     */
//    public void compressExe(OutputStream os,InputStream is,String fileName) {
//        try {
//            String basedir = "";
////            compressByType(file, out, basedir);
//            compressFiles2Zip(is,os,fileName);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("执行压缩操作时发生异常:"+e);
//            throw new RuntimeException(e);
//        }
//    }

    /**
     * 判断是目录还是文件，根据类型（文件/文件夹）执行不同的压缩方法
     * @param file
     * @param out
     * @param basedir
     */
    private void compressByType(File file, ZipArchiveOutputStream out, String basedir) {
        /* 判断是目录还是文件 */
        if (file.isDirectory()) {
            logger.info("压缩：" + basedir + file.getName());
            this.compressDirectory(file, out, basedir);
        } else {
            logger.info("压缩：" + basedir + file.getName());
            this.compressFiles2Zip(file, out, basedir);
        }
    }

    /**
     * 压缩一个目录
     * @param dir
     * @param out
     * @param basedir
     */
    private void compressDirectory(File dir, ZipArchiveOutputStream out, String basedir) {
        if (!dir.exists()){
            return;
        }

        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            /* 递归 */
            compressByType(files[i], out, basedir + dir.getName() + "/");
        }
    }

    /**
     * 压缩一个文件流
     * @param fileIs 文件的输入流
     * @param fileName 待压缩文件的名字
     * @param out
     * @param basedir
     */
    private void compressFile(InputStream fileIs, String fileName , ZipOutputStream out, String basedir) {
        try {
            BufferedInputStream bis = new BufferedInputStream(fileIs);
            ZipEntry entry = new ZipEntry(basedir + fileName);
            out.putNextEntry(entry);
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = bis.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
            bis.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 压缩一个文件
     * @param file
     * @param out
     * @param basedir
     */
    private void compressFile(File file, ZipOutputStream out, String basedir) {
        if (!file.exists()) {
            return;
        }
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            ZipEntry entry = new ZipEntry(basedir + file.getName());
            out.putNextEntry(entry);
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = bis.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
            bis.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void compressFiles2Zip(File file, ZipArchiveOutputStream zaos, String fileName) {
        try {
//Use Zip64 extensions for all entries where they are required
            zaos.setUseZip64(Zip64Mode.AsNeeded);

//将每个文件用ZipArchiveEntry封装
//再用ZipArchiveOutputStream写到压缩文件中
            if (file != null) {
                ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file, fileName);
                zaos.putArchiveEntry(zipArchiveEntry);
                InputStream is = null;
                try {
                    is = new FileInputStream(file);
                    byte[] buffer = new byte[1024 * 5];
                    int len = -1;
                    while ((len = is.read(buffer)) != -1) {
//把缓冲区的字节写入到ZipArchiveEntry
                        zaos.write(buffer, 0, len);
                    }
//Writes all necessary data for this entry.
                    zaos.closeArchiveEntry();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    if (is != null)
                        is.close();
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
        }
    }
}
