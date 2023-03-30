package com.gsc.cathelp.service;

import com.gsc.cathelp.po.Adopt;
import com.gsc.cathelp.po.Cat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;


public interface CatAdoptService {
    Page<Cat> listUnAdoptCat(Pageable pageable);
    Page<Adopt> listAdoptApply(Pageable pageable);
    Page<Adopt> listUserApply(HttpSession session, Pageable pageable);
    Adopt getAdoptId(Long id);
    Adopt save(Adopt adopt);
    Adopt userSave(Adopt adopt);
    Adopt updateAdopt(Long id,Adopt adopt);
    Adopt adoptTt(Long id,Adopt adopt);

}
