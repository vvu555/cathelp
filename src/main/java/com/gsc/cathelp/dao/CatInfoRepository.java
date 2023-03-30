package com.gsc.cathelp.dao;

import com.gsc.cathelp.po.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.awt.peer.LightweightPeer;
import java.util.List;

public interface CatInfoRepository extends JpaRepository<Cat,Long> , JpaSpecificationExecutor<Cat> {
    @Transactional
    @Modifying
    @Query("update t_cat b set b.views = b.views+1 where b.id = ?1")
    int updateViews(Long id);

    @Transactional
    @Modifying
    @Query("update t_cat a set a.adopt = 1 where a.id = ?1")
    int updateAdopt(Long id);

    @Query(value = "select * from t_cat  where id = ?1",nativeQuery = true)
    Cat findCatById(Long id);

    @Query(value = "select date_format(b.update_time, '%Y') as year from t_cat b group by year order by year desc",nativeQuery = true)
    List<String> findGroupYear();

    @Query("select b from t_cat b where function('date_format',b.updateTime,'%Y') = ?1")
    List<Cat> findByYear(String year);


    //修改
    @Query(value = "select * from t_cat c WHERE user_id = ?1",nativeQuery = true)
    List<Cat> findByUser(Long userId);

}
