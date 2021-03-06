package com.server.cx.dao.cx;

import com.server.cx.dao.cx.custom.SmsMessageCustomDao;
import com.server.cx.entity.cx.SmsMessage;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-7-20
 * Time: 下午2:21
 * FileName:SmsMessageCustomDao
 */
public interface SmsMessageDao extends PagingAndSortingRepository<SmsMessage, Long>, SmsMessageCustomDao {
    public List<SmsMessage> findByIsSendAndFromMobileNo(boolean isSend,String fromMobileNo);
}
