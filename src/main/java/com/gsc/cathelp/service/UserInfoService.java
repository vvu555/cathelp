package com.gsc.cathelp.service;

import com.gsc.cathelp.po.User;
import com.gsc.cathelp.vo.UserQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserInfoService {
    User getUser(Long id);
    Page<User> listUser(Pageable pageable,UserQuery user);
    User saveUser(User user);
    User updateUser(Long id,User user);
    void deleteUser(Long id);
}
