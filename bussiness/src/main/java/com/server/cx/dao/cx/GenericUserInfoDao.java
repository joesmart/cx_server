package com.server.cx.dao.cx;

import com.server.cx.entity.cx.UserInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: yanjianzou
 * Date: 12-7-20
 * Time: 下午2:09
 * FileName:GenericUserInfoDao
 */
public interface GenericUserInfoDao extends PagingAndSortingRepository<UserInfo,Long> {
}
