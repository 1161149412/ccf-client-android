package cn.cnlinfo.ccf.entity;

import java.io.Serializable;

/**
 * Created by JP on 2017/11/21 0021.
 */

public class ItemNewsEntity implements Serializable {


    /**
     * NewsID : 10
     * Subject : 公告测试2
     * ClassID : 2
     * IssueDate : /Date(1509527396217+0800)/
     */

    private int NewsID;
    private String Subject;
    private int ClassID;
    private String IssueDate;

    public int getNewsID() {
        return NewsID;
    }

    public void setNewsID(int NewsID) {
        this.NewsID = NewsID;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public int getClassID() {
        return ClassID;
    }

    public void setClassID(int ClassID) {
        this.ClassID = ClassID;
    }

    public String getIssueDate() {
        return IssueDate;
    }

    public void setIssueDate(String IssueDate) {
        this.IssueDate = IssueDate;
    }
}
