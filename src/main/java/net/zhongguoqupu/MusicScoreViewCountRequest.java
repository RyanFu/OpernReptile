package net.zhongguoqupu;

import net.Client;
import okhttp3.Request;

public class MusicScoreViewCountRequest {

    public MusicScoreViewCountRequest() {
        Client.init();
    }

    public int request(int id){
        Request request = new Request.Builder()
                .get()
                .url("http://www.qupu123.com/Opern-cnum-id-" + id + ".html")
                .build();
        int views;
        try{
            views = Integer.parseInt(Client.request(request));
        }catch (Exception e){
            e.printStackTrace();
            views = 0;
        }
        return views;
    }
}
