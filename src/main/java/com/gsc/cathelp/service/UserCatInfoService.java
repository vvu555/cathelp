package com.gsc.cathelp.service;

import com.gsc.cathelp.po.Cat;
import com.gsc.cathelp.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserCatInfoService {
    Cat getCat(Long userId);
    Page<Cat> listUserCat(HttpSession session, Pageable pageable);
    Cat saveUserCatInfo(Cat cat);
    Cat updateUserCatInfo(Long id,Cat cat);
    void delete(Long id);
}
