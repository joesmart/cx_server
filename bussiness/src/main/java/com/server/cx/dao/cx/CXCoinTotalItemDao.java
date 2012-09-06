package com.server.cx.dao.cx;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.server.cx.entity.cx.CXCoinTotalItem;

public interface CXCoinTotalItemDao  extends JpaRepository<CXCoinTotalItem, Long>,JpaSpecificationExecutor<CXCoinTotalItem> {

}
