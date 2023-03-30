package com.gsc.cathelp.service;

import com.gsc.cathelp.NotFoundException;
import com.gsc.cathelp.dao.CatInfoRepository;
import com.gsc.cathelp.po.Cat;
import com.gsc.cathelp.po.Type;
import com.gsc.cathelp.util.MyBeanUtils;
import com.gsc.cathelp.vo.CatQuery;
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
import java.util.*;

@Service
public class CatInfoServiceImpl implements CatInfoService {

    @Autowired
    private CatInfoRepository catInfoRepository;

    @Transactional
    @Override
    public Cat getCatInfo(Long id) {
        catInfoRepository.updateViews(id);
        Optional<Cat> cat = catInfoRepository.findById(id);
        Cat cat1 = cat.get();
        return cat1;

    }

    @Override
    public Page<Cat> listCat(Pageable pageable, CatQuery cat) {
        return catInfoRepository.findAll(new Specification<Cat>() {
            @Override
            public Predicate toPredicate(Root<Cat> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(!"".equals(cat.getName()) && cat.getName() !=null){
                    predicates.add(criteriaBuilder.like(root.<String>get("name"),"%"+cat.getName()+"%"));
                }
                if (cat.getTypeId() != null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), cat.getTypeId()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Cat> listTypeCat(Pageable pageable, CatQuery cat) {
        return catInfoRepository.findAll(new Specification<Cat>() {
            @Override
            public Predicate toPredicate(Root<Cat> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                Predicate published = criteriaBuilder.equal(root.get("published"),true);
                if(!"".equals(cat.getName()) && cat.getName() !=null){
                    predicates.add(criteriaBuilder.like(root.<String>get("name"),"%"+cat.getName()+"%"));
                    return published;
                }
                if (cat.getTypeId() != null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), cat.getTypeId()));

                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Cat> listCat(Pageable pageable) {
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
    public Map<String, List<Cat>> archiveCat() {
        List<String> years = catInfoRepository.findGroupYear();
        Map<String, List<Cat>> map = new HashMap<>();
        for(String year : years){
            map.put(year,catInfoRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countCat() {
        return catInfoRepository.count();
    }

    @Transactional
    @Override
    public Cat saveCat(Cat cat) {
        if(cat.getId() ==null){
            cat.setCreateTime(new Date());
            cat.setUpdateTime(new Date());
            cat.setViews(0);
        }
        else {
            cat.setUpdateTime(new Date());
        }
        return catInfoRepository.save(cat);
    }

    @Transactional
    @Override
    public Cat updateCat(Long id, Cat cat) {
        Cat t = catInfoRepository.getOne(id);
        if (t == null){
            throw new NotFoundException("不存在");
        }
        BeanUtils.copyProperties(cat,t, MyBeanUtils.getNullPropertyNames(cat));
        return catInfoRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteCat(Long id) {
        catInfoRepository.deleteById(id);
    }
}
