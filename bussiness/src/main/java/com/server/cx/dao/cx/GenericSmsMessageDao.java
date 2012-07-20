package com.server.cx.dao.cx;

import com.server.cx.entity.cx.SmsMessage;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: yanjianzou
 * Date: 12-7-20
 * Time: 下午2:21
 * FileName:GenericSmsMessageDao
 */
public interface GenericSmsMessageDao extends PagingAndSortingRepository<SmsMessage, Long> {
}
