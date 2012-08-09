package com.server.cx.dao.cx.impl;

import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.custom.UserFavoritesCustomDao;
import com.server.cx.entity.cx.UserFavorites;
import com.server.cx.entity.cx.UserInfo;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("userFavoritesDao")
@Transactional
public class UserFavoritesDaoImpl extends BasicDao implements UserFavoritesCustomDao {

    public UserFavoritesDaoImpl() {
    }

    // 通过获取User的Favorites 列表去判断是否已经添加 会生成多条select语句,所以添加一个方法来判断是否已经添加.
    @Override
    public boolean isAlreadAddedInUserFavorites(final String userId, final String graphicInfoId) {

        Criteria criteria = getSession().createCriteria(UserFavorites.class).add(Restrictions.eq("user.id", userId))
                .add(Restrictions.eq("graphicInfo.id", graphicInfoId))
                .setProjection(Projections.projectionList().add(Projections.rowCount()));
        Integer queryResult = Integer.valueOf(((Long) criteria.uniqueResult()).intValue());

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
    public List<UserFavorites> getAllUserFavorites(String userid, final int requestPage, final int requesPageSize) {
        int perPageSize = requesPageSize == 0 ? Constants.DEFAULT_SIZE : requesPageSize;
        int begineRecord = (requestPage == 0 ? 1 : requestPage - 1) * perPageSize;

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserFavorites.class);
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        criteria.add(Restrictions.eq("user.id", userid)).add(Restrictions.isNotNull("resourceId"))
                .addOrder(Property.forName("id").desc());
        // 通过createCriteria("cxInfo") 创建表级关联查询.
        // .createCriteria("cxInfo").add(Restrictions.eq("type", typeId));
        criteria.setFirstResult(begineRecord).setMaxResults(perPageSize);
        List<UserFavorites> result = criteria.list();
        return result;
    }

    @Override
    public Integer getUserFavoritesTotalCount(String userId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserFavorites.class);
        detachedCriteria.add(Restrictions.eq("user.id", userId)).setProjection(Projections.rowCount());
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        Long rowCount = (Long) criteria.uniqueResult();

        return rowCount.intValue();
    }

    @Override
    public List<String> getGraphicIdListByUserInfo(UserInfo userInfo) {

        String hql = "select a.graphicInfo.id from UserFavorites a where a.user = :userInfo ";
        List<String> graphicInfoIdList = em.createQuery(hql,String.class).setParameter("userInfo",userInfo).getResultList();

        return graphicInfoIdList;
    }

}
