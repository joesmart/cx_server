package com.server.cx.dao.cx;

import com.server.cx.entity.cx.StatusCXInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: yanjianzou
 * Date: 12-7-20
 * Time: 下午2:36
 * FileName:GenericStatusCXInfoDao
 */
public interface GenericStatusCXInfoDao extends PagingAndSortingRepository<StatusCXInfo, Long> {
}
