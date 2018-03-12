package cn.cnlinfo.ccf.entity;

/**
 * Created by JP on 2018/3/5 0005.
 */

public class LeaveMessageRecordEntity {
    private String id;
    private String userID;
    private String msgBody;
    private String title;
    private String replyMsg;
    private String Addtime;

    public LeaveMessageRecordEntity(String id, String userID, String msgBody, String title, String replyMsg, String addtime) {
        this.id = id;
        this.userID = userID;
        this.msgBody = msgBody;
        this.title = title;
        this.replyMsg = replyMsg;
        Addtime = addtime;
    }

    public LeaveMessageRecordEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReplyMsg() {
        return replyMsg;
    }

    public void setReplyMsg(String replyMsg) {
        this.replyMsg = replyMsg;
    }

    public String getAddtime() {
        return Addtime;
    }

    public void setAddtime(String addtime) {
        Addtime = addtime;
    }
}
