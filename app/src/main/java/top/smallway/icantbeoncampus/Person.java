package top.smallway.icantbeoncampus;

public class Person {
    private String title,type,time,status;

    public Person(String title, String type, String time, String status) {
        this.title = title;
        this.type = type;
        this.time = time;
        this.status = status;
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
}
