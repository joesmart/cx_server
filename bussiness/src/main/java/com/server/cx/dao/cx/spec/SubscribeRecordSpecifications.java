package com.server.cx.dao.cx.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.entity.cx.UserSubscribeRecord;

public class SubscribeRecordSpecifications {

    public static Specification<UserSubscribeRecord> userSubscribeRecord(final UserInfo userInfo) {
        return new Specification<UserSubscribeRecord>() {
            @Override
            public Predicate toPredicate(Root<UserSubscribeRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.and(cb.isNotNull(root.get("userInfo")),
                    cb.equal(root.get("userInfo").<String> get("id"), userInfo.getId()));
            }
        };
    }

}
