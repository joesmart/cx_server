package com.server.cx;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.server.cx.dao.cx.CategoryDao;
import com.server.cx.dao.cx.GraphicInfoDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.Category;
import com.server.cx.entity.cx.GraphicInfo;
import com.server.cx.entity.cx.GraphicResource;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.util.business.AuditStatus;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang.math.RandomUtils.nextInt;

/**
 * User: yanjianzou
 * Date: 12-8-24
 * Time: 上午11:41
 * FileName:InsertTestData
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles("development")
public class InsertTestData extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private GraphicInfoDao graphicInfoDao;
    @Autowired
    private CategoryDao categoryDao;

    protected DataSource dataSource;

    @Autowired
    protected UserInfoDao userInfoDao;

    @Override
    @Autowired
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
        this.dataSource = dataSource;
    }

    @Test
    @Transactional(readOnly = false)
    @Rollback(value = false)
    public void should_insert_ten_thousands_data() {
        int i = 0;
        while (i++ < 10000) {
            logger.info("i:"+i);
            Long id = new Long(nextInt(5) + 1);
            Category category = categoryDao.findOne(id);
            GraphicInfo graphicInfo = new GraphicInfo();
            graphicInfo.setAuditStatus(AuditStatus.PASSED);
            graphicInfo.setCategory(category);
            graphicInfo.setLevel(6);
            graphicInfo.setName("" + new Date().getTime());
            graphicInfo.setSignature("" + new Date().getTime());
            graphicInfo.setOwner("test");
            graphicInfo.setUseCount(1000);
            graphicInfo = graphicInfoDao.save(graphicInfo);
            GraphicResource graphicResource = new GraphicResource();
            graphicResource.setGraphicInfo(graphicInfo);
            graphicResource.setType("jpg");
            graphicResource.setResourceId("5003a546e4b09ed787af714f");
            graphicResource.setAuditStatus(AuditStatus.PASSED);

            List<GraphicResource> graphicResourceList = graphicInfo.getGraphicResources();
            if (graphicResourceList == null) {
                graphicResourceList = Lists.newArrayList();
            }
            graphicResourceList.add(graphicResource);
            graphicInfo.setGraphicResources(graphicResourceList);
            graphicInfoDao.save(graphicInfo);
        }
    }

    @Autowired
    @Rollback(false)
    @Test
    public void should_insert_ten_thousands_userinfo(){

        for (int i=0 ;i<10000;i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setDeviceId(""+System.currentTimeMillis()+ RandomUtils.nextLong());
            userInfo.setImsi(Objects.hashCode(userInfo.getDeviceId(), RandomStringUtils.randomNumeric(3)) + "");
            userInfo.setUserAgent("test" + RandomStringUtils.randomNumeric(6));
            userInfo.setPhoneNo("" + Objects.hashCode(userInfo.getDeviceId(), userInfo.getUserAgent()));
//            userInfo.setTotleMoney(123D);
            userInfoDao.save(userInfo);
            System.out.println("XXXXX:"+userInfo.toString());
            logger.info("XXXXX:"+userInfo.toString());
        }
    }

}
