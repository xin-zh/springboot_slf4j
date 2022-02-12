package com.example.demo.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 25790 on 2021/12/12.
 */
public class HttpClientUtil {
    public static String doGet(String httpUrl){
        HttpURLConnection connection=null;
        InputStream ins=null;
        BufferedReader br=null;
        String result=null;
        try{
            //创建远程url连接对象
            URL url=new URL(httpUrl);
            //通过远程url连接对象开启一个连接，
             connection = (HttpURLConnection) url.openConnection();
             connection.setRequestMethod("GET");
             //设置连接超时时间
             connection.setConnectTimeout(3000);
             //设置远程读取返回的数据时间
            connection.setReadTimeout(6000);
            //发送请求
            connection.connect();
            //通过connection连接获取输入流
            if (connection.getResponseCode()==200){
                ins=connection.getInputStream();
                //封装输入流并指定字符集
                br=new BufferedReader(new InputStreamReader(ins,"UTF-8"));
                //存放数据
                StringBuffer sbf = new StringBuffer();
                String temp=null;
                while((temp=br.readLine())!=null){
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result=sbf.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ins!=null){
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //关闭远程连接
            connection.disconnect();
        }
        return result;
    }

    public static String doPost(String httpUrl,String param){
        HttpURLConnection connection=null;
        InputStream ins=null;
        BufferedReader br=null;
        OutputStream ous=null;
        String result=null;
        try{
            URL url=new URL(httpUrl);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(6000);
            //默认为false，当向远程服务器提交数据时，需要设置为true
            connection.setDoOutput(true);
            //默认为true，当向远程服务器读取数据时，需要设置为true
            connection.setDoInput(true);
            //设置入参格式
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            //设置鉴权信息，有需求时通过Authorization设置
//            connection.setRequestProperty("Authorization","");
            //通过连接对象获取一个输出流
             ous = connection.getOutputStream();
             //通过输出流将参数写进去，通过字节数组写出
             ous.write(param.getBytes());
             if (connection.getResponseCode()==200){
                 //获取输入流
                 ins = connection.getInputStream();
                 br= new BufferedReader(new InputStreamReader(ins,"UTF-8"));
                 StringBuffer sbf = new StringBuffer();
                 String temp=null;
                 while ((temp=br.readLine())!=null){
                     sbf.append(temp);
                     sbf.append("\r\n");
                 }
                 result=sbf.toString();
             }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (ins!=null){
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ous!=null){
                try {
                    ous.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            connection.disconnect();
        }
        return result;
    }
}
