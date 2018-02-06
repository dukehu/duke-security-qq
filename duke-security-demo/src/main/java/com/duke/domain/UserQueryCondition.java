package com.duke.domain;

/**
 * Created by duke on 2017/12/24
 */
public class UserQueryCondition {

    /**
     * 查询条件对象，但是一般在跨服务的时候不使用复杂对象作为查询条件，但是可以使用Map来封装
     */

    private String userName;

    private int age;

    private int ageTo;

    private String xxx;

    public UserQueryCondition() {
    }

    public UserQueryCondition(String userName, int age, int ageTo, String xxx) {
        this.userName = userName;
        this.age = age;
        this.ageTo = ageTo;
        this.xxx = xxx;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAgeTo() {
        return ageTo;
    }

    public void setAgeTo(int ageTo) {
        this.ageTo = ageTo;
    }

    public String getXxx() {
        return xxx;
    }

    public void setXxx(String xxx) {
        this.xxx = xxx;
    }
}
