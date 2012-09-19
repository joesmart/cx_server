package com.server.cx.service.cx.impl;

import com.server.cx.dao.cx.SmsMessageDao;
import com.server.cx.entity.cx.SmsMessage;
import com.server.cx.service.cx.SendSMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

/**
 * User: yanjianzou
 * Date: 9/7/12
 * Time: 10:31 AM
 * FileName:SendSMSServiceImpl
 */
@Service(value = "sendSMSService")
public class SendSMSServiceImpl implements SendSMSService {
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    private SmsMessageDao smsMessageDao;

    @Override
    public Long[] sendSMS(String fromMobileNo) {
      //  setDataSource();
        List<SmsMessage> smsMessageList = smsMessageDao.findByIsSendAndFromMobileNo(false, fromMobileNo);
        if(smsMessageList == null || smsMessageList.size() == 0){
            return null;
        }
        Long[] ids = new Long[smsMessageList.size()];
        SqlParameterSource[] sqlParameterSources = new SqlParameterSource[smsMessageList.size()];
        int i=0;
        for(SmsMessage smsMessage:smsMessageList){
            sqlParameterSources[i]=new MapSqlParameterSource().addValue("addresses",smsMessage.getToMobileNo())
                                                                 .addValue("senderName",smsMessage.getFromMobileNo())
                                                                 .addValue("message", smsMessage.getSms());
            ids[i]= smsMessage.getId();
            i = i+1;
        }
        int[] row = simpleJdbcInsert.executeBatch(sqlParameterSources);
        if( row != null && row.length > 0){
            return ids;
        }else{
            return null;
        }
    }


    @Autowired
    @Qualifier("sqlServerDataSource")
    public void setDataSource(DataSource dataSource) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("sendSms").usingColumns("addresses","senderName","message");
    }
}
