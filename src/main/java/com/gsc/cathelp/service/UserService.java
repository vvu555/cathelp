package com.gsc.cathelp.service;
import com.gsc.cathelp.po.User;

//将登陆传入的用户名和密码获取到这里
public interface UserService {
    User checkUser(String username, String password);
    User checkUsername(String username);
}
