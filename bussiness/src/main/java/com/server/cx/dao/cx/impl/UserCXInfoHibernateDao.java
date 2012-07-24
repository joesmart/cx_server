package com.server.cx.dao.cx.impl;

import com.google.common.base.Preconditions;
import com.server.cx.dao.cx.GenericDaoHibernate;
import com.server.cx.dao.cx.UserCXInfoDao;
import com.server.cx.entity.cx.UserCXInfo;
import com.server.cx.exception.CXServerBussinessException;
import com.server.cx.exception.SystemException;
import com.server.cx.util.business.ValidationUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Repository("userCXInfoDao")
@Transactional
public class UserCXInfoHibernateDao extends GenericDaoHibernate<UserCXInfo, Long> implements UserCXInfoDao {

  public UserCXInfoHibernateDao() {
    super(UserCXInfo.class);
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<UserCXInfo> loadUserCXInfoByImsi(String imsi) {
    List<UserCXInfo> resultList = Collections.emptyList();

    DetachedCriteria criteria = DetachedCriteria.forClass(UserCXInfo.class);
    criteria.add(Restrictions.eq("imsi", imsi)).add(Restrictions.eq("type", 3)).addOrder(Property.forName("id").desc());

    resultList = super.getHibernateTemplate().findByCriteria(criteria);
    return resultList;

  }

  @Override
  @SuppressWarnings("unchecked")
  public UserCXInfo getDefaulModeUserCXInfo() throws SystemException {
    UserCXInfo userCXInfo = null;
    try {
      DetachedCriteria criteria = DetachedCriteria.forClass(UserCXInfo.class);
      criteria.add(Restrictions.eq("type", 1));
      List<UserCXInfo> list = super.getHibernateTemplate().findByCriteria(criteria);
      if (list != null && list.size() > 0) {
        int index = (int) Math.round(Math.random() * (list.size() - 1));
        userCXInfo = list.get(index);
      }
    } catch (DataAccessException e) {
      SystemException exception = new CXServerBussinessException(e, "数据存储错误");
      throw exception;
    }

    return userCXInfo;
  }

  @SuppressWarnings("unchecked")
  public List<UserCXInfo> getAllUserCXInfosByUserId(Long userId) {
    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserCXInfo.class);
    detachedCriteria.add(Restrictions.eq("userInfo.id", userId));
    List<UserCXInfo> result = getHibernateTemplate().findByCriteria(detachedCriteria);
    return result;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Integer getCurrentUserCXInfoModeType(Long id) {
    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserCXInfo.class);
    detachedCriteria.setProjection(Projections.property("modeType")).add(Restrictions.eq("id", id));
    List<Integer> modeTypes = super.getHibernateTemplate().findByCriteria(detachedCriteria);
    if (modeTypes != null && modeTypes.size() > 0) return modeTypes.get(0);
    return 0;
  }

  @SuppressWarnings("unchecked")
  @Override
  public UserCXInfo getStatusUserCXInfo(Integer statusType) {
    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserCXInfo.class);
    detachedCriteria.add(Restrictions.eq("type", 2)).add(Restrictions.eq("statusType", statusType));

    List<UserCXInfo> list = getHibernateTemplate().findByCriteria(detachedCriteria);
    if (list != null && list.size() > 0) {
      return list.get(0);
    } else {
      return null;
    }
  }

  @Override
  public Long getCommonModeUserCXInfo(Long userId) throws SystemException {

    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserCXInfo.class);
    detachedCriteria.add(Restrictions.eq("userInfo.id", userId)).add(Restrictions.eq("modeType", 1))
        .setProjection(Projections.property("id"));
    Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
    try {
      Long commonModeUserCXInfoId = (Long) criteria.uniqueResult();
      return commonModeUserCXInfoId;
    } catch (HibernateException e) {
      SystemException systemException = new CXServerBussinessException(e, "服务器存在重复的一般模式用户设定彩像");
      throw systemException;
    }
  }

  @Override
  public int getUserCXInfosCountByModetype(Long userId, int modeType) throws SystemException {

    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserCXInfo.class);
    detachedCriteria.add(Restrictions.eq("userInfo.id", userId)).add(Restrictions.eq("modeType", modeType))
        .add(Restrictions.eq("type", 3)).setProjection(Projections.projectionList().add(Projections.rowCount()));

    Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
    int count = ((Long) criteria.uniqueResult()).intValue();
    return count;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Long> getIdOfTheSameUserCXInfo(Long userId, UserCXInfo userCXInfo) throws SystemException {

    ValidationUtil.checkParametersNotNull(userCXInfo);
    Preconditions.checkNotNull(userCXInfo.getModeType());

    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserCXInfo.class);
    int modeType = userCXInfo.getModeType();
    // 时间模式的重复性验证.
    if (userCXInfo.getStartTime() != null) {
      detachedCriteria.add(Restrictions.eq("startTime", userCXInfo.getStartTime()));
    }
    if (userCXInfo.getEndTime() != null) {

      detachedCriteria.add(Restrictions.eq("endTime", userCXInfo.getEndTime()));
    }
    if (userCXInfo.getPhoneNo() != null && !"".equals(userCXInfo.getPhoneNo())) {
      detachedCriteria.add(Restrictions.eq("phoneNo", userCXInfo.getPhoneNo()));
    }
    detachedCriteria.add(Restrictions.eq("modeType", modeType)).add(Restrictions.eq("userInfo.id", userId))
        .add(Restrictions.eq("type", 3)).setProjection(Projections.property("id"));

    Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
    List<Long> id = criteria.list();
    return id;
  }
}
