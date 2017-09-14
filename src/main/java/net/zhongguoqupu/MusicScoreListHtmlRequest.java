package net.zhongguoqupu;

import net.Client;
import okhttp3.Request;

public class MusicScoreListHtmlRequest {

    public MusicScoreListHtmlRequest() {
        Client.init();
    }

    public String request(String opernCategory, int pageIndex) {
        Request request = new Request.Builder()
                .url("http://www.qupu123.com/" + opernCategory + "/" + pageIndex + ".html")
                .get()
                .build();
        return Client.request(request);
    }
}
