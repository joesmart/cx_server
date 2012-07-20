package com.server.cx.dao.cx;

import com.server.cx.entity.cx.UserStatus;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: yanjianzou
 * Date: 12-7-20
 * Time: 下午2:31
 * FileName:GenericUserStatusDao
 */
public interface GenericUserStatusDao extends PagingAndSortingRepository<UserStatus,Long> {
}
