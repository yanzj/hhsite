package com.vilio.fms.util;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * Created by dell on 2017/6/5.
 */
public class ZipUtil {
    private static final String zipTempFile = "./zipTempFile/";

    private static Logger logger = Logger.getLogger(ZipUtil.class);

    public static void writeZip(String[] strs, String zipname) throws IOException {
        String[] files = strs;
        OutputStream os = new BufferedOutputStream(new FileOutputStream("./tempFile/" + zipname + ".zip"));
        ZipOutputStream zos = new ZipOutputStream(os);
        byte[] buf = new byte[8192];
        int len;
        for (int i = 0; i < files.length; i++) {
            File file = new File(files[i]);
            if (!file.isFile()) continue;
            ZipEntry ze = new ZipEntry(file.getName());
            zos.putNextEntry(ze);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            while ((len = bis.read(buf)) > 0) {
                zos.write(buf, 0, len);
            }
            zos.closeEntry();
        }
        zos.setEncoding("gbk");
        zos.closeEntry();
        zos.close();

        for (int i = 0; i < files.length; i++) {
            File file = new File(files[i]);
            file.delete();
        }
    }

    /**
     * @param files
     * @param zipname
     * @return
     * @throws IOException
     */
    public static InputStream writeZip(MultipartFile[] files, String zipname) throws IOException {
        File outFile = new File(zipTempFile + zipname + ".zip");
        File parentFile = outFile.getParentFile();

        boolean flag = false;
        //如果输出目标文件夹不存在，则创建
        if (!parentFile.exists()) {
            flag = parentFile.mkdirs();
        }
        if (!outFile.exists()) {
            flag = outFile.createNewFile();
        }

        OutputStream os = new BufferedOutputStream(new FileOutputStream(zipTempFile + zipname + ".zip"));

        ZipOutputStream zos = new ZipOutputStream(os);
        byte[] buf = new byte[8192];
        int len;
        for (int i = 0; i < files.length; i++) {
            ZipEntry ze = new ZipEntry(((CommonsMultipartFile) files[i]).getFileItem().getName());
            zos.putNextEntry(ze);
            BufferedInputStream bis = new BufferedInputStream(files[i].getInputStream());
            while ((len = bis.read(buf)) > 0) {
                zos.write(buf, 0, len);
            }
            zos.closeEntry();
        }
        zos.setEncoding("gbk");
        zos.closeEntry();
        zos.close();

        os.close();

        InputStream returnInputStream = new FileInputStream(zipTempFile + zipname + ".zip");

        return returnInputStream;
    }

    /**
     * 删出生成的本地压缩文件
     *
     * @param zipname 压缩文件的文件名（不含后缀）
     * @return 删除是否成功
     * @throws IOException
     */
    public static boolean deleteZipFile(String zipname, InputStream in) throws IOException {
        if (null != in) {
            in.close();
        }
        boolean deleteSuccess = false;
        //删除本地文件
        File localFile = new File(zipTempFile + zipname + ".zip");

        if (localFile.exists() && localFile.isFile()) {
            deleteSuccess = localFile.delete();
        }

        return deleteSuccess;

    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }


    public static InputStream writeZip(List<Map<String, InputStream>> streamList, String zipname) throws IOException {
        zipname = zipTempFile + (zipname.endsWith(".zip") ? zipname : zipname + ".zip");

        File outFile = new File(zipname);
        File parentFile = outFile.getParentFile();

        boolean flag = false;
        //如果输出目标文件夹不存在，则创建
        if (!parentFile.exists()) {
            flag = parentFile.mkdirs();
        }
        if (!outFile.exists()) {
            flag = outFile.createNewFile();
        }


        byte[] buffer = new byte[1024];
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(zipname));

            for (int i = 0, len = streamList.size(); i < len; i++) {
                Map<String, InputStream> inputStreamMap = streamList.get(i);
                Iterator it = inputStreamMap.keySet().iterator();
                while (it.hasNext()) {
                    String name = (String) it.next();
                    out.putNextEntry(new ZipEntry(name));
                    InputStream in = inputStreamMap.get(name);
                    int dataLen;
                    //读入需要下载的文件的内容，打包到zip文件
                    while ((dataLen = in.read(buffer)) > 0) {
                        out.write(buffer, 0, dataLen);
                    }
                    out.closeEntry();
                    in.close();
                }

            }
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //读取压缩包
        File filezip = new File(zipname);

        InputStream returnIn = new FileInputStream(filezip);

        return returnIn;
    }

    /**
     * 创建ZIP文件
     *
     * @param sourcePath 文件或文件夹路径
     * @param zipPath    生成的zip文件存在路径（包括文件名）
     */
    public static void createZip(String sourcePath, String zipPath) {
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipPath);
            zos = new ZipOutputStream(fos);
            zos.setEncoding("gbk");//此处修改字节码方式。
            //createXmlFile(sourcePath,"293.xml");
            writeZip(new File(sourcePath), "", zos);
        } catch (FileNotFoundException e) {
            logger.error("创建ZIP文件失败", e);
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (IOException e) {
                logger.error("创建ZIP文件失败", e);
            }

        }
    }


    private static void writeZip(File file, String parentPath, ZipOutputStream zos) {
        if (file.exists()) {
            if (file.isDirectory()) {//处理文件夹
                parentPath += file.getName() + File.separator;
                File[] files = file.listFiles();
                if (files.length != 0) {
                    for (File f : files) {
                        writeZip(f, parentPath, zos);
                    }
                } else {       //空目录则不创建当前目录
                    try {
                        zos.putNextEntry(new ZipEntry(parentPath));
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } else {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    ZipEntry ze = new ZipEntry(parentPath + file.getName());
                    zos.putNextEntry(ze);
                    byte[] content = new byte[8192];
                    int len;
                    while ((len = fis.read(content)) != -1) {
                        zos.write(content, 0, len);
                        zos.flush();
                    }

                } catch (FileNotFoundException e) {
                    logger.error("创建ZIP文件失败", e);
                } catch (IOException e) {
                    logger.error("创建ZIP文件失败", e);
                } finally {
                    try {
                        if (fis != null) {
                            fis.close();
                        }
                    } catch (IOException e) {
                        logger.error("创建ZIP文件失败", e);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        String[] strs = {"D:\\ss/JavaScript语言精粹.pdf", "D:\\ss/seo实战密码.pdf", "D:\\ss/蚂蚁金服.pdf"};
        //ZipUtil.writeZip(strs, "test");
        createZip("D:\\Drivers\\", "D:\\Driverzip.zip");
    }
}
