package com.server.cx.dao.cx.impl;

import com.server.cx.dao.cx.custom.SmsMessageCustomDao;
import com.server.cx.exception.InvalidParameterException;
import com.server.cx.exception.SystemException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository("smsMessageDao")
public class SmsMessageDaoImpl extends BasicDao implements SmsMessageCustomDao {

    public SmsMessageDaoImpl() {
    }

    @Override
    @Transactional
    public void batchInsertSmsMessage(final List<String> content, final List<String> mobiles, final String userInfoPhoneNo)
            throws SystemException {

        if (mobiles == null || mobiles.size() == 0) {
            throw new InvalidParameterException(getMessageHelp().getZhMessage("server.runtime.exception.parametersnull"));
        }

        final int size = mobiles.size();
        getJdbcTemplate().batchUpdate(
                "insert into sms_message(sms,from_mobile_no,to_mobile_no,is_send,created_on) values(?,?,?,?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int index) throws SQLException {
                        ps.setString(1, content.get(index));
                        ps.setString(2, userInfoPhoneNo);
                        ps.setString(3, mobiles.get(index));
                        ps.setBoolean(4, false);
                        ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                    }

                    @Override
                    public int getBatchSize() {
                        return size;
                    }
                });
    }

    @Override
    @Transactional
    public void updateSmsMessageSentStatus(final Long[] ids) {
        super.getJdbcTemplate().batchUpdate("update sms_message set is_send = ?,updated_on=? where id= ?",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int index) throws SQLException {
                        ps.setBoolean(1, true);
                        ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                        ps.setLong(3, ids[index]);
                    }

                    @Override
                    public int getBatchSize() {
                        return ids.length;
                    }
                });
    }

}
