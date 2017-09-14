package net.zhongguoqupu;

import net.Client;
import okhttp3.Request;

public class GetAllImgUrlMobileRequest {

    public GetAllImgUrlMobileRequest() {
        Client.init();
    }

    public String request(int id){
        Request request = new Request.Builder()
                .get()
                .url("http://www.qupu123.com/Mobile-view-id-" + id + ".html")
                .build();
        return Client.request(request);
    }
}
