package com.gsc.cathelp.service;

import com.gsc.cathelp.NotFoundException;
import com.gsc.cathelp.dao.CatAdoptRepository;
import com.gsc.cathelp.dao.CatInfoRepository;
import com.gsc.cathelp.po.Adopt;
import com.gsc.cathelp.po.Cat;
import com.gsc.cathelp.po.User;
import com.gsc.cathelp.util.MyBeanUtils;
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
import javax.servlet.http.HttpSession;
import java.util.Date;

@Service
public class CatAdoptServiceImpl implements CatAdoptService{

    @Autowired
    private CatInfoRepository catInfoRepository;

    @Autowired
    private CatAdoptRepository catAdoptRepository;

    @Override
    public Page<Cat> listUnAdoptCat(Pageable pageable) {
        Specification<Cat> specification = new Specification<Cat>() {
            @Override
            public Predicate toPredicate(Root<Cat> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate published = criteriaBuilder.equal(root.get("published"),true);
                Predicate adopt = criteriaBuilder.equal(root.get("adopt"),false);
                Predicate and = criteriaBuilder.and(adopt,published);
                return and;
            }
        };
        return catInfoRepository.findAll(specification,pageable);
    }

    @Override
    public Adopt getAdoptId(Long id) {
        return catAdoptRepository.getOne(id);
    }

    @Transactional
    @Override
    public Adopt save(Adopt adopt) {
        if (adopt.getId() != null){
            adopt.setUpdateTime(new Date());
        }
        return catAdoptRepository.save(adopt);

    }

    @Transactional
    @Override
    public Adopt userSave(Adopt adopt) {
        adopt.setUpdateTime(new Date());
        adopt.setCreateTime(new Date());
        Cat cat = adopt.getCat();
        cat = catInfoRepository.getOne(cat.getId());
        adopt.setCat(cat);
        return catAdoptRepository.save(adopt);
    }


    @Override
    public Page<Adopt> listAdoptApply(Pageable pageable) {
        return catAdoptRepository.findAll(pageable);
    }

    @Override
    public Page<Adopt> listUserApply(HttpSession session, Pageable pageable) {
        User user = (User) session.getAttribute("user");
        Specification<Adopt> specification = new Specification<Adopt>() {
            @Override
            public Predicate toPredicate(Root<Adopt> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("user"),user.getId());
                return predicate;
            }
        };
        Page<Adopt> page = catAdoptRepository.findAll(specification, pageable);
        return page;
    }

    @Override
    public Adopt updateAdopt(Long id, Adopt adopt) {
        Adopt a = catAdoptRepository.getOne(id);
        if (a == null){
            throw new NotFoundException("不存在");
        }
        BeanUtils.copyProperties(adopt,a, MyBeanUtils.getNullPropertyNames(adopt));
        return catAdoptRepository.save(a);
    }

    @Override
    public Adopt adoptTt(Long id, Adopt adopt) {
        Adopt a = catAdoptRepository.getOne(id);
        //a.setMoney();
        BeanUtils.copyProperties(adopt,a, MyBeanUtils.getNullPropertyNames(adopt));
        return catAdoptRepository.save(a);
    }

}
