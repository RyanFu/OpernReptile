import dao.OpernDao;
import db.MyBatis;
import model.OpernImgInfo;
import net.zhongguoqupu.ImageRequest;
import org.apache.ibatis.session.SqlSession;
import utils.L;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class DownLoadImgFileIntoLocal {
    private static CountDownLatch countDownLatch = new CountDownLatch(4);

    public static void main(String[] strings) throws IOException, InterruptedException {
        long startTime = System.currentTimeMillis();
        SqlSession sqlSession = MyBatis.getSqlSessionFactory().openSession();
        OpernDao dao = sqlSession.getMapper(OpernDao.class);
        ArrayList<OpernImgInfo> opernImgInfoArrayList = dao.getAllOpernImgInfo();
        sqlSession.commit();
        sqlSession.close();
        File file = new File("c:/曲谱");
        if (!file.exists()) {
            file.mkdir();
        }
        new WorkThread(opernImgInfoArrayList.subList(0, opernImgInfoArrayList.size() / 4)).start();
        new WorkThread(opernImgInfoArrayList.subList(opernImgInfoArrayList.size() / 4, opernImgInfoArrayList.size() / 4 * 2)).start();
        new WorkThread(opernImgInfoArrayList.subList(opernImgInfoArrayList.size() / 4 * 2, opernImgInfoArrayList.size() / 4 * 3)).start();
        new WorkThread(opernImgInfoArrayList.subList(opernImgInfoArrayList.size() / 4 * 3, opernImgInfoArrayList.size())).start();
        countDownLatch.await();
        L.i(opernImgInfoArrayList.size() + "");
        L.i((System.currentTimeMillis() - startTime) / 1000 + "");
    }


    public static class WorkThread extends Thread {
        private List<OpernImgInfo> opernImgInfoList;

        public WorkThread(List<OpernImgInfo> opernImgInfoList) {
            this.opernImgInfoList = opernImgInfoList;
        }

        @Override
        public void run() {
            super.run();
            ImageRequest imageRequest = new ImageRequest();
            int index = 0;
            for (OpernImgInfo info : opernImgInfoList) {
                L.i(Thread.currentThread().getName() + " " + index++);
                byte[] bytes = imageRequest.request(info.getOpernImg());
                if(bytes == null){
                    continue;
                }
                String title = info.getOpernTitle();
                title = title.replace("/", "");
                title = title.replace("\\", "");
                title = title.replace(":", "");
                title = title.replace("\"", "");
                title = title.replace("|", "");
                title = title.replace("*", "");
                title = title.replace("?", "");
                title = title.replace(">", ")");
                title = title.replace("<", "(");
                title = title.replace("_", "");
                File imgFile = new File("c:/曲谱/" + title + "_" + info.getId() + ".jpg");
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(imgFile);
                    fileOutputStream.write(bytes);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            countDownLatch.countDown();
        }
    }
}
