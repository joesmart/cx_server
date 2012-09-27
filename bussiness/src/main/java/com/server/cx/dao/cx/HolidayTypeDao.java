package com.server.cx.dao.cx;

import com.server.cx.entity.cx.HolidayType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface HolidayTypeDao extends PagingAndSortingRepository<HolidayType, Long>,JpaSpecificationExecutor<HolidayType> {

    public HolidayType findByName(String name);
}