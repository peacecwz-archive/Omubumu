package com.hsd.omubumu.Helper;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by APALYazilim on 9.7.2015.
 */
public class HttpClient {

    InputStream is = null;
    JSONObject jObj = null;
    String json = "";
    JSONArray jAry = null;
    CookieStore cookies;

    public HttpClient() {
    }

    public JSONObject makeHttpRequest(String url, String method,
                                      List<NameValuePair> params) {
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            if (cookies != null)
                httpClient.setCookieStore(cookies);
            if (method == "POST") {
                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("User-Agent",AppHelper.GetDeviceInfo());
                httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                cookies = httpClient.getCookieStore();
            } else if (method == "GET") {
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);
                httpGet.addHeader("User-Agent",AppHelper.GetDeviceInfo());
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "windows-1254"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = DataEncrypter.Decrypt(sb.toString());
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return jObj;
    }

    public JSONObject makeHttpRequestWithFile(String url, String method,
                                              List<NameValuePair> params,String Resim1, String Resim2, String Resim1Ad, String Resim2Ad) {

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            if (cookies != null)
                httpClient.setCookieStore(cookies);


            if (method == "POST") {
                HttpPost httpPost = new HttpPost(url);

                httpPost.addHeader("User-Agent",AppHelper.GetDeviceInfo());
                MultipartEntity mpEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

                ContentBody body = new FileBody(new File(Resim1), ContentType.APPLICATION_OCTET_STREAM,Resim1Ad);
                ContentBody body2 = new FileBody(new File(Resim2), ContentType.APPLICATION_OCTET_STREAM,Resim2Ad);
                mpEntity.addPart("Resim1",body);
                mpEntity.addPart("Resim2",body2);
                for(int i=0;i<params.size();i++){
                    mpEntity.addPart(params.get(i).getName(),new StringBody(params.get(i).getValue(), Charset.forName("UTF-8")));
                }
                httpPost.setEntity(mpEntity);
                //httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            } else if (method == "GET") {

                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);
                httpGet.addHeader("User-Agent",AppHelper.GetDeviceInfo());
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "windows-1254"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = DataEncrypter.Decrypt(sb.toString());
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }


        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }


        return jObj;

    }


    public JSONObject makeHttpRequestWithFile(String url, String method,
                                              List<NameValuePair> params,String Resim, String ResimAd) {

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            if (cookies != null)
                httpClient.setCookieStore(cookies);

            if (method == "POST") {


                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("User-Agent",AppHelper.GetDeviceInfo());
                MultipartEntity mpEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

                ContentBody body = new FileBody(new File(Resim), ContentType.APPLICATION_OCTET_STREAM,ResimAd);
                mpEntity.addPart("Resim",body);
                for(int i=0;i<params.size();i++){
                    mpEntity.addPart(params.get(i).getName(),new StringBody(params.get(i).getValue(), Charset.forName("UTF-8")));
                }
                httpPost.setEntity(mpEntity);
                //httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            } else if (method == "GET") {

                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);
                httpGet.addHeader("User-Agent",AppHelper.GetDeviceInfo());
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "windows-1254"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = DataEncrypter.Decrypt( sb.toString());
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }


        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }


        return jObj;

    }

}
