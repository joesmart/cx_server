package com.server.cx.dao.cx.spec;

import com.server.cx.entity.cx.Holiday;
import com.server.cx.entity.cx.HolidayType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Date;

/**
 * User: yanjianzou
 * Date: 12-8-13
 * Time: 上午9:41
 * FileName:HolidaySpecifications
 */
public class HolidaySpecifications {
    public static Specification<Holiday> getCurrentValidaHolidayQueryByHolidayTypeAndCurrentDate(final HolidayType holidayType, final Date date){
      return new Specification<Holiday>() {
          @Override
          public Predicate toPredicate(Root<Holiday> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              Expression equal = cb.equal(root.<HolidayType>get("type"),holidayType);
              Expression time = cb.greaterThanOrEqualTo(root.<Date>get("day"),date);
              return cb.and(equal,time);
          }
      };
    }
}
