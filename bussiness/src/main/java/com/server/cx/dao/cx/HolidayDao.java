package com.server.cx.dao.cx;

import com.server.cx.entity.cx.Holiday;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface HolidayDao  extends PagingAndSortingRepository<Holiday, Long> {


}
