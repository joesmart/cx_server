package com.server.cx.dao.cx.impl;

import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.GenericDaoHibernate;
import com.server.cx.dao.cx.ShortPhoneNoDao;
import com.server.cx.entity.cx.ShortPhoneNo;
import com.server.cx.exception.CXServerBussinessException;
import com.server.cx.exception.SystemException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("shortPhoneNoDao")
@Transactional
public class ShortPhoneNoHibernateDao extends GenericDaoHibernate<ShortPhoneNo, Long> implements ShortPhoneNoDao {

  public ShortPhoneNoHibernateDao() {
    super(ShortPhoneNo.class);
  }

  @Override
  public void deleteAllOldShortPhonNos(Long userId) throws SystemException {
    try {
      String hql = "delete from ShortPhoneNo d where d.user.id=" + userId;
      super.getHibernateTemplate().bulkUpdate(hql);
    } catch (DataAccessException e) {
      SystemException exception = new CXServerBussinessException(e, Constants.SERVER_RUNTIME_DATA_DELETE_ERROR);
      throw exception;
    }
  }

}
