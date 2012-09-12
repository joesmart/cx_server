package com.server.cx.dao.cx;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.server.cx.entity.cx.StatusType;

public interface StatusTypeDao extends PagingAndSortingRepository<StatusType, Long>{

    public StatusType findByName(String name);

}
