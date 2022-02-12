package com.example.demo.http;

import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.net.URL;

/**
 * Created by 25790 on 2021/12/12.
 */
public class HttpPostTest {
    public static void main(String[] args) throws IOException{
        HttpClient client = HttpClient.New(new URL("http://www.baidu.com/"));
//        HttpPost httpPost = new HttpPost("http://www.baidu.com/");

    }
}
