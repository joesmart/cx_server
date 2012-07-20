package com.server.cx.dao.cx.impl;

import com.google.common.collect.Lists;
import com.server.cx.dao.cx.GenericDaoHibernate;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.ShortPhoneNo;
import com.server.cx.entity.cx.UserInfo;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository("userInfoDao")
@Transactional
public class UserInfoHibernateDao extends GenericDaoHibernate<UserInfo,Long> implements UserInfoDao {

    public UserInfoHibernateDao(){
        super(UserInfo.class);
    }

    @Override
    public UserInfo getUserInfoByPhoneNo(String phoneNo){
        String property="phoneNo";
        return getUserInfoByPropertyAndValue(property, phoneNo);
    }
    
    
    
    @Override
    public UserInfo getUserInfoByImsi(String imsi){
        String property = "imsi";
        return getUserInfoByPropertyAndValue(property, imsi);
    }

    @SuppressWarnings("unchecked")
    private UserInfo getUserInfoByPropertyAndValue(String property, String value) {
        DetachedCriteria criteria = DetachedCriteria.forClass(UserInfo.class);
        criteria.add(Restrictions.eq(property, value));
        List<UserInfo> list = super.getHibernateTemplate().findByCriteria(criteria);
        if(list != null && list.size()>0){
            UserInfo userInfo = list.get(0);
            return userInfo;
        }
        return null;
    }

    //检测是否传入的电话号码都已经注册过了没,返回 未注册的电话号码
    @SuppressWarnings("unchecked")
    @Override 
    public List<String> getHasRegisteredPhoneNos(List<String> phoneNos) {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(UserInfo.class);
        criteria.add(Restrictions.in("phoneNo", phoneNos)).setProjection(Projections.property("phoneNo"));
        List<String> mobiles = getHibernateTemplate().findByCriteria(criteria);
        return mobiles;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserInfo> getUserInfoByShortPhoneNo(String shortPhoneNo) {
        //TODO 严重的业务逻辑错误,短号码有实现的必要吗?
        DetachedCriteria criteria = DetachedCriteria.forClass(ShortPhoneNo.class);
        criteria.add(Restrictions.eq("shortPhoneNo",shortPhoneNo));
        List<ShortPhoneNo> shortPhoneNos = super.getHibernateTemplate().findByCriteria(criteria);
        if(shortPhoneNos != null && shortPhoneNos.size()>0){
            List<UserInfo> userInfos = Lists.newArrayList();
            for(ShortPhoneNo tempShortPhoneNo:shortPhoneNos){
                userInfos.add(tempShortPhoneNo.getUser());
            }
            return userInfos;
        }
        return null;
    }
}
