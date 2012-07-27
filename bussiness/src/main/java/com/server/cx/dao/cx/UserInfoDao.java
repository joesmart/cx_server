package com.server.cx.dao.cx;

import com.server.cx.dao.cx.custom.UserInfoCustomDao;
import com.server.cx.entity.cx.UserInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: yanjianzou
 * Date: 12-7-20
 * Time: 下午2:09
 * FileName:UserInfoDao
 */
public interface UserInfoDao extends PagingAndSortingRepository<UserInfo, Long>, UserInfoCustomDao {
}
