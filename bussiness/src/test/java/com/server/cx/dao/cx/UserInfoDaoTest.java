package com.server.cx.dao.cx;

import static org.fest.assertions.Assertions.assertThat;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.server.cx.data.UserInfoData;
import com.server.cx.entity.cx.UserInfo;

/**
 * User: yanjianzou
 * Date: 12-8-1
 * Time: 下午2:02
 * FileName:UserInfoDaoTest
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles(profiles = {"test"})
public class UserInfoDaoTest extends SpringTransactionalTestCase {
    @Autowired
    private UserInfoDao userInfoDao;
    
    public void should_get_userinfo_when_find_by_imsi(){
        List<String> mobiles = UserInfoData.buildPhoneNos();
        userInfoDao.getUserInfosByPhoneNos(mobiles);
    }
    
    @Test
    public void should_get_userinfos_by_phoneNos() {
        List<String> phoneNos = UserInfoData.buildPhoneNos();
        List<UserInfo> infos = userInfoDao.getUserInfosByPhoneNos(phoneNos);
        assertThat(infos.size()).isEqualTo(2);
        assertThat(infos.get(0).getImsi()).isEqualTo("13146001000");
        assertThat(infos.get(1).getImsi()).isEqualTo("13146001001");
    }
    
    @Test
    public void should_get_userinfo_by_imsi() {
        UserInfo info = userInfoDao.getUserInfoByImsi("13146001000");
        assertThat(info.getId()).isEqualTo("1");
        assertThat(info.getPhoneNo()).isEqualTo("1512581470");
    }
}
