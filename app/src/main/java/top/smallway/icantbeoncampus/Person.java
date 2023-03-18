package top.smallway.icantbeoncampus;

public class Person {
    private String title, type, time, status, id, logId;

    public Person(String title, String type, String time, String status,String id, String logId) {
        this.title = title;
        this.type = type;
        this.time = time;
        this.status = status;
        this.id = id;
        this.logId = logId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public String getLogId() {
        return logId;
    }


}
