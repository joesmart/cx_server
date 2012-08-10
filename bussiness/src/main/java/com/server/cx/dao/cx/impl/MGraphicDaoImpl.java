package com.server.cx.dao.cx.impl;

import com.server.cx.dao.cx.custom.MGraphicCustomDao;
import com.server.cx.entity.cx.MGraphic;
import com.server.cx.entity.cx.UserInfo;

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
    public List<MGraphic> queryUserMGraphics(UserInfo userInfo, Integer maxPriority, String callPhoneNo) {
        String sql = "select a from MGraphic a where a.userInfo =:userInfo and a.priority =:maxPriority";
        if(maxPriority == 4){
            sql = "select a from UserCommonMGraphic a where a.userInfo =:userInfo and a.priority =:maxPriority and :callPhoneNo in elements(a.phoneNos)";
        }
        TypedQuery<MGraphic> typedQuery = em.createQuery(sql, MGraphic.class);
        typedQuery.setParameter("userInfo", userInfo);
        typedQuery.setParameter("maxPriority", maxPriority);
        if(maxPriority == 4){
            typedQuery.setParameter("callPhoneNo", callPhoneNo);
        }
        return typedQuery.getResultList();
    }

    @Override
    public int queryMaxPriorityByUserInfo(UserInfo userInfo, String callPhoneNo) {
        String hql = "select max(a.priority) from UserCommonMGraphic a where (a.userInfo = :userInfo and a.modeType=1 ) " +
                "or (:callPhoneNo in elements(a.phoneNos) and a.userInfo=:userInfo and a.modeType=2)";
        TypedQuery<Integer> query = em.createQuery(hql, Integer.class);
        query.setParameter("userInfo", userInfo);
        query.setParameter("callPhoneNo", callPhoneNo);
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
