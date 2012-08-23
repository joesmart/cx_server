package com.server.cx.dao.cx.impl;

import java.util.List;
import javax.persistence.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.server.cx.dao.cx.custom.UserInfoCustomDao;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.MoneyNotEnoughException;


//@Repository("userInfoDao")
//@Transactional
@Component
public class UserInfoDaoImpl extends BasicDao implements UserInfoCustomDao {

    public UserInfoDaoImpl() {
    }

    @Override
    public UserInfo getUserInfoByPhoneNo(String phoneNo) {
        String property = "phoneNo";
        return getUserInfoByPropertyAndValue(property, phoneNo);
    }


    @Override
    public UserInfo getUserInfoByImsi(String imsi) {
        String property = "imsi";
        return getUserInfoByPropertyAndValue(property, imsi);
    }

    @SuppressWarnings("unchecked")
    private UserInfo getUserInfoByPropertyAndValue(String property, String value) {
        DetachedCriteria criteria = DetachedCriteria.forClass(UserInfo.class);
        criteria.add(Restrictions.eq(property, value));
        List<UserInfo> list = criteria.getExecutableCriteria(getSession()).list();
        if (list != null && list.size() > 0) {
            UserInfo userInfo = list.get(0);
            return userInfo;
        }
        return null;
    }

    // 检测是否传入的电话号码都已经注册过了没,返回 未注册的电话号码
    @SuppressWarnings("unchecked")
    @Override
    public List<String> getHasRegisteredPhoneNos(List<String> phoneNos) {

        DetachedCriteria criteria = DetachedCriteria.forClass(UserInfo.class);
        criteria.add(Restrictions.in("phoneNo", phoneNos)).setProjection(Projections.property("phoneNo"));
        List<String> mobiles = criteria.getExecutableCriteria(getSession()).list();
        return mobiles;
    }

    @Override
    public List<UserInfo> getUserInfosByPhoneNos(List<String> phoneNos) {
        DetachedCriteria criteria = DetachedCriteria.forClass(UserInfo.class);
        criteria.add(Restrictions.in("phoneNo", phoneNos));
        List<UserInfo> userinfos = criteria.getExecutableCriteria(getSession()).list();
        return userinfos;
    }

    @Override
    public boolean checkCurrentMoneyValidate(String id, Double price) {
        DetachedCriteria criteria = DetachedCriteria.forClass(UserInfo.class);
        criteria.add(Restrictions.idEq(id)).add(Restrictions.gt("totleMoney", price));
        List<UserInfo> userInfos = criteria.getExecutableCriteria(getSession()).list();
        if(userInfos != null && !userInfos.isEmpty()) 
            return true;
        throw new MoneyNotEnoughException("余额不足");
    }

    @Override
    public void minusMoneyByPrice(String id, Double price) {
        String hql = "update UserInfo info set info.totleMoney = ? where id = ?";
        Query query = em.createQuery(hql);
        query.setParameter(1, 35d);
        query.setParameter(2, id);
        query.executeUpdate();
    }
    
    
}
