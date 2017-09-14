package net.zhongguoqupu;

import net.Client;
import okhttp3.Request;

public class MusicScoreImgHtmlRequest {

    public MusicScoreImgHtmlRequest() {
        Client.init();
    }

    public String request(String htmlUrl){
        Request request = new Request.Builder()
                .url(htmlUrl)
                .get()
                .build();
        return Client.request(request);
    }
}
