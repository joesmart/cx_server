package com.server.cx.dao.cx;

import com.server.cx.entity.cx.ShortPhoneNo;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: yanjianzou
 * Date: 12-7-20
 * Time: 下午2:29
 * FileName:GenericShortPhoneNoDao
 */
public interface GenericShortPhoneNoDao extends PagingAndSortingRepository<ShortPhoneNo,Long> {
}
