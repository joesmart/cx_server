package com.server.cx.dao.cx;

import com.server.cx.entity.cx.StatusPackage;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: yanjianzou
 * Date: 12-7-20
 * Time: 下午2:37
 * FileName:GenericStatusPackageDao
 */
public interface GenericStatusPackageDao extends PagingAndSortingRepository<StatusPackage, Long> {
}
