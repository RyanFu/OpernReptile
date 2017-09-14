import dao.OpernDao;
import db.MyBatis;
import model.OpernInfo;
import org.apache.ibatis.session.SqlSession;
import utils.L;

import java.util.*;

public class MakeCategory {
    private static Map<String, String> map = new HashMap<>();

    public static void main(String[] args) {
        map.put("meisheng","美声");
        map.put("jipu","个人记谱");
        map.put("minge","民歌");
        map.put("hechang","合唱");
        map.put("yuanchuang","原创");
        map.put("tongsu","通俗");
        map.put("xiqu","戏曲");
        map.put("qiyue","器乐");
        map.put("waiguo","外国");
        map.put("shaoer","少儿");


        map.put("gangqin","钢琴");
        map.put("dianziqin","电子琴");
        map.put("shoufengqin","手风琴");
        map.put("sakesi","萨克斯");
        map.put("changdi","长笛");
        map.put("tongguan","铜管");
        map.put("jita","吉他");
        map.put("guzhengguqin","古筝古琴");
        map.put("dixiao","笛箫");
        map.put("hulusi","葫芦丝");
        map.put("huqin","胡琴");
        map.put("pipa","琵琶");
        map.put("yangqin","扬琴");
        map.put("kouqin","口琴");
        map.put("tiqin","提琴");
        map.put("qita","其他");


        map.put("jingju","京剧");
        map.put("yueju","越剧");
        map.put("huangmeixi","黄梅戏");
        map.put("huaguxi","花鼓戏");
        map.put("yuju","豫剧");
        map.put("pingju","评剧");
        map.put("errenzhuan","二人转");



        map.put("erziyixia","二字以下");
        map.put("sanzi","三字");
        map.put("sizi","四字");
        map.put("wuzi","五字");
        map.put("liuzi","六字");
        map.put("qizi","七字");
        map.put("bazi","八字");
        map.put("jiuziyishang","九字以上");


        SqlSession sqlSession = MyBatis.getSqlSessionFactory().openSession();
        OpernDao dao = sqlSession.getMapper(OpernDao.class);
        ArrayList<OpernInfo> opernInfos = dao.getOpernInfo();
        final int[] index = {0};
        opernInfos.forEach(opernInfo -> {
            index[0]++;
            String url = opernInfo.getHtml();
            String[] a = url.replace("http://","").split("/");
            String[] b = new String[a.length - 2];
            if (a.length > 2) {
                System.arraycopy(a, 1, b, 0, a.length - 2);

                if(b.length >= 1){
                    String categoryOne = map.getOrDefault(b[0], "");
                    opernInfo.setCategoryOne(categoryOne);
                }
                if(b.length >= 2){
                    String categoryTwo = map.getOrDefault(b[1], "");
                    opernInfo.setCategoryTwo(categoryTwo);
                }
                if(b.length >= 3){
                    String categoryThree = map.getOrDefault(b[2], "");
                    opernInfo.setCategoryThree(categoryThree);
                }
                int i = dao.updateCategory(opernInfo);
                L.i(index[0] + " " + i);
            }
        });
        sqlSession.commit();
        sqlSession.close();
    }
}
