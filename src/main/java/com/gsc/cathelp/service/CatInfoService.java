package com.gsc.cathelp.service;
import com.gsc.cathelp.po.Cat;
import com.gsc.cathelp.vo.CatQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


public interface CatInfoService {
    Cat getCatInfo(Long id);
    Page<Cat> listCat(Pageable pageable, CatQuery cat);
    Page<Cat> listTypeCat(Pageable pageable, CatQuery cat);
    Page<Cat> listCat(Pageable pageable);
    Map<String, List<Cat>> archiveCat();
    Long countCat();
    Cat saveCat(Cat cat);
    Cat updateCat(Long id,Cat cat);
    void deleteCat(Long id);

}
