package com.server.cx.dao.cx.spec;

import com.server.cx.entity.cx.GraphicInfo;
import com.server.cx.entity.cx.GraphicResource;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * User: yanjianzou
 * Date: 12-8-29
 * Time: 下午4:45
 * FileName:GraphicResourceSpecifications
 */
public class GraphicResourceSpecifications {
    public static Specification<GraphicResource> findGraphicResourceByGraphicinfo(final GraphicInfo graphicInfo){
        return  new Specification<GraphicResource>() {
            @Override
            public Predicate toPredicate(Root<GraphicResource> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("graphicInfo"),graphicInfo);
            }
        };
    }
}
