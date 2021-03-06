package domain;

/**
 * @author 周杨清
 * @time 2020/6/5
 * 用户信息的实体类
 */
public class User {
    private String userid;
    private String username;
    private String password;
    private String birthday;
    private String sex;
    private String autograph;
    private int userstatus;


    public int getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(int userstatus) {
        this.userstatus = userstatus;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }

    public User(String userid) {
        this.userid = userid;
    }

    public User(String userid, String username, String password, String birthday, String sex, String autograph) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.birthday = birthday;
        this.sex = sex;
        this.autograph = autograph;
    }

    public User() {
    }

    public User(String userid, String password) {
        this.userid = userid;
        this.password = password;
    }
}
