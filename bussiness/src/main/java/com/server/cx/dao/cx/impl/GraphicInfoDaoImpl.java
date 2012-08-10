package com.server.cx.dao.cx.impl;

import com.server.cx.dao.cx.custom.GraphicInfoCustomDao;
import com.server.cx.entity.cx.GraphicInfo;
import org.springframework.data.domain.Page;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-7-30
 * Time: 下午2:06
 * FileName:GraphicInfoDaoImpl
 */
public class GraphicInfoDaoImpl extends BasicDao implements GraphicInfoCustomDao {
    @Override
    public Page findGraphicInfoByCategoryType(int offset, int limit, Long categoryId) {
        return  null;
    }

    public List<GraphicInfo> findMark(){
        String hql = "select a from GraphicInfo a left outer join MGraphic  b with b.graphicInfo= a ";
        TypedQuery<GraphicInfo> query =  em.createQuery(hql, GraphicInfo.class);
          return query.getResultList();
    }
}
