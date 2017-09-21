import dao.OpernDao;
import db.MyBatis;
import model.OpernInfo;
import model.OpernTempInfo;
import net.zhongguoqupu.MusicScoreListHtmlRequest;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.L;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class GetAllImgHtmlData {
    private static CountDownLatch countDownLatch = new CountDownLatch(3);
    /*private static String[] opernCategorys1 = new String[]{"yuanchuang", "jipu", "xiqu"};
    private static String[] opernCategorys2 = new String[]{"minge", "tongsu", "meisheng"};
    private static String[] opernCategorys3 = new String[]{"hechang", "shaoer", "waiguo", "qiyue"};*/
    private static String[] opernCategorys1 = new String[]{"waiguo"};
    private static String[] opernCategorys2 = new String[]{"shaoer"};
    private static String[] opernCategorys3 = new String[]{"hechang"};
    private static ArrayList<OpernTempInfo> list = new ArrayList<OpernTempInfo>();
    private static ArrayList<OpernTempInfo> list1 = new ArrayList<OpernTempInfo>();
    private static ArrayList<OpernTempInfo> list2 = new ArrayList<OpernTempInfo>();
    private static ArrayList<OpernTempInfo> list3 = new ArrayList<OpernTempInfo>();

    public static void main(String[] strings) throws Exception {
        new WorkThread(opernCategorys1, list1).start();
        new WorkThread(opernCategorys2, list2).start();
        new WorkThread(opernCategorys3, list3).start();
        countDownLatch.await();
        list.addAll(list1);
        list.addAll(list2);
        list.addAll(list3);
        SqlSession sqlSession = MyBatis.getSqlSessionFactory().openSession(ExecutorType.BATCH);
        OpernDao dao = sqlSession.getMapper(OpernDao.class);
        ArrayList<OpernInfo> opernInfos = dao.getOpernInfo();
        for(int i = 0; i < list.size(); i++){
            if(opernInfos.contains(list.get(i))){
                list.remove(i);
                i--;
            }
        }
        dao.deleteOpernTempInfo();
        int i = dao.insertOpernTempInfo(list);
        L.i(i + "");
        sqlSession.commit();
        sqlSession.close();
    }

    public static class WorkThread extends Thread {
        private String[] opernCategorys;
        private ArrayList<OpernTempInfo> list;

        public WorkThread(String[] opernCategorys, ArrayList<OpernTempInfo> list) {
            this.opernCategorys = opernCategorys;
            this.list = list;
        }

        @Override
        public void run() {
            super.run();
            MusicScoreListHtmlRequest listHtmlRequest = new MusicScoreListHtmlRequest();
            for (String opernCategory : opernCategorys) {
                int pageIndex = 1;
                boolean nextPage = true;
                while (nextPage) {
                    L.i(Thread.currentThread().getName() + " " + opernCategory + " " + pageIndex);
                    String html = listHtmlRequest.request(opernCategory, pageIndex++);
                    if (html.equals("")) {
                        nextPage = false;
                    } else {
                        Document document = Jsoup.parse(html);
                        Elements elements = document.getElementsByClass("opern_list").get(0).child(1).children();
                        for (Element element : elements) {
                            if (element.children().size() != 1) {
                                OpernTempInfo info = new OpernTempInfo();
                                info.setHtmlUrl("http://www.qupu123.com" + element.child(1).getElementsByClass("f1").get(0).child(0).attr("href"));
                                info.setTitle(element.child(1).getElementsByClass("f1").get(0).text());
                                L.i(info.toString());
                                list.add(info);
                            }
                        }
                    }
                }
            }
            countDownLatch.countDown();
        }
    }
}
