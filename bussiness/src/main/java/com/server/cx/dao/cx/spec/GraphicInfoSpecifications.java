package com.server.cx.dao.cx.spec;

import com.server.cx.entity.cx.GraphicInfo;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * User: yanjianzou
 * Date: 12-7-30
 * Time: 上午10:04
 * FileName:GraphicInfoSpecifications
 */
public class GraphicInfoSpecifications {

    public static Specification<GraphicInfo> categoryTypeGraphicInfo(final Long categoryId){
       return  new Specification<GraphicInfo>() {
           @Override
           public Predicate toPredicate(Root<GraphicInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
               return  cb.and(cb.isNotNull(root.get("category")),cb.equal(root.get("category").<String>get("id"),categoryId));
           }
       };
    }
}
