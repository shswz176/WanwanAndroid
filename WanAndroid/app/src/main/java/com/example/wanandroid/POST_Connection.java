package com.example.wanandroid;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

public class POST_Connection {

    private String responseData;

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    protected String sendGetNetRequest(String murl, HashMap<String,String> params) {
        POST_Connection post_connection = new POST_Connection();
        try {
            URL url = new URL(murl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setDoOutput(true);
            connection.setDoInput(true);

            StringBuilder dataTowrite = new StringBuilder();
            for(String key : params.keySet()){
                dataTowrite.append(key).append("=").append(params.get(key)).append("&");
            }

            connection.connect();
            OutputStream outputStream =connection.getOutputStream();
            outputStream.write(dataTowrite.substring(0,dataTowrite.length()-1).getBytes());

            InputStream in = connection.getInputStream();
            Log.e("send", "ok");
            post_connection.setResponseData(StreamToString(in));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
            Log.e("time1", "请求超时");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("time2", "请求超时");
        }
        String finally_responseData = post_connection.getResponseData();
        return finally_responseData;
    }

    private String StreamToString(InputStream in) {
        StringBuilder sb = new StringBuilder();
        String oneLine;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        try {
            while ((oneLine = reader.readLine()) != null) {
                sb.append(oneLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}