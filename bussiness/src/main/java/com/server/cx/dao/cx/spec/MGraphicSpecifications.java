package com.server.cx.dao.cx.spec;

import com.server.cx.entity.cx.MGraphic;
import com.server.cx.entity.cx.UserInfo;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * User: yanjianzou
 * Date: 12-8-7
 * Time: 下午12:45
 * FileName:MGraphicSpecifications
 */
public class MGraphicSpecifications {
    public static Specification<MGraphic> userMGraphic(final UserInfo userInfo){
        return  new Specification<MGraphic>() {
            @Override
            public Predicate toPredicate(Root<MGraphic> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return  cb.and(cb.equal(root.get("userInfo"),userInfo),cb.le(root.<Integer>get("modeType"),2));
            }
        };
    }
}
