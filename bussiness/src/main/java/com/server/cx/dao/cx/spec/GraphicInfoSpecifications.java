package com.server.cx.dao.cx.spec;

import com.server.cx.entity.cx.GraphicInfo;
import com.server.cx.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * User: yanjianzou Date: 12-7-30 Time: 上午10:04 FileName:GraphicInfoSpecifications
 */
public class GraphicInfoSpecifications {

    public static Specification<GraphicInfo> categoryTypeGraphicInfo(final Long categoryId) {
        return new Specification<GraphicInfo>() {
            @Override
            public Predicate toPredicate(Root<GraphicInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.and(cb.isNotNull(root.get("category")),
                    cb.equal(root.get("category").<Long> get("id"), categoryId));
            }
        };
    }

    public static Specification<GraphicInfo> hotCategoryTypeGraphicInfo() {
        return new Specification<GraphicInfo>() {
            @Override
            public Predicate toPredicate(Root<GraphicInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.isNotNull(root.get("category"));
            }
        };
    }

    public static Specification<GraphicInfo> recommendGraphicInfo() {
        return new Specification<GraphicInfo>() {
            @Override
            public Predicate toPredicate(Root<GraphicInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.and(cb.isNotNull(root.get("category")), cb.equal(root.<Boolean> get("recommend"), true));
            }
        };
    }

    public static Specification<GraphicInfo> statusTypeGraphicInfoExcludedUsed(final Long statusTypeId, final String usedId) {
        return new Specification<GraphicInfo>() {
            @Override
            public Predicate toPredicate(Root<GraphicInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if(StringUtil.notNull(usedId)) {
                    return cb.and(cb.isNotNull(root.get("statusType")),
                        cb.equal(root.get("statusType").<Long> get("id"), statusTypeId),
                        cb.notEqual(root.get("id"), usedId));
                }
                return cb.and(cb.isNotNull(root.get("statusType")),
                    cb.equal(root.get("statusType").<Long> get("id"), statusTypeId));
            }
        };
    }

    public static Specification<GraphicInfo> holidayTypeGraphicInfoExcludedUsed(final Long holidayTypeId, final String usedId) {
        return new Specification<GraphicInfo>() {
            @Override
            public Predicate toPredicate(Root<GraphicInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if(StringUtil.notNull(usedId)) {
                    return cb.and(cb.isNotNull(root.get("holidayType")),
                        cb.equal(root.get("holidayType").<Long> get("id"), holidayTypeId),
                        cb.notEqual(root.get("id"), usedId));
                }
                return cb.and(cb.isNotNull(root.get("holidayType")),
                    cb.equal(root.get("holidayType").<Long> get("id"), holidayTypeId));
            }
        };
    }
}
