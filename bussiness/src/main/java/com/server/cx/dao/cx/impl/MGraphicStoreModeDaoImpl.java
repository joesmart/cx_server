package com.server.cx.dao.cx.impl;

import com.google.common.base.Preconditions;
import com.server.cx.dao.cx.custom.MGraphicStoreModeCustomDao;
import com.server.cx.entity.cx.Contacts;
import com.server.cx.entity.cx.MGraphicStoreMode;
import com.server.cx.exception.CXServerBussinessException;
import com.server.cx.exception.SystemException;
import com.server.cx.util.business.ValidationUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("mgraphicStoreModeDao")
@Transactional
public class MGraphicStoreModeDaoImpl extends BasicDao implements MGraphicStoreModeCustomDao {

    public MGraphicStoreModeDaoImpl() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public MGraphicStoreMode getDefaulModeUserCXInfo() throws SystemException {
        MGraphicStoreMode mgraphicStoreMode = null;
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(MGraphicStoreMode.class);
            criteria.add(Restrictions.eq("type", 1));
            List<MGraphicStoreMode> list = criteria.getExecutableCriteria(getSession()).list();
            if (list != null && list.size() > 0) {
                int index = (int) Math.round(Math.random() * (list.size() - 1));
                mgraphicStoreMode = list.get(index);
            }
        } catch (DataAccessException e) {
            SystemException exception = new CXServerBussinessException(e, "数据存储错误");
            throw exception;
        }

        return mgraphicStoreMode;
    }

    @SuppressWarnings("unchecked")
    public List<MGraphicStoreMode> getAllMGraphicStoreModeByUserId(Long userId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MGraphicStoreMode.class);
        detachedCriteria.add(Restrictions.eq("userInfo.id", userId))
                .add(Restrictions.ne("modeType", 5))
                .addOrder(Order.desc("modeType"))
                .addOrder(Order.desc("auditPass"))
                .addOrder(Order.asc("modifyTime"));
        List<MGraphicStoreMode> result = detachedCriteria.getExecutableCriteria(getSession()).list();
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public MGraphicStoreMode getCurrentValidStatusMGraphicStoreMode(Long userId, Integer currentHour) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MGraphicStoreMode.class);
        detachedCriteria.add(Restrictions.eq("type", 3)).add(Restrictions.eq("modeType", 5))
                .add(Restrictions.eq("userInfo.id", userId)).add(Restrictions.le("startHour", currentHour))
                .add(Restrictions.gt("endHour", currentHour));
        List<MGraphicStoreMode> list = detachedCriteria.getExecutableCriteria(getSession()).list();
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getIdOfTheSameMGraphicStoreMode(Long userId, MGraphicStoreMode mgraphicStoreMode)
            throws SystemException {

        ValidationUtil.checkParametersNotNull(mgraphicStoreMode);
        Preconditions.checkNotNull(mgraphicStoreMode.getModeType());

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MGraphicStoreMode.class);
        int modeType = mgraphicStoreMode.getModeType();
        // 时间模式的重复性验证.
        if (mgraphicStoreMode.getStartHour() != null) {
            detachedCriteria.add(Restrictions.eq("startHour", mgraphicStoreMode.getStartHour()));
        }
        if (mgraphicStoreMode.getEndHour() != null) {

            detachedCriteria.add(Restrictions.eq("endHour", mgraphicStoreMode.getEndHour()));
        }
        if (mgraphicStoreMode.getPhoneNo() != null && !"".equals(mgraphicStoreMode.getPhoneNo())) {
            detachedCriteria.add(Restrictions.eq("phoneNo", mgraphicStoreMode.getPhoneNo()));
        }
        detachedCriteria.add(Restrictions.eq("modeType", modeType)).add(Restrictions.eq("userInfo.id", userId))
                .add(Restrictions.eq("type", 3)).setProjection(Projections.property("id"));

        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        List<String> id = criteria.list();
        return id;
    }

    @Override
    public List<MGraphicStoreMode> getAllCatactsMGraphicStoreModes(Long userId) {
        DetachedCriteria contactsCriteria = DetachedCriteria.forClass(Contacts.class);
        contactsCriteria.add(Restrictions.eq("userInfo.id", userId))
                .setProjection(Property.forName("selfUserInfo.id"));

        /* DetachedCriteria userInfoCriteria = DetachedCriteria.forClass(UserInfo.class);
userInfoCriteria.add(Property.forName("imsi").in(contactsCriteria))
        .setProjection(Property.forName("id"));*/

        DetachedCriteria mgraphicStoreModeCriteria = DetachedCriteria.forClass(MGraphicStoreMode.class);
        mgraphicStoreModeCriteria.add(Property.forName("userInfo.id").in(contactsCriteria))
                .add(Restrictions.eq("type", 3))
                .add(Restrictions.ne("modeType", 0))
                .add(Restrictions.ne("modeType", 5));

        List<MGraphicStoreMode> result = mgraphicStoreModeCriteria.getExecutableCriteria(getSession()).list();

        return result;
    }

    @Override
    public void deleteUserAllStatus(Long userid) {
        //TODO need refactor to resolve the parameter depend issue; by JoeSmart
        String hql = "delete from MGraphicStoreMode where userInfo.id=" + userid + " and modeType=5";
        em.createQuery(hql).executeUpdate();
    }

    @Override
    public MGraphicStoreMode getMGraphicStoreModeByModeType(Long userId, Integer modeType) {
        MGraphicStoreMode mgraphicStoreMode = null;
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(MGraphicStoreMode.class);

            criteria.add(Restrictions.eq("type", 3))
                    .add(Restrictions.eq("modeType", modeType))
                    .add(Restrictions.eq("userInfo.id", userId));

            List<MGraphicStoreMode> list = criteria.getExecutableCriteria(getSession()).list();
            if (list != null && list.size() > 0) {
                int index = (int) Math.round(Math.random() * (list.size() - 1));
                mgraphicStoreMode = list.get(index);
            }
        } catch (DataAccessException e) {
            SystemException exception = new CXServerBussinessException(e, "数据检索错误");
            throw exception;
        }

        return mgraphicStoreMode;
    }

}
