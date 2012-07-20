package com.server.cx.dao.cx;

import com.server.cx.entity.cx.UserCXInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: yanjianzou
 * Date: 12-7-20
 * Time: 下午2:16
 * FileName:GenericUserCXInfoDao
 */
public interface GenericUserCXInfoDao extends PagingAndSortingRepository<UserCXInfo, Long> {
}
