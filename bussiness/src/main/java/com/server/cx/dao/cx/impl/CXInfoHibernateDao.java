package com.server.cx.dao.cx.impl;

import com.google.common.collect.Lists;
import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.CXInfoDao;
import com.server.cx.dao.cx.GenericDaoHibernate;
import com.server.cx.entity.cx.CXInfo;
import com.server.cx.exception.CXServerBussinessException;
import com.server.cx.exception.SystemException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository("cxInfoDao")
@Transactional
public class CXInfoHibernateDao extends GenericDaoHibernate<CXInfo, Long> implements CXInfoDao {

  public CXInfoHibernateDao() {
    super(CXInfo.class);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<CXInfo> browserAllData(final Map<String, String> params) throws SystemException {

    // TODO 需要转换为 criteria 方式减少 repeat.
    final String type = params.get("type");
    final String category = params.get("category");

    try {
      List<CXInfo> result = super.getHibernateTemplate().execute(new HibernateCallback<List<CXInfo>>() {

        @Override
        public List<CXInfo> doInHibernate(Session session) throws HibernateException, SQLException {
          int requestPage = 0;
          int requestPageSize = 0;

          try {
            requestPage = Integer.parseInt(params.get("begingPage"));
            requestPageSize = Integer.parseInt(params.get("pageSize"));
          } catch (NumberFormatException e) {
            requestPage = 0;
            requestPageSize = Constants.DEFAULT_SIZE;
          }

          int perPageSize = requestPageSize == 0 ? Constants.DEFAULT_SIZE : requestPageSize;
          int begineRecord = requestPage == 0 ? 0 : (requestPage - 1) * perPageSize;

          Query query = null;

          // the method which with many parameters will kill programmer.
          String hsql = "from CXInfo c where c.type = ? and c.category= ? and c.isServer=1";
          if (type != null && !"".equals(type) && category != null && !"".equals(category)) {
            hsql = "from CXInfo c where c.type = ? and c.category = ? and c.isServer=1";
            hsql += " order by uploadTime desc";
            query = session.createQuery(hsql);
            query.setParameter(0, type);
            query.setParameter(1, category);
          }
          if ((type == null || "".equals(type)) && category != null && !"".equals(category)) {
            hsql = "from CXInfo c where c.category = ? and c.isServer=1";
            hsql += " order by uploadTime desc";
            query = session.createQuery(hsql);
            query.setParameter(0, category);
          }
          if ((type != null && !"".equals(type)) && (category == null || "".equals(category))) {
            hsql = "from CXInfo c where c.type = ? and  c.isServer=1";
            query = session.createQuery(hsql);
            hsql += " order by uploadTime desc";
            query.setParameter(0, type);
          }
          if ((type == null || "".equals(type)) && (category == null || "".equals(category))) {
            hsql = "from CXInfo c where c.isServer=1";
            hsql += " order by uploadTime desc";
            query = session.createQuery(hsql);
          }

          query.setFirstResult(begineRecord);
          query.setMaxResults(perPageSize);

          List<CXInfo> resultList = query.list();
          return resultList;

        }
      });
      return result;
    } catch (HibernateException e) {
      e.printStackTrace();
      SystemException exception =
          new CXServerBussinessException(e, getMessageHelp().getZhMessage(Constants.SERVER_RUNTIME_DATA_QUERY_ERROR));
      throw exception;
    }


  }

  @SuppressWarnings("unchecked")
  public List<CXInfo> getCMCCCXInfos() {
    List<CXInfo> list = Lists.newArrayList();
    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CXInfo.class);
    detachedCriteria.add(Restrictions.eq("isServer", 10086));
    list = getHibernateTemplate().findByCriteria(detachedCriteria);
    return list;
  }

}
