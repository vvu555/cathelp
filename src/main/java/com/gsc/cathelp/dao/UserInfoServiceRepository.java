package com.gsc.cathelp.dao;

import com.gsc.cathelp.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserInfoServiceRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User>{
}
