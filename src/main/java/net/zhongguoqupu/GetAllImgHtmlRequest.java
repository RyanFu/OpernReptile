package net.zhongguoqupu;

import net.Client;
import okhttp3.Request;

public class GetAllImgHtmlRequest {

    public GetAllImgHtmlRequest() {
        Client.init();
    }

    public String request(String id, String pagenum, String k, String referer){
        Request request = new Request.Builder()
                .get()
                .url("http://www.qupu123.com/get_img/" + id + "/" + pagenum + "/" + k)
                .header("Referer","http://www.qupu123.com" + referer)
                .build();
        return Client.request(request);
    }
}
