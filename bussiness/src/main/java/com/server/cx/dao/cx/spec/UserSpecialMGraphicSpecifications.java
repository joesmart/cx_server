package com.server.cx.dao.cx.spec;

import com.server.cx.entity.cx.UserInfo;
import com.server.cx.entity.cx.UserSpecialMGraphic;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * User: yanjianzou
 * Date: 12-8-6
 * Time: 下午4:15
 * FileName:UserSpecialMGraphicSpecifications
 */
public class UserSpecialMGraphicSpecifications {

    public static Specification<UserSpecialMGraphic> userSpecialMGraphicCount(final UserInfo userInfo){
        return  new Specification<UserSpecialMGraphic>() {
            @Override
            public Predicate toPredicate(Root<UserSpecialMGraphic> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("userInfo"),userInfo);
            }
        };
    }
}
