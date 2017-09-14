package net;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.L;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Yun on 2017/7/31 0031.
 */
public class Client {
    private static OkHttpClient client;

    private Client() {
        client = new OkHttpClient.Builder()
                .sslSocketFactory(TrustAllCerts.createSSLSocketFactory())
                .hostnameVerifier(new TrustAllCerts.TrustAllHostnameVerifier())
                .connectTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                //.proxy(new Proxy(Proxy.Type.HTTP,new InetSocketAddress("127.0.0.1", 8888)))
                .build();
    }

    public static OkHttpClient init() {
        if (client == null) {
            synchronized (Client.class) {
                if (client == null) {
                    new Client();
                }
            }
        }
        return client;
    }


    public static String request(Request request) {
        String responseStr = null;
        try {
            Response response = client.newCall(request).execute();
            int code = response.code();
            responseStr = response.body().string();
            if (code == 200) {
                L.i("net","请求成功");
            } else {
                L.i("net", "code " + response.code());
                responseStr = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            L.e("error", request.url().toString());
            return "";
        }
        return responseStr;
    }

    public static Response next(Request request) {
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            L.e("error", request.url().toString());
            return null;
        }
        return response;
    }

}
