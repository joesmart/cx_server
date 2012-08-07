package com.server.cx.dao.cx.spec;

import com.server.cx.entity.cx.HistoryMGraphic;
import com.server.cx.entity.cx.UserInfo;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * User: yanjianzou
 * Date: 12-8-7
 * Time: 下午2:55
 * FileName:HistoryMGraphicSpecifications
 */
public class HistoryMGraphicSpecifications {
    public static Specification<HistoryMGraphic> userHistMGraphics(final UserInfo userInfo){
        return  new Specification<HistoryMGraphic>() {
            @Override
            public Predicate toPredicate(Root<HistoryMGraphic> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("userInfo"),userInfo);
            }
        };
    }
}
