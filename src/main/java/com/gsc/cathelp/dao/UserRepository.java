package com.gsc.cathelp.dao;
import com.gsc.cathelp.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

//应用SpringBoot里的JPA
public interface UserRepository extends JpaRepository<User,Long> {   //继承JpaRepository解释为dao操作对象（User）的Long（主键），不用在意细节里面会自动增删改查
    //自动查找用户名和密码
    User findByUsernameAndPassword(String username, String password);
    User findByUsername(String username);
}
