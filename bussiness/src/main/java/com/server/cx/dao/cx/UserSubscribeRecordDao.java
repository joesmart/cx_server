package com.server.cx.dao.cx;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.server.cx.entity.cx.UserSubscribeRecord;

public interface UserSubscribeRecordDao extends JpaRepository<UserSubscribeRecord, Long>,
    JpaSpecificationExecutor<UserSubscribeRecord> {

}
