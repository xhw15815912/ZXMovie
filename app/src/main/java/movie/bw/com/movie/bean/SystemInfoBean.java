package movie.bw.com.movie.bean;

import java.util.List;

/**
 * Created by zxk
 * on 2019/1/26 15:44
 * QQ:666666
 * Describe:
 */
public class SystemInfoBean {


    private String content;
    private int id;
    private long pushTime;
    private int status;
    private String title;
    private int userId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPushTime() {
        return pushTime;
    }

    public void setPushTime(long pushTime) {
        this.pushTime = pushTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
