package com.dhongchuan.chapplication.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by dhongchuan on 15/2/11.
 */
public class NetUtil {
    public static byte[] getFileBytesByUrl(String strUrl){
        HttpURLConnection httpConnection = null;
        try {
            URL url = new URL(strUrl);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setConnectTimeout(30000);
            httpConnection.setReadTimeout(30000);
            httpConnection.setInstanceFollowRedirects(true);

            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            InputStream inStream = httpConnection.getInputStream();
            if(httpConnection.getResponseCode() != HttpsURLConnection.HTTP_OK){
                return null;
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while((bytesRead = inStream.read(buffer)) > 0){
                byteOut.write(buffer, 0, bytesRead);
            }
            byteOut.close();
            return byteOut.toByteArray();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(httpConnection != null)
                httpConnection.disconnect();
        }
        return null;
    }
}
