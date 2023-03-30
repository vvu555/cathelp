package com.gsc.cathelp.dao;
import com.gsc.cathelp.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TypeRepository extends JpaRepository<Type,Long> {
    Page findAll(Pageable pageable);

    Type findByName(String name);

    @Query("select t from t_type t")
    List<Type> finTop(Pageable pageable);
}
