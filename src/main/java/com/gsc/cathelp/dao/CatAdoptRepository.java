package com.gsc.cathelp.dao;

import com.gsc.cathelp.po.Adopt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CatAdoptRepository extends JpaRepository<Adopt,Long>, JpaSpecificationExecutor<Adopt> {

    @Transactional
    @Modifying
    @Query("update t_adopt a set a.money = 2 where a.id = ?1")
    int updateMoney(Long id);

    @Transactional
    @Modifying
    @Query("update t_adopt a set a.paySn = ?2 where a.id = ?1")
    int updateSn(Long id,String sn);

    @Transactional
    @Modifying
    @Query("update t_adopt a set a.money = 1 where a.paySn = ?1")
    int updateNotifySn(String sn);

}
