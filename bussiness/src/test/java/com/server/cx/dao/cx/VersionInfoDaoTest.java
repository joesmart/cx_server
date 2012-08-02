package com.server.cx.dao.cx;

import static org.fest.assertions.Assertions.assertThat;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.server.cx.entity.cx.VersionInfo;

@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles(profiles = {"test"})
public class VersionInfoDaoTest extends SpringTransactionalTestCase {
    @Autowired
    private VersionInfoDao versionInfoDao;

    @Test
    public void should_find_all_verioninfo() {
        List<VersionInfo> versionInfos = (List<VersionInfo>) versionInfoDao.findAll();
        assertThat(versionInfos.size()).isGreaterThan(0);
        assertThat(versionInfos.get(0).getVersion()).isEqualTo("3.0.122");
        assertThat(versionInfos.size()).isEqualTo(2);
    }
}
