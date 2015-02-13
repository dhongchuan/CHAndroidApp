package com.dhongchuan.chapplication.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.dhongchuan.chapplication.base.ICache;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dhongchuan on 15/2/11.
 */
public class FileCache implements ICache<String, byte[]> {
    private static FileCache sFileCache;
    private File mCache;

    private FileCache(Context context){
        mCache = getCacheDir(context);
    }

    public static FileCache obtain(Context context){
        if(sFileCache == null){
            sFileCache = new FileCache(context);
        }
        return sFileCache;
    }
    /**
     * 获取缓存的目录
     * @param context
     * @return
     */
    private File getCacheDir(Context context){
        File cacheDir;
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), Config.FILE_CACHE_DIR_NAME);
        else
            cacheDir = context.getCacheDir();
        if (!cacheDir.exists())
            cacheDir.mkdirs();
        return cacheDir;
    }

    @Override
    public void set(String key, byte[] value) {
        FileOutputStream fileOutPutStream = null;
        File file;
        String fileName = String.valueOf(key.hashCode());
        try {
            file = new File(mCache, fileName);
            fileOutPutStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        byte[] buffer = value;
        int len = 0;
        int count = 0;
        int downloadFileSize;
        do {
            len = buffer.length;
            if (len <= 0)
                break;
            try {
                fileOutPutStream.write(buffer, 0, len);
                fileOutPutStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

        } while (true);
    }

    @Override
    public byte[] get(String key) {
        FileInputStream fileInputStream = null;
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        File file = getFile(key);
        byte[] buffer = new byte[1024];
        int bytesRead;
        try {
            fileInputStream = new FileInputStream(file);
            while((bytesRead = fileInputStream.read(buffer)) > 0){
                byteOut.write(buffer, 0, bytesRead);
            }
            byteOut.close();
            return byteOut.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new byte[0];
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    @Override
    public void delete(String key) {

    }

    @Override
    public void deleteAll() {

    }

    private File getFile(String url){
        String path = getPath(url);
        return new File(path);
    }
    private String getPath(String url){
        return new StringBuilder(mCache.getAbsolutePath()).append("/").append(url).toString();
    }
}
