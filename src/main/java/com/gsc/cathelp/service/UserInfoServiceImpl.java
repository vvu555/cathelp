package com.gsc.cathelp.service;

import com.gsc.cathelp.NotFoundException;
import com.gsc.cathelp.dao.UserInfoServiceRepository;
import com.gsc.cathelp.po.User;
import com.gsc.cathelp.util.MyBeanUtils;
import com.gsc.cathelp.vo.UserQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoServiceRepository userInfoServiceRepository;

    @Override
    public User getUser(Long id) {
        return userInfoServiceRepository.getOne(id);
    }

    //搜索功能
    @Override
    public Page<User> listUser(Pageable pageable, UserQuery user) {
        return userInfoServiceRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(user.getUsername()) && user.getUsername() !=null){
                    predicates.add(criteriaBuilder.like(root.<String>get("username"),"%"+user.getUsername()+"%"));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Transactional
    @Override
    public User saveUser(User user) {
        if(user.getId() == null){
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
        }
        else {
            user.setUpdateTime(new Date());
        }
        return userInfoServiceRepository.save(user);
    }

    @Transactional
    @Override
    public User updateUser(Long id, User user) {
        User u = userInfoServiceRepository.getOne(id);
        if (u == null){
            throw new NotFoundException("该用户不存在");
        }
        BeanUtils.copyProperties(user,u, MyBeanUtils.getNullPropertyNames(user));
        return userInfoServiceRepository.save(u);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userInfoServiceRepository.deleteById(id);
    }
}
