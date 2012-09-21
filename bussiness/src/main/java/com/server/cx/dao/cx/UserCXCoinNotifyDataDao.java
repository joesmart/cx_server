package com.server.cx.dao.cx;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.server.cx.entity.cx.UserCXCoinNotifyData;

public interface UserCXCoinNotifyDataDao extends JpaRepository<UserCXCoinNotifyData, Long>,
    JpaSpecificationExecutor<UserCXCoinNotifyData> {

    public UserCXCoinNotifyData findByOutTradeNo(String outTradeNo);

}
