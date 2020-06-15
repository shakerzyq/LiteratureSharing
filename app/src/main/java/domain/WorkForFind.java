package domain;

/**
 * @author 周杨清
 * @time 2020/6/12
 * 一个仅仅针对文库查询的实体类
 */
public class WorkForFind {
    private String workid;
    private String workname;
    private String userid;
    private String username;



    public String getWorkid() {
        return workid;
    }

    public void setWorkid(String workid) {
        this.workid = workid;
    }

    public String getWorkname() {
        return workname;
    }

    public void setWorkname(String workname) {
        this.workname = workname;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public WorkForFind(String workid, String workname, String userid, String username) {
        this.workid = workid;
        this.workname = workname;
        this.userid = userid;
        this.username = username;
    }

    public WorkForFind() {
    }
}
