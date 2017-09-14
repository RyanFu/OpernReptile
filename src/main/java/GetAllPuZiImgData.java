import dao.OpernDao;
import db.MyBatis;
import model.OpernImgInfo;
import model.OpernInfo;
import model.OpernTempInfo;
import net.zhongguoqupu.GetAllImgUrlMobileRequest;
import net.zhongguoqupu.MusicScoreImgHtmlRequest;
import net.zhongguoqupu.MusicScoreViewCountRequest;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.L;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class GetAllPuZiImgData {
    private static String commonjs = "function utf16to8(str) {\n" +
            "    var out, i, len, c;\n" +
            "    out = \"\";\n" +
            "    len = str.length;\n" +
            "    for (i = 0; i < len; i++) {\n" +
            "        c = str.charCodeAt(i);\n" +
            "        if ((c >= 0x0001) && (c <= 0x007F)) {\n" +
            "            out += str.charAt(i)\n" +
            "        } else if (c > 0x07FF) {\n" +
            "            out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));\n" +
            "            out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));\n" +
            "            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F))\n" +
            "        } else {\n" +
            "            out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));\n" +
            "            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F))\n" +
            "        }\n" +
            "    }\n" +
            "    return out\n" +
            "}\n" +
            "function utf8to16(str) {\n" +
            "    var out, i, len, c;\n" +
            "    var char2, char3;\n" +
            "    out = \"\";\n" +
            "    len = str.length;\n" +
            "    i = 0;\n" +
            "    while (i < len) {\n" +
            "        c = str.charCodeAt(i++);\n" +
            "        switch (c >> 4) {\n" +
            "            case 0:\n" +
            "            case 1:\n" +
            "            case 2:\n" +
            "            case 3:\n" +
            "            case 4:\n" +
            "            case 5:\n" +
            "            case 6:\n" +
            "            case 7:\n" +
            "                out += str.charAt(i - 1);\n" +
            "                break;\n" +
            "            case 12:\n" +
            "            case 13:\n" +
            "                char2 = str.charCodeAt(i++);\n" +
            "                out += String.fromCharCode(((c & 0x1F) << 6) | (char2 & 0x3F));\n" +
            "                break;\n" +
            "            case 14:\n" +
            "                char2 = str.charCodeAt(i++);\n" +
            "                char3 = str.charCodeAt(i++);\n" +
            "                out += String.fromCharCode(((c & 0x0F) << 12) | ((char2 & 0x3F) << 6) | ((char3 & 0x3F) << 0));\n" +
            "                break\n" +
            "        }\n" +
            "    }\n" +
            "    return out\n" +
            "}\n" +
            "var base64EncodeChars = \"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/\";\n" +
            "var base64DecodeChars = new Array(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);\n" +
            "function base64encode(str) {\n" +
            "    var out, i, len;\n" +
            "    var c1, c2, c3;\n" +
            "    len = str.length;\n" +
            "    i = 0;\n" +
            "    out = \"\";\n" +
            "    while (i < len) {\n" +
            "        c1 = str.charCodeAt(i++) & 0xff;\n" +
            "        if (i == len) {\n" +
            "            out += base64EncodeChars.charAt(c1 >> 2);\n" +
            "            out += base64EncodeChars.charAt((c1 & 0x3) << 4);\n" +
            "            out += \"==\";\n" +
            "            break\n" +
            "        }\n" +
            "        c2 = str.charCodeAt(i++);\n" +
            "        if (i == len) {\n" +
            "            out += base64EncodeChars.charAt(c1 >> 2);\n" +
            "            out += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));\n" +
            "            out += base64EncodeChars.charAt((c2 & 0xF) << 2);\n" +
            "            out += \"=\";\n" +
            "            break\n" +
            "        }\n" +
            "        c3 = str.charCodeAt(i++);\n" +
            "        out += base64EncodeChars.charAt(c1 >> 2);\n" +
            "        out += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));\n" +
            "        out += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >> 6));\n" +
            "        out += base64EncodeChars.charAt(c3 & 0x3F)\n" +
            "    }\n" +
            "    return out\n" +
            "}\n" +
            "function base64decode(str) {\n" +
            "    var c1, c2, c3, c4;\n" +
            "    var i, len, out;\n" +
            "    len = str.length;\n" +
            "    i = 0;\n" +
            "    out = \"\";\n" +
            "    while (i < len) {\n" +
            "        do {\n" +
            "            c1 = base64DecodeChars[str.charCodeAt(i++) & 0xff]\n" +
            "        } while (i < len && c1 == -1);\n" +
            "        if (c1 == -1)break;\n" +
            "        do {\n" +
            "            c2 = base64DecodeChars[str.charCodeAt(i++) & 0xff]\n" +
            "        } while (i < len && c2 == -1);\n" +
            "        if (c2 == -1)break;\n" +
            "        out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));\n" +
            "        do {\n" +
            "            c3 = str.charCodeAt(i++) & 0xff;\n" +
            "            if (c3 == 61)return out;\n" +
            "            c3 = base64DecodeChars[c3]\n" +
            "        } while (i < len && c3 == -1);\n" +
            "        if (c3 == -1)break;\n" +
            "        out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));\n" +
            "        do {\n" +
            "            c4 = str.charCodeAt(i++) & 0xff;\n" +
            "            if (c4 == 61)return out;\n" +
            "            c4 = base64DecodeChars[c4]\n" +
            "        } while (i < len && c4 == -1);\n" +
            "        if (c4 == -1)break;\n" +
            "        out += String.fromCharCode(((c3 & 0x03) << 6) | c4)\n" +
            "    }\n" +
            "    return out\n" +
            "}\n" +
            "function strdecode(str) {\n" +
            "    return utf8to16(base64decode(str))\n" +
            "}\n" +
            "function de(string, key) {\n" +
            "    string = strdecode(string);\n" +
            "    len = key.length;\n" +
            "    code = '';\n" +
            "    for (i = 0; i < string.length; i++) {\n" +
            "        k = i % len;\n" +
            "        code += String.fromCharCode(string.charCodeAt(i) ^ key.charCodeAt(k))\n" +
            "    }\n" +
            "    return strdecode(code)\n" +
            "}\n" +
            "var kid = 2;\n" +
            "\n" +
            "function showopern(string, key) {\n" +
            "    var detxt = de(string, key);\n" +
            "    var c = detxt.split('|');\n" +
            "    return c[0];\n" +
            "}";
    private static ScriptEngineManager manager = new ScriptEngineManager();
    private static ScriptEngine engine = manager.getEngineByName("javascript");
    private static Invocable inv;
    private static ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    private static ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("javascript");

    private static CountDownLatch countDownLatch;
    private static ArrayList<OpernInfo> opernInfoArrayList1 = new ArrayList<OpernInfo>();
    private static ArrayList<OpernInfo> opernInfoArrayList2 = new ArrayList<OpernInfo>();
    private static ArrayList<OpernInfo> opernInfoArrayList3 = new ArrayList<OpernInfo>();
    private static ArrayList<OpernInfo> opernInfoArrayList4 = new ArrayList<OpernInfo>();
    private static ArrayList<OpernInfo> opernInfoArrayList5 = new ArrayList<OpernInfo>();

    private static OpernDao dao;

    public static void main(String[] strings) throws Exception {
        engine.eval(commonjs);
        inv = (Invocable) engine;
        ArrayList<OpernInfo> opernInfoArrayList = new ArrayList<OpernInfo>();
        SqlSession sqlSession = MyBatis.getSqlSessionFactory().openSession(ExecutorType.BATCH);
        dao = sqlSession.getMapper(OpernDao.class);
        ArrayList<OpernTempInfo> list = dao.getOpernTempInfo();
        countDownLatch = new CountDownLatch(5);
        /*new WorkThread(list.subList(0, list.size()), opernInfoArrayList1).start();*/
        dao.deleteOpernInfo();
        new WorkThread(list.subList(0, list.size() / 5), opernInfoArrayList1).start();
        new WorkThread(list.subList(list.size() / 5, list.size() / 5 * 2), opernInfoArrayList2).start();
        new WorkThread(list.subList(list.size() / 5 * 2, list.size() / 5 * 3), opernInfoArrayList3).start();
        new WorkThread(list.subList(list.size() / 5 * 3, list.size() / 5 * 4), opernInfoArrayList4).start();
        new WorkThread(list.subList(list.size() / 5 * 4, list.size()), opernInfoArrayList5).start();
        countDownLatch.await();

        opernInfoArrayList.addAll(opernInfoArrayList1);
        opernInfoArrayList.addAll(opernInfoArrayList2);
        opernInfoArrayList.addAll(opernInfoArrayList3);
        opernInfoArrayList.addAll(opernInfoArrayList4);
        opernInfoArrayList.addAll(opernInfoArrayList5);


        insertIntoDB(opernInfoArrayList);
        sqlSession.commit();
        sqlSession.close();
    }

    private synchronized static void insertIntoDB(ArrayList<OpernInfo> opernInfoArrayList){
        dao.insertIntoOpernInfo(opernInfoArrayList);
        ArrayList<OpernImgInfo> opernImgInfos = new ArrayList<OpernImgInfo>();
        for (OpernInfo info : opernInfoArrayList) {
            for (int i = 0; i < info.getImgs().size(); i++) {
                OpernImgInfo opernImgInfo = new OpernImgInfo();
                opernImgInfo.setId(info.getId() + "_" + (i + 1));
                opernImgInfo.setOpernId(info.getId());
                opernImgInfo.setOpernIndex(i + 1);
                opernImgInfo.setOpernTitle(info.getTitle());
                opernImgInfo.setOpernImg(info.getImgs().get(i));
                opernImgInfos.add(opernImgInfo);
            }
        }
        dao.insertIntoOpernImgInfo(opernImgInfos);
        opernImgInfos.clear();
        opernInfoArrayList.clear();
    }

    public static class WorkThread extends Thread {
        List<OpernTempInfo> list;
        ArrayList<OpernInfo> opernInfoArrayList;

        public WorkThread(List<OpernTempInfo> list, ArrayList<OpernInfo> opernInfoArrayList) {
            this.list = list;
            this.opernInfoArrayList = opernInfoArrayList;
        }

        @Override
        public void run() {
            super.run();
            MusicScoreImgHtmlRequest request = new MusicScoreImgHtmlRequest();
            MusicScoreViewCountRequest viewCountRequest = new MusicScoreViewCountRequest();
            GetAllImgUrlMobileRequest getAllImgUrlMobileRequest = new GetAllImgUrlMobileRequest();
            int a = 0;
            for (OpernTempInfo info : list) {
                if(opernInfoArrayList.size() % 1000 == 0 && opernInfoArrayList.size() != 0){
                    insertIntoDB(opernInfoArrayList);
                    opernInfoArrayList.clear();
                }
                L.i(Thread.currentThread().getName() + " " + a++ + " " + info.getHtmlUrl());
                OpernInfo opernInfo = new OpernInfo();
                opernInfo.setTitle(info.getTitle());
                opernInfo.setHtml(info.getHtmlUrl());
                String html = "";
                try{
                    html = request.request(info.getHtmlUrl());
                }catch (Exception e){
                    e.printStackTrace();
                    L.e("error", info.getHtmlUrl());
                }
                Document document = Jsoup.parse(html.replace("&nbsp;",""));
                String parameter;
                try {
                    parameter = document.getElementById("look_all").attr("onclick");
                } catch (Exception e) {
                    continue;
                }
                int index1 = parameter.indexOf("'");
                int index2 = parameter.indexOf("'", index1 + 1);

                int id = Integer.parseInt(parameter.substring(index1 + 1, index2));
                opernInfo.setId(id);
                String title = document.getElementsByClass("content_head").get(0).child(0).text();
                opernInfo.setTitle(title);
                Elements headInfo = document.getElementsByClass("content_head").get(0).getElementsByClass("info").get(0).getElementsByTag("span");
                int[] indexs = new int[headInfo.size() - 1];
                for (int i = 0; i < headInfo.size() - 1; i++) {
                    indexs[i] = document.getElementsByClass("content_head").get(0).child(1).text().indexOf(headInfo.get(i).text());
                }
                String[] strs = new String[headInfo.size() - 2];
                for (int i = 0; i < indexs.length - 1; i++) {
                    try {
                        strs[i] = document.getElementsByClass("content_head").get(0).child(1).text().substring(indexs[i] + headInfo.get(i).text().length(), indexs[i + 1]);
                    } catch (Exception e) {
                        strs[i] = "解析错误";
                    }
                }
                for (int i = 0; i < strs.length; i++) {
                    String s = headInfo.get(i).text();
                    if (s.equals("作词：")) {
                        opernInfo.setWordAuthor(strs[i]);
                    } else if (s.equals("作曲：")) {
                        opernInfo.setSongAuthor(strs[i]);
                    } else if (s.equals("演唱(奏)：")) {
                        opernInfo.setSinger(strs[i]);
                    } else if (s.equals("格式：")) {
                        opernInfo.setFormat(strs[i]);
                    } else if (s.equals("来源：")) {
                        opernInfo.setOrigin(strs[i]);
                    } else if (s.equals("上传：")) {
                        opernInfo.setUploadUserName(strs[i]);
                    } else if (s.equals("日期：")) {
                        opernInfo.setUploadDateTime(strs[i]);
                    }
                }
                opernInfo.setViews(viewCountRequest.request(opernInfo.getId()));
                ArrayList<String> imgs = new ArrayList<String>();
                String mobileHtml = getAllImgUrlMobileRequest.request(id);
                if(mobileHtml.equals("")){
                    continue;
                }
                Document mobileDocument = Jsoup.parse(mobileHtml);
                Elements elements = mobileDocument.getElementsByTag("head").get(0).getElementsByTag("script");
                String x = elements.get(elements.size() - 1).toString();
                String js = x.replace("<script>","");
                js = js.replace("</script>","");
                try {
                    scriptEngine.eval(js);
                } catch (ScriptException e) {
                    L.e("js error", info.toString());
                }
                for (Element l : mobileDocument.getElementsByClass("image_list").get(0).children()) {
                    if(l.tagName().equals("a")){
                        imgs.add(l.attr("href"));
                    }
                    if(l.tagName().equals("script")){
                        try {
                            int b = l.toString().indexOf("(");
                            int c = l.toString().indexOf(",");
                            String k0 = l.toString().substring(b + 1, c);
                            int d = l.toString().indexOf("\"");
                            int e = l.toString().indexOf("\"", d + 1);
                            String k1 = l.toString().substring(d + 1, e);
                            String k0_p = (String) scriptEngine.get(k0);
                            String imgUrl = (String) inv.invokeFunction("showopern", k0_p, k1);
                            imgs.add(imgUrl);
                        } catch (Exception e) {

                        }
                    }
                }
                opernInfo.setImgs(imgs);
                opernInfoArrayList.add(opernInfo);
                L.i(opernInfo.toString());
            }
            countDownLatch.countDown();
            L.i(Thread.currentThread().getName() + " over");
        }
    }
}
