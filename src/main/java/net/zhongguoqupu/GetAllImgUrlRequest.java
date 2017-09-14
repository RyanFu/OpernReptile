package net.zhongguoqupu;

import net.Client;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;


public class GetAllImgUrlRequest {

    public GetAllImgUrlRequest() {
        Client.init();
    }

    public String request(String id, String pagenum, String k, String referer,  String p, String k1){
        Request request = new Request.Builder()
                .url("http://www.qupu123.com/get_img/" + id + "/" + pagenum + "/" + k)
                .header("Referer","http://www.qupu123.com/get_img/" + id + "/" + pagenum + "/" + k)
                .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),"id=" +id + "&p=" + p + "&k1=" + k1))
                .build();
        return Client.request(request);
    }
}
