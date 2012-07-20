package com.server.cx.dao.cx.impl;

import com.server.cx.dao.cx.SmsMessageDao;
import com.server.cx.entity.cx.SmsMessage;
import com.server.cx.exception.InvalidParameterException;
import com.server.cx.exception.SystemException;
import com.server.cx.util.MessageHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository("smsMessageDao")
public class SmsMessageHibernateDao implements SmsMessageDao {

    public SmsMessageHibernateDao() {
    }

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MessageHelp messageHelp;

    @Override
    @Transactional
    public void batchInsertSmsMessage(final List<String> content, final List<String> mobiles, final String userInfoPhoneNo) throws SystemException {

        if (mobiles == null || mobiles.size() == 0) {
            throw new InvalidParameterException(messageHelp.getZhMessage("server.runtime.exception.parametersnull"));
        }
        SmsMessage smsMessage = null;

        for (int i = 0; i < content.size(); i++) {
            smsMessage = new SmsMessage();
            smsMessage.setSms(content.get(i));
            smsMessage.setFromMobileNo(userInfoPhoneNo);
            smsMessage.setToMobileNo(mobiles.get(i));
            smsMessage.setIsSend(false);
            smsMessage.setCreatedOn(new Date());
            em.persist(smsMessage);
        }

    }

    @Override
    @Transactional
    public void updateSmsMessageSentStatus(final Long[] ids) {
        String updateHql =  "update sms_message set isSend=?, updatedOn=? where id=?";
        for (Long id : ids) {
            em.createQuery(updateHql).setParameter(0, true).setParameter(1, new Timestamp(System.currentTimeMillis())).setParameter(2, id).executeUpdate();
        }
    }

}
