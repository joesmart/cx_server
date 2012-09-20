package com.server.cx.dao.cx.impl;

import com.server.cx.dao.cx.custom.MGraphicCustomDao;
import com.server.cx.entity.cx.MGraphic;
import com.server.cx.entity.cx.UserInfo;
import org.joda.time.LocalDate;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-8
 * Time: 下午3:04
 * FileName:MGraphicDaoImpl
 */
public class MGraphicDaoImpl extends BasicDao implements MGraphicCustomDao {

    @Override
    public int queryMaxPriorityByUserInfo(UserInfo callerUserInfo, String selfPhoneNo) {
        String hql = "select max(a.priority) from UserCommonMGraphic a " +
                "where a.userInfo = :userInfo " +
                "and ( " +
                "( :currentDate between  a.begin and a.end and a.modeType=3)  " +
                "or  ( :callPhoneNo in elements(a.phoneNos) and a.modeType =3 and :currentDate between  a.begin and a.end )" +
                "or  ( :callPhoneNo in elements(a.phoneNos) and a.modeType !=3)" +
                "or (a.modeType=2 and a.common=true) " +
                "or (a.modeType=3 and a.common=true) " +
                "or (a.validDate = :currentDate and a.modeType =5) " +
                "or (a.holiday = :currentDate and a.modeType=4)" +
                ") ";
        TypedQuery<Integer> query = em.createQuery(hql, Integer.class);
        query.setParameter("userInfo", callerUserInfo);
        query.setParameter("callPhoneNo", selfPhoneNo);
        query.setParameter("currentDate", LocalDate.now().toDate());
        Integer commonPriority = query.getSingleResult();

        if(commonPriority != null){
            return commonPriority;
        }else {
            return  -1;
        }
    }

    @Override
    public MGraphic queryDefaultMGraphic(String callPhoneNo) {
        String hql = "select a from DefaultMGraphic a where (a.specialPhoneNo=:callPhoneNo or a.specialPhoneNo is null) order by a.specialPhoneNo desc ";
        TypedQuery<MGraphic> typedQuery = em.createQuery(hql, MGraphic.class);
        typedQuery.setParameter("callPhoneNo", callPhoneNo);
        List<MGraphic> mGraphics = typedQuery.getResultList();
        return mGraphics!=null&& mGraphics.size()>0 ? mGraphics.get(0): null;
    }


}
