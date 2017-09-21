package dao;

import model.OpernImgInfo;
import model.OpernInfo;
import model.OpernTempInfo;

import java.util.ArrayList;

public interface OpernDao {

    int deleteOpernTempInfo();

    int insertOpernTempInfo(ArrayList<OpernTempInfo> infos);

    ArrayList<OpernTempInfo> getOpernTempInfo();

    int deleteOpernInfo();

    int deleteOpernImgInfo();

    int insertIntoOpernInfo(ArrayList<OpernInfo> infos);

    int insertIntoOpernImgInfo(ArrayList<OpernImgInfo> infos);

    ArrayList<OpernImgInfo> getAllOpernImgInfo();

    ArrayList<OpernInfo> getOpernInfo();

    ArrayList<Integer> getOpernInfoId();

    int updateCategory(OpernInfo opernInfo);
}
