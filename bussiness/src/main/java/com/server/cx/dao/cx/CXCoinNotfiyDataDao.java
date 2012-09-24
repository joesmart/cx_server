package com.server.cx.dao.cx;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.server.cx.entity.cx.CXCoinNotfiyData;

public interface CXCoinNotfiyDataDao extends JpaRepository<CXCoinNotfiyData, Long>,
    JpaSpecificationExecutor<CXCoinNotfiyData> {
    
    public CXCoinNotfiyData findByOutTradeNo(String outTradeNo);

}
