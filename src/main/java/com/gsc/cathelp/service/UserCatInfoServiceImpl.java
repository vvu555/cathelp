package com.gsc.cathelp.service;

import com.gsc.cathelp.NotFoundException;
import com.gsc.cathelp.dao.CatInfoRepository;
import com.gsc.cathelp.po.Cat;
import com.gsc.cathelp.po.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserCatInfoServiceImpl implements UserCatInfoService{

    @Autowired
    private CatInfoRepository catInfoRepository;


    @Transactional
    @Override
    public Cat getCat(Long userId) {
        return catInfoRepository.getOne(userId);
    }

    @Transactional
    @Override
    public Page<Cat> listUserCat(HttpSession session ,Pageable pageable) {
        User user = (User) session.getAttribute("user");
        Specification<Cat> specification = new Specification<Cat>() {
            @Override
            public Predicate toPredicate(Root<Cat> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("user"),user.getId());
                return predicate;
            }
        };
        Page<Cat> page = catInfoRepository.findAll(specification, pageable);
        return page;
    }

    @Transactional
    @Override
    public Cat saveUserCatInfo(Cat cat) {
        return catInfoRepository.save(cat);
    }

    @Transactional
    @Override
    public Cat updateUserCatInfo(Long id, Cat cat) {
        Cat c = catInfoRepository.getOne(id);
        if (c == null){
            throw new NotFoundException("该信息不存在");
        }
        BeanUtils.copyProperties(cat,c);
        return catInfoRepository.save(c);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        catInfoRepository.deleteById(id);
    }
}
