package com.server.cx.dao.cx.impl;

import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.UserFavoritesDao;
import com.server.cx.entity.cx.UserFavorites;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("userFavoritesDao")
@Transactional
public class UserFavoritesHibernateDao  implements UserFavoritesDao {

    public UserFavoritesHibernateDao(){
    }

    @PersistenceContext
    private EntityManager em;
    //通过获取User的Favorites 列表去判断是否已经添加  会生成多条select语句,所以添加一个方法来判断是否已经添加.
    @Override
    public boolean isAlreadAddedInUserFavorites(final Long userId,final Long cxInfoId) {
        
        Integer queryResult = null; //(Integer)super.getHibernateTemplate().findByCriteria(criteria);
        Session session = (Session) em.getDelegate();
        Criteria criteria = session.createCriteria(UserFavorites.class)
                .add(Restrictions.eq("user.id", userId))
                .add(Restrictions.eq("cxInfo.id", cxInfoId))
                .setProjection(Projections.projectionList().add(Projections.rowCount()));
        int o = ((Long) criteria.uniqueResult()).intValue();
        queryResult =  Integer.valueOf(o);

        if (queryResult == null || queryResult.intValue() == 0) {
            return false;
        }
        if (queryResult.intValue() > 0) {
            return true;
        }
        return false;
        
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserFavorites> getAllUserFavorites(Long userid, String typeId, final int requestPage, final int requesPageSize) {
        int perPageSize = requesPageSize==0? Constants.DEFAULT_SIZE:requesPageSize;
        int begineRecord = (requestPage==0?1:requestPage-1)*perPageSize;
        
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserFavorites.class);
        Criteria criteria  =  detachedCriteria.getExecutableCriteria((Session) em.getDelegate());
        criteria.add(Restrictions.eq("user.id",userid)).addOrder(Property.forName("id").desc())
                .createCriteria("cxInfo")
                .add(Restrictions.eq("type", typeId));
                
        criteria.setFirstResult(begineRecord).setMaxResults(perPageSize);
        List<UserFavorites> result  = criteria.list();
        return result;
    }



    @Override
    public Integer getUserFavoritesTotalCount(Long userId) {
        // TODO Auto-generated method stub
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserFavorites.class);
        detachedCriteria.add(Restrictions.eq("user.id", userId)).setProjection(Projections.rowCount());
        
        Criteria criteria = detachedCriteria.getExecutableCriteria((Session) em.getDelegate());
        Long rowCount = (Long)criteria.uniqueResult();
        
        return rowCount.intValue();
    }

}
