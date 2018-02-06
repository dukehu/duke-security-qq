package com.duke.domain;

import com.duke.validator.MyConstraint;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Past;
import java.util.Date;

/**
 * Created by duke on 2017/12/24
 */
public class User {

    /**
     * jsonView使用步骤1：
     * 使用接口来声明多个视图
     */
    public interface UserSimpleView {
    }

    public interface UserDetailView extends UserSimpleView {
    }

    @MyConstraint(message = "id不存在！")
    private String id;

    private String userName;

    @NotBlank(message = "密码不能为空！")
    private String password;

    @Past(message = "你还没出生！")
    private Date birthday;

    public User() {
    }

    public User(String id, String userName, String password, Date birthday) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.birthday = birthday;
    }

    /**
     * jsonView使用步骤2：
     * 在值对象的get方法上指定视图
     */
    @JsonView(UserSimpleView.class)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * jsonView使用步骤2：
     * 在值对象的get方法上指定视图
     */
    @JsonView(UserSimpleView.class)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * jsonView使用步骤2：
     * 在值对象的get方法上指定视图
     */
    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonView(UserSimpleView.class)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
