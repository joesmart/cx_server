package com.server.cx.dao.cx;

import com.server.cx.entity.cx.CXInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: yanjianzou
 * Date: 12-7-20
 * Time: 下午2:07
 * FileName:GenericCXInfoDao
 */
public interface GenericCXInfoDao extends PagingAndSortingRepository<CXInfo, Long> {
}
