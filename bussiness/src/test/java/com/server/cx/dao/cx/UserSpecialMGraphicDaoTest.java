package com.server.cx.dao.cx;

import com.server.cx.entity.cx.MGraphic;
import com.server.cx.entity.cx.UserInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: yanjianzou
 * Date: 12-8-7
 * Time: 上午9:51
 * FileName:UserSpecialMGraphicDaoTest
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class UserSpecialMGraphicDaoTest extends SpringTransactionalTestCase {

    @Autowired
    private MGraphicDao mGraphicDao;
    @Autowired
    private UserInfoDao userInfoDao;



    @Test
    public void should_query_by_phoneNos_successful(){
        List<MGraphic> mGraphicList =  mGraphicDao.queryUserMGraphics(null, null, null);
        assertThat(mGraphicList).isNotNull();

    }

    @Test
    public void should_get_max_priority_m_graphic(){
        UserInfo userInfo = userInfoDao.findOne("1");
        int maxPriority =mGraphicDao.queryMaxPriorityByUserInfo(userInfo, "13561341576");
        System.out.println(maxPriority);
    }

    @Test
    public void should_get_maxPriority_MGraphic(){
        UserInfo userInfo = userInfoDao.findOne("2");
        int maxPriority =mGraphicDao.queryMaxPriorityByUserInfo(userInfo, "13561341576");

        List<MGraphic> mGraphics = mGraphicDao.queryUserMGraphics(userInfo,maxPriority, null);
        assertThat(mGraphics).isNotNull();
        assertThat(mGraphics.size()).isGreaterThan(0);
        for(MGraphic mGraphic:mGraphics){
            System.out.println(mGraphic.getClass());
        }
    }
}
