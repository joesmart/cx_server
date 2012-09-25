package com.server.cx.dao.cx.impl;

import com.google.common.base.Preconditions;
import com.server.cx.dao.cx.custom.UserCommonMGraphicCustomDao;
import com.server.cx.entity.cx.Contacts;
import com.server.cx.entity.cx.UserCommonMGraphic;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.CXServerBusinessException;
import com.server.cx.exception.SystemException;
import com.server.cx.util.business.ValidationUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.joda.time.LocalDate;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository("mgraphicStoreModeDao")
@Transactional
public class UserCommonMGraphicDaoImpl extends BasicDao implements UserCommonMGraphicCustomDao {

    public UserCommonMGraphicDaoImpl() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public UserCommonMGraphic getDefaultModeUserCXInfo() throws SystemException {
        UserCommonMGraphic mgraphicUserCommon = null;
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(UserCommonMGraphic.class);
            criteria.add(Restrictions.eq("type", 1));
            List<UserCommonMGraphic> list = criteria.getExecutableCriteria(getSession()).list();
            if (list != null && list.size() > 0) {
                int index = (int) Math.round(Math.random() * (list.size() - 1));
                mgraphicUserCommon = list.get(index);
            }
        } catch (DataAccessException e) {
            SystemException exception = new CXServerBusinessException("数据存储错误", e);
            throw exception;
        }

        return mgraphicUserCommon;
    }

    @SuppressWarnings("unchecked")
    public List<UserCommonMGraphic> getAllMGraphicStoreModeByUserId(String userId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserCommonMGraphic.class);
        detachedCriteria.add(Restrictions.eq("userInfo.id", userId))
                .add(Restrictions.ne("modeType", 5))
                .addOrder(Order.desc("modeType"))
                .addOrder(Order.desc("auditPass"))
                .addOrder(Order.asc("modifyTime"));
        List<UserCommonMGraphic> result = detachedCriteria.getExecutableCriteria(getSession()).list();
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public UserCommonMGraphic getCurrentValidStatusMGraphicStoreMode(String userId, Integer currentHour) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserCommonMGraphic.class);
        detachedCriteria.add(Restrictions.eq("type", 3)).add(Restrictions.eq("modeType", 5))
                .add(Restrictions.eq("userInfo.id", userId)).add(Restrictions.le("startHour", currentHour))
                .add(Restrictions.gt("endHour", currentHour));
        List<UserCommonMGraphic> list = detachedCriteria.getExecutableCriteria(getSession()).list();
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getIdOfTheSameMGraphicStoreMode(Long userId, UserCommonMGraphic mgraphicUserCommon)
            throws SystemException {

        ValidationUtil.checkParametersNotNull(mgraphicUserCommon);
        Preconditions.checkNotNull(mgraphicUserCommon.getModeType());

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserCommonMGraphic.class);
        int modeType = mgraphicUserCommon.getModeType();
        // 时间模式的重复性验证.
        /*if (mgraphicUserCommon.getStartHour() != null) {
            detachedCriteria.add(Restrictions.eq("startHour", mgraphicUserCommon.getStartHour()));
        }
        if (mgraphicUserCommon.getEndHour() != null) {

            detachedCriteria.add(Restrictions.eq("endHour", mgraphicUserCommon.getEndHour()));
        }*/

        detachedCriteria.add(Restrictions.eq("modeType", modeType)).add(Restrictions.eq("userInfo.id", userId))
                .add(Restrictions.eq("type", 3)).setProjection(Projections.property("id"));

        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        List<String> id = criteria.list();
        return id;
    }

    @Override
    public List<UserCommonMGraphic> getAllContactsMGraphicStoreModes(String userId) {
        DetachedCriteria contactsCriteria = DetachedCriteria.forClass(Contacts.class);
        contactsCriteria.add(Restrictions.eq("userInfo.id", userId))
                .setProjection(Property.forName("selfUserInfo.id"));

        /* DetachedCriteria userInfoCriteria = DetachedCriteria.forClass(UserInfo.class);
userInfoCriteria.add(Property.forName("imsi").in(contactsCriteria))
        .setProjection(Property.forName("id"));*/

        DetachedCriteria mgraphicStoreModeCriteria = DetachedCriteria.forClass(UserCommonMGraphic.class);
        mgraphicStoreModeCriteria.add(Property.forName("userInfo.id").in(contactsCriteria))
                .add(Restrictions.eq("type", 3))
                .add(Restrictions.ne("modeType", 0))
                .add(Restrictions.ne("modeType", 5));

        List<UserCommonMGraphic> result = mgraphicStoreModeCriteria.getExecutableCriteria(getSession()).list();

        return result;
    }

    @Override
    public void deleteUserAllStatus(String userId) {
        //TODO need refactor to resolve the parameter depend issue; by JoeSmart
        String hql = "delete from UserCommonMGraphic where userInfo.id=" + userId + " and modeType=5";
        em.createQuery(hql).executeUpdate();
    }

    @Override
    public UserCommonMGraphic getMGraphicStoreModeByModeType(String userId, Integer modeType) {
        UserCommonMGraphic mgraphicUserCommon = null;
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(UserCommonMGraphic.class);

            criteria.add(Restrictions.eq("type", 3))
                    .add(Restrictions.eq("modeType", modeType))
                    .add(Restrictions.eq("userInfo.id", userId));

            List<UserCommonMGraphic> list = criteria.getExecutableCriteria(getSession()).list();
            if (list != null && list.size() > 0) {
                int index = (int) Math.round(Math.random() * (list.size() - 1));
                mgraphicUserCommon = list.get(index);
            }
        } catch (DataAccessException e) {
            SystemException exception = new CXServerBusinessException("数据检索错误", e);
            throw exception;
        }

        return mgraphicUserCommon;
    }


    @Override
    public List<UserCommonMGraphic> queryUserMGraphics(UserInfo userInfo, Integer maxPriority, String callPhoneNo) {

        String sql = "select a from UserCommonMGraphic a " +
                "where a.userInfo = :userInfo " +
                "and ( " +
                "( :currentDate between  a.begin and a.end and a.modeType=3)  " +
                "or  ( :callPhoneNo in elements(a.phoneNos)  " +
                "and ( (:currentDate between  a.begin and a.end) or a.begin is null or a.holiday = :currentDate or a.holiday is null or a.validDate =  :currentDate or a.validDate is null ) )" +
                "or  ( :callPhoneNo in elements(a.phoneNos) and a.modeType !=3) " +
                "or (a.modeType=2 and a.common=true) " +
                "or (a.modeType=3 and a.common=true) " +
                "or (a.validDate = :currentDate and a.modeType =5) " +
                "or (a.holiday = :currentDate and a.modeType=4) " +
                ") and a.priority = :maxPriority order by a.modeType desc,a.end ";

        TypedQuery<UserCommonMGraphic> typedQuery = em.createQuery(sql, UserCommonMGraphic.class);
        typedQuery.setParameter("userInfo", userInfo);
        typedQuery.setParameter("maxPriority", maxPriority);
        typedQuery.setParameter("callPhoneNo", callPhoneNo);
        typedQuery.setParameter("currentDate", LocalDate.now().toDate());

        return typedQuery.getResultList();
    }


}
