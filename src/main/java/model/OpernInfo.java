package model;

import java.io.Serializable;
import java.util.ArrayList;

public class OpernInfo implements Serializable{
    private int id;
    private String title = "";
    private String wordAuthor = "";
    private String songAuthor = "";
    private String singer = "";
    private String format = "";
    private String uploadUserName = "";
    private String uploadDateTime = "";
    private String categoryOne = "";
    private String categoryTwo = "";
    private String categoryThree = "";
    private String origin = "";
    private int views;
    private String html = "";
    private ArrayList<String> imgs;
    private String dataOrigin = "";
    private String insertDataTime = "";
    private String updateDataTime = "";
    private String deleteFlage = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWordAuthor() {
        return wordAuthor;
    }

    public void setWordAuthor(String wordAuthor) {
        this.wordAuthor = wordAuthor;
    }

    public String getSongAuthor() {
        return songAuthor;
    }

    public void setSongAuthor(String songAuthor) {
        this.songAuthor = songAuthor;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getUploadUserName() {
        return uploadUserName;
    }

    public void setUploadUserName(String uploadUserName) {
        this.uploadUserName = uploadUserName;
    }

    public String getUploadDateTime() {
        return uploadDateTime;
    }

    public void setUploadDateTime(String uploadDateTime) {
        this.uploadDateTime = uploadDateTime;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public ArrayList<String> getImgs() {
        return imgs;
    }

    public void setImgs(ArrayList<String> imgs) {
        this.imgs = imgs;
    }

    public String getDataOrigin() {
        return dataOrigin;
    }

    public void setDataOrigin(String dataOrigin) {
        this.dataOrigin = dataOrigin;
    }

    public String getCategoryOne() {
        return categoryOne;
    }

    public void setCategoryOne(String categoryOne) {
        this.categoryOne = categoryOne;
    }

    public String getCategoryTwo() {
        return categoryTwo;
    }

    public void setCategoryTwo(String categoryTwo) {
        this.categoryTwo = categoryTwo;
    }

    public String getCategoryThree() {
        return categoryThree;
    }

    public void setCategoryThree(String categoryThree) {
        this.categoryThree = categoryThree;
    }

    public String getInsertDataTime() {
        return insertDataTime;
    }

    public void setInsertDataTime(String insertDataTime) {
        this.insertDataTime = insertDataTime;
    }

    public String getUpdateDataTime() {
        return updateDataTime;
    }

    public void setUpdateDataTime(String updateDataTime) {
        this.updateDataTime = updateDataTime;
    }

    public String getDeleteFlage() {
        return deleteFlage;
    }

    public void setDeleteFlage(String deleteFlage) {
        this.deleteFlage = deleteFlage;
    }

    @Override
    public String toString() {
        return "OpernInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", wordAuthor='" + wordAuthor + '\'' +
                ", songAuthor='" + songAuthor + '\'' +
                ", singer='" + singer + '\'' +
                ", format='" + format + '\'' +
                ", uploadUserName='" + uploadUserName + '\'' +
                ", uploadDateTime='" + uploadDateTime + '\'' +
                ", categoryOne='" + categoryOne + '\'' +
                ", categoryTwo='" + categoryTwo + '\'' +
                ", categoryThree='" + categoryThree + '\'' +
                ", origin='" + origin + '\'' +
                ", views=" + views +
                ", html='" + html + '\'' +
                ", imgs=" + imgs +
                ", dataOrigin='" + dataOrigin + '\'' +
                '}';
    }
}
