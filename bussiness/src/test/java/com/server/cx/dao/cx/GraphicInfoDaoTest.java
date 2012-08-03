package com.server.cx.dao.cx;

import com.google.common.collect.Lists;
import com.server.cx.dao.cx.spec.GraphicInfoSpecifications;
import com.server.cx.entity.cx.Category;
import com.server.cx.entity.cx.GraphicInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: yanjianzou
 * Date: 12-7-30
 * Time: 上午10:21
 * FileName:GraphicInfoDaoTest
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class GraphicInfoDaoTest extends SpringTransactionalTestCase {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private GraphicInfoDao graphicInfoDao;
    //@Autowired
    //private DataInitializer dataInitializer;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void should_get_one_Category_when_query_category_table() {
        List<Category> categoryList = Lists.newArrayList(categoryDao.findOne(1L));
        assertThat(categoryList.size()).isGreaterThan(0);
        assertThat(categoryList.get(0).getName()).isEqualTo("心情物语");
    }

    @Test
    public void should_get_one_GraphicInfo_when_query_graphicinfo_table(){
        List<GraphicInfo> graphicInfoList = Lists.newArrayList(graphicInfoDao.findAll());
        assertThat(graphicInfoList.size()).isGreaterThan(0);
    }

    @Test
    public void should_find_first_page_data_when_find_by_Specification_categoryTypeGraphicInfo(){
        PageRequest pageRequest  = new PageRequest(0,2, Sort.Direction.DESC,"createdOn");
        Page<GraphicInfo> page  = graphicInfoDao.findAll(GraphicInfoSpecifications.categoryTypeGraphicInfo(1L),pageRequest);
        List<GraphicInfo> graphicInfoList = page.getContent();
        assertThat(graphicInfoList).isNotNull();
        assertThat(graphicInfoList.size()).isGreaterThanOrEqualTo(2);
        assertThat(graphicInfoList.get(graphicInfoList.size()-1).getCreatedOn().compareTo(graphicInfoList.get(0).getCreatedOn())).isLessThan(0);
    }

    @Test
    public void should_get_catetory_2_graphicInfo_data_when_query_by_category_type_2(){
        PageRequest pageRequest  = new PageRequest(0,2, Sort.Direction.DESC,"createdOn");
        Page<GraphicInfo> page  = graphicInfoDao.findAll(GraphicInfoSpecifications.categoryTypeGraphicInfo(2L),pageRequest);
        List<GraphicInfo> graphicInfoList = page.getContent();
        assertThat(graphicInfoList).isNotNull();
        assertThat(page.getTotalPages()).isEqualTo(1);
        assertThat(graphicInfoList.size()).isGreaterThanOrEqualTo(2);
        for(GraphicInfo graphicInfo:graphicInfoList){
            assertThat(graphicInfo.getCategory().getId()).isEqualTo(2L);
        }
        assertThat(graphicInfoList.get(graphicInfoList.size()-1).getCreatedOn().compareTo(graphicInfoList.get(0).getCreatedOn())).isLessThan(0);
    }
}
