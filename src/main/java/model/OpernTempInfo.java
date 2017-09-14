package model;

public class OpernTempInfo {
    private String htmlUrl;
    private String title;
    private String insertDataTime;
    private String updateDataTime;
    private String deleteFlage;

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        return "OpernTempInfo{" +
                "htmlUrl='" + htmlUrl + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
