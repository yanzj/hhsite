package com.vilio.fms.util;

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

    public static void writeZip(String[] strs,String zipname) throws IOException {
        String[] files = strs;
        OutputStream os = new BufferedOutputStream( new FileOutputStream( "./tempFile/" + zipname +".zip" ) );
        ZipOutputStream zos = new ZipOutputStream( os );
        byte[] buf = new byte[8192];
        int len;
        for (int i=0;i<files.length;i++) {
            File file = new File( files[i] );
            if ( !file.isFile() ) continue;
            ZipEntry ze = new ZipEntry( file.getName() );
            zos.putNextEntry( ze );
            BufferedInputStream bis = new BufferedInputStream( new FileInputStream( file ) );
            while ( ( len = bis.read( buf ) ) > 0 ) {
                zos.write( buf, 0, len );
            }
            zos.closeEntry();
        }
        zos.setEncoding("gbk");
        zos.closeEntry();
        zos.close();

        for(int i=0;i<files.length;i++){
            File file= new File(files[i] );
            file.delete();
        }
    }

    /**
     *
     * @param files
     * @param zipname
     * @return
     * @throws IOException
     */
    public static InputStream writeZip(MultipartFile[] files, String zipname) throws IOException {
        File outFile = new File(zipTempFile + zipname + ".zip" );
        File parentFile = outFile.getParentFile();

        boolean flag = false;
        //如果输出目标文件夹不存在，则创建
        if (!parentFile.exists()){
            flag = parentFile.mkdirs();
        }
        if(!outFile.exists()){
            flag = outFile.createNewFile();
        }

        OutputStream os = new BufferedOutputStream( new FileOutputStream( zipTempFile + zipname +".zip" ) );

        ZipOutputStream zos = new ZipOutputStream( os );
        byte[] buf = new byte[8192];
        int len;
        for (int i=0;i<files.length;i++) {
            ZipEntry ze = new ZipEntry( ((CommonsMultipartFile)files[i]).getFileItem().getName() );
            zos.putNextEntry( ze );
            BufferedInputStream bis = new BufferedInputStream(files[i].getInputStream());
            while ( ( len = bis.read( buf ) ) > 0 ) {
                zos.write( buf, 0, len );
            }
            zos.closeEntry();
        }
        zos.setEncoding("gbk");
        zos.closeEntry();
        zos.close();

        os.close();

        InputStream returnInputStream = new FileInputStream(zipTempFile + zipname +".zip" );

        return returnInputStream;
    }

    /**
     * 删出生成的本地压缩文件
     * @param zipname 压缩文件的文件名（不含后缀）
     * @return 删除是否成功
     * @throws IOException
     */
    public static boolean deleteZipFile(String zipname, InputStream in) throws IOException {
        if(null != in){
            in.close();
        }
        boolean deleteSuccess = false;
        //删除本地文件
        File localFile = new File(zipTempFile + zipname +".zip" );

        if(localFile.exists() && localFile.isFile()){
            deleteSuccess  = localFile.delete();
        }

        return deleteSuccess;

    }


    public static InputStream writeZip(List<Map<String, InputStream>>  streamList, String zipname) throws IOException {
        zipname = zipTempFile +  zipname + ".zip";
        byte[]buffer=new byte[1024];
        ZipOutputStream out=null;
        try{
            out = new ZipOutputStream(new FileOutputStream(zipname));

            for(int i = 0,len = streamList.size(); i < len; i++) {
                Map<String, InputStream> inputStreamMap = streamList.get(i);
                Iterator it = inputStreamMap.keySet().iterator();
                while(it.hasNext()){
                    String name = (String) it.next();
                    out.putNextEntry(new ZipEntry(name));
                    InputStream in = inputStreamMap.get(name);
                    int dataLen;
                    //读入需要下载的文件的内容，打包到zip文件
                    while((dataLen = in.read(buffer)) > 0){
                        out.write(buffer,0,dataLen);
                    }
                    out.closeEntry();
                    in.close();
                }

            }
            out.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        //读取压缩包
        File filezip = new File(zipname);

        InputStream returnIn  = new FileInputStream(filezip);

        return returnIn;
    }
    public static void main(String[] args) {








        String[] strs = {"D:\\ss/JavaScript语言精粹.pdf","D:\\ss/seo实战密码.pdf","D:\\ss/蚂蚁金服.pdf"};
        try {
            ZipUtil.writeZip(strs,"test");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
