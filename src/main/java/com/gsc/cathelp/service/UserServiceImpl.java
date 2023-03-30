package com.gsc.cathelp.service;
import com.gsc.cathelp.dao.UserRepository;
import com.gsc.cathelp.po.User;
import com.gsc.cathelp.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


//转入数据库的操作层（dao）调用SpringBooot里的JAP的方法
//注解意思是一个可以用的service
@Service
public class UserServiceImpl implements UserService {
    //注入
    @Autowired
    private UserRepository userRepository;//获取在dao层的UserRepository方法查找到的用户名和密码

    @Override
    public User checkUser(String username, String password) {  //查询数据库，是否存在用户名和密码
        User user = userRepository.findByUsernameAndPassword(username, password); //获取在数据库查到的用户名和密码
        //User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password)); MD5加密查询
        return user; //返回给user
    }

    @Override
    public User checkUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }
    //注册功能
    public void insertUser(User user){
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userRepository.save(user);
    }
}
