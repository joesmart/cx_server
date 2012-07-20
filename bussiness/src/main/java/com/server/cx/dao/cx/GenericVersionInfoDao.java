package com.server.cx.dao.cx;

import com.server.cx.entity.cx.VersionInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: yanjianzou
 * Date: 12-7-20
 * Time: 下午2:22
 * FileName:GenericVersionInfoDao
 */
public interface GenericVersionInfoDao extends PagingAndSortingRepository<VersionInfo,Long> {
}
