package com.server.cx.dao.cx.spec;

import com.server.cx.entity.cx.UserFavorites;
import com.server.cx.entity.cx.UserInfo;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * User: yanjianzou
 * Date: 12-8-2
 * Time: 上午10:49
 * FileName:UserFavoriteSpecifications
 */
public class UserFavoriteSpecifications {
    public static Specification<UserFavorites> userFavoritesSpecification(final UserInfo userInfo){
       return  new Specification<UserFavorites>() {
           @Override
           public Predicate toPredicate(Root<UserFavorites> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
               return cb.equal(root.get("user"),userInfo);
           }
       };
    }
}
