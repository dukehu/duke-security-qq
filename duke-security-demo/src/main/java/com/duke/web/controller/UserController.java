package com.duke.web.controller;

import com.duke.domain.User;
import com.duke.domain.UserQueryCondition;
import com.duke.web.error.UserNotFoundException;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by duke on 2017/12/23
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @RequestMapping(method = RequestMethod.GET)
    @JsonView(User.UserSimpleView.class)
    public List<User> query(@RequestParam(name = "userName", required = false, defaultValue = "duke") String userName) {
        List<User> users = new ArrayList<>();
        users.add(new User("3", "duke2", "duke2", new Date()));
        users.add(new User("1", "duke", "duke", new Date()));
        users.add(new User("2", "duke1", "duke1", new Date()));
        return users;
    }

    @RequestMapping(value = "/object", method = RequestMethod.GET)
    @JsonView(User.UserSimpleView.class)
    public List<User> queryByCondition(UserQueryCondition userQueryCondition) {
        List<User> users = new ArrayList<>();
        // System.out.println(ReflectionToStringBuilder.toString(userQueryCondition, ToStringStyle.SIMPLE_STYLE));
        users.add(new User("2", "duke", "duke", new Date()));
        users.add(new User("1", "duke1", "duke1", new Date()));
        users.add(new User("3", "duke2", "duke2", new Date()));
        return users;
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    @JsonView(User.UserDetailView.class)
    public User getInfo(@PathVariable(name = "id", required = false) String id) {
        // System.out.println("进入getInfo服务");
        User user = new User();
        user.setUserName("duke");
        return user;
    }

    @RequestMapping(method = RequestMethod.POST)
    public User create(@Valid @RequestBody User user, BindingResult errors) {
//        if (errors.hasErrors()) {
//            errors.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
//        }
//        System.out.println(user.getPassword());
//        System.out.println(user.getBirthday());
//        System.out.println(user.getUserName());
        user.setId("1");
        return user;
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.PUT)
    public User update(@Valid @RequestBody User user, BindingResult errors) {

//        if (errors.hasErrors()) {
//            errors.getAllErrors().forEach(error -> {
//                FieldError fieldError = (FieldError) error;
//                String message = fieldError.getField() + "：" + fieldError.getDefaultMessage();
//                System.out.println(message);
//            });
//        }
//        if (errors.hasErrors()) {
//            errors.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
//        }
//        System.out.println(user.getPassword());
//        System.out.println(user.getUserName());
//
//        System.out.println(user.getBirthday());
        user.setId("1");
        return user;
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id) {
        System.out.println(id);
    }

    @RequestMapping(value = "/test/exception/{id}", method = RequestMethod.GET)
    public void customizeException(@PathVariable String id) {
        throw new UserNotFoundException("用户不存在", id);
    }

    /**
     * 注册用户
     * @param user 用户对象
     */
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    public void regist(User user, HttpServletRequest request) {
        // 注册用户

        providerSignInUtils.doPostSignUp(user.getUserName(), new ServletWebRequest(request));
    }
}
