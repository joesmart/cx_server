package com.server.cx.dao.cx.spec;

import com.server.cx.entity.cx.UserCustomMGraphic;
import com.server.cx.entity.cx.UserInfo;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * User: yanjianzou
 * Date: 12-8-14
 * Time: 下午1:32
 * FileName:UserCustomMGraphicSpecifications
 */
public class UserCustomMGraphicSpecifications {
    public static Specification<UserCustomMGraphic> userCustomMGraphic(final UserInfo userInfo){
        return  new Specification<UserCustomMGraphic>() {
            @Override
            public Predicate toPredicate(Root<UserCustomMGraphic> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return  cb.and(cb.equal(root.get("userInfo"),userInfo));
            }
        };
    }
}
