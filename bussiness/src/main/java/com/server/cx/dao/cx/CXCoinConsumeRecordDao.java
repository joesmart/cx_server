package com.server.cx.dao.cx;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.server.cx.entity.cx.CXCoinConsumeRecord;
import com.server.cx.entity.cx.UserInfo;

public interface CXCoinConsumeRecordDao extends PagingAndSortingRepository<CXCoinConsumeRecord, Long> {

    public CXCoinConsumeRecord findByUserInfo(UserInfo userInfo);
}
